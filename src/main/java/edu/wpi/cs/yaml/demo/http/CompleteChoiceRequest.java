package edu.wpi.cs.yaml.demo.http;

public class CompleteChoiceRequest {
	String choiceID;
	int alternativeID;
	int participantID;
	
	public String getChoiceID() {return this.choiceID;}
	public void setChoiceID(String ChoiceID) {this.choiceID = ChoiceID;}
	
	public int getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(int alternativeID) {this.alternativeID = alternativeID;}
	
	public int getParticipantID() {return this.participantID;}
	public void setParticipantID(int participantID) {this.participantID = participantID;}
	
	public CompleteChoiceRequest() {}
	
	public CompleteChoiceRequest(String choiceID, int alternativeID, int participantID) {
		this.choiceID = choiceID;
		this.alternativeID = alternativeID;
		this.participantID = participantID;
	}
	
	public String toString() {
		return "Requesting to complete ChoiceID: " + this.choiceID;
	}
}
