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
    
    public Vote getVote(String alternative_ID, String participant_ID) throws Exception {
        try {
        	Vote vote = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_id = ? AND participant_id = ?;");
            ps.setString(1,  alternative_ID);
            ps.setString(2,  participant_ID);
            
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
    
    public List<Object> getVotes(String alternative_ID) throws Exception {
    	List<Object> votes = new ArrayList<Object>();
    	int numUpvotes = 0;
    	int numDownvotes = 0;
    	List<Vote> upvotes = new ArrayList<Vote>();
    	List<Vote> downvotes = new ArrayList<Vote>();
        try {
            Statement statement = conn.createStatement();
          
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_id=?;");
            ps.setString(1,  alternative_ID);
            
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
             Vote c = generateVote(resultSet);
            	if(c.amendType == 1) {
            		upvotes.add(c);
            		numUpvotes++;
            	}
            	else if(c.amendType == 0) {
            		downvotes.add(c);
            		numDownvotes++;
            	}
            }
            resultSet.close();
            statement.close();
            //votes.add()
            votes.add(numUpvotes);
            votes.add(numDownvotes);
            votes.add(upvotes);
            votes.add(downvotes);
            return votes;

        } catch (Exception e) {
            throw new Exception("Failed in getting votes: " + e.getMessage());
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
    	String participantID = resultSet.getString("participant_id");
    	String alternativeID = resultSet.getString("alternative_id");
    	int amendType = resultSet.getInt("vote_type");
        return new Vote(voteID, participantID, alternativeID, amendType);
    }
}

