package edu.wpi.cs.yaml.demo.http;

public class PostFeedbackRequest {
	int alternativeID;
	int participantID;
	String text;
	
	public int getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(int alternativeID) {this.alternativeID = alternativeID;}
	
	public int getParticipantID() {return this.participantID;}
	public void setParticipantID(int participantID) {this.participantID = participantID;}
	
	public String getText() {return this.text;}
	public void setText(String text) {this.text = text;}
	
	public PostFeedbackRequest() {}
	
	public PostFeedbackRequest(int alternativeID, int participantID, String text) {
		this.alternativeID = alternativeID;
		this.participantID = participantID;
		this.text = text;
	}
	
	public String toString() {
		return "Requesting to post feedback for alternativeID: "+ Integer.toString(alternativeID) + " and participantName " + Integer.toString(participantID);
		}
}
