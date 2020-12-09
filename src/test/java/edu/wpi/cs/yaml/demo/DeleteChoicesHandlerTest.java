package edu.wpi.cs.yaml.demo;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.DeleteChoicesRequest;
import edu.wpi.cs.yaml.demo.model.Choice;

public class DeleteChoicesHandlerTest extends LambdaTest {
	@Test
	public void testDeleteChoices() {
		ChoiceDAO dao = new ChoiceDAO();
		DeleteChoicesHanlder handler = new DeleteChoicesHandler();
		DeleteChoicesRequest request = new DeleteChoicesRequest(100.0f);
		
		try {
			Choice choice1 = new Choice("001", "DeleteOldChoicesTest",  7, "DeleteOldChoicesDescr", 1506002170300l, false, 0, 0);
			Choice choice2 = new Choice("002", "DeleteOldChoicesTest",  7, "DeleteOldChoicesDescr", 1506002170300l, false, 0, 0);
			Choice choice3 = new Choice("003", "DeleteOldChoicesTest",  7, "DeleteOldChoicesDescr", 1506002170300l, false, 0, 0);
			Choice choice4 = new Choice("004", "DeleteOldChoicesTest",  7, "DeleteOldChoicesDescr", 1506002170300l, false, 0, 0);
			int initialNumOfChoices = dao.getAllChoices().size();
			dao.addChoice(choice1);
			int difference1  = dao.getAllChoices().size() - initialNumOfChoices;
			Assert.assertEquals(1, difference1);
			handler.handleRequest(request);
			int difference2 = dao.getAllChoices().size() - initialNumOfChoices;
			Assert.assertEquals(0, difference2);
			dao.addChoice(choice2);
			dao.addChoice(choice3);
			dao.addChoice(choice4);
			int difference3 = dao.getAllChoices().size() - initialNumOfChoices;
			Assert.assertEquals(3, difference3);
			handler.handleRequest(request);
			int difference4 = dao.getAllChoices().size() - initialNumOfChoices;
			Assert.assertEquals(0, difference4);
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
