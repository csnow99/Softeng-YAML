package edu.wpi.cs.yaml.demo;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;



public class CreateChoiceHandler implements RequestHandler<CreateChoiceRequest,CreateChoiceResponse> {
	LambdaLogger logger;
	
	boolean createChoice(String choiceID, String choiceName, int maxParticipants,String choiceDescription, ArrayList<Alternative> alternatives) throws Exception { 
		if (logger != null) { logger.log("in createChoice"); }
		ChoiceDAO choiceDao = new ChoiceDAO();
		
		// check if present
		Choice exist = choiceDao.getChoice(choiceID);
		Choice choice = new Choice(choiceID, choiceName, maxParticipants, choiceDescription, false);
		if (exist == null) {
			choiceDao.addChoice(choice);
		} else {
			return false;
		}
		
		AlternativeDAO altDao = new AlternativeDAO();
		for (Alternative alt : alternatives) {
			alt.setChoiceID(choiceID);
			altDao.addAlternative(alt);
		}
		return true;
	}
	
	@Override 
	public CreateChoiceResponse handleRequest(CreateChoiceRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateChoiceResponse response;
		try {
			String choiceID = Integer.toString(req.getName().hashCode());
			if (createChoice(choiceID, req.getName(), req.getMaxParticipants(), req.getDescription(), req.getAlternatives()))
			{
				response = new CreateChoiceResponse(choiceID, 200);
			} else {
				response = new CreateChoiceResponse(choiceID, 400);
			}
		} catch (Exception e) {
			response = new CreateChoiceResponse("Unable to create choice: " + req.getName() + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}

}
