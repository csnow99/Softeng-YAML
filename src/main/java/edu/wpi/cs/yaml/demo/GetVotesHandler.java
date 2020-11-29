package edu.wpi.cs.yaml.demo;


import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.VoteDAO;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;

public class GetVotesHandler implements RequestHandler<String, GetVotesResponse> {

	public LambdaLogger logger;
	
	@Override 
	public GetVotesResponse handleRequest(String alternativeID, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all votes for ID: "+ alternativeID);
		
		try {
			VoteDAO dao = new VoteDAO();
			List<Object> votes = dao.getVotes(alternativeID);
			return new GetVotesResponse("Succesfully fetched votes: ", votes);
			
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetVotesResponse(404, "Vote not found");
		}
	}
	
	
}
