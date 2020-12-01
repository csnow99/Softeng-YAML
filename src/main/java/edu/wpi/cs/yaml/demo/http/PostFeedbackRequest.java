package edu.wpi.cs.yaml.demo.http;

public class PostFeedbackRequest {
	int alternativeID;
	int participantid;
	String text;
	
	public int getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(int alternativeID) {this.alternativeID = alternativeID;}
	
	public int getParticipantID() {return this.participantid;}
	public void setParticipantID(int participantid) {this.participantid = participantid;}
	
	public String getText() {return this.text;}
	public void setText(String text) {this.text = text;}
	
	public PostFeedbackRequest() {}
	
	public PostFeedbackRequest(int alternativeID, int participantName, String text) {
		this.alternativeID = alternativeID;
		this.participantid = participantName;
		this.text = text;
	}
	
	public String toString() {
		return "Requesting to post feedback for alternativeID: "+ Integer.toString(alternativeID) + " and participantName " + Integer.toString(participantid);
		}
}
