package edu.wpi.cs.yaml.demo.http;

public class PostFeedbackRequest {
	String alternativeID;
	String participantName;
	String text;
	
	public String getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(String alternativeID) {this.alternativeID = alternativeID;}
	
	public String getParticipantName() {return this.participantName;}
	public void setParticipantName(String participantName) {this.participantName = participantName;}
	
	public String getText() {return this.text;}
	public void setText(String text) {this.text = text;}
	
	public PostFeedbackRequest() {}
	
	public PostFeedbackRequest(String alternativeID, String participantName, String text) {
		this.alternativeID = alternativeID;
		this.participantName = participantName;
		this.text = text;
	}
	
	public String toString() {
		return "Requesting to post feedback for alternativeID: "+alternativeID + " and participantName " +participantName;
		}
}
