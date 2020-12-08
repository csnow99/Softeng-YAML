package edu.wpi.cs.yaml.demo;


import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.FeedbackDAO;
import edu.wpi.cs.yaml.demo.http.GetFeedbackResponse;
import edu.wpi.cs.yaml.demo.model.FeedbackInfo;

public class GetFeedbackHandler implements RequestHandler<String, GetFeedbackResponse> {

	public LambdaLogger logger;
	
	@Override 
	public GetFeedbackResponse handleRequest(String choiceID, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all feedback for ID: "+ choiceID);
		
		try {
			FeedbackDAO dao = new FeedbackDAO();
			List<FeedbackInfo> feedback = dao.getFeedback(choiceID);
			return new GetFeedbackResponse("Succesfully fetched feedbacks for choiceID: " + choiceID , feedback);
			
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetFeedbackResponse(404, "Choice not found");
		}
	}
}
