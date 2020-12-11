package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.http.CompleteChoiceRequest;
import edu.wpi.cs.yaml.demo.http.GetChoiceResponse;

public class CompleteChoiceHandler implements RequestHandler<CompleteChoiceRequest, GetChoiceResponse> {
	LambdaLogger logger;

	@Override
	public GetChoiceResponse handleRequest(CompleteChoiceRequest req, Context context) {

		logger = context.getLogger();
		logger.log(req.toString());

		ChoiceDAO choiceDAO = new ChoiceDAO();
		ParticipantDAO partDao = new ParticipantDAO();
		AlternativeDAO altDao = new AlternativeDAO();
		GetChoiceResponse response = null;

		String choiceID = req.getChoiceID();
		int altID = req.getAlternativeID();

		try {

			/*If the participantID does not belong to the choiceID (someone trying to hack in)*/
			if (!partDao.belongsToChoiceID(choiceID, req.getParticipantID())) {return new GetChoiceResponse(403, "ParticipantID not associated with choiceID");}

			/*If the choice has been completed, then return a 403 i.e. forbidden*/
			if (choiceDAO.getIsCompleted(choiceID)) {return new GetChoiceResponse(403, "Choice has already been completed");}

			if(choiceDAO.setIsCompleted(choiceID, altID)) {
				response = new GetChoiceResponse(200, "Succesfully completed choice: " + choiceID);
			} else {
				response = new GetChoiceResponse(422, "Unable to complete choice: " + choiceID);
			}

		}catch(Exception e) {
			logger.log(e.getMessage());
			return new GetChoiceResponse(400, "Cannot complete choice: " + choiceID +  " (" + e.getMessage() + ")");
		}

		return response;
	}
}
