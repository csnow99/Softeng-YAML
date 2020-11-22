package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.model.Choice;

public class GetChoiceHandler implements RequestHandler<String,Choice>{
	LambdaLogger logger;
	
	Choice getChoice(String choiceID) {
		if (logger != null) { logger.log("in getChoice"); }
		ChoiceDAO choiceDao = new ChoiceDAO();
		
		// check if present
		try {
		Choice choice = choiceDao.getChoice(choiceID);
		return choice;
		}catch (Exception e) {
			logger.log(e.getMessage());
			return null;
		}
	}
	
	@Override 
	public Choice handleRequest(String choiceID, Context context)  {
		logger = context.getLogger();
		logger.log("Attempting to get choiceID " + choiceID);
		
		Choice response = getChoice(choiceID);
		
		return response;
	}
}
