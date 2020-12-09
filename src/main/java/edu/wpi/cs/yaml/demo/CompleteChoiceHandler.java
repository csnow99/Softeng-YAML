package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.http.CompleteChoiceRequest;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;

public class CompleteChoiceHandler implements RequestHandler<CompleteChoiceRequest, GetChoiceResponse> {
	LambdaLogger logger;

	public GetChoiceResponse handleRequest(CompleteChoiceRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());
		
		/*Along with making sure that the basic functionality works, make sure to check the following
		 * 1) ChoiceID exists (code 404 choice does not exist otherwise)
		 * 2) ParticipantID is a valid ID for the choiceID (code 403)
		 * 3) choiceID is not already completed (code 403)
		 * */
	}
}
