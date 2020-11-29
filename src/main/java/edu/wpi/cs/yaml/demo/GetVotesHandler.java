package edu.wpi.cs.yaml.demo;


import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.VoteDAO;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;
import edu.wpi.cs.yaml.demo.model.VoteInfo;

public class GetVotesHandler implements RequestHandler<String, GetVotesResponse> {

	public LambdaLogger logger;
	
	@Override 
	public GetVotesResponse handleRequest(String choiceID, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all votes for ID: "+ choiceID);
		
		try {
			VoteDAO dao = new VoteDAO();
			List<VoteInfo> votes = dao.getVotes(choiceID);
			return new GetVotesResponse("Succesfully fetched votes: ", votes);
			
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetVotesResponse(404, "Choice not found");
		}
	}
}
