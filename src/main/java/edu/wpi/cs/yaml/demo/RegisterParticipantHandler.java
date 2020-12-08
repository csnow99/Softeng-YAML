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
	
	int registerParticipant(RegisterParticipantRequest req) throws Exception { 
		if (logger != null) { logger.log("in createChoice"); }
		ParticipantDAO participantDAO = new ParticipantDAO();
		ChoiceDAO choiceDAO = new ChoiceDAO();
	
		List<Participant> list = participantDAO.getParticipants(req.getChoiceID());
		
		// check if already logged in
		Participant participant = new Participant(req.getChoiceID(), req.getName(), req.getPassword());
		for(Participant p : list) {
			if (participant.equals(p)) {
				if (participant.getPassword().equals(p.getPassword())) {return 200;} //valid login
				else {return 401;}										   //false password
			}
		}
		
		// check if maxNumber of participants is reached
		int maxParticipants = choiceDAO.getMaxParticipants(req.getChoiceID());
		if (list.size() >= maxParticipants) {return 403;} //no more space

		//If everything is fine, we may register this new participant
		if(participantDAO.addParticipant(participant))	{return 201;}		//new entry created
		else{return 400;}													//failed somehow
	}
	

	@Override 
	public RegisterParticipantResponse handleRequest(RegisterParticipantRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		RegisterParticipantResponse response;
		try {
			ParticipantDAO participantDAO = new ParticipantDAO();
			switch (registerParticipant(req))
			{
				case 200:
					response = new RegisterParticipantResponse("Successfully logged in as: "+req.getName(), 200, participantDAO.getParticipantIDFromChoiceIDAndParticipantName(req.getChoiceID(), req.getName()));
					break;
				case 201:
					response = new RegisterParticipantResponse("Successfully registered participant: "+req.getName(), 201, participantDAO.getParticipantIDFromChoiceIDAndParticipantName(req.getChoiceID(), req.getName()));
					break;
				case 401:
					response = new RegisterParticipantResponse("Wrong password for participant: "+req.getName(), 401, 0);
					break;
				case 403:
					response = new RegisterParticipantResponse("Maximum number of participants reached for choice: "+req.getName(), 403, 0);
					break;
				default:
					response = new RegisterParticipantResponse("Unable to register participant: " + req.getName(), 400, 0);
					break;
			}
		} catch (Exception e) {
			response = new RegisterParticipantResponse("Unable to register participant: " + req.getName() + "(" + e.getMessage() + ")", 400, 0);
		}
		return response;
	}

}
