package edu.wpi.cs.yaml.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;


import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.http.GetAlternativesResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;



public class GetAlternativesHandler implements RequestHandler<String, GetAlternativesResponse> {

	public LambdaLogger logger;

	
	@Override 
	public GetAlternativesResponse handleRequest(String choiceID, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all alternatives for ID: "+ choiceID);
		
		try {
			AlternativeDAO dao = new AlternativeDAO();
			List<Alternative> alternatives = dao.getAlternatives(choiceID);;
			return new GetAlternativesResponse("Succesfully fetched alternatives", alternatives);
			
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetAlternativesResponse(404, "ChoiceID not found");
		}
	}
	
}