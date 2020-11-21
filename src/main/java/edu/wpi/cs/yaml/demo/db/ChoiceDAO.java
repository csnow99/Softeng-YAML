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
            ps.setTimestamp(5, choice.timeCreated);
            ps.setBoolean(6, false);     //a newly created choice is not completed
            ps.setTimestamp(7, null);    //it does not have a completion date
            ps.setString(8, null);       //it does not have a selected alternative
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create choice: " + e.getMessage());
        }
    }

   /* public List<Choice> getAllConstants() throws Exception {
        
        List<Choice> allConstants = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
            	Choice c = generateChoice(resultSet);
                allConstants.add(c);
            }
            resultSet.close();
            statement.close();
            return allConstants;

        } catch (Exception e) {
            throw new Exception("Failed in getting constants: " + e.getMessage());
        }
    }*/
    
    private Choice generateChoice(ResultSet resultSet) throws Exception {
        String choiceID = resultSet.getString("choice_ID");
    	String choiceName = resultSet.getString("choice_name");
    	int maxParticipants = resultSet.getInt("choice_maxParticipants");
    	String choiceDescription = resultSet.getString("choice_description");
    	boolean isCompleted = resultSet.getBoolean("choice_isCompleted");
        return new Choice(choiceID, choiceName, maxParticipants, choiceDescription, isCompleted);
    }

}