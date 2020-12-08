package edu.wpi.cs.yaml.demo;

import com.google.gson.Gson;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
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
import edu.wpi.cs.yaml.demo.model.Participant;
import edu.wpi.cs.yaml.demo.model.VoteInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import java.io.IOException;


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
			choiceID = createResp.response;
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
				assertEquals("Succesfully deleted: "+choiceID, d_resp.response);
			}

		}catch (Exception e) {
			Assert.fail();
		}
	}
    
}
