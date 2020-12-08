package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.http.AmendVoteRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;
import edu.wpi.cs.yaml.demo.http.RegisterParticipantRequest;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Participant;
import edu.wpi.cs.yaml.demo.model.VoteInfo;

public class GetVotesHandlerTest extends LambdaTest {

	@Test 
	public void testGetVotes() {
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
			CreateChoiceRequest ccr = new CreateChoiceRequest("testGetVotesHandler", 3, "sample description", alternatives);
			CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
			choiceID = createResp.getResponse();

			//We need alternativeID's
			AlternativeDAO altDAO = new AlternativeDAO();
			int alt1ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt1_name");
			int alt2ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt2_name");
			int alt3ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt3_name");


			RegisterParticipantHandler registerHandler = new RegisterParticipantHandler();

			/*Now that it's inserted we can try to create some participants */
			Participant participant1 = new Participant(choiceID, "name1", "password1");
			Participant participant2 = new Participant(choiceID, "name2", "password2");


			RegisterParticipantRequest reg1 = new RegisterParticipantRequest(participant1.getChoiceID(), participant1.getName(), participant1.getPassword());
			RegisterParticipantRequest reg2 = new RegisterParticipantRequest(participant2.getChoiceID(), participant2.getName(), participant1.getPassword());

			if(choiceID == null) {Assert.fail("Created ChoiceID is null");}

			registerHandler.handleRequest(reg1, createContext("register1"));
			registerHandler.handleRequest(reg2, createContext("register2"));

			//We need participant ID's to vote
			ParticipantDAO partDAO = new ParticipantDAO();

			int part1ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "name1");
			int part2ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "name2");


			//We can attempt to place some votes now
			
			AmendVoteHandler amendHandler = new AmendVoteHandler();
			AmendVoteRequest amendRequest1 = new AmendVoteRequest(part1ID, 1, alt1ID);
			AmendVoteRequest amendRequest2 = new AmendVoteRequest(part2ID, 1, alt1ID);
			AmendVoteRequest amendRequest3 = new AmendVoteRequest(part1ID, 0, alt2ID);
			AmendVoteRequest amendRequest4 = new AmendVoteRequest(part2ID, 1, alt2ID);
			AmendVoteRequest amendRequest5 = new AmendVoteRequest(part1ID, 0, alt3ID);
			AmendVoteRequest amendRequest6 = new AmendVoteRequest(part2ID, 0, alt3ID);
			
			amendHandler.handleRequest(amendRequest1, createContext("amend1"));
			amendHandler.handleRequest(amendRequest2, createContext("amend1"));
			amendHandler.handleRequest(amendRequest3, createContext("amend1"));
			amendHandler.handleRequest(amendRequest4, createContext("amend1"));
			amendHandler.handleRequest(amendRequest5, createContext("amend1"));
			amendHandler.handleRequest(amendRequest6, createContext("amend1"));
			
			//Let us now attempt to get back all of the votes
			
			GetVotesHandler getVotesHandler = new GetVotesHandler();
			GetVotesResponse getVotesResponse = getVotesHandler.handleRequest(choiceID, createContext("getVotes"));
			
			List<VoteInfo> voteInfos = getVotesResponse.votes;
			
			VoteInfo alt1Info = voteInfos.get(0);
			VoteInfo alt2Info = voteInfos.get(1);
			VoteInfo alt3Info = voteInfos.get(2);
			
			Assert.assertEquals(alt1ID, alt1Info.getAlternativeID());
			Assert.assertEquals(alt2ID, alt2Info.getAlternativeID());
			Assert.assertEquals(alt3ID, alt3Info.getAlternativeID());
			
			Assert.assertEquals(alt1.getTitle(), alt1Info.getAlternativeName());
			Assert.assertEquals(alt2.getTitle(), alt2Info.getAlternativeName());
			Assert.assertEquals(alt3.getTitle(), alt3Info.getAlternativeName());
			
			Assert.assertEquals(2, alt1Info.getNumUpvotes());
			Assert.assertEquals(1, alt2Info.getNumUpvotes());
			Assert.assertEquals(0, alt3Info.getNumUpvotes());
			
			Assert.assertEquals(0, alt1Info.getNumDownvotes());
			Assert.assertEquals(1, alt2Info.getNumDownvotes());
			Assert.assertEquals(2, alt3Info.getNumDownvotes());
			
			Assert.assertEquals(2, alt1Info.getUpvoters().size());
			Assert.assertEquals(1, alt2Info.getUpvoters().size());
			Assert.assertEquals(0, alt3Info.getUpvoters().size());
			
			Assert.assertEquals(0, alt1Info.getdownvoters().size());
			Assert.assertEquals(1, alt2Info.getdownvoters().size());
			Assert.assertEquals(2, alt3Info.getdownvoters().size());
			

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

}
