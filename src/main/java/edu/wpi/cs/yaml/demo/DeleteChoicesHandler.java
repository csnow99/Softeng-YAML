package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.DeleteChoicesRequest;
import edu.wpi.cs.yaml.demo.http.DeleteChoicesResponse;

public class DeleteChoicesHandler implements RequestHandler<DeleteChoicesRequest, DeleteChoicesResponse>{
	public LambdaLogger logger = null;

	@Override
	public DeleteChoicesResponse handleRequest(DeleteChoicesRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteChoicesResponse response = null;
		logger.log(req.toString());

		ChoiceDAO dao = new ChoiceDAO();

		float days = req.returnHowOld();
		try {
			if (dao.deleteChoicesOlderThan(days)) {
				response = new DeleteChoicesResponse("Succesfully deleted choices older than "+days+" days", 200);
			} else {
				response = new DeleteChoicesResponse("Unable to delete choices older than " + days + " days", 404);
			}
		} catch (Exception e) {
			response = new DeleteChoicesResponse("Unable to delete choices older than " + days +  " days:  (" + e.getMessage() + ")", 403);
		}

		return response;
	}
}
