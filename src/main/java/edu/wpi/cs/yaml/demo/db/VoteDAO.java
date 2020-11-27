package edu.wpi.cs.yaml.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.model.Vote;

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
    
    public Vote getVote(String vote_ID) throws Exception {
        try {
        	Vote vote = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE vote_ID=?;");
            ps.setString(1,  vote_ID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	vote = generateVote(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return vote;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting choice: " + e.getMessage());
        }
    }
    
    
    public boolean addVote(Vote vote) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (participant_id,alternative_id,vote_type) values(?,?,?);");
            ps.setString(1, vote.participantID);
            ps.setString(2, vote.alternativeID);
            ps.setInt(3, vote.amendType);       
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert vote: " + e.getMessage());
        }
    }
    public boolean deleteVote(int voteID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE vote_ID = ?;");
            ps.setInt(1, voteID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete choice: " + e.getMessage());
        }
    }
    public List<Vote> getVotes(String alternative_id) throws Exception {
        
        List<Vote> votes = new ArrayList<Vote>();
        try {
            Statement statement = conn.createStatement();
          
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_id=?;");
            ps.setString(1,  alternative_id);
            //String query = "SELECT * FROM " + tblName + "GROUP BY alternative_id HAVING MAX(choice_id) = " + choiceID + " AND MIN(choice_id) = "+choiceID + ";";
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
             Vote c = generateVote(resultSet);
            	votes.add(c);
            }
            resultSet.close();
            statement.close();
            return votes;

        } catch (Exception e) {
            throw new Exception("Failed in getting alternatives: " + e.getMessage());
        }
    }
    private Vote generateVote(ResultSet resultSet) throws Exception {
        int voteID = resultSet.getInt("vote_id");
    	String participantID = resultSet.getString("participant_id");
    	String alternativeID = resultSet.getString("alternative_id");
    	int amendType = resultSet.getInt("vote_type");
        return new Vote(voteID, participantID, alternativeID, amendType);
    }
}

