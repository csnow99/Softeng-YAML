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
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.db.VoteDAO;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.model.Participant;
import edu.wpi.cs.yaml.demo.model.Vote;
import edu.wpi.cs.yaml.demo.model.VoteInfo;

public class VoteDAOTest {

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
		
		Vote vote1 = new Vote(alt1.getAlternativeID(), part1.getParticipantID(), 1);
		Vote vote2 = new Vote(alt1.getAlternativeID(), part2.getParticipantID(), 0);
		Vote vote3 = new Vote(alt1.getAlternativeID(), part3.getParticipantID(), 0);
		Vote vote4 = new Vote(alt2.getAlternativeID(), part1.getParticipantID(), 0);
		Vote vote5 = new Vote(alt2.getAlternativeID(), part2.getParticipantID(), 1);
		Vote vote6 = new Vote(alt2.getAlternativeID(), part3.getParticipantID(), 1);
		
		/*test addVote and generateVote*/
    	VoteDAO voteDao = new VoteDAO();
		try {
			voteDao.addVote(vote1);
			voteDao.addVote(vote2);
			voteDao.addVote(vote3);
			voteDao.addVote(vote4);
			voteDao.addVote(vote5);
			voteDao.addVote(vote6);


			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Votes WHERE alternative_id=?;");
			ps.setInt(1, alt1.getAlternativeID());
			ResultSet rs = ps.executeQuery();
			/*Test generateVote*/
			List<Vote> result = new ArrayList<Vote> ();
			while (rs.next()) {result.add(voteDao.generateVote(rs));}

			Assert.assertEquals(alt1.getAlternativeID(), result.get(0).getAlternativeID());
			Assert.assertEquals(alt1.getAlternativeID(), result.get(1).getAlternativeID());
			Assert.assertEquals(alt1.getAlternativeID(), result.get(2).getAlternativeID());

			
			Assert.assertEquals(part1.getParticipantID(), result.get(0).getParticipantID());
			Assert.assertEquals(part2.getParticipantID(), result.get(1).getParticipantID());
			Assert.assertEquals(part3.getParticipantID(), result.get(2).getParticipantID());

			Assert.assertEquals(1, result.get(0).getAmendType());
			Assert.assertEquals(0, result.get(1).getAmendType());
			Assert.assertEquals(0, result.get(2).getAmendType());
			
			ps.setInt(1, alt2.getAlternativeID());
			rs = ps.executeQuery();
			
			/*Test generateVote*/
			result = new ArrayList<Vote> ();
			while (rs.next()) {result.add(voteDao.generateVote(rs));}
			Assert.assertEquals(alt2.getAlternativeID(), result.get(0).getAlternativeID());
			Assert.assertEquals(alt2.getAlternativeID(), result.get(1).getAlternativeID());
			Assert.assertEquals(alt2.getAlternativeID(), result.get(2).getAlternativeID());

			
			Assert.assertEquals(part1.getParticipantID(), result.get(0).getParticipantID());
			Assert.assertEquals(part2.getParticipantID(), result.get(1).getParticipantID());
			Assert.assertEquals(part3.getParticipantID(), result.get(2).getParticipantID());

			Assert.assertEquals(0, result.get(0).getAmendType());
			Assert.assertEquals(1, result.get(1).getAmendType());
			Assert.assertEquals(1, result.get(2).getAmendType());
			
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*test getVote, getVotesForAlternative and getVotes*/
		try {
		/*getVote*/
		Vote resultVote = voteDao.getVote(alt1.getAlternativeID(), part1.getParticipantID());
		Assert.assertEquals(alt1.getAlternativeID(), resultVote.getAlternativeID());
		Assert.assertEquals(part1.getParticipantID(), resultVote.getParticipantID());
		Assert.assertEquals(1 ,resultVote.getAmendType());
		
		/*getVotesForAlternative*/
		VoteInfo resultAlternative = voteDao.getVotesForAlternative(alt1.getAlternativeID());
		Assert.assertEquals(alt1.getAlternativeID(), resultAlternative.getAlternativeID());
		Assert.assertEquals(alt1.getTitle(), resultAlternative.getAlternativeName());
		Assert.assertEquals(1, resultAlternative.getNumUpvotes());
		Assert.assertEquals(2, resultAlternative.getNumDownvotes());
		Assert.assertEquals(part1.getName(), resultAlternative.getUpvoters().get(0));
		Assert.assertEquals(part2.getName(), resultAlternative.getdownvoters().get(0));
		Assert.assertEquals(part3.getName(), resultAlternative.getdownvoters().get(1));
		
		/*getVotes*/
		List<VoteInfo> resultChoice = voteDao.getVotes(choice1.getChoiceID());
		VoteInfo resultAlternative1 = resultChoice.get(0);
		VoteInfo resultAlternative2 =  resultChoice.get(1);
		
		Assert.assertEquals(alt1.getAlternativeID(), resultAlternative1.getAlternativeID());
		Assert.assertEquals(alt1.getTitle(), resultAlternative1.getAlternativeName());
		Assert.assertEquals(1, resultAlternative1.getNumUpvotes());
		Assert.assertEquals(2, resultAlternative1.getNumDownvotes());
		Assert.assertEquals(part1.getName(), resultAlternative1.getUpvoters().get(0));
		Assert.assertEquals(part2.getName(), resultAlternative1.getdownvoters().get(0));
		Assert.assertEquals(part3.getName(), resultAlternative1.getdownvoters().get(1));
		
		Assert.assertEquals(alt2.getAlternativeID(), resultAlternative2.getAlternativeID());
		Assert.assertEquals(alt2.getTitle(), resultAlternative2.getAlternativeName());
		Assert.assertEquals(2, resultAlternative2.getNumUpvotes());
		Assert.assertEquals(1, resultAlternative2.getNumDownvotes());
		Assert.assertEquals(part1.getName(), resultAlternative2.getdownvoters().get(0));
		Assert.assertEquals(part2.getName(), resultAlternative2.getUpvoters().get(0));
		Assert.assertEquals(part3.getName(), resultAlternative2.getUpvoters().get(1));
	
		} catch (Exception e) {
		Assert.fail();
		}
		
		/*test deleteVote*/
		try {
		voteDao.deleteVote(alt1.getAlternativeID(), part1.getParticipantID());
		voteDao.deleteVote(alt2.getAlternativeID(), part2.getParticipantID());
		VoteInfo resultAlternative1 = voteDao.getVotesForAlternative(alt1.getAlternativeID());
		Assert.assertEquals(alt1.getAlternativeID(), resultAlternative1.getAlternativeID());
		Assert.assertEquals(alt1.getTitle(), resultAlternative1.getAlternativeName());
		Assert.assertEquals(0, resultAlternative1.getNumUpvotes());
		Assert.assertEquals(2, resultAlternative1.getNumDownvotes());
		Assert.assertEquals(0, resultAlternative1.getUpvoters().size());
		Assert.assertEquals(part2.getName(), resultAlternative1.getdownvoters().get(0));
		Assert.assertEquals(part3.getName(), resultAlternative1.getdownvoters().get(1));
		
		VoteInfo resultAlternative2 = voteDao.getVotesForAlternative(alt2.getAlternativeID());
		Assert.assertEquals(alt2.getAlternativeID(), resultAlternative2.getAlternativeID());
		Assert.assertEquals(alt2.getTitle(), resultAlternative2.getAlternativeName());
		Assert.assertEquals(1, resultAlternative2.getNumUpvotes());
		Assert.assertEquals(1, resultAlternative2.getNumDownvotes());
		Assert.assertEquals(part1.getName(), resultAlternative2.getdownvoters().get(0));
		Assert.assertEquals(part3.getName(), resultAlternative2.getUpvoters().get(0));
		
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