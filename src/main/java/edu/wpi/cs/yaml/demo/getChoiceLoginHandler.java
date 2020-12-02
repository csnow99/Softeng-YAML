package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.http.GetChoiceLoginRequest;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Choice;

public class getChoiceLoginHandler implements RequestHandler<GetChoiceLoginRequest,GetChoiceResponse>{
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
				return new GetChoiceResponse("Succesfully fetched choice", choice);
			}
			}catch (Exception e) {
				logger.log(e.getMessage());
				return new GetChoiceResponse(404, "ChoiceID could not be found");
			}
			try {
				if (!participantDAO.belongsToChoiceID(request.getChoiceID(), request.getParticipantID())) {
					return new GetChoiceResponse(404, "ChoiceID could not be found");
				}
			} catch (Exception e) {
				logger.log(e.getMessage());
				return new GetChoiceResponse(404, "ChoiceID could not be found");
			}
			// check if present
			try {
				Choice choice = choiceDAO.getChoice(request.getChoiceID());
				return new GetChoiceResponse("Succesfully fetched choice", choice);
			}catch (Exception e) {
				logger.log(e.getMessage());
				return new GetChoiceResponse(404, "ChoiceID could not be found");
			}
		}

	}
