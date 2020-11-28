package edu.wpi.cs.yaml.demo.db;

import com.mysql.cj.protocol.Resultset;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetDAO {

    java.sql.Connection conn;

    final String alternativesTable = "Alternatives";
    final String participantsTable = "Participants";

    public GetDAO(){
        try {
            conn = DatabaseUtil.connect();
        } catch (Exception e) {
            conn = null;
        }
    }

    public String getChoiceIDA(String alternativeID) throws Exception {
        String choiceID = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT choice_id FROM " + alternativesTable + " WHERE alternative_id = ?;");
            ps.setString(1, alternativeID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                choiceID = rs.getString("choice_id");
            }
            rs.close();
            ps.close();

            return choiceID;

        } catch (Exception e) {
            throw new Exception("Could not get ChoiceID from Alternatives table: " + e.getMessage());
        }
    }

    public String getChoiceIDP(String participantID) throws Exception {
        String choiceID = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT choice_id FROM " + participantsTable + " WHERE participantID = ?;");
            ps.setString(1, participantID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                choiceID = rs.getString("choice_id");
            }
            rs.close();
            ps.close();

            return choiceID;

        } catch (Exception e){
            throw new Exception("Could not get ChoiceID from Participants table: " + e.getMessage());
        }
    }
}
