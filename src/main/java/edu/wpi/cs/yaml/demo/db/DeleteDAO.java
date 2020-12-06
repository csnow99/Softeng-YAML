package edu.wpi.cs.yaml.demo.db;

import java.sql.PreparedStatement;

public class DeleteDAO {

    java.sql.Connection conn;

    final String tblName = "Votes";   // Exact capitalization

    public DeleteDAO() {
        try  {
            conn = DatabaseUtil.connect();
        } catch (Exception e) {
            conn = null;
        }
    }

    public void deleteChoicesOlderThan(int days) throws Exception{
    	try {
            PreparedStatement ps = conn.prepareStatement("delete from Choices where datediff(creation_time, date_sub(now(), interval ? day)) < 0;");
            ps.setInt(1,  days);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices older than "+days+" days");
        }
    }
    
    public void deleteChoices() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from Choices;");

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }

    public void deleteAlternatives() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from Alternatives; ALTER TABLE Alternatives AUTO_INCREMENT = 1;");

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }

    public void deleteParticipants() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from Participants; ALTER TABLE Participants AUTO_INCREMENT = 1;");

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }

    public void deleteVotes() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from Votes; ALTER TABLE Votes AUTO_INCREMENT = 1;");

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }

    public void deleteFeedback() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from Feedback; ALTER TABLE Feedback AUTO_INCREMENT = 1;");

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }
}
