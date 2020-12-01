package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.http.RegisterParticipantRequest;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Participant;

public class GetVotesHandlerTest extends LambdaTest {
	
	@Test 
	public void testGetVotes() {
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


		RegisterParticipantRequest reg1 = new RegisterParticipantRequest(participant1.choiceID, participant1.username, participant1.password);
		RegisterParticipantRequest reg2 = new RegisterParticipantRequest(participant2.choiceID, participant2.username, participant1.password);


		if(choiceID == null) {Assert.fail("Created ChoiceID is null");}

		Assert.assertEquals(200, handler.handleRequest(reg1, createContext("register1")).httpCode);
		Assert.assertEquals(200, handler.handleRequest(reg2, createContext("register2")).httpCode);

		/*Delete the inserted choice*/
		if (choiceID != null) {
			DeleteSingleChoiceByIDRequest dcr = new DeleteSingleChoiceByIDRequest(choiceID);
			DeleteSingleChoiceByIDResponse d_resp = new DeleteSingleChoiceByIDChoiceHandler().handleRequest(dcr, createContext("delete"));
			assertEquals("Succesfully deleted: "+choiceID, d_resp.response);
		}
	
	}

}
