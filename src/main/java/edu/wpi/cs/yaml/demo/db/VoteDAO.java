package edu.wpi.cs.yaml.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Vote;
import edu.wpi.cs.yaml.demo.model.VoteInfo;

public class VoteDAO {
	
	java.sql.Connection conn;
	
	final String tblName = "Votes";   // Exact capitalization

    public VoteDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    public Vote getVote(int alternative_ID, int participant_ID) throws Exception {
        try {
        	Vote vote = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_id = ? AND participant_id = ?;");
            ps.setInt(1,  alternative_ID);
            ps.setInt(2,  participant_ID);
            
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	vote = generateVote(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return vote;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting vote: " + e.getMessage());
        }
    }
    public VoteInfo getVotesForAlternative(int alternative_ID) throws Exception {
    try {
    	int numUpvotes = 0;
    	int numDownvotes = 0;
    	List<String> upvoters = new ArrayList<String>();
    	List<String> downvoters = new ArrayList<String>();
    	ParticipantDAO participantDAO = new ParticipantDAO();
    	AlternativeDAO alternativeDAO = new AlternativeDAO();
    	Statement statement = conn.createStatement();
        
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_id=?;");
        ps.setInt(1,  alternative_ID);
        
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
         Vote v = generateVote(resultSet);
        	if(v.amendType == 1) {
        		upvoters.add(participantDAO.getParticipantNameFromID(v.getParticipantID()));
        		numUpvotes++;
        	}
        	else if(v.amendType == 0) {
        		downvoters.add(participantDAO.getParticipantNameFromID(v.getParticipantID()));
        		numDownvotes++;
        	}
        }
        resultSet.close();
        statement.close();
        String alternativeTitle = alternativeDAO.getAlternativeTitleFromAlternativeID(alternative_ID);
        return new VoteInfo(alternative_ID, alternativeTitle, numUpvotes, numDownvotes, upvoters, downvoters);

    } catch (Exception e) {
        throw new Exception("Failed in getting votes: " + e.getMessage());
    }
    
    }
    
    public List<VoteInfo> getVotes(String choice_ID) throws Exception {
    	List<VoteInfo> votes = new ArrayList<VoteInfo>();
    	AlternativeDAO altDAO = new AlternativeDAO();
    	
    	List<Alternative> alternatives = altDAO.getAlternatives(choice_ID);
    	for(Alternative alt : alternatives) {
    		votes.add(getVotesForAlternative(alt.getAlternativeID()));
    	}
    	
    	return votes;
    }
    
    public boolean addVote(Vote vote) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (participant_id,alternative_id,vote_type) values(?,?,?);");
            ps.setInt(1, vote.participantID);
            ps.setInt(2, vote.alternativeID);
            ps.setInt(3, vote.amendType);       
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert vote: " + e.getMessage());
        }
    }
    public boolean deleteVote(int alternativeID, int participantID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE alternative_id = ? AND participant_id = ?;");
            ps.setInt(1, alternativeID);
            ps.setInt(2, participantID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete vote: " + e.getMessage());
        }
    }

    public boolean deleteVote(String alternativeID, String participantID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE alternative_id = ? AND participant_id = ?;");
            ps.setString(1, alternativeID);
            ps.setString(2, participantID);
            int numAffected = ps.executeUpdate();
            ps.close();

            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete vote: " + e.getMessage());
        }
    }
    
    private Vote generateVote(ResultSet resultSet) throws Exception {
        int voteID = resultSet.getInt("vote_id");
    	int participantID = resultSet.getInt("participant_id");
    	int alternativeID = resultSet.getInt("alternative_id");
    	int amendType = resultSet.getInt("vote_type");
        return new Vote(voteID, participantID, alternativeID, amendType);
    }
}

