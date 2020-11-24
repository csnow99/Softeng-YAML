package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Choice;

public class GetChoiceHandler implements RequestHandler<String,GetChoiceResponse>{
	LambdaLogger logger;
	
	@Override 
	public GetChoiceResponse handleRequest(String choiceID, Context context)  {
		logger = context.getLogger();
		logger.log("Attempting to get choiceID " + choiceID);
		
		if (logger != null) { logger.log("in getChoice"); }
		ChoiceDAO choiceDao = new ChoiceDAO();
		
		// check if present
		try {
		Choice choice = choiceDao.getChoice(choiceID);
		return new GetChoiceResponse("Succesfully fetched choice", choice);
		}catch (Exception e) {
			logger.log(e.getMessage());
			return new GetChoiceResponse(404, "ChoiceID could not be found");
		}
	}
}