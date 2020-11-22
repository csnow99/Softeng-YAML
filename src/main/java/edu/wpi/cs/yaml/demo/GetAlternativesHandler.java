package edu.wpi.cs.yaml.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;


import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.model.Alternative;



public class GetAlternativesHandler implements RequestHandler<String, List<Alternative>> {

	public LambdaLogger logger;
	
	List<Alternative> getAlternatives(String choiceID) throws Exception {
		logger.log("in getAlternatives");
		AlternativeDAO dao = new AlternativeDAO();
		
		return dao.getAlternatives(choiceID);
	}
	
	@Override 
	public List<Alternative> handleRequest(String choiceID, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all alternatives for ID: "+ choiceID);

		List<Alternative> response;
		try {
			response = getAlternatives(choiceID);
			
		} catch (Exception e) {
			response = null;
			logger.log(e.getMessage());
		}
		
		return response;
	}
	
}