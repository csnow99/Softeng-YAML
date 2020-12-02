package edu.wpi.cs.yaml.demo.http;

public class GetChoiceLoginRequest {
	String choiceID;
	int participantID;
	
	public String getChoiceID() {return this.choiceID;}
	public int getParticipantID() {return this.participantID;}
	
	public void setChoiceID(String choiceID) {this.choiceID = choiceID;}
	public void setParticipantID(int participantID) {this.participantID = participantID;}
	
	public GetChoiceLoginRequest() {}
	
	public GetChoiceLoginRequest(String choiceID, int participantID) {
		this.choiceID = choiceID;
		this.participantID = participantID;
	}
}
