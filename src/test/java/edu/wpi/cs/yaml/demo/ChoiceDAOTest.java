package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;

public class ChoiceDAOTest extends LambdaTest {
	/*Test addChoice, getChoice and deleteChoice*/
	java.sql.Connection conn;

	@Test 
	public void testChoiceDAOBasics() {
		String choiceID = "001";
		String choiceName = "ChoiceDAOTest1";
		int maxParticipants = 5;
		String choiceDescription = "ChoiceDAOTest1Description";
	
		Choice choice = new Choice(choiceID,  choiceName,  maxParticipants, choiceDescription);
		
		/*Test addChoice*/
		ChoiceDAO dao = new ChoiceDAO();
		try {
			dao.addChoice(choice);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Choice WHERE choice_ID=001;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			Choice result = dao.generateChoice(rs);
		} catch (Exception e) {
			
		}
		
		
	}
	
	/*Test getting max Participants*/
	@Test
	public void testGetMaxParticipants() {
		/*We first need to insert a choice in the database*/
		ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
		Alternative alt1 = new Alternative("alt1_name", "alt1_description");
		Alternative alt2 = new Alternative("alt2_name", "alt2_description");
		Alternative alt3 = new Alternative("alt3_name", "alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
	    String choiceID = null;

	    CreateChoiceHandler createHandler = new CreateChoiceHandler();
	    CreateChoiceRequest ccr = new CreateChoiceRequest("testChoiceRegisterParticipant", 3, "sample description", alternatives);
	    CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
	    choiceID = createResp.response;
	    
	    ChoiceDAO dao = new ChoiceDAO();
	    
	    try {
	    Assert.assertEquals(3, dao.getMaxParticipants(choiceID));
	    } catch (Exception e) {
	    	Assert.fail("Dao failed");
	    }
	    
	    
	    if (choiceID != null) {
	        DeleteSingleChoiceByIDRequest dcr = new DeleteSingleChoiceByIDRequest(choiceID);
	        DeleteSingleChoiceByIDResponse d_resp = new DeleteSingleChoiceByIDChoiceHandler().handleRequest(dcr, createContext("delete"));
	        assertEquals("Succesfully deleted: "+choiceID, d_resp.response);
	        }
	    
	}
}
