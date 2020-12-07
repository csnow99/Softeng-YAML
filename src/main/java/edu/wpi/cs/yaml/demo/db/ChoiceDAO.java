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
             throw new Exception("Failed in getting maxParticipants for choice: " + choice_ID + e.getMessage());
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


    
    public Choice generateChoice(ResultSet resultSet) throws Exception {
    	Choice returnChoice = new Choice();
    	returnChoice.choiceID = resultSet.getString("choice_ID");
    	returnChoice.choiceName = resultSet.getString("choice_name");
    	returnChoice.maxParticipants = resultSet.getInt("choice_maxParticipants");
    	returnChoice.choiceDescription = resultSet.getString("choice_description");
    	Timestamp timeCreated =  resultSet.getTimestamp("creation_time");
    	if (timeCreated == null) {returnChoice.dateCreated = 0;}
    	else {returnChoice.dateCreated = timeCreated.getTime();}
    	returnChoice.isCompleted = resultSet.getBoolean("choice_isCompleted");
    	Timestamp timeCompleted =  resultSet.getTimestamp("completion_time");
    	if (timeCompleted == null) {returnChoice.dateCompleted = 0;}
    	else {returnChoice.dateCompleted = timeCompleted.getTime();}
    	returnChoice.selectedAlternativeID = resultSet.getString("chosen_alternative");
        return returnChoice;
    }

    public ChoiceInfo generateChoiceInfo(ResultSet resultSet) throws Exception {

        String choiceID = resultSet.getString("choice_id");
        Timestamp timeCreated = resultSet.getTimestamp("creation_time");
        long createTime = 0;
        if (timeCreated != null) { createTime = timeCreated.getTime(); }
        boolean isComplete = resultSet.getBoolean("choice_isCompleted");
        Timestamp timeCompleted = resultSet.getTimestamp("completion_time");
        long completeTime = 0;
        if (timeCompleted != null) { completeTime = timeCompleted.getTime(); }

        ChoiceInfo choiceInfo = new ChoiceInfo(choiceID, createTime, completeTime, isComplete);

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