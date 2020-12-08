package edu.wpi.cs.yaml.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.DatabaseUtil;
import edu.wpi.cs.yaml.demo.db.FeedbackDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.model.Feedback;
import edu.wpi.cs.yaml.demo.model.FeedbackInfo;
import edu.wpi.cs.yaml.demo.model.Participant;


public class FeedbackDAOTest {
	@Test 
	public void testChoiceDAOBasics() {
		java.sql.Connection conn;

		try  {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
		
		Choice choice1 = new Choice("001", "ChoiceDAOTest1",  5, "ChoiceDAOTest1Description", 1507019912630l, false, 0, 0);
		/*addChoice1*/
		ChoiceDAO choiceDao = new ChoiceDAO();
		try {
			choiceDao.addChoice(choice1);
		} catch (Exception e) {
			Assert.fail();
		}
		
		Alternative alt1 = new Alternative("001", "alt1_name", "alt1_description");
    	Alternative alt2 = new Alternative("001", "alt2_name", "alt2_description");
    	/*add Alternatives*/
		AlternativeDAO altDao = new AlternativeDAO();
		try {
			altDao.addAlternative(alt1);
			altDao.addAlternative(alt2);
			alt1 = altDao.getAlternatives("001").get(0);
			alt2 = altDao.getAlternatives("001").get(1);
		} catch (Exception e) {
			Assert.fail();
		}
		
		Participant part1 = new Participant("001", "PartName1", "PartPass1");
		Participant part2 = new Participant("001", "PartName2", "PartPass2");
		Participant part3 = new Participant("001", "PartName3", "PartPass3");
		/*add Participants*/
		ParticipantDAO partDao = new ParticipantDAO();
		try {
			partDao.addParticipant(part1);
			partDao.addParticipant(part2);
			partDao.addParticipant(part3);
			part1 = partDao.getParticipants("001").get(0);
			part2 = partDao.getParticipants("001").get(1);
			part3 = partDao.getParticipants("001").get(2);
		} catch (Exception e) {
			Assert.fail();
		}	
		
		Feedback feedback1 = new Feedback(alt1.getAlternativeID(), part1.getParticipantID(),"feedbackText1", 1507019912630l);
		Feedback feedback2 = new Feedback(alt1.getAlternativeID(), part2.getParticipantID(),"feedbackText2", 1507019912630l);
		Feedback feedback3 = new Feedback(alt1.getAlternativeID(), part3.getParticipantID(),"feedbackText3", 1507019912630l);
		
		Feedback feedback4 = new Feedback(alt2.getAlternativeID(), part1.getParticipantID(),"feedbackText4", 1507019912630l);
		Feedback feedback5 = new Feedback(alt2.getAlternativeID(), part2.getParticipantID(),"feedbackText5", 1507019912630l);
		Feedback feedback6 = new Feedback(alt2.getAlternativeID(), part3.getParticipantID(),"feedbackText6", 1507019912630l);
		
		Feedback feedback7 = new Feedback(alt1.getAlternativeID(), part1.getParticipantID(),"feedbackText7", 1507019912630l);
		Feedback feedback8 = new Feedback(alt2.getAlternativeID(), part2.getParticipantID(),"feedbackText8", 1507019912630l);

		/*test addFeedback and generateFeedback*/
    	FeedbackDAO feedDao = new FeedbackDAO();
		try {
			feedDao.addFeedback(feedback1);
			feedDao.addFeedback(feedback2);
			feedDao.addFeedback(feedback3);
			feedDao.addFeedback(feedback4);
			feedDao.addFeedback(feedback5);
			feedDao.addFeedback(feedback6);
			feedDao.addFeedback(feedback7);
			feedDao.addFeedback(feedback8);


			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Feedback WHERE alternative_id=?;");
			ps.setInt(1, alt1.getAlternativeID());
			ResultSet rs = ps.executeQuery();
			/*Test generateFeedback*/
			List<Feedback> result = new ArrayList<Feedback> ();
			while (rs.next()) {result.add(feedDao.generateFeedback(rs));}

			Assert.assertEquals(alt1.getAlternativeID(), result.get(0).getAlternativeID());
			Assert.assertEquals(alt1.getAlternativeID(), result.get(1).getAlternativeID());
			Assert.assertEquals(alt1.getAlternativeID(), result.get(2).getAlternativeID());
			Assert.assertEquals(alt1.getAlternativeID(), result.get(3).getAlternativeID());
			
			Assert.assertEquals(part1.getParticipantID(), result.get(0).getParticipantID());
			Assert.assertEquals(part2.getParticipantID(), result.get(1).getParticipantID());
			Assert.assertEquals(part3.getParticipantID(), result.get(2).getParticipantID());
			Assert.assertEquals(part1.getParticipantID(), result.get(3).getParticipantID());
			
			Assert.assertEquals("feedbackText1", result.get(0).getFeedbackText());
			Assert.assertEquals("feedbackText2", result.get(1).getFeedbackText());
			Assert.assertEquals("feedbackText3", result.get(2).getFeedbackText());
			Assert.assertEquals("feedbackText7", result.get(3).getFeedbackText());
			
			Assert.assertEquals(1507019912630l, result.get(0).getFeedbackTimestamp());
			Assert.assertEquals(1507019912630l, result.get(1).getFeedbackTimestamp());
			Assert.assertEquals(1507019912630l, result.get(2).getFeedbackTimestamp());
			Assert.assertEquals(1507019912630l, result.get(3).getFeedbackTimestamp());

			
			ps.setInt(1, alt2.getAlternativeID());
			rs = ps.executeQuery();
			
			/*Test generateFeedback*/
			result = new ArrayList<Feedback> ();
			while (rs.next()) {result.add(feedDao.generateFeedback(rs));}

			Assert.assertEquals(alt2.getAlternativeID(), result.get(0).getAlternativeID());
			Assert.assertEquals(alt2.getAlternativeID(), result.get(1).getAlternativeID());
			Assert.assertEquals(alt2.getAlternativeID(), result.get(2).getAlternativeID());
			Assert.assertEquals(alt2.getAlternativeID(), result.get(3).getAlternativeID());
			
			Assert.assertEquals(part1.getParticipantID(), result.get(0).getParticipantID());
			Assert.assertEquals(part2.getParticipantID(), result.get(1).getParticipantID());
			Assert.assertEquals(part3.getParticipantID(), result.get(2).getParticipantID());
			Assert.assertEquals(part2.getParticipantID(), result.get(3).getParticipantID());
			
			Assert.assertEquals("feedbackText4", result.get(0).getFeedbackText());
			Assert.assertEquals("feedbackText5", result.get(1).getFeedbackText());
			Assert.assertEquals("feedbackText6", result.get(2).getFeedbackText());
			Assert.assertEquals("feedbackText8", result.get(3).getFeedbackText());
			
			Assert.assertEquals(1507019912630l, result.get(0).getFeedbackTimestamp());
			Assert.assertEquals(1507019912630l, result.get(1).getFeedbackTimestamp());
			Assert.assertEquals(1507019912630l, result.get(2).getFeedbackTimestamp());
			Assert.assertEquals(1507019912630l, result.get(3).getFeedbackTimestamp());
			
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*test getFeedbakcsForAlternative and getFeedbacks*/
		try {
		/*getVote*/
	
		/*getVotesForAlternative*/
		FeedbackInfo resultAlternative = feedDao.getFeedbackForAlternative(alt1.getAlternativeID());
		Assert.assertEquals(alt1.getAlternativeID(), resultAlternative.getAlternativeID());
		Assert.assertEquals(alt1.getTitle(), resultAlternative.getAlternativeName());
		Assert.assertEquals(part1.getName(), resultAlternative.getParticipantName().get(0));
		Assert.assertEquals(part2.getName(), resultAlternative.getParticipantName().get(1));
		Assert.assertEquals(part3.getName(), resultAlternative.getParticipantName().get(2));
		Assert.assertEquals(part1.getName(), resultAlternative.getParticipantName().get(3));
		Assert.assertEquals("feedbackText1", resultAlternative.getFeedbackText().get(0));
		Assert.assertEquals("feedbackText2", resultAlternative.getFeedbackText().get(1));
		Assert.assertEquals("feedbackText3", resultAlternative.getFeedbackText().get(2));
		Assert.assertEquals("feedbackText7", resultAlternative.getFeedbackText().get(3));
		Assert.assertTrue(1507019912630l == resultAlternative.getFeedbackTimestamp().get(0));
		Assert.assertTrue(1507019912630l == resultAlternative.getFeedbackTimestamp().get(1));
		Assert.assertTrue(1507019912630l == resultAlternative.getFeedbackTimestamp().get(2));
		Assert.assertTrue(1507019912630l == resultAlternative.getFeedbackTimestamp().get(3));
		
		/*getFeedbacks*/
		List<FeedbackInfo> resultChoice = feedDao.getFeedback(choice1.getChoiceID());
		FeedbackInfo resultAlternative1 = resultChoice.get(0);
		FeedbackInfo resultAlternative2 =  resultChoice.get(1);
		
		Assert.assertEquals(alt1.getAlternativeID(), resultAlternative1.getAlternativeID());
		Assert.assertEquals(alt1.getTitle(), resultAlternative1.getAlternativeName());
		Assert.assertEquals(part1.getName(), resultAlternative1.getParticipantName().get(0));
		Assert.assertEquals(part2.getName(), resultAlternative1.getParticipantName().get(1));
		Assert.assertEquals(part3.getName(), resultAlternative1.getParticipantName().get(2));
		Assert.assertEquals(part1.getName(), resultAlternative1.getParticipantName().get(3));
		Assert.assertEquals("feedbackText1", resultAlternative1.getFeedbackText().get(0));
		Assert.assertEquals("feedbackText2", resultAlternative1.getFeedbackText().get(1));
		Assert.assertEquals("feedbackText3", resultAlternative1.getFeedbackText().get(2));
		Assert.assertEquals("feedbackText7", resultAlternative1.getFeedbackText().get(3));
		Assert.assertTrue(1507019912630l == resultAlternative1.getFeedbackTimestamp().get(0));
		Assert.assertTrue(1507019912630l == resultAlternative1.getFeedbackTimestamp().get(1));
		Assert.assertTrue(1507019912630l == resultAlternative1.getFeedbackTimestamp().get(2));
		Assert.assertTrue(1507019912630l == resultAlternative1.getFeedbackTimestamp().get(3));
		
		Assert.assertEquals(alt2.getAlternativeID(), resultAlternative2.getAlternativeID());
		Assert.assertEquals(alt2.getTitle(), resultAlternative2.getAlternativeName());
		Assert.assertEquals(part1.getName(), resultAlternative2.getParticipantName().get(0));
		Assert.assertEquals(part2.getName(), resultAlternative2.getParticipantName().get(1));
		Assert.assertEquals(part3.getName(), resultAlternative2.getParticipantName().get(2));
		Assert.assertEquals(part2.getName(), resultAlternative2.getParticipantName().get(3));
		Assert.assertEquals("feedbackText4", resultAlternative2.getFeedbackText().get(0));
		Assert.assertEquals("feedbackText5", resultAlternative2.getFeedbackText().get(1));
		Assert.assertEquals("feedbackText6", resultAlternative2.getFeedbackText().get(2));
		Assert.assertEquals("feedbackText8", resultAlternative2.getFeedbackText().get(3));
		Assert.assertTrue(1507019912630l == resultAlternative2.getFeedbackTimestamp().get(0));
		Assert.assertTrue(1507019912630l == resultAlternative2.getFeedbackTimestamp().get(1));
		Assert.assertTrue(1507019912630l == resultAlternative2.getFeedbackTimestamp().get(2));
		Assert.assertTrue(1507019912630l == resultAlternative2.getFeedbackTimestamp().get(3));
		
		} catch (Exception e) {
		Assert.fail();
		}
		
		/*Remove choice*/
		try {
			choiceDao.deleteChoice(choice1.getChoiceID());
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
