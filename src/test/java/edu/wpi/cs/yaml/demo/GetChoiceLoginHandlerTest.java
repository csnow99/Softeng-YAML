package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.http.GetChoiceLoginRequest;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;



public class GetChoiceLoginHandlerTest extends LambdaTest{
	
    @Test
    public void testGetChoice() {
    	GetChoiceLoginHandler getChoiceLoginHandler = new GetChoiceLoginHandler();
    	
    	/*We first need to insert a choice in the database*/
    	ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
    	Alternative alt1 = new Alternative("alt1_name", "alt1_description");
    	Alternative alt2 = new Alternative("alt2_name", "alt2_description");
    	Alternative alt3 = new Alternative("alt3_name", "alt3_description");
    	alternatives.add(alt1);
    	alternatives.add(alt2);
    	alternatives.add(alt3);
        String choiceID = null;

        CreateChoiceHandler createHandler = new CreateChoiceHandler();
        CreateChoiceRequest ccr = new CreateChoiceRequest("testChoice1", 10, "sample description", alternatives);
        
        try {
        CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("createChoice"));
        choiceID = createResp.getResponse();
        
        GetChoiceLoginRequest getRequest1 = new GetChoiceLoginRequest(choiceID, 0);
        /*Now that it's inserted we may */
        if(choiceID == null) {Assert.fail("Created ChoiceID is null");}
        GetChoiceResponse resp1 = getChoiceLoginHandler.handleRequest(getRequest1, createContext("getChoiceTest"));
        if(resp1.getHttpCode() == 404) {Assert.fail("ChoiceID not found");}
        
        Assert.assertEquals(206, resp1.getHttpCode());
        Assert.assertEquals("", resp1.getParticipantName());
        Assert.assertEquals("Succesfully fetched choice", resp1.getResponse());
        
        Choice choice = resp1.getChoice();
        
        Assert.assertEquals("testChoice1", choice.getChoiceName());
        Assert.assertEquals(10, choice.getMaxParticipants());
        Assert.assertEquals("sample description", choice.getChoiceDescription());
        Assert.assertEquals(0, choice.getSelectedAlternativeID());
       
        
        /*Test what happens when getting non-existent choiceID*/
        GetChoiceLoginRequest getRequest2 = new GetChoiceLoginRequest("fakeID", 0);
        GetChoiceResponse resp2 = getChoiceLoginHandler.handleRequest(getRequest2, createContext("getChoiceTest2"));
        Assert.assertEquals(404, resp2.getHttpCode());
        Assert.assertEquals("", resp1.getParticipantName());
        Assert.assertEquals(null, resp2.getChoice());
        Assert.assertEquals("ChoiceID could not be found", resp2.getResponse());
        } catch (Exception e) {
        	Assert.fail();
        }
        
       /*Register a participant to the choice using participantDAO and use getChoiceLoginHandler*/
        
        /*Delete the inserted choice*/
        if (choiceID != null) {
            
        }
    
    }
}
