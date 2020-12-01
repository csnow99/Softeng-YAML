package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
//import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.http.RegisterParticipantRequest;
import edu.wpi.cs.yaml.demo.http.RegisterParticipantResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Participant;

public class RegisterParticipantHandlerTest extends LambdaTest{
	@Test
    public void testRegisterParticipant() {
	RegisterParticipantHandler handler = new RegisterParticipantHandler();
	
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
    CreateChoiceRequest ccr = new CreateChoiceRequest("testChoiceRegisterParticipant", 3, "sample description", alternatives);
    CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
    choiceID = createResp.response;
  
    
    /*Now that it's inserted we can try to create some participants */
    Participant participant1 = new Participant(choiceID, "name1", "password1");
    Participant participant2 = new Participant(choiceID, "name2", "password2");
    Participant participant3 = new Participant(choiceID, "name3", "password3");
    Participant participant4 = new Participant(choiceID, "name4", "password4");
    Participant loginParticipant1 = new Participant(choiceID, "name1", "password1");
    Participant incorrectPasswordParticipant1 = new Participant(choiceID, "name1", "password2");
    
    RegisterParticipantRequest reg1 = new RegisterParticipantRequest(participant1.choiceID, participant1.username, participant1.password);
    RegisterParticipantRequest reg2 = new RegisterParticipantRequest(participant2.choiceID, participant2.username, participant1.password);
    RegisterParticipantRequest reg3 = new RegisterParticipantRequest(loginParticipant1.choiceID, loginParticipant1.username, loginParticipant1.password);
    RegisterParticipantRequest reg4 = new RegisterParticipantRequest(incorrectPasswordParticipant1.choiceID, incorrectPasswordParticipant1.username, incorrectPasswordParticipant1.password);
    RegisterParticipantRequest reg5 = new RegisterParticipantRequest(participant3.choiceID, participant3.username, participant1.password);
    RegisterParticipantRequest reg6 = new RegisterParticipantRequest(participant4.choiceID, participant4.username, participant1.password);

    if(choiceID == null) {Assert.fail("Created ChoiceID is null");}
    
    RegisterParticipantResponse resp1 = handler.handleRequest(reg1, createContext("register1"));
    RegisterParticipantResponse resp2 = handler.handleRequest(reg2, createContext("register2"));
    RegisterParticipantResponse resp3 = handler.handleRequest(reg3, createContext("login1"));
    RegisterParticipantResponse resp4 = handler.handleRequest(reg4, createContext("falsePassword1"));
    RegisterParticipantResponse resp5 = handler.handleRequest(reg5, createContext("register3"));
    RegisterParticipantResponse resp6 = handler.handleRequest(reg6, createContext("maxReached4"));
    
    Assert.assertEquals(201, resp1.httpCode);			//register 201
    Assert.assertEquals(201, resp2.httpCode);			//register 201
    Assert.assertEquals(200, resp3.httpCode);			//login 200
    Assert.assertEquals(401, resp4.httpCode);			//wrong password 401
    Assert.assertEquals(201, resp5.httpCode);			//register 201
    Assert.assertEquals(403, resp6.httpCode);			//too many 403
    
    ParticipantDAO participantDAO = new ParticipantDAO();
    try {
    Assert.assertEquals(participantDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "name1"), resp1.getParticipantID());	//
    Assert.assertEquals(participantDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "name2"), resp2.getParticipantID());			//
    Assert.assertEquals(participantDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "name1"), resp3.getParticipantID());			//
    Assert.assertEquals(0, resp4.getParticipantID());			//
    Assert.assertEquals(participantDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "name3"), resp5.getParticipantID());			//
    Assert.assertEquals(0, resp6.getParticipantID());
    } catch (Exception e) {
    	Assert.fail();
    }
    
    /*Delete the inserted choice*/
    if (choiceID != null) {
        DeleteSingleChoiceByIDRequest dcr = new DeleteSingleChoiceByIDRequest(choiceID);
        DeleteSingleChoiceByIDResponse d_resp = new DeleteSingleChoiceByIDChoiceHandler().handleRequest(dcr, createContext("delete"));
        assertEquals("Succesfully deleted: "+choiceID, d_resp.response);
    }


	}
}
