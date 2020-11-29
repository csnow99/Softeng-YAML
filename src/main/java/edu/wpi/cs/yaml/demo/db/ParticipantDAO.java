package edu.wpi.cs.yaml.demo.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.model.Participant;

public class ParticipantDAO { 

	java.sql.Connection conn;
	
	final String tblName = "Participants";   // Exact capitalization

    public ParticipantDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    /*public Alternative getAlternative(String alternative_ID) throws Exception {
        
        try {
        	Alternative alternative = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_ID=?;");
            ps.setString(1,  alternative_ID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	alternative = generateAlternative(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return alternative;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting choice: " + e.getMessage());
        }
    }*/
    
    
    public String getParticipantNameFromID(int participant_ID) throws Exception {
    	try {
    		Participant participant = null;
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE participantID=?;");
    		ps.setInt(1,  participant_ID);
    		ResultSet resultSet = ps.executeQuery();

    		while (resultSet.next()) {
    			participant = generateParticipant(resultSet);
    		}
    		resultSet.close();
    		ps.close();

    		return participant.getName();

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Failed in getting participant: " + e.getMessage());
    	}
    }


    public boolean addParticipant(Participant participant) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (choice_ID, username, password) values(?,?,?);");
            ps.setString(1, participant.choiceID);
            ps.setString(2, participant.username);
            ps.setString(3, participant.password);       
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert participant: " + e.getMessage());
        }
    }

    public List<Participant> getParticipants(String choiceID) throws Exception {
        
        List<Participant> participants = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
          
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_id=?;");
            ps.setString(1,  choiceID);
            //String query = "SELECT * FROM " + tblName + "GROUP BY alternative_id HAVING MAX(choice_id) = " + choiceID + " AND MIN(choice_id) = "+choiceID + ";";
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
            	Participant p = generateParticipant(resultSet);
            	participants.add(p);
            }
            resultSet.close();
            statement.close();
            return participants;

        } catch (Exception e) {
            throw new Exception("Failed in getting alternatives: " + e.getMessage());
        }
    }
    
    private Participant generateParticipant(ResultSet resultSet) throws Exception {
        int participantID = resultSet.getInt("participantID");
    	String choiceID = resultSet.getString("choice_id");
    	String alternativeName = resultSet.getString("username");
    	String alternativeDescription = resultSet.getString("password");
        return new Participant(participantID, choiceID, alternativeName, alternativeDescription);
    }

}