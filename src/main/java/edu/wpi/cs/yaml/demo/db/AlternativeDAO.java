package edu.wpi.cs.yaml.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.model.Alternative;

public class AlternativeDAO { 

	java.sql.Connection conn;
	
	final String tblName = "Alternatives";   // Exact capitalization

    public AlternativeDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    public String getAlternativeTitleFromAlternativeID(int alternative_ID) throws Exception {
    	 try {
         	Alternative alternative = null;
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_ID=?;");
             ps.setInt(1,  alternative_ID);
             ResultSet resultSet = ps.executeQuery();
             
             while (resultSet.next()) {
             	alternative = generateAlternative(resultSet);
             }
             resultSet.close();
             ps.close();
             
             return alternative.getTitle();

         } catch (Exception e) {
         	e.printStackTrace();
             throw new Exception("Failed in getting choice: " + e.getMessage());
         }
    }
    
    public int getAlternativeIDFromChoiceIDandTitle(String choiceID, String title) throws Exception {
    	try {
         	 Alternative alternative = null;
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_id=? AND alternative_title=?;");
             ps.setString(1,  choiceID);
             ps.setString(2, title);
             
             ResultSet resultSet = ps.executeQuery();
             
             while (resultSet.next()) {
             	alternative = generateAlternative(resultSet);
             }
             resultSet.close();
             ps.close();
             
             return alternative.getAlternativeID();

         } catch (Exception e) {
         	e.printStackTrace();
             throw new Exception("Failed in getting choice: " + e.getMessage());
         }
    }


    public boolean addAlternative(Alternative alternative) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (choice_ID, alternative_title,alternative_description) values(?,?,?);");
            ps.setString(1, alternative.getChoiceID());
            ps.setString(2, alternative.getTitle());
            ps.setString(3, alternative.getDescription());       
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert alternative: " + e.getMessage());
        }
    }

    public List<Alternative> getAlternatives(String choiceID) throws Exception {
        
        List<Alternative> alternatives = new ArrayList<Alternative>();
        try {
            Statement statement = conn.createStatement();
          
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choice_id=?;");
            ps.setString(1,  choiceID);
            //String query = "SELECT * FROM " + tblName + "GROUP BY alternative_id HAVING MAX(choice_id) = " + choiceID + " AND MIN(choice_id) = "+choiceID + ";";
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
             Alternative c = generateAlternative(resultSet);
            	alternatives.add(c);
            }
            resultSet.close();
            statement.close();
            return alternatives;

        } catch (Exception e) {
            throw new Exception("Failed in getting alternatives: " + e.getMessage());
        }
    }
    
    private Alternative generateAlternative(ResultSet resultSet) throws Exception {
        int alternativeID = resultSet.getInt("alternative_ID");
    	String choiceID = resultSet.getString("choice_ID");
    	String alternativeName = resultSet.getString("alternative_title");
    	String alternativeDescription = resultSet.getString("alternative_description");
        return new Alternative(alternativeID, choiceID, alternativeName, alternativeDescription);
    }

}