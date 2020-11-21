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
		Choice choice = new Choice(choiceID, choiceName, maxParticipants, choiceDescription);
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
	
	/*Returns a newly generated ID for a choice
	 * 
	 * */
	String generateChoiceID(CreateChoiceRequest req) {
		
		String nameHashCode = Integer.toString(req.getName().hashCode());					//never longer than 10 digits
		String descriptionHashCode = Integer.toString(req.getDescription().hashCode());		//never longer than 10 digits
		String maxParticipants = Integer.toString(req.getMaxParticipants());				//never longer than 10 digits
		String alternativesHashCode = "";													//never longer than 30 digits
		for (Alternative alt : req.getAlternatives()) {
			Integer altHashCode = alt.name.hashCode()%1000000; 								//make these 6 digits long
			alternativesHashCode = alternativesHashCode.concat(altHashCode.toString());
		}
	
		String newID = "";																	//never longer than 60 digits
		newID = newID.concat(nameHashCode).concat(descriptionHashCode).concat(maxParticipants).concat(alternativesHashCode);
		return newID;
	}
	
	@Override 
	public CreateChoiceResponse handleRequest(CreateChoiceRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateChoiceResponse response;
		try {
			String choiceID = generateChoiceID(req);
			if (createChoice(choiceID, req.getName(), req.getMaxParticipants(), req.getDescription(), req.getAlternatives()))
			{
				response = new CreateChoiceResponse(choiceID, 200);
			} else {
				response = new CreateChoiceResponse("Unable to create choice: " +choiceID, 400);
			}
		} catch (Exception e) {
			response = new CreateChoiceResponse("Unable to create choice: " + req.getName() + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}

}
