package edu.wpi.cs.yaml.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Feedback;
import edu.wpi.cs.yaml.demo.model.FeedbackInfo;

public class FeedbackDAO {
	
	java.sql.Connection conn;
	
	final String tblName = "Feedback";   // Exact capitalization

    public FeedbackDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    public Feedback getFeedback(int alternative_ID, int participant_ID) throws Exception {
        try {
        	Feedback feedback= null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_id = ? AND participant_id = ?;");
            ps.setInt(1,  alternative_ID);
            ps.setInt(2,  participant_ID);
            
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	feedback = generateFeedback(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return feedback;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting vote: " + e.getMessage());
        }
    }
    
    public FeedbackInfo getFeedbackForAlternative(int alternative_ID) throws Exception {
        try {
        	List<String> participants = new ArrayList<String>();
        	List<String> feedback = new ArrayList<String>();
        	List<Long> timestamps = new ArrayList<Long>();
        	ParticipantDAO participantDAO = new ParticipantDAO();
        	AlternativeDAO alternativeDAO = new AlternativeDAO();
        	Statement statement = conn.createStatement();
            
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternative_id=?;");
            ps.setInt(1,  alternative_ID);
            
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
            	Feedback f = generateFeedback(resultSet);
            	participants.add(participantDAO.getParticipantNameFromID(f.getParticipantID()));
            	feedback.add(f.feedbackText);
            	timestamps.add(f.feedbackTimestamp);
            }
            resultSet.close();
            statement.close();
            String alternativeTitle = alternativeDAO.getAlternativeTitleFromAlternativeID(alternative_ID);
            return new FeedbackInfo(alternative_ID, alternativeTitle, participants, feedback, timestamps);
        } catch (Exception e) {
            throw new Exception("Failed in getting feedback: " + e.getMessage());
        }
        
        }

    public boolean addFeedback(Feedback feedback) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (participant_id,alternative_id,feedback_text,feedback_timestamp) values(?,?,?,?);");
            ps.setInt(1, feedback.participantID);
            ps.setInt(2, feedback.alternativeID);
            ps.setString(3, feedback.feedbackText); 
            ps.setTimestamp(4, new Timestamp(feedback.feedbackTimestamp));
            ChoiceDAO choiceDAO = new ChoiceDAO();
            String choiceID = null;
            String ps2 = "select choice_id from Alternatives where alternative_id="+feedback.alternativeID;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(ps2);
            while(rs.next()) {
            	choiceID=rs.getString("choice_id");
            }
            boolean isComp = choiceDAO.getIsCompleted(choiceID);
            if(!isComp) {
            	ps.execute();
            	return true;
            }
            return false;

        } catch (Exception e) {
            throw new Exception("Failed to insert feedback: " + e.getMessage());
        }
    }
    
    public List<FeedbackInfo> getFeedback(String choice_ID) throws Exception {
    	List<FeedbackInfo> feedback = new ArrayList<FeedbackInfo>();
    	AlternativeDAO altDAO = new AlternativeDAO();
    	
    	List<Alternative> alternatives = altDAO.getAlternatives(choice_ID);
    	for(Alternative alt : alternatives) {
    		feedback.add(getFeedbackForAlternative(alt.getAlternativeID()));
    	}
    	
    	return feedback;
    }
    
    private Feedback generateFeedback(ResultSet resultSet) throws Exception {
        int feedbackID = resultSet.getInt("feedback_id");
    	int participantID = resultSet.getInt("participant_id");
    	int alternativeID = resultSet.getInt("alternative_id");
    	String feedbackText = resultSet.getString("feedback_text");
    	long feedbackTimestamp = resultSet.getLong("feedback_timestamp");
        return new Feedback(feedbackID, alternativeID, participantID, feedbackText, feedbackTimestamp);
    }
}
