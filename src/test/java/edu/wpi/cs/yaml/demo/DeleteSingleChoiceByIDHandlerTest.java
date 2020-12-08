package edu.wpi.cs.yaml.demo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;

public class DeleteSingleChoiceByIDHandlerTest extends LambdaTest{
	@Test
	public void testDeleteSingleChoiceByIDHandler() {
		ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
		Alternative alt1 = new Alternative("alt1_name", "alt1_description");
		Alternative alt2 = new Alternative("alt2_name", "alt2_description");
		Alternative alt3 = new Alternative("alt3_name", "alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		CreateChoiceRequest request1 = new CreateChoiceRequest("testChoice1", 10, "sample description", alternatives);
		CreateChoiceRequest request2 = new CreateChoiceRequest("testChoice2", 5, "sample description", alternatives);
		CreateChoiceHandler createHandler = new CreateChoiceHandler();
		ChoiceDAO choiceDAO = new ChoiceDAO();
		AlternativeDAO altDAO = new AlternativeDAO();
		String choiceID1 = null;
		String choiceID2 = null;
		
		/*Create the two choices*/
		try {
			CreateChoiceResponse createResponse1 = createHandler.handleRequest(request1, createContext("createTest"));
			CreateChoiceResponse createResponse2 = createHandler.handleRequest(request2, createContext("createTest"));
			choiceID1 = createResponse1.getResponse();
			choiceID2 = createResponse2.getResponse();
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Try deleting the two choices*/
		DeleteSingleChoiceByIDRequest deleteRequest1 = new DeleteSingleChoiceByIDRequest(choiceID1);
		DeleteSingleChoiceByIDRequest deleteRequest2 = new DeleteSingleChoiceByIDRequest(choiceID2);
		DeleteSingleChoiceByIDChoiceHandler deleteHandler = new DeleteSingleChoiceByIDChoiceHandler();
		try {
			/*Delete choice and check if it's really removed*/
			DeleteSingleChoiceByIDResponse deleteResponse1 = deleteHandler.handleRequest(deleteRequest1,  createContext("deleteTest1"));
			Assert.assertEquals(200, deleteResponse1.getHttpCode());
			Assert.assertEquals("Succesfully deleted: "+choiceID1, deleteResponse1.getResponse());
			Assert.assertEquals(null, choiceDAO.getChoice(choiceID1));
			
			/*Delete choice and check if it's really removed*/
			DeleteSingleChoiceByIDResponse deleteResponse2 = deleteHandler.handleRequest(deleteRequest2,  createContext("deleteTest1"));
			Assert.assertEquals(200, deleteResponse2.getHttpCode());
			Assert.assertEquals("Succesfully deleted: "+choiceID2, deleteResponse2.getResponse());
			Assert.assertEquals(null, choiceDAO.getChoice(choiceID2));
			
			/*See what happens if attempting to delete a non-existent choice*/
			DeleteSingleChoiceByIDResponse deleteResponse3 = deleteHandler.handleRequest(deleteRequest1,  createContext("deleteTest1"));
			Assert.assertEquals(404, deleteResponse3.getHttpCode());
			Assert.assertEquals("Unable to delete choice: " + choiceID1, deleteResponse3.getResponse());

		} catch (Exception e) {
			Assert.fail();
		}

	}
}
