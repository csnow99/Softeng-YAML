package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;



public class GetChoiceHandlerTest extends LambdaTest{
	
    @Test
    public void testGetChoice() {
    	GetChoiceHandler handler = new GetChoiceHandler();
    	
    	/*We first need to insert a choice in the database*/
    	ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
    	Alternative alt1 = new Alternative("alt1_ID", "alt1_name", "alt1_description");
    	Alternative alt2 = new Alternative("alt2_ID", "alt2_name", "alt2_description");
    	Alternative alt3 = new Alternative("alt3_ID", "alt3_name", "alt3_description");
    	alternatives.add(alt1);
    	alternatives.add(alt2);
    	alternatives.add(alt3);
        String choiceID = null;

        CreateChoiceHandler createHandler = new CreateChoiceHandler();
        CreateChoiceRequest ccr = new CreateChoiceRequest("testChoice1", 10, "sample description", alternatives);
        CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
        choiceID = createResp.response;
      
        
        /*Now that it's inserted we may */
        if(choiceID == null) {Assert.fail("Created ChoiceID is null");}
        Choice resp = handler.handleRequest(choiceID, createContext("list"));
        if(resp == null) {Assert.fail("Found Choice is null");}
        
        Assert.assertTrue(resp.choiceName.equals("testChoice1"));
        Assert.assertTrue(resp.maxParticipants == 10);
        Assert.assertTrue(resp.choiceDescription.equals("sample description"));
        Assert.assertTrue(resp.selectedAlternativeID == null);
        
        
        /*Delete the inserted choice*/
        if (choiceID != null) {
            DeleteSingleChoiceByIDRequest dcr = new DeleteSingleChoiceByIDRequest(choiceID);
            DeleteSingleChoiceByIDResponse d_resp = new DeleteSingleChoiceByIDChoiceHandler().handleRequest(dcr, createContext("delete"));
            assertEquals("Succesfully deleted: "+choiceID, d_resp.response);
        }
    
    }
}
