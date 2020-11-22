package edu.wpi.cs.yaml.demo.db;

import java.sql.*;

import edu.wpi.cs.yaml.demo.model.Choice;


public class ChoiceDAO { 

	java.sql.Connection conn;
	
	final String tblName = "Choices";   // Exact capitalization

    public ChoiceDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public Choice getChoice(String choice_ID) throws Exception {
        
        try {
        	Choice choice = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_ID=?;");
            ps.setString(1,  choice_ID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	choice = generateChoice(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return choice;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting choice: " + e.getMessage());
        }
    }
    
    public int getMaxParticipants(String choice_ID) throws Exception {
    	 try {
             PreparedStatement ps = conn.prepareStatement("SELECT choice_maxParticipants FROM " + tblName + " WHERE choice_ID=?;");
             ps.setString(1,  choice_ID);
             ResultSet resultSet = ps.executeQuery();
             
             return resultSet.getInt("choice_maxParticipants");

         } catch (Exception e) {
         	e.printStackTrace();
             throw new Exception("Failed in getting maxParticipants for choice: " + e.getMessage());
         }
    	
    }
/*    public boolean updateChoice(Choice constant) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET value=? WHERE name=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, constant.value);
            ps.setString(2, constant.name);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }*/
    public boolean deleteChoice(String choiceID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE choice_ID = ?;");
            ps.setString(1, choiceID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete choice: " + e.getMessage());
        }
    }


    public boolean addChoice(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_ID = ?;");
            ps.setString(1, choice.choiceID);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
            	Choice c = generateChoice(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (choice_ID, choice_name, choice_maxParticipants, choice_description, creation_time, choice_isCompleted, completion_time, chosen_alternative) values(?,?,?,?,?,?,?,?);");
            ps.setString(1,  choice.choiceID);
            ps.setString(2,  choice.choiceName);
            ps.setInt(3, choice.maxParticipants);
            ps.setString(4, choice.choiceDescription);
            ps.setTimestamp(5, new Timestamp(choice.dateCreated));
            ps.setBoolean(6, false);     //a newly created choice is not completed
            ps.setTimestamp(7, null);    //it does not have a completion date
            ps.setString(8, null);       //it does not have a selected alternative
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create choice: " + e.getMessage());
        }
    }


    
    private Choice generateChoice(ResultSet resultSet) throws Exception {
    	Choice returnChoice = new Choice();
    	returnChoice.choiceID = resultSet.getString("choice_ID");
    	returnChoice.choiceName = resultSet.getString("choice_name");
    	returnChoice.maxParticipants = resultSet.getInt("choice_maxParticipants");
    	returnChoice.choiceDescription = resultSet.getString("choice_description");
    	returnChoice.dateCreated = resultSet.getTimestamp("creation_time").getTime();
    	returnChoice.isCompleted = resultSet.getBoolean("choice_isCompleted");
    	returnChoice.dateCompleted = resultSet.getTimestamp("completion_time").getTime();
    	returnChoice.selectedAlternativeID = resultSet.getString("chosen_alternative");
        return returnChoice;
    }

}