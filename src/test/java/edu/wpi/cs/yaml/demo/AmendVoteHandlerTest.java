package edu.wpi.cs.yaml.demo;

import com.google.gson.Gson;
import edu.wpi.cs.yaml.demo.db.DeleteDAO;
import edu.wpi.cs.yaml.demo.http.AmendVoteRequest;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;
import edu.wpi.cs.yaml.demo.model.VoteInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
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
}
