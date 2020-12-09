package edu.wpi.cs.yaml.demo.http;

public class CompleteChoiceRequest {
	String choiceID;
	int participantID;
	
	public String getChoiceID() {return this.choiceID;}
	public void setChoiceID(String ChoiceID) {this.choiceID = ChoiceID;}
	
	public int getParticipantID() {return this.participantID;}
	public void setParticipantID(int participantID) {this.participantID = participantID;}
	
	public CompleteChoiceRequest() {}
	
	public CompleteChoiceRequest(String choiceID, int participantID) {
		this.choiceID = choiceID;
		this.participantID = participantID;
	}
	
	public String toString() {
		return "Requesting to complete ChoiceID: " + this.choiceID;
	}
}
