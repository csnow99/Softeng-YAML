package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.CompleteChoiceRequest;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;
import edu.wpi.cs.yaml.demo.http.GetVotesResponse;

public class CompleteChoiceHandler implements RequestHandler<CompleteChoiceRequest, GetChoiceResponse> {
	LambdaLogger logger;

	@Override
	public GetChoiceResponse handleRequest(CompleteChoiceRequest req, Context context) {

		logger = context.getLogger();
		logger.log(req.toString());

		ChoiceDAO choiceDAO = new ChoiceDAO();
		GetChoiceResponse response = null;

		String choiceID = req.getChoiceID();

		try {
			if(choiceDAO.setIsCompleted(choiceID)) {
				response = new GetChoiceResponse(200, "Succesfully completed choice: " + choiceID);
			} else {
				response = new GetChoiceResponse(422, "Unable to completed choice: " + choiceID);
			}
		}catch(Exception e) {
			logger.log(e.getMessage());
			return new GetChoiceResponse(400, "Cannot complete choice: " + choiceID +  " (" + e.getMessage() + ")");
		}

		return response;
	}
}
