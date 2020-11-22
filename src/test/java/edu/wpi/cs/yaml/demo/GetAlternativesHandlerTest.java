package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;



public class GetAlternativesHandlerTest extends LambdaTest{
	
    @Test
    public void testGetAlternatives()  {
    	GetAlternativesHandler handler = new GetAlternativesHandler();
    	
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
        List<Alternative> resp = handler.handleRequest(choiceID, createContext("list"));
        if(resp == null) {Assert.fail("Found Alternatives are null");}
        
        Assert.assertEquals(3, resp.size());
        
        Assert.assertTrue(resp.get(0).choiceID.equals(choiceID));
        Assert.assertTrue(resp.get(0).title.equals("alt1_name"));
        Assert.assertTrue(resp.get(0).description.equals("alt1_description"));
        
        Assert.assertTrue(resp.get(1).choiceID.equals(choiceID));
        Assert.assertTrue(resp.get(1).title.equals("alt2_name"));
        Assert.assertTrue(resp.get(1).description.equals("alt2_description"));
        
        Assert.assertTrue(resp.get(2).choiceID.equals(choiceID));
        Assert.assertTrue(resp.get(2).title.equals("alt3_name"));
        Assert.assertTrue(resp.get(2).description.equals("alt3_description"));
        
        
        /*Delete the inserted choice*/
        if (choiceID != null) {
            DeleteSingleChoiceByIDRequest dcr = new DeleteSingleChoiceByIDRequest(choiceID);
            DeleteSingleChoiceByIDResponse d_resp = new DeleteSingleChoiceByIDChoiceHandler().handleRequest(dcr, createContext("delete"));
            assertEquals("Succesfully deleted: "+choiceID, d_resp.response);
        }
    
    }
}