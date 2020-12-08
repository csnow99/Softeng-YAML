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

    
    public boolean belongsToChoiceID(String choiceID, int participantID) throws Exception {
    	try {
    		Participant participant = null;
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_id=? AND participantID=?;");
    		ps.setString(1,  choiceID);
    		ps.setInt(2, participantID);
    		ResultSet resultSet = ps.executeQuery();
    		
    		while (resultSet.next()) {
    			participant = generateParticipant(resultSet);
    		}
    		resultSet.close();
    		ps.close();

    		if (participant == null) {return false;}
    		
    		return true;

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Failed in getting participant: " + e.getMessage());
    	}
    }
    
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
    
    public int getParticipantIDFromChoiceIDAndParticipantName(String choice_ID, String participant_name) throws Exception{
    	try {
    		Participant participant = null;
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_id=? AND username=?;");
    		ps.setString(1,  choice_ID);
    		ps.setString(2, participant_name);
    		ResultSet resultSet = ps.executeQuery();

    		while (resultSet.next()) {
    			participant = generateParticipant(resultSet);
    		}
    		resultSet.close();
    		ps.close();

    		return participant.getParticipantID();

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Failed in getting participant: " + e.getMessage());
    	}
    }


    public boolean addParticipant(Participant participant) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (choice_ID, username, password) values(?,?,?);");
            ps.setString(1, participant.getChoiceID());
            ps.setString(2, participant.getName());
            ps.setString(3, participant.getPassword());       
            
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