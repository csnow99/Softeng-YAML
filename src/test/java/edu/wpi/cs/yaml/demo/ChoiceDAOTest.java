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
import edu.wpi.cs.yaml.demo.model.ChoiceInfo;

public class ChoiceDAOTest extends LambdaTest {
	/*Test addChoice, getChoice and deleteChoice*/
	java.sql.Connection conn;

	@Test 
	public void testChoiceDAOBasics() {
	
		Choice choice1 = new Choice("001", "ChoiceDAOTest1",  5, "ChoiceDAOTest1Description", 1506002170200l, false, 0, 0);
		Choice choice2 = new Choice("002", "ChoiceDAOTest2",  6, "ChoiceDAOTest2Description", 1506002170300l, true, 1606002170700l, 69);
	
		/*Test addChoice1*/
		ChoiceDAO dao = new ChoiceDAO();
		try {
			dao.addChoice(choice1);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Choice WHERE choice_ID=001;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			Choice result = dao.generateChoice(rs);
			Assert.assertEquals("001", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest1", result.getChoiceName());
			Assert.assertEquals( 5, result.getMaxParticipants());
			Assert.assertEquals( "ChoiceDAOTest1Description", result.getChoiceDescription());
			Assert.assertEquals( 1506002170200l, result.getDateCreated());
			Assert.assertEquals( false, result.getIsCompleted());
			Assert.assertEquals( 0, result.getDateCompleted());
			Assert.assertEquals( 0, result.getSelectedAlternativeID());
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test addChoice2*/
		try {
			dao.addChoice(choice2);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Choice WHERE choice_ID=001;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			Choice result = dao.generateChoice(rs);
			Assert.assertEquals("002", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest2", result.getChoiceName());
			Assert.assertEquals( 6, result.getMaxParticipants());
			Assert.assertEquals( "ChoiceDAOTest2Description", result.getChoiceDescription());
			Assert.assertEquals( 1506002170300l, result.getDateCreated());
			Assert.assertEquals( true, result.getIsCompleted());
			Assert.assertEquals( 1606002170700l, result.getDateCompleted());
			Assert.assertEquals( 69, result.getSelectedAlternativeID());
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test getChoice1*/
		try {
			Choice result = dao.getChoice(choice1.getChoiceID());
			Assert.assertEquals("001", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest1", result.getChoiceName());
			Assert.assertEquals( 5, result.getMaxParticipants());
			Assert.assertEquals( "ChoiceDAOTest1Description", result.getChoiceDescription());
			Assert.assertEquals( 1506002170200l, result.getDateCreated());
			Assert.assertEquals( false, result.getIsCompleted());
			Assert.assertEquals( 0, result.getDateCompleted());
			Assert.assertEquals( 0, result.getSelectedAlternativeID());
			
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test getChoice2*/
		try {
			Choice result = dao.getChoice(choice1.getChoiceID());
			Assert.assertEquals("002", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest2", result.getChoiceName());
			Assert.assertEquals( 6, result.getMaxParticipants());
			Assert.assertEquals( "ChoiceDAOTest2Description", result.getChoiceDescription());
			Assert.assertEquals( 1506002170300l, result.getDateCreated());
			Assert.assertEquals( true, result.getIsCompleted());
			Assert.assertEquals( 1606002170700l, result.getDateCompleted());
			Assert.assertEquals( 69, result.getSelectedAlternativeID());
			
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test generateChoiceInfo1*/
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Choice WHERE choice_ID=001;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			ChoiceInfo result = dao.generateChoiceInfo(rs);
			Assert.assertEquals("001", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest1Description", result.getDescription());
			Assert.assertEquals( 1506002170200l, result.getCreationDate());
			Assert.assertEquals( false, result.getIsCompleted());
			Assert.assertEquals( 0, result.getCompletionDate());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
}
