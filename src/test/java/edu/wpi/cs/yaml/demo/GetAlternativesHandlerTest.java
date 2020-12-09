package edu.wpi.cs.yaml.demo;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.GetAlternativesResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;



public class GetAlternativesHandlerTest extends LambdaTest{
	
    @Test
    public void testGetAlternatives()  {
    	GetAlternativesHandler handler = new GetAlternativesHandler();
    	
    	/*We first need to insert a choice in the database*/
    	ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
    	Alternative alt1 = new Alternative("alt1_name", "alt1_description");
    	Alternative alt2 = new Alternative("alt2_name", "alt2_description");
    	Alternative alt3 = new Alternative("alt3_name", "alt3_description");
    	alternatives.add(alt1);
    	alternatives.add(alt2);
    	alternatives.add(alt3);
        String choiceID = null;
        ChoiceDAO choiceDAO = new ChoiceDAO();

        CreateChoiceHandler createHandler = new CreateChoiceHandler();
        CreateChoiceRequest ccr = new CreateChoiceRequest("testChoice1", 10, "sample description", alternatives);
        
        try {
        CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
        choiceID = createResp.getResponse();
      
        
        /*Now that it's inserted we may */
        GetAlternativesResponse resp = handler.handleRequest(choiceID, createContext("list"));
        List<Alternative> respAlternatives = resp.getAlternatives();
        
        Assert.assertEquals(3, respAlternatives.size());
        
        Assert.assertTrue(respAlternatives.get(0).getChoiceID().equals(choiceID));
        Assert.assertTrue(respAlternatives.get(0).getTitle().equals("alt1_name"));
        Assert.assertTrue(respAlternatives.get(0).getDescription().equals("alt1_description"));
        
        Assert.assertTrue(respAlternatives.get(1).getChoiceID().equals(choiceID));
        Assert.assertTrue(respAlternatives.get(1).getTitle().equals("alt2_name"));
        Assert.assertTrue(respAlternatives.get(1).getDescription().equals("alt2_description"));
        
        Assert.assertTrue(respAlternatives.get(2).getChoiceID().equals(choiceID));
        Assert.assertTrue(respAlternatives.get(2).getTitle().equals("alt3_name"));
        Assert.assertTrue(respAlternatives.get(2).getDescription().equals("alt3_description"));
        
        } catch(Exception e) {
        	try {
				choiceDAO.deleteChoice(choiceID);	//delete the choice automatically if the test fails anywhere
			} catch (Exception e2) {
				Assert.fail();
			}
        	Assert.fail();
        }
        /*Delete the inserted choice*/
        try {
         choiceDAO.deleteChoice(choiceID);
        } catch (Exception e) {
        	Assert.fail();
        }
        
    
    }
}