package edu.wpi.cs.yaml.demo.db;

import edu.wpi.cs.yaml.demo.model.Vote;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public void deleteChoices() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "set sql_safe_updates = 0; \n" +
                    "delete from Choices;");

            ResultSet resultSet = ps.executeQuery();
            resultSet.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }

    public void deleteAlternatives() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "set sql_safe_updates = 0;\n" +
                            "delete from Alternatives;\n" +
                    "ALTER TABLE Alternatives AUTO_INCREMENT = 1;");

            ResultSet resultSet = ps.executeQuery();
            resultSet.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }

    public void deleteParticipants() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "set sql_safe_updates = 0;\n" +
                            "delete from Participants;\n" +
                    "ALTER TABLE Participants AUTO_INCREMENT = 1;");

            ResultSet resultSet = ps.executeQuery();
            resultSet.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }

    public void deleteVotes() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "set sql_safe_updates = 0;\n" +
                            "delete from Votes;\n" +
                            "ALTER TABLE Votes AUTO_INCREMENT = 1;");

            ResultSet resultSet = ps.executeQuery();
            resultSet.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }

    public void deleteFeedback() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "set sql_safe_updates = 0;\n" +
                            "delete from Feedback;\n" +
                            "ALTER TABLE Feedback AUTO_INCREMENT = 1;");

            ResultSet resultSet = ps.executeQuery();
            resultSet.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed deleting all choices");
        }
    }
}
