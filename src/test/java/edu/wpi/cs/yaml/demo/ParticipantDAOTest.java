package edu.wpi.cs.yaml.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.DatabaseUtil;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.model.Participant;

public class ParticipantDAOTest {
	
	@Test 
	public void testChoiceDAOBasics() {
		java.sql.Connection conn;
		
		try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}

		Choice choice1 = new Choice("001", "ChoiceDAOTest1",  5, "ChoiceDAOTest1Description", 1507019912630l, false, 0, 0);
		Choice choice2 = new Choice("002", "ChoiceDAOTest2",  5, "ChoiceDAOTest2Description", 1507019912630l, false, 0, 0);
		/*addChoice1*/
		ChoiceDAO choiceDao = new ChoiceDAO();
		try {
			choiceDao.addChoice(choice1);
			choiceDao.addChoice(choice2);
		} catch (Exception e) {
			Assert.fail();
		}
		
		Participant part1 = new Participant("001", "PartName1", "PartPass1");
		Participant part2 = new Participant("001", "PartName2", "PartPass2");

		Participant part3 = new Participant("002", "PartName3", "PartPass3");

    	/*test addParticipant and generateParticipant*/
    	ParticipantDAO partDao = new ParticipantDAO();
		try {
			partDao.addParticipant(part1);
			partDao.addParticipant(part2);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Participants WHERE choice_ID=001;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			List<Participant> result = new ArrayList<Participant> ();
			while (rs.next()) {result.add(partDao.generateParticipant(rs));}

			Assert.assertEquals("001", result.get(0).getChoiceID());
			Assert.assertEquals("PartName1", result.get(0).getName());
			Assert.assertEquals("PartPass1", result.get(0).getPassword());
			
			Assert.assertEquals("001", result.get(1).getChoiceID());
			Assert.assertEquals("PartName2", result.get(1).getName());
			Assert.assertEquals("PartPass2", result.get(1).getPassword());;
						
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test getParticipants, getParticipantNameFromID, getParticipantIDFromChoiceIDAndParticipantName and belongsToChoiceID*/
		try {
			List<Participant> result = partDao.getParticipants(choice1.getChoiceID());
			Assert.assertEquals("001", result.get(0).getChoiceID());
			Assert.assertEquals("PartName1", result.get(0).getName());
			Assert.assertEquals("PartPass1", result.get(0).getPassword());
			
			Assert.assertEquals("001", result.get(1).getChoiceID());
			Assert.assertEquals("PartName2", result.get(1).getName());
			Assert.assertEquals("PartPass2", result.get(1).getPassword());
			
			Assert.assertEquals("PartName1", partDao.getParticipantNameFromID(result.get(0).getParticipantID()));
			Assert.assertEquals("PartName2", partDao.getParticipantNameFromID(result.get(1).getParticipantID()));
			
			Assert.assertEquals(result.get(0).getParticipantID(), partDao.getParticipantIDFromChoiceIDAndParticipantName("001", "PartName1"));
			Assert.assertEquals(result.get(1).getParticipantID(), partDao.getParticipantIDFromChoiceIDAndParticipantName("001", "PartName2"));
		
			/*test belongsToChoiceID*/

			choiceDao.addChoice(choice2);
			partDao.addParticipant(part3);
			List<Participant> result2 = partDao.getParticipants(choice2.getChoiceID());
			
			Assert.assertTrue(partDao.belongsToChoiceID("001", result.get(0).getParticipantID()));
			Assert.assertTrue(partDao.belongsToChoiceID("001", result.get(1).getParticipantID()));
			Assert.assertTrue(partDao.belongsToChoiceID("002", result2.get(0).getParticipantID()));

			Assert.assertFalse(partDao.belongsToChoiceID("002", result.get(0).getParticipantID()));
			Assert.assertFalse(partDao.belongsToChoiceID("002", result.get(1).getParticipantID()));
			Assert.assertFalse(partDao.belongsToChoiceID("001", result2.get(0).getParticipantID()));
		} catch (Exception e) {
			Assert.fail();
		}
		
		
		/*Delete choice*/
		try {
			choiceDao.deleteChoice(choice1.getChoiceID());
			choiceDao.deleteChoice(choice2.getChoiceID());
		} catch (Exception e) {
			Assert.fail();
		}
		
	}
}
