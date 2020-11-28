package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.VoteDAO;
import edu.wpi.cs.yaml.demo.db.GetDAO;
import edu.wpi.cs.yaml.demo.http.AmendVoteRequest;
import edu.wpi.cs.yaml.demo.http.GetVoteResponse;
import edu.wpi.cs.yaml.demo.model.Vote;

public class AmendVoteHandler implements RequestHandler<AmendVoteRequest, GetVoteResponse> {
    LambdaLogger logger;

    @Override
    public GetVoteResponse handleRequest(AmendVoteRequest req, Context context) {

        logger = context.getLogger();
        logger.log(req.toString());
        VoteDAO voteDAO = new VoteDAO();

        try {
            if(amendVote(req)) {
                Vote vote = voteDAO.getVote(req.getAlternativeID(), req.getParticipantID());
                return new GetVoteResponse("Successfully amended a Vote", vote);
            } else {
                return new GetVoteResponse(400, "Could not amend Vote");
            }
        } catch (Exception e) {
            logger.log(e.getMessage());
            return new GetVoteResponse(400, "Cannot amend Vote: " + e);
        }
    }

    public boolean amendVote(AmendVoteRequest req) throws Exception {

        if (logger != null) { logger.log("in amendVote"); }
        VoteDAO voteDAO = new VoteDAO();
        GetDAO getDAO = new GetDAO();

        // check if alternative and participant belong to the same choice
        if (getDAO.getChoiceIDA(req.getAlternativeID()).equals(getDAO.getChoiceIDP(req.getParticipantID()))) {
            // check if exists
            Vote exist = voteDAO.getVote(req.getAlternativeID(), req.getParticipantID());
            Vote aVote = new Vote(req.getAlternativeID(), req.getParticipantID(), req.getAmendType());
            if (exist == null){
                voteDAO.addVote(aVote);
                return true;
            } else {
                if (exist.getAmendType() == req.getAmendType()){
                    // edit to be delete vote by alternativeID and participantID
                    voteDAO.deleteVote(req.getAlternativeID(), req.getParticipantID());
                    return true;
                } else {
                    voteDAO.deleteVote(req.getAlternativeID(), req.getParticipantID());
                    Vote v = new Vote(req.getAlternativeID(), req.getParticipantID(), req.getAmendType());
                    voteDAO.addVote(v);
                    return true;
                }
            }
        } else {
            return false;
        }
    }
}
