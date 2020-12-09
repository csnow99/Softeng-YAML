package edu.wpi.cs.yaml.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateChoiceHandlerTest extends LambdaTest {

    // NOTE: Manually remove the added choice every time
    @Test
    public void testCreateChoiceHandler() {
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
        try {
        	 /*Attempt creating the first choice for the first time*/
        	 CreateChoiceResponse response = createHandler.handleRequest(request1, createContext("createTest"));
        	 Assert.assertEquals(200, response.getHttpCode());
        	 choiceID1 = response.getResponse(); //choiceID1
        	 
        	 Choice choice1 = choiceDAO.getChoice(choiceID1);
        	 Assert.assertEquals("testChoice1", choice1.getChoiceName());
        	 Assert.assertEquals(10, choice1.getMaxParticipants());
        	 Assert.assertEquals("sample description", choice1.getChoiceDescription());
        	 Assert.assertEquals(false, choice1.getIsCompleted());
        	 Assert.assertEquals(0, choice1.getDateCompleted());
        	 Assert.assertEquals(0, choice1.getSelectedAlternativeID());

        	 List<Alternative> alternatives1 = altDAO.getAlternatives(choiceID1);
        	 Assert.assertEquals(choiceID1, alternatives1.get(0).getChoiceID());
        	 Assert.assertEquals("alt1_name", alternatives1.get(0).getTitle());
        	 Assert.assertEquals("alt1_description", alternatives1.get(0).getDescription());

        	 Assert.assertEquals(choiceID1, alternatives1.get(1).getChoiceID());
        	 Assert.assertEquals("alt2_name", alternatives1.get(1).getTitle());
        	 Assert.assertEquals("alt2_description", alternatives1.get(1).getDescription());

        	 Assert.assertEquals(choiceID1, alternatives1.get(2).getChoiceID());
        	 Assert.assertEquals("alt3_name", alternatives1.get(2).getTitle());
        	 Assert.assertEquals("alt3_description", alternatives1.get(2).getDescription());
 			
        	 /*Attempt creating another choice*/
        	 response = createHandler.handleRequest(request2, createContext("createTest"));
        	 Assert.assertEquals(200, response.getHttpCode());
        	 choiceID2 = response.getResponse(); //choiceID2
        	 
        	 Choice choice2 = choiceDAO.getChoice(choiceID2);
        	 Assert.assertEquals("testChoice2", choice2.getChoiceName());
        	 Assert.assertEquals(5, choice2.getMaxParticipants());
        	 Assert.assertEquals("sample description", choice2.getChoiceDescription());
        	 Assert.assertEquals(false, choice2.getIsCompleted());
        	 Assert.assertEquals(0, choice2.getDateCompleted());
        	 Assert.assertEquals(0, choice2.getSelectedAlternativeID());

        	 List<Alternative> alternatives2 = altDAO.getAlternatives(choiceID2);
        	 Assert.assertEquals(choiceID2, alternatives2.get(0).getChoiceID());
        	 Assert.assertEquals("alt1_name", alternatives2.get(0).getTitle());
        	 Assert.assertEquals("alt1_description", alternatives2.get(0).getDescription());

        	 Assert.assertEquals(choiceID2, alternatives2.get(1).getChoiceID());
        	 Assert.assertEquals("alt2_name", alternatives2.get(1).getTitle());
        	 Assert.assertEquals("alt2_description", alternatives2.get(1).getDescription());

        	 Assert.assertEquals(choiceID2, alternatives2.get(2).getChoiceID());
        	 Assert.assertEquals("alt3_name", alternatives2.get(2).getTitle());
        	 Assert.assertEquals("alt3_description", alternatives2.get(2).getDescription());
        	 
        	 /*Attempt creating the first choice again (duplicate choiceID)*/
        	 response = createHandler.handleRequest(request1, createContext("createTest"));
        	 Assert.assertEquals(409, response.getHttpCode());
        	 Assert.assertEquals("Unable to create choice, due to dupliate choiceID: "+choiceID1,response.getResponse()); //choiceID1
        
        } catch (Exception e) {
        	try {
				choiceDAO.deleteChoice(choiceID1);	//delete the choice automatically if the test fails anywhere
				choiceDAO.deleteChoice(choiceID2);
			} catch (Exception e2) {
				Assert.fail();
			}
        	Assert.fail();
        }
        
        /*Delete Using choiceDAO*/
        try {
        	choiceDAO.deleteChoice(choiceID1);
        	choiceDAO.deleteChoice(choiceID2);
        } catch (Exception e) {
        	Assert.fail();
        }
    }
    
    
    
}
