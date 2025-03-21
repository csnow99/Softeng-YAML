package edu.wpi.cs.yaml.demo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.http.CompleteChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;
import edu.wpi.cs.yaml.demo.http.RegisterParticipantRequest;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.model.Participant;

public class CompleteChoiceHandlerTest extends LambdaTest{
	@Test
	public void testCompleteChoice() {
		String choiceID = null;
		ChoiceDAO choiceDAO = new ChoiceDAO();
		AlternativeDAO altDAO = new AlternativeDAO();
		try {
			/*We first need to insert a choice in the database*/
			ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
			Alternative alt1 = new Alternative("alt1_name", "alt1_description");
			Alternative alt2 = new Alternative("alt2_name", "alt2_description");
			Alternative alt3 = new Alternative("alt3_name", "alt3_description");
			alternatives.add(alt1);
			alternatives.add(alt2);
			alternatives.add(alt3);

			CreateChoiceHandler createHandler = new CreateChoiceHandler();
			CreateChoiceRequest ccr = new CreateChoiceRequest("testCompleteChoice", 3, "sample description", alternatives);
			CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
			choiceID = createResp.getResponse();
			if(choiceID == null) {Assert.fail("Created ChoiceID is null");}
			/*we need altID*/
			int alt1ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt1_name");
			/*Now that it's inserted we can try to register the creator */

			RegisterParticipantHandler registerHandler = new RegisterParticipantHandler();
			Participant participant1 = new Participant(choiceID, "creator", "password1");
			RegisterParticipantRequest reg1 = new RegisterParticipantRequest(participant1.getChoiceID(), participant1.getName(), participant1.getPassword());
			registerHandler.handleRequest(reg1, createContext("register1"));

			//We need creator ID to vote
			ParticipantDAO partDAO = new ParticipantDAO();
			int part1ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "creator");

			/*make sure the choice is initially not completed*/
			Choice initialChoice = choiceDAO.getChoice(choiceID);
			Assert.assertFalse(initialChoice.getIsCompleted());
			Assert.assertEquals(0, initialChoice.getDateCompleted());


			/*Next complete the choice*/
			CompleteChoiceRequest completeReq = new CompleteChoiceRequest(choiceID, alt1ID, part1ID);
			CompleteChoiceHandler completeHandler = new CompleteChoiceHandler();
			GetChoiceResponse completeResp = completeHandler.handleRequest(completeReq, createContext("complete"));
			/*Assert the choice is completed now*/
			Choice finalChoice = choiceDAO.getChoice(choiceID);
			Assert.assertEquals(200, completeResp.getHttpCode());
			Assert.assertEquals(true, finalChoice.getIsCompleted());
			Assert.assertNotEquals(0, finalChoice.getDateCompleted());
			Assert.assertEquals(alt1ID, finalChoice.getSelectedAlternativeID());

			/*make sure the creator cannot complete the choiceAgain*/
			GetChoiceResponse completeResp2 = completeHandler.handleRequest(completeReq, createContext("complete"));
			/*Assert the httpCode and response */
			Assert.assertEquals(403, completeResp2.getHttpCode());
			Assert.assertEquals("Choice has already been completed", completeResp2.getResponse());

			choiceDAO.deleteChoice(choiceID);


		} catch (Exception e) {
			try {
				choiceDAO.deleteChoice(choiceID);	//delete the choice automatically if the test fails anywhere
			} catch (Exception e2) {
				Assert.fail();
			}
			Assert.fail();
		}

	}

	@Test
	public void testCompleteChoiceEdgeCases() {
		ChoiceDAO choiceDAO = new ChoiceDAO();
		AlternativeDAO altDAO = new AlternativeDAO();
		ParticipantDAO partDAO = new ParticipantDAO();
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
		CreateChoiceRequest ccr = new CreateChoiceRequest("testChompleteChoice2", 3, "sample description", alternatives);
		CreateChoiceRequest ccr2 = new CreateChoiceRequest("testChompleteChoice3", 3, "sample description", alternatives);
		try {
			CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
			CreateChoiceResponse createResp2 = createHandler.handleRequest(ccr2, createContext("create"));
			choiceID = createResp.getResponse();
			choiceID2 = createResp2.getResponse();
			if(choiceID == null) {Assert.fail("Created ChoiceID is null");}
			int alt1ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt1_name");
			int alt2ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID2, "alt1_name");




			/*Now that it's inserted we can try to register the first user to both choices */

			RegisterParticipantHandler registerHandler = new RegisterParticipantHandler();

			Participant participant1 = new Participant(choiceID, "creator", "password1");
			RegisterParticipantRequest reg1 = new RegisterParticipantRequest(participant1.getChoiceID(), participant1.getName(), participant1.getPassword());
			registerHandler.handleRequest(reg1, createContext("register1"));
			int part1ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "creator");

			Participant participant2 = new Participant(choiceID2, "creator", "password1");
			RegisterParticipantRequest reg2 = new RegisterParticipantRequest(participant2.getChoiceID(), participant2.getName(), participant2.getPassword());
			registerHandler.handleRequest(reg2, createContext("register2"));
			int part2ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID2, "creator");

			CompleteChoiceHandler completeHandler = new CompleteChoiceHandler();
			CompleteChoiceRequest completeRequest1 = new CompleteChoiceRequest(choiceID, alt1ID, part2ID);
			CompleteChoiceRequest completeRequest2 = new CompleteChoiceRequest(choiceID2, alt2ID, part1ID);

			GetChoiceResponse completeResponse1 = completeHandler.handleRequest(completeRequest1,  createContext("improper Amend1"));
			GetChoiceResponse completeResponse2 = completeHandler.handleRequest(completeRequest2,  createContext("improper Amend2"));

			Assert.assertEquals(403, completeResponse1.getHttpCode());
			Assert.assertEquals("ParticipantID not associated with choiceID", completeResponse1.getResponse());
			Assert.assertEquals(null, completeResponse1.getChoice());

			Assert.assertEquals(403, completeResponse2.getHttpCode());
			Assert.assertEquals("ParticipantID not associated with choiceID", completeResponse2.getResponse());
			Assert.assertEquals(null, completeResponse1.getChoice());

			choiceDAO.deleteChoice(choiceID);
			choiceDAO.deleteChoice(choiceID2);


		} catch (Exception e) {
			try {
				choiceDAO.deleteChoice(choiceID);	//delete the choice automatically if the test fails anywhere
				choiceDAO.deleteChoice(choiceID2);
			} catch (Exception e2) {
				Assert.fail();
			}
			Assert.fail();
		}

	}
}
