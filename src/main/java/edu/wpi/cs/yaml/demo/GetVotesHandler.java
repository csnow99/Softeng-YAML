package edu.wpi.cs.yaml.demo;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.VoteDAO;
import edu.wpi.cs.yaml.demo.http.GetVoteRequest;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;
import edu.wpi.cs.yaml.demo.model.Vote;

public class GetVoteHandler implements RequestHandler<GetVoteRequest, GetVotesResponse> {

	public LambdaLogger logger;
	
	@Override 
	public GetVotesResponse handleRequest(GetVoteRequest req, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to get vote");
		
		try {
			VoteDAO dao = new VoteDAO();
			Vote vote = dao.getVote(req.getParticipantID(), req.getAlternativeID());
			return new GetVotesResponse("Succesfully fetched vote: ", vote);
			
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetVotesResponse(404, "Vote not found");
		}
	}
	
	
}
