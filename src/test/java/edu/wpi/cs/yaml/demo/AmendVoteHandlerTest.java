package edu.wpi.cs.yaml.demo;

import com.google.gson.Gson;
import edu.wpi.cs.yaml.demo.http.AmendVoteRequest;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;
import edu.wpi.cs.yaml.demo.model.VoteInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AmendVoteHandlerTest extends LambdaTest {

    /*
        choiceID = 134506584412165061945382715807948142150
        Alternatives ID = 32 (UpVote)  33 (DownVote)  34 (BothCombined)
        Participant 1 ID = 18 (Bill)  19 (Bill2)
        VoteTypes: 1 for Upvote 0 for DownVote
     */

    @Test
    public void testUpVote() {

        String voteInput1 = "{\n" +
                "  \"alternativeID\": 32,\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 1\n" +
                "}";
        String voteInput2 = "{\n" +
                "  \"alternativeID\": 32,\n" +
                "  \"participantID\": 19,\n" +
                "  \"amendType\": 1\n" +
                "}";
        String JSONString1 = new Gson().toJson(voteInput1);
        String JSONString2 = new Gson().toJson(voteInput2);

        AmendVoteRequest req1 = new Gson().fromJson(JSONString1, AmendVoteRequest.class);
        AmendVoteRequest req2 = new Gson().fromJson(JSONString2, AmendVoteRequest.class);

        AmendVoteHandler avh = new AmendVoteHandler();

        GetVotesResponse response1 = avh.handleRequest(req1, createContext("vote"));
        GetVotesResponse response2 = avh.handleRequest(req2, createContext("vote"));

        List<String> namesUp1 = new ArrayList<>();
        namesUp1.add("Bill");
        namesUp1.add("Bill2");

        List<String> namesDown1 = new ArrayList<>();

        List<String> emptyList = new ArrayList<>();

        VoteInfo vi1 = new VoteInfo(32, "UpVote",
                2, 0, namesUp1, namesDown1);
        VoteInfo vi2 = new VoteInfo(33, "DownVote",
                0, 0, emptyList, emptyList);
        VoteInfo vi3 = new VoteInfo(34, "BothCombined",
                0, 0, emptyList, emptyList);

        List<VoteInfo> allVotes = new ArrayList<>();
        allVotes.add(vi1);
        allVotes.add(vi2);
        allVotes.add(vi3);

        Assert.assertEquals(200, response1.httpCode);
        Assert.assertEquals(200, response2.httpCode);
        Assert.assertEquals(allVotes, response2.votes);
    }

    @Test
    public void testDownVote() {

        String voteInput1 = "{\n" +
                "  \"alternativeID\": 33,\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 0\n" +
                "}";
        String voteInput2 = "{\n" +
                "  \"alternativeID\": 33,\n" +
                "  \"participantID\": 19,\n" +
                "  \"amendType\": 0\n" +
                "}";

        String JSONString1 = new Gson().toJson(voteInput1);
        String JSONString2 = new Gson().toJson(voteInput2);

        AmendVoteRequest req1 = new Gson().fromJson(JSONString1, AmendVoteRequest.class);
        AmendVoteRequest req2 = new Gson().fromJson(JSONString2, AmendVoteRequest.class);

        AmendVoteHandler avh = new AmendVoteHandler();

        GetVotesResponse response1 = avh.handleRequest(req1, createContext("vote"));
        GetVotesResponse response2 = avh.handleRequest(req2, createContext("vote"));

        List<String> namesUp1 = new ArrayList<>();
        namesUp1.add("Bill");
        namesUp1.add("Bill2");
        List<String> namesDown1 = new ArrayList<>();
        List<String> namesUp2 = new ArrayList<>();
        List<String> namesDown2 = new ArrayList<>();
        namesDown2.add("Bill");
        namesDown2.add("Bill2");
        List<String> emptyList = new ArrayList<>();

        VoteInfo vi1 = new VoteInfo(32, "UpVote",
                2, 0, namesUp1, namesDown1);
        VoteInfo vi2 = new VoteInfo(33, "DownVote",
                0, 2, namesUp2, namesDown2);
        VoteInfo vi3 = new VoteInfo(34, "BothCombined",
                0, 0, emptyList, emptyList);

        List<VoteInfo> allVotes = new ArrayList<>();
        allVotes.add(vi1);
        allVotes.add(vi2);
        allVotes.add(vi3);

        Assert.assertEquals(200, response1.httpCode);
        Assert.assertEquals(200, response2.httpCode);
        Assert.assertEquals(allVotes, response2.votes);
    }

    @Test
    public void testBothCombined() {

        String voteInput1 = "{\n" +
                "  \"alternativeID\": 34,\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 1\n" +
                "}";
        String voteInput2 = "{\n" +
                "  \"alternativeID\": 34,\n" +
                "  \"participantID\": 19,\n" +
                "  \"amendType\": 0\n" +
                "}";

        String JSONString1 = new Gson().toJson(voteInput1);
        String JSONString2 = new Gson().toJson(voteInput2);

        AmendVoteRequest req1 = new Gson().fromJson(JSONString1, AmendVoteRequest.class);
        AmendVoteRequest req2 = new Gson().fromJson(JSONString2, AmendVoteRequest.class);

        AmendVoteHandler avh = new AmendVoteHandler();

        GetVotesResponse response1 = avh.handleRequest(req1, createContext("vote"));
        GetVotesResponse response2 = avh.handleRequest(req2, createContext("vote"));

        List<String> namesUp1 = new ArrayList<>();
        namesUp1.add("Bill");
        namesUp1.add("Bill2");
        List<String> namesDown1 = new ArrayList<>();
        List<String> namesUp2 = new ArrayList<>();
        List<String> namesDown2 = new ArrayList<>();
        namesDown2.add("Bill");
        namesDown2.add("Bill2");
        List<String> namesUp3 = new ArrayList<>();
        namesUp3.add("Bill");
        List<String> namesDown3 = new ArrayList<>();
        namesDown3.add("Bill2");

        VoteInfo vi1 = new VoteInfo(32, "UpVote",
                2, 0, namesUp1, namesDown1);
        VoteInfo vi2 = new VoteInfo(33, "DownVote",
                0, 2, namesUp2, namesDown2);
        VoteInfo vi3 = new VoteInfo(34, "BothCombined",
                1, 1, namesUp3, namesDown3);

        List<VoteInfo> allVotes = new ArrayList<>();
        allVotes.add(vi1);
        allVotes.add(vi2);
        allVotes.add(vi3);

        Assert.assertEquals(200, response1.httpCode);
        Assert.assertEquals(200, response2.httpCode);
        Assert.assertEquals(allVotes, response2.votes);
    }

    @Test
    public void testDeleteAndChange() {

        // Changing Vote
        String voteInput1 = "{\n" +
                "  \"alternativeID\": 34,\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 0\n" +
                "}";
        // Deleting Vote
        String voteInput2 = "{\n" +
                "  \"alternativeID\": 34,\n" +
                "  \"participantID\": 19,\n" +
                "  \"amendType\": 1\n" +
                "}";

        String JSONString1 = new Gson().toJson(voteInput1);
        String JSONString2 = new Gson().toJson(voteInput2);

        AmendVoteRequest req1 = new Gson().fromJson(JSONString1, AmendVoteRequest.class);
        AmendVoteRequest req2 = new Gson().fromJson(JSONString2, AmendVoteRequest.class);

        AmendVoteHandler avh = new AmendVoteHandler();

        GetVotesResponse response1 = avh.handleRequest(req1, createContext("vote"));
        GetVotesResponse response2 = avh.handleRequest(req2, createContext("vote"));

        List<String> namesUp1 = new ArrayList<>();
        namesUp1.add("Bill");
        namesUp1.add("Bill2");
        List<String> namesDown1 = new ArrayList<>();
        List<String> namesUp2 = new ArrayList<>();
        List<String> namesDown2 = new ArrayList<>();
        namesDown2.add("Bill");
        namesDown2.add("Bill2");
        List<String> namesUp4 = new ArrayList<>();
        List<String> namesDown4 = new ArrayList<>();
        namesDown4.add("Bill");

        VoteInfo vi1 = new VoteInfo(32, "UpVote",
                2, 0, namesUp1, namesDown1);
        VoteInfo vi2 = new VoteInfo(33, "DownVote",
                0, 2, namesUp2, namesDown2);
        VoteInfo vi4 = new VoteInfo(34, "BothCombined",
                0, 1, namesUp4, namesDown4);

        List<VoteInfo> allVotes = new ArrayList<>();
        allVotes.add(vi1);
        allVotes.add(vi2);
        allVotes.add(vi4);

        Assert.assertEquals(200, response1.httpCode);
        Assert.assertEquals(200, response2.httpCode);
        Assert.assertEquals(allVotes, response2.votes);
    }

    @Test
    public void testWrongInput() {
        // Fail
        String voteInput1 = "{\n" +
                "  \"participantID\": 18,\n" +
                "  \"amendType\": 0\n" +
                "}";
        // Success
        String voteInput2 = "{\n" +
                "  \"alternativeID\": 34,\n" +
                "  \"participantID\": 19,\n" +
                "  \"amendType\": 1\n" +
                "}";

        String JSONString1 = new Gson().toJson(voteInput1);
        String JSONString2 = new Gson().toJson(voteInput2);

        AmendVoteRequest req1 = new Gson().fromJson(JSONString1, AmendVoteRequest.class);
        AmendVoteRequest req2 = new Gson().fromJson(JSONString2, AmendVoteRequest.class);

        AmendVoteHandler avh = new AmendVoteHandler();

        GetVotesResponse response1 = avh.handleRequest(req1, createContext("vote"));
        GetVotesResponse response2 = avh.handleRequest(req2, createContext("vote"));

        Assert.assertEquals(400, response1.httpCode);
        Assert.assertEquals(200, response2.httpCode);
    }
}
