package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.http.GetChoiceLoginRequest;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Choice;

public class GetChoiceLoginHandler implements RequestHandler<GetChoiceLoginRequest,GetChoiceResponse>{
	LambdaLogger logger;

	@Override 
	public GetChoiceResponse handleRequest(GetChoiceLoginRequest request, Context context)  {
		logger = context.getLogger();
		logger.log("Attempting to get choiceID " + request.getChoiceID());

		if (logger != null) { logger.log("in getChoice"); }
		ChoiceDAO choiceDAO = new ChoiceDAO();
		ParticipantDAO participantDAO = new ParticipantDAO();

		try {
			if (request.getParticipantID() == 0) {
				Choice choice = choiceDAO.getChoice(request.getChoiceID());
				return new GetChoiceResponse(206, "Succesfully fetched choice", choice);
			}
			}catch (Exception e) {
				logger.log(e.getMessage());
				return new GetChoiceResponse(404, "ChoiceID could not be found");
			}
		
			// check if participantID is associated with choiceID
			try {
				if (!participantDAO.belongsToChoiceID(request.getChoiceID(), request.getParticipantID())) {
					return new GetChoiceResponse(404, "Participant ID does not belong to choiceID");
				}
			} catch (Exception e) {
				logger.log(e.getMessage());
				return new GetChoiceResponse(404, "ChoiceID could not be found");
			}
			
			try {
				Choice choice = choiceDAO.getChoice(request.getChoiceID());
				String name = participantDAO.getParticipantNameFromID(request.getParticipantID());
				return new GetChoiceResponse("Login successful", choice, name);
			}catch (Exception e) {
				logger.log(e.getMessage());
				return new GetChoiceResponse(404, "ChoiceID could not be found");
			}
		}

	}
