package edu.wpi.cs.yaml.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.DatabaseUtil;
import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.model.ChoiceInfo;

public class ChoiceDAOTest extends LambdaTest {
	
	@Test 
	public void testChoiceDAOBasics() {
		java.sql.Connection conn;
		
		try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}

		Choice choice1 = new Choice("001", "ChoiceDAOTest1",  5, "ChoiceDAOTest1Description", 1507019912630l, false, 0, 0);
		Choice choice2 = new Choice("002", "ChoiceDAOTest2",  6, "ChoiceDAOTest2Description", 1507019912630l, true, 1606002170260l, 69);
	
		/*Test addChoice1*/
		ChoiceDAO dao = new ChoiceDAO();
		try {
			dao.addChoice(choice1);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Choices WHERE choice_ID=001;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			Choice result = null;
			while (rs.next()) {result = dao.generateChoice(rs);}

			Assert.assertEquals("001", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest1", result.getChoiceName());
			Assert.assertEquals( 5, result.getMaxParticipants());
			Assert.assertEquals( "ChoiceDAOTest1Description", result.getChoiceDescription());
			Assert.assertEquals( 1507019912630l, result.getDateCreated());
			Assert.assertEquals( false, result.getIsCompleted());
			Assert.assertEquals( 0, result.getDateCompleted());
			Assert.assertEquals( 0, result.getSelectedAlternativeID());
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test addChoice2*/
		try {
			dao.addChoice(choice2);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Choices WHERE choice_ID=002;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			Choice result = null;
			while (rs.next()) {result = dao.generateChoice(rs);}
			
			Assert.assertEquals("002", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest2", result.getChoiceName());
			Assert.assertEquals( 6, result.getMaxParticipants());
			Assert.assertEquals( "ChoiceDAOTest2Description", result.getChoiceDescription());
			Assert.assertEquals( 1507019912630l, result.getDateCreated());
			Assert.assertEquals( true, result.getIsCompleted());
			Assert.assertEquals( 1606002170260l, result.getDateCompleted());
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
			Assert.assertEquals( 1507019912630l, result.getDateCreated());
			Assert.assertEquals( false, result.getIsCompleted());
			Assert.assertEquals( 0, result.getDateCompleted());
			Assert.assertEquals( 0, result.getSelectedAlternativeID());
			
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test getChoice2*/
		try {
			Choice result = dao.getChoice(choice2.getChoiceID());
			Assert.assertEquals("002", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest2", result.getChoiceName());
			Assert.assertEquals( 6, result.getMaxParticipants());
			Assert.assertEquals( "ChoiceDAOTest2Description", result.getChoiceDescription());
			Assert.assertEquals( 1507019912630l, result.getDateCreated());
			Assert.assertEquals( true, result.getIsCompleted());
			Assert.assertEquals( 1606002170260l, result.getDateCompleted());
			Assert.assertEquals( 69, result.getSelectedAlternativeID());
			
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test generateChoiceInfo1*/
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Choices WHERE choice_ID=001;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			ChoiceInfo result = null;
			while (rs.next()) {result = dao.generateChoiceInfo(rs);}
			
			Assert.assertEquals("001", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest1D", result.getDescription());
			Assert.assertEquals( 1507019912630l, result.getCreationDate());
			Assert.assertEquals( false, result.getIsCompleted());
			Assert.assertEquals( 0, result.getCompletionDate());
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test generateChoiceInfo2*/
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Choices WHERE choice_ID=002;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			ChoiceInfo result = null;
			while (rs.next()) {result = dao.generateChoiceInfo(rs);}
			
			Assert.assertEquals("002", result.getChoiceID());
			Assert.assertEquals( "ChoiceDAOTest2D", result.getDescription());
			Assert.assertEquals( 1507019912630l, result.getCreationDate());
			Assert.assertEquals( true, result.getIsCompleted());
			Assert.assertEquals( 1606002170260l, result.getCompletionDate());
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test getMaxParticipants and getIsCompleted*/
		try {
			Assert.assertEquals( 5, dao.getMaxParticipants(choice1.getChoiceID()));
			Assert.assertEquals( false, dao.getIsCompleted(choice1.getChoiceID()));
			
			Assert.assertEquals( 6, dao.getMaxParticipants(choice2.getChoiceID()));
			Assert.assertEquals( true, dao.getIsCompleted(choice2.getChoiceID()));
		} catch (Exception e) {
			Assert.fail();
		}
		
		
		/*Test getAllChoices, deleteChoice and DeleteOld choices*/
		try {
			Choice choice3 = new Choice("003", "ChoiceDAOTest3",  7, "ChoiceDAOTest2Description", 1606002170300l, false, 0, 0);
			int initialNumOfChoices = dao.getAllChoices().size();
			dao.addChoice(choice3);
			int difference1  = dao.getAllChoices().size() - initialNumOfChoices;
			Assert.assertEquals(1, difference1);
			dao.deleteChoice(choice3.getChoiceID());
			int difference2 = dao.getAllChoices().size() - initialNumOfChoices;
			Assert.assertEquals(0, difference2);
			dao.deleteChoicesOlderThan(100);
			int difference3 = dao.getAllChoices().size() - initialNumOfChoices;
			Assert.assertEquals(-2, difference3);
		} catch (Exception e) {
			
		}
		
	}
	
}
