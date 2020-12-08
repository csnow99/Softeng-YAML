package edu.wpi.cs.yaml.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.model.Choice;
import edu.wpi.cs.yaml.demo.model.ChoiceInfo;


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
             Choice choice = null;

    		 PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_ID=?;");
             ps.setString(1,  choice_ID);
             ResultSet resultSet = ps.executeQuery();
             
             while(resultSet.next()) {
            	 choice = generateChoice(resultSet);
             }
             
             resultSet.close();
             ps.close();
             
             if(choice == null) {throw new Exception("Couldn't find choice with ID: " + choice_ID);}
             
             return choice.getMaxParticipants();

         } catch (Exception e) {
         	e.printStackTrace();
             throw new Exception("Failed in getting maxParticipants for choice: " + choice_ID + e.getMessage());
         }
    	
    }
    
    public boolean getIsCompleted(String choice_ID) throws Exception {
    	 try {
             Choice choice = null;

    		 PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_ID=?;");
             ps.setString(1,  choice_ID);
             ResultSet resultSet = ps.executeQuery();
             
             while(resultSet.next()) {
            	 choice = generateChoice(resultSet);
             }
             
             resultSet.close();
             ps.close();
             
             if(choice == null) {throw new Exception("Couldn't find choice with ID: " + choice_ID);}
             
             return choice.getIsCompleted();

         } catch (Exception e) {
         	e.printStackTrace();
             throw new Exception("Failed in getting completion status for choice: " + choice_ID + e.getMessage());
         }
    }
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

    /*Adds whichever choice is passed to it to the Choices table
     * Blindly copies info provided in the choice object
     * Make sure that the choice object contains accurate info 
     * */
    public boolean addChoice(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_ID = ?;");
            ps.setString(1, choice.getChoiceID());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
            	Choice c = generateChoice(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (choice_ID, choice_name, choice_maxParticipants, choice_description, creation_time, choice_isCompleted, completion_time, chosen_alternative) values(?,?,?,?,?,?,?,?);");
            ps.setString(1,  choice.getChoiceID());
            ps.setString(2,  choice.getChoiceName());
            ps.setInt(3, choice.getMaxParticipants());
            ps.setString(4, choice.getChoiceDescription());
            if (choice.getDateCreated() == 0) 
            	ps.setTimestamp(5, null);
            else 
                ps.setTimestamp(5, new Timestamp(choice.getDateCreated()));
            ps.setBoolean(6, choice.getIsCompleted());    
            if (choice.getDateCompleted() == 0) 
            	ps.setTimestamp(7, null);
            else  
                ps.setTimestamp(7, new Timestamp(choice.getDateCompleted()));               
            ps.setInt(8, choice.getSelectedAlternativeID());       
            

            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create choice: " + e.getMessage());
        }
    }


    
    public Choice generateChoice(ResultSet resultSet) throws Exception {
    	Choice returnChoice = new Choice();
    	returnChoice.setChoiceID(resultSet.getString("choice_ID"));
    	returnChoice.setChoiceName(resultSet.getString("choice_name"));
    	returnChoice.setMaxParticipants(resultSet.getInt("choice_maxParticipants"));
    	returnChoice.setChoiceDescription(resultSet.getString("choice_description"));
    	Timestamp timeCreated =  resultSet.getTimestamp("creation_time");
    	if (timeCreated == null) {returnChoice.setDateCreated(0);}
    	else {returnChoice.setDateCreated(timeCreated.getTime());}
    	returnChoice.setIsCompleted(resultSet.getBoolean("choice_isCompleted"));
    	Timestamp timeCompleted =  resultSet.getTimestamp("completion_time");
    	if (timeCompleted == null) {returnChoice.setDateCompleted(0);}
    	else {returnChoice.setDateCompleted(timeCompleted.getTime());}
    	returnChoice.setSelectedAlternativeID(resultSet.getInt("chosen_alternative"));
        return returnChoice;
    }

    public ChoiceInfo generateChoiceInfo(ResultSet resultSet) throws Exception {

        String choiceID = resultSet.getString("choice_id");
        String description = resultSet.getString("choice_description");
        Timestamp timeCreated = resultSet.getTimestamp("creation_time");
        long createTime = 0;
        if (timeCreated != null) { createTime = timeCreated.getTime(); }
        boolean isComplete = resultSet.getBoolean("choice_isCompleted");
        Timestamp timeCompleted = resultSet.getTimestamp("completion_time");
        long completeTime = 0;
        if (timeCompleted != null) { completeTime = timeCompleted.getTime(); }

        ChoiceInfo choiceInfo = new ChoiceInfo(choiceID, description, createTime, completeTime, isComplete);

        return choiceInfo;
    }

    public void deleteChoicesOlderThan(int days) throws Exception{
    	try {
            PreparedStatement ps = conn.prepareStatement("delete from Choices where datediff(creation_time, date_sub(now(), interval ? day)) <= 0;");
            ps.setInt(1,  days);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices older than "+days+" days");
        }
    }
    
    public List<ChoiceInfo> getAllChoices() throws Exception {

        List<ChoiceInfo> allChoices = new ArrayList<>();

        try{
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                ChoiceInfo c = generateChoiceInfo(resultSet);
                System.out.println(c.toString());
                allChoices.add(c);
            }

            resultSet.close();
            statement.close();
            return allChoices;

        } catch(Exception e) {
            throw new Exception("Failed in getting choices: " + e.getMessage());
        }
    }

}