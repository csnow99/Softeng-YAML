package edu.wpi.cs.yaml.demo;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.RegisterParticipantRequest;
import edu.wpi.cs.yaml.demo.http.RegisterParticipantResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;



public class RegisterParticipantHandler implements RequestHandler<RegisterParticipantRequest,RegisterParticipantResponse> {
	LambdaLogger logger;
	
	boolean registerChoice(RegisterParticipantRequest req) throws Exception { 
		if (logger != null) { logger.log("in createChoice"); }
		ParticipantDAO participantDAO = new ParticipantDAO();
		
		// check if present
		Participant exist = participantDAO.getParticipant(req.getChoiceID(),req.getName());
		Participant participant = new Participant(req.getChoiceID(), req.getName(), req.getPassword());
		if (exist == null) {
			participantDAO.addParticipant(participant);
		} else {
			return false;
		}
		return true;
	}
	

	@Override 
	public RegisterParticipantResponse handleRequest(RegisterParticipantRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateChoiceResponse response;
		try {
			if (registerChoice(req))
			{
				response = new CreateChoiceResponse("Unable to register participant: "+req.getName(), 200);
			} else {
				response = new CreateChoiceResponse("Unable to create choice: ", 400);
			}
		} catch (Exception e) {
			response = new CreateChoiceResponse("Unable to create choice: " + req.getName() + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}

}
