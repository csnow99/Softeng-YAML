package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.VoteDAO;
import edu.wpi.cs.yaml.demo.db.GetDAO;
import edu.wpi.cs.yaml.demo.http.AmendVoteRequest;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;
import edu.wpi.cs.yaml.demo.model.Vote;

public class AmendVoteHandler implements RequestHandler<AmendVoteRequest, GetVotesResponse> {
    LambdaLogger logger;

    @Override
    public GetVotesResponse handleRequest(AmendVoteRequest req, Context context) {

        logger = context.getLogger();
        logger.log(req.toString());
        VoteDAO voteDAO = new VoteDAO();

        try {
            if(amendVote(req)) {
                Vote vote = voteDAO.getVote(req.getAlternativeID(), req.getParticipantID());
                return new GetVotesResponse("Successfully amended a Vote", voteDAO.getVotes(alternative_ID));
            } else {
                return new GetVotesResponse(400, "Could not amend Vote");
            }
        } catch (Exception e) {
            logger.log(e.getMessage());
            return new GetVotesResponse(400, "Cannot amend Vote: " + e);
        }
    }

    public boolean amendVote(AmendVoteRequest req) throws Exception {

        if (logger != null) { logger.log("in amendVote"); }
        VoteDAO voteDAO = new VoteDAO();

        Vote exist = voteDAO.getVote(req.getAlternativeID(), req.getParticipantID());
        Vote aVote = new Vote(req.getAlternativeID(), req.getParticipantID(), req.getAmendType());
        if (exist == null){
        	voteDAO.addVote(aVote);
        	return true;
        } else {
        	voteDAO.deleteVote(req.getAlternativeID(), req.getParticipantID());	//if vote already exists delete it
        	if (exist.getAmendType() != req.getAmendType()){					//if we changed our vote preference
        		Vote v = new Vote(req.getAlternativeID(), req.getParticipantID(), req.getAmendType()); 
        		voteDAO.addVote(v); 											//add a new vote with the now different preference
        	}
        	return true;														//return true
        }
    }
}

