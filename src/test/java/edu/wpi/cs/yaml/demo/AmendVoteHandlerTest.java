package edu.wpi.cs.yaml.demo;

import com.google.gson.Gson;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.DeleteDAO;
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
import static org.junit.Assert.fail;

import java.io.IOException;


public class AmendVoteHandlerTest extends LambdaTest {

    /*
        choiceID = 134506584412165061945382715807948142150
        Alternatives ID = 32 (UpVote)  33 (DownVote)  34 (BothCombined)
        Participant 1 ID = 18 (Bill)  19 (Bill2)
        VoteTypes: 1 for Upvote 0 for DownVote
     */

    void testInput(String incoming, List<VoteInfo> result) throws IOException {
        try {
            deleteFromDB();
        } catch (Exception e) {
            Assert.fail("Could not delete from Votes: " + e.getMessage());
        }

        AmendVoteRequest req = new Gson().fromJson(incoming, AmendVoteRequest.class);
        AmendVoteHandler handler = new AmendVoteHandler();
        GetVotesResponse response = handler.handleRequest(req, createContext("vote"));

        Assert.assertEquals(200, response.httpCode);
        Assert.assertEquals("Successfully amended a Vote", response.response);
        Assert.assertEquals(result, response.votes);
    }
    void testTwoInput(String incoming, String incoming2,
                      List<VoteInfo> result, List<VoteInfo> result2) throws IOException {
        try {
            deleteFromDB();
        } catch (Exception e) {
            Assert.fail("Could not delete from Votes: " + e.getMessage());
        }

        AmendVoteRequest req = new Gson().fromJson(incoming, AmendVoteRequest.class);
        AmendVoteHandler handler = new AmendVoteHandler();
        GetVotesResponse response = handler.handleRequest(req, createContext("vote"));

        Assert.assertEquals(200, response.httpCode);
        Assert.assertEquals("Successfully amended a Vote", response.response);
        Assert.assertEquals(result, response.votes);

        AmendVoteRequest req2 = new Gson().fromJson(incoming2, AmendVoteRequest.class);
        AmendVoteHandler handler2 = new AmendVoteHandler();
        GetVotesResponse response2 = handler2.handleRequest(req2, createContext("vote"));

        Assert.assertEquals(200, response.httpCode);
        Assert.assertEquals("Successfully amended a Vote", response.response);
        Assert.assertEquals(result2, response2.votes);

    }

    void deleteFromDB() throws Exception {
        DeleteDAO deleteDAO = new DeleteDAO();
        deleteDAO.deleteVotes();
    }

    @Test
    public void upVoteTest() {
        String voteInput = "{\n" +
                "  \"alternativeID\": 32,\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 1\n" +
                "}";

        List<String> namesUp1 = new ArrayList<>();
        namesUp1.add("Bill");

        List<String> emptyList = new ArrayList<>();

        VoteInfo vi1 = new VoteInfo(32, "UpVote",
                1, 0, namesUp1, emptyList);
        VoteInfo vi2 = new VoteInfo(33, "DownVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi3 = new VoteInfo(34, "BothCombined",
                0, 0, emptyList, emptyList);

        List<VoteInfo> allVotes = new ArrayList<>();
        allVotes.add(vi1);
        allVotes.add(vi2);
        allVotes.add(vi3);

        try {
            testInput(voteInput, allVotes);
        } catch (IOException ioe) {
            Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void downVoteTest() {
        String voteInput = "{\n" +
                "  \"alternativeID\": 33,\n" +
                "  \"participantID\": 19,\n" +
                "  \"amendType\": 0\n" +
                "}";

        List<String> names = new ArrayList<>();
        names.add("Bill2");

        List<String> emptyList = new ArrayList<>();

        VoteInfo vi1 = new VoteInfo(32, "UpVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi2 = new VoteInfo(33, "DownVote",
                0, 1, emptyList, names);
        VoteInfo vi3 = new VoteInfo(34, "BothCombined",
                0, 0, emptyList, emptyList);

        List<VoteInfo> allVotes = new ArrayList<>();
        allVotes.add(vi1);
        allVotes.add(vi2);
        allVotes.add(vi3);

        try {
            testInput(voteInput, allVotes);
        } catch (IOException ioe) {
            Assert.fail("Invalid:" + ioe.getMessage());
        }

    }

    @Test
    public void deleteVoteTest() {
        String voteInput = "{\n" +
                "  \"alternativeID\": 34,\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 0\n" +
                "}";

        List<String> names = new ArrayList<>();
        names.add("Bill");

        List<String> emptyList = new ArrayList<>();

        VoteInfo vi1 = new VoteInfo(32, "UpVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi2 = new VoteInfo(33, "DownVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi3 = new VoteInfo(34, "BothCombined",
                0, 1, emptyList, names);

        List<VoteInfo> allVotes = new ArrayList<>();
        allVotes.add(vi1);
        allVotes.add(vi2);
        allVotes.add(vi3);

        VoteInfo vi4 = new VoteInfo(32, "UpVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi5 = new VoteInfo(33, "DownVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi6 = new VoteInfo(34, "BothCombined",
                0, 0, emptyList, emptyList);

        List<VoteInfo> allVotes2 = new ArrayList<>();
        allVotes2.add(vi4);
        allVotes2.add(vi5);
        allVotes2.add(vi6);

        try {
            testTwoInput(voteInput, voteInput, allVotes, allVotes2);
        } catch (IOException ioe) {
            Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void changeVoteTest() {
        String voteInput = "{\n" +
                "  \"alternativeID\": 34,\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 0\n" +
                "}";
        String voteInput2 = "{\n" +
                "  \"alternativeID\": 34,\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 1\n" +
                "}";

        List<String> names = new ArrayList<>();
        names.add("Bill");

        List<String> emptyList = new ArrayList<>();

        VoteInfo vi1 = new VoteInfo(32, "UpVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi2 = new VoteInfo(33, "DownVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi3 = new VoteInfo(34, "BothCombined",
                0, 1, emptyList, names);

        List<VoteInfo> allVotes = new ArrayList<>();
        allVotes.add(vi1);
        allVotes.add(vi2);
        allVotes.add(vi3);

        VoteInfo vi4 = new VoteInfo(32, "UpVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi5 = new VoteInfo(33, "DownVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi6 = new VoteInfo(34, "BothCombined",
                1, 0, names, emptyList);

        List<VoteInfo> allVotes2 = new ArrayList<>();
        allVotes2.add(vi4);
        allVotes2.add(vi5);
        allVotes2.add(vi6);

        try {
            testTwoInput(voteInput, voteInput2, allVotes, allVotes2);
        } catch (IOException ioe) {
            Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
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
			int alt2ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt2_name");
			int alt3ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt3_name");

			/*Now that it's inserted we can try to register the creator */

			RegisterParticipantHandler registerHandler = new RegisterParticipantHandler();
			Participant participant1 = new Participant(choiceID, "creator", "password1");
			RegisterParticipantRequest reg1 = new RegisterParticipantRequest(participant1.choiceID, participant1.username, participant1.password);
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
			RegisterParticipantRequest reg2 = new RegisterParticipantRequest(participant2.choiceID, participant2.username, participant1.password);
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
