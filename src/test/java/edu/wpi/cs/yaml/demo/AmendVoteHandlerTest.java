package edu.wpi.cs.yaml.demo;


import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.db.VoteDAO;
import edu.wpi.cs.yaml.demo.http.AmendVoteRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;
import edu.wpi.cs.yaml.demo.http.RegisterParticipantRequest;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.model.Participant;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AmendVoteHandlerTest extends LambdaTest {

	@Test
	public void testHeinemanIteration2() {
		try {
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
			CreateChoiceRequest ccr = new CreateChoiceRequest("testAmendVote", 3, "sample description", alternatives);
			CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
			choiceID = createResp.getResponse();
			if(choiceID == null) {Assert.fail("Created ChoiceID is null");}

			//We need alternativeID's
			AlternativeDAO altDAO = new AlternativeDAO();
			int alt1ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt1_name");

			/*Now that it's inserted we can try to register the creator */

			RegisterParticipantHandler registerHandler = new RegisterParticipantHandler();
			Participant participant1 = new Participant(choiceID, "creator", "password1");
			RegisterParticipantRequest reg1 = new RegisterParticipantRequest(participant1.getChoiceID(), participant1.getName(), participant1.getPassword());
			registerHandler.handleRequest(reg1, createContext("register1"));

			//We need creator ID to vote
			ParticipantDAO partDAO = new ParticipantDAO();
			int part1ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "creator");

			//We can attempt to place a vote on the first alt now
			AmendVoteHandler amendHandler = new AmendVoteHandler();
			AmendVoteRequest amendRequest1 = new AmendVoteRequest(part1ID, 1, alt1ID);
			amendHandler.handleRequest(amendRequest1, createContext("amend1"));

			//Check if the vote is there
			VoteDAO voteDAO = new VoteDAO();
			try {assertEquals(1, voteDAO.getVote(alt1ID, part1ID).getAmendType());
			} catch (Exception e) {Assert.fail();}	//if the vote doesn't exist then fail

			//Register participant 2
			Participant participant2 = new Participant(choiceID, "name2", "password2");
			RegisterParticipantRequest reg2 = new RegisterParticipantRequest(participant2.getChoiceID(), participant2.getName(), participant1.getPassword());
			registerHandler.handleRequest(reg2, createContext("register2"));
			int part2ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "name2");

			//Participant 2 downvotes the same alternative
			AmendVoteRequest amendRequest2 = new AmendVoteRequest(part2ID, 0, alt1ID);
			amendHandler.handleRequest(amendRequest2, createContext("amend2"));
			try {assertEquals(0, voteDAO.getVote(alt1ID, part2ID).getAmendType());
			} catch (Exception e) {Assert.fail();}	//if the vote doesn't exist then fail


			/*Delete the inserted choice*/
			if (choiceID != null) {
				DeleteSingleChoiceByIDRequest dcr = new DeleteSingleChoiceByIDRequest(choiceID);
				DeleteSingleChoiceByIDResponse d_resp = new DeleteSingleChoiceByIDChoiceHandler().handleRequest(dcr, createContext("delete"));
				assertEquals("Succesfully deleted: "+choiceID, d_resp.getResponse());
			}

		}catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testAmendVoteEdgeCases() {
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
		CreateChoiceRequest ccr = new CreateChoiceRequest("testAmendVote2", 3, "sample description", alternatives);
		CreateChoiceRequest ccr2 = new CreateChoiceRequest("testAmendVote3", 3, "sample description", alternatives);
		try {
			CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
			CreateChoiceResponse createResp2 = createHandler.handleRequest(ccr2, createContext("create"));
			choiceID = createResp.getResponse();
			choiceID2 = createResp2.getResponse();
			if(choiceID == null) {Assert.fail("Created ChoiceID is null");}

			//We need alternativeID's
			int alt1ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt1_name");
			int alt1ID2 = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID2, "alt1_name");

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

			AmendVoteHandler amendHandler = new AmendVoteHandler();
			AmendVoteRequest amendRequest1 = new AmendVoteRequest(part2ID,1, alt1ID);
			AmendVoteRequest amendRequest2 = new AmendVoteRequest(part1ID, 1, alt1ID2);

			GetVotesResponse amendResponse1 = amendHandler.handleRequest(amendRequest1,  createContext("improper Amend1"));
			GetVotesResponse amendResponse2 = amendHandler.handleRequest(amendRequest2,  createContext("improper Amend2"));
			
			Assert.assertEquals(403, amendResponse1.getHttpCode());
			Assert.assertEquals("ParticipantID not associated with choiceID", amendResponse1.getResponse());
			
			Assert.assertEquals(403, amendResponse2.getHttpCode());
			Assert.assertEquals("ParticipantID not associated with choiceID", amendResponse2.getResponse());
		
			choiceDAO.deleteChoice(choiceID);
			choiceDAO.deleteChoice(choiceID2);
			
			/*Try amending a choice that has been completed*/
			Choice choice1 = new Choice("001", "ChoiceDAOTest1",  5, "ChoiceDAOTest1Description", 1507019912630l, true, 0, 0);
			choiceDAO.addChoice(choice1);
			Alternative alt4 = new Alternative("001", "alt1_name", "alt1_description");
			altDAO.addAlternative(alt4);
			Participant participant3 = new Participant("001", "PartName1", "PartPass1");
			RegisterParticipantRequest reg3 = new RegisterParticipantRequest(participant3.getChoiceID(), participant3.getName(), participant3.getPassword());
			registerHandler.handleRequest(reg3, createContext("register3"));
			
			
			int altID = altDAO.getAlternativeIDFromChoiceIDandTitle("001", "alt1_name");
			int partID = partDAO.getParticipantIDFromChoiceIDAndParticipantName("001", "PartName1");
			
			AmendVoteRequest amendRequest3 = new AmendVoteRequest(partID, 1,altID);
			GetVotesResponse amendResponse3 = amendHandler.handleRequest(amendRequest3,  createContext("improper Amend3"));

			Assert.assertEquals(403, amendResponse3.getHttpCode());
			Assert.assertEquals("Choice has been completed", amendResponse3.getResponse());
			
			
			choiceDAO.deleteChoice("001");
			

		} catch (Exception e) {
			Assert.fail();
		}

	}
}
