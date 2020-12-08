package edu.wpi.cs.yaml.demo;


import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.GetChoiceLoginRequest;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.model.Participant;



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
        String choiceID2 = null;
        CreateChoiceHandler createHandler = new CreateChoiceHandler();
        CreateChoiceRequest createRequest1 = new CreateChoiceRequest("testChoice1", 10, "sample description", alternatives);
        CreateChoiceRequest createRequest2 = new CreateChoiceRequest("testChoice2", 5, "sample description", alternatives);
        
        try {
        CreateChoiceResponse createResp = createHandler.handleRequest(createRequest1, createContext("createChoice"));
        choiceID = createResp.getResponse();
        
        /*Now that it's inserted we may */
        GetChoiceLoginRequest getRequest1 = new GetChoiceLoginRequest(choiceID, 0);
        GetChoiceResponse resp1 = getChoiceLoginHandler.handleRequest(getRequest1, createContext("getChoiceTest"));
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
        
        
        /*Register participants to 2 different choices and test getChoiceLoginHandler*/
        ParticipantDAO partDAO = new ParticipantDAO();
        CreateChoiceResponse createResp2 = createHandler.handleRequest(createRequest2, createContext("createChoice"));
        choiceID2 = createResp2.getResponse();
        
        Participant part1 = new Participant(choiceID, "PartName1", "PartPass1");
		Participant part2 = new Participant(choiceID2, "PartName2", "PartPass2");
		
		partDAO.addParticipant(part1);
		partDAO.addParticipant(part2);
		
		GetChoiceLoginRequest getRequest3 = new GetChoiceLoginRequest(choiceID, partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "PartName1"));
        GetChoiceResponse resp3 = getChoiceLoginHandler.handleRequest(getRequest3, createContext("getChoiceTest3"));
        Assert.assertEquals(200, resp3.getHttpCode());
        Assert.assertEquals("PartName1", resp3.getParticipantName());
        Assert.assertEquals("Login successful", resp3.getResponse());
        
        Choice choice3 = resp1.getChoice();
        
        Assert.assertEquals("testChoice1", choice3.getChoiceName());
        Assert.assertEquals(10, choice3.getMaxParticipants());
        Assert.assertEquals("sample description", choice3.getChoiceDescription());
        Assert.assertEquals(0, choice3.getSelectedAlternativeID());
       
        /*See what happens if we use part2 ID with choice1ID*/
        GetChoiceLoginRequest getRequest4 = new GetChoiceLoginRequest(choiceID, partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID2, "PartName2"));
        GetChoiceResponse resp4 = getChoiceLoginHandler.handleRequest(getRequest4, createContext("getChoiceTest4"));
        Assert.assertEquals(404, resp4.getHttpCode());
        Assert.assertEquals("", resp4.getParticipantName());
        Assert.assertEquals(null, resp4.getChoice());
        Assert.assertEquals("ChoiceID could not be found", resp2.getResponse());
		
        
        } catch (Exception e) {
         }
        
        /*Delete the choices*/
        try {
        	ChoiceDAO choiceDAO = new ChoiceDAO();
        	choiceDAO.deleteChoice(choiceID);
        	choiceDAO.deleteChoice(choiceID2);
        } catch (Exception e) {
        	Assert.fail();
        }
    }
}
