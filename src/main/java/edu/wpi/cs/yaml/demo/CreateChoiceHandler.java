package edu.wpi.cs.yaml.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;

import java.lang.*;

public class CreateChoiceHandler implements RequestHandler<CreateChoiceRequest,CreateChoiceResponse> {
	LambdaLogger logger;
	
	int createChoice(String choiceID, CreateChoiceRequest req) throws Exception { 
		if (logger != null) { logger.log("in createChoice"); }
		ChoiceDAO choiceDao = new ChoiceDAO();
		
		// check if present
		Choice exist = choiceDao.getChoice(choiceID);
		Choice choice = new Choice(choiceID, req.getName(), req.getMaxParticipants(), req.getDescription());
		if (exist == null) {
			choiceDao.addChoice(choice);
		} else {
			return 409;		//duplicate choiceID
		}
		
		AlternativeDAO altDao = new AlternativeDAO();
		List<Alternative> alternatives = req.getAlternatives();
		for (Alternative alt :alternatives) {
			alt.setChoiceID(choiceID);
			altDao.addAlternative(alt);
		} 
		return 200;
	}
	
	/*Returns a newly generated ID for a choice
	 * 
	 * */
	String generateChoiceID(CreateChoiceRequest req) {
		
		
		String nameHashCode = Integer.toString(Math.abs((req.getName().hashCode())));				//never longer than 10 digits
		String descriptionHashCode = Integer.toString(Math.abs(req.getDescription().hashCode()));	//never longer than 10 digits
		String maxParticipants = Integer.toString(Math.abs(req.getMaxParticipants()));				//never longer than 10 digits
		String alternativesHashCode = "";															//never longer than 30 digits
		for (Alternative alt : req.getAlternatives()) {
			Integer altHashCode = Math.abs(alt.getTitle().hashCode()%1000000); 							//make these 6 digits long
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
			if (createChoice(choiceID, req) == 200)
			{
				response = new CreateChoiceResponse(choiceID, 200);
			} else {
				response = new CreateChoiceResponse("Unable to create choice, due to dupliate choiceID: " +choiceID, 409);
			}
		} catch (Exception e) {
			response = new CreateChoiceResponse("Unable to create choice: " + req.getName() + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}

}
