package edu.wpi.cs.yaml.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    /*
    public boolean deleteAlternative(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE choice_ID = ?;");
            ps.setString(1, choice.choiceID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete choice: " + e.getMessage());
        }
    }*/


    public boolean addAlternative(Alternative alternative) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (alternative_ID, choice_ID, alternative_title,alternative_description) values(?,?,?,?);");
            ps.setString(1, alternative.alternativeID);
            ps.setString(2, alternative.choiceID);
            ps.setString(3, alternative.name);
            ps.setString(4, alternative.description);       
            
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
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
    /*
    private Alternative generateAlternative(ResultSet resultSet) throws Exception {
        String alternativeID = resultSet.getString("alternative_ID");
    	String choiceID = resultSet.getString("choice_ID");
    	String alternativeName = resultSet.getString("alternative_name");
    	String alternativeDescription = resultSet.getString("alternative_description");
        return new Alternative(alternativeID, choiceID, alternativeName, alternativeDescription);
    }*/

}