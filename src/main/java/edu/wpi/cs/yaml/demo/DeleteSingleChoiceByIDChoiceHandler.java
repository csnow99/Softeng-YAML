package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;



/**
 * No more JSON parsing
 */
public class DeleteSingleChoiceByIDChoiceHandler implements RequestHandler<DeleteSingleChoiceByIDRequest,DeleteSingleChoiceByIDResponse> {

	public LambdaLogger logger = null;
	
	/*Deletes a choice in the database, which would include
	 * 1) Deleting the single row entry in the choices table
	 * 2) Deleting the multiple row entries in the alternatives table
	 * 3) Deleting the multiple row entries in the votes table
	 * 4) Deleting the multiple row entries in the feedbacks table
	 * 
	 * */

	@Override
	public DeleteSingleChoiceByIDResponse handleRequest(DeleteSingleChoiceByIDRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteSingleChoiceByIDResponse response = null;
		logger.log(req.toString());

		ChoiceDAO dao = new ChoiceDAO();

		String choiceID = req.getChoiceID();
		try {
			if (dao.deleteChoice(choiceID)) {
				response = new DeleteSingleChoiceByIDResponse("Succesfully deleted: "+choiceID, 200);
			} else {
				response = new DeleteSingleChoiceByIDResponse("Unable to delete choice: " + choiceID, 422);
			}
		} catch (Exception e) {
			response = new DeleteSingleChoiceByIDResponse("Unable to delete choice: " + choiceID +  " (" + e.getMessage() + ")", 403);
		}

		return response;
	}
}
