package edu.wpi.cs.yaml.demo;

import java.util.ArrayList;
import java.util.List;

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
import edu.wpi.cs.yaml.demo.model.Participant;



public class RegisterParticipantHandler implements RequestHandler<RegisterParticipantRequest,RegisterParticipantResponse> {
	LambdaLogger logger;
	
	boolean registerParticipant(RegisterParticipantRequest req) throws Exception { 
		if (logger != null) { logger.log("in createChoice"); }
		ParticipantDAO participantDAO = new ParticipantDAO();
		ChoiceDAO choiceDAO = new ChoiceDAO();
	
		List<Participant> list = participantDAO.getParticipants(req.getChoiceID());
		
		// check if maxNumber of perticipants is reached
		int maxParticipants = choiceDAO.getMaxParticipants(req.getChoiceID());
		if (list.size() >= maxParticipants) {return false;} //no more space
		
		// check if it already exists
		Participant participant = new Participant(req.getChoiceID(), req.getName(), req.getPassword());
		for(Participant p : list) {
			if (participant.equals(p)) {
				return false;				//no duplicate names allowed
			}
		}
		
		//If everything is fine, we may register this new participant
		return participantDAO.addParticipant(participant);
	}
	

	@Override 
	public RegisterParticipantResponse handleRequest(RegisterParticipantRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		RegisterParticipantResponse response;
		try {
			if (registerParticipant(req))
			{
				response = new RegisterParticipantResponse("Successfully registered participant: "+req.getName(), 200);
			} else {
				response = new RegisterParticipantResponse("Unable to register participant: "+req.getName(), 400);
			}
		} catch (Exception e) {
			response = new RegisterParticipantResponse("Unable to register participant: " + req.getName() + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}

}
