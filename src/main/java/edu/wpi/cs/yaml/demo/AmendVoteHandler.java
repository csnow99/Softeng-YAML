package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.VoteDAO;
import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
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
        ChoiceDAO choiceDAO = new ChoiceDAO();
        ParticipantDAO partDao = new ParticipantDAO();
        AlternativeDAO altDao = new AlternativeDAO();
        
        
        
        try {
        	String choiceID = altDao.getChoiceIDA(req.getAlternativeID());
        	
        	/*If the participantID does not belong to the choiceID (someone trying to hack in)*/
        	if (!partDao.belongsToChoiceID(choiceID, req.getParticipantID())) {return new GetVotesResponse(403, "ParticipantID not associated with choiceID");}
        	
        	/*If the choice has been completed, then return a 403 i.e. forbidden*/
        	if (choiceDAO.getIsCompleted(choiceID)) {return new GetVotesResponse(403, "Choice has been completed");}
        	
        	if(amendVote(req)) {
                return new GetVotesResponse("Successfully amended a Vote",
                        voteDAO.getVotes(altDao.getChoiceIDA(req.getAlternativeID())));
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
        } else {
            // if vote already exists delete it
        	voteDAO.deleteVote(req.getAlternativeID(), req.getParticipantID());
            // if we changed our vote preference
        	if (exist.getAmendType() != req.getAmendType()) {
        		Vote v = new Vote(req.getAlternativeID(), req.getParticipantID(), req.getAmendType());
                // add a new vote with the now different preference
        		voteDAO.addVote(v);
        	}
        }
        return true;
    }
}

