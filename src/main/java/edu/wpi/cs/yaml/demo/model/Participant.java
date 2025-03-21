package edu.wpi.cs.yaml.demo.model;

public class Participant {
	int participantID;
	String choiceID;
	String username;
	String password;
	
	public int getParticipantID() {return this.participantID;}
	public String getChoiceID() {return this.choiceID;}
	public String getName() {return this.username;}
	public String getPassword() {return this.password;}
	
	public void setParticipantID(int participantID) {this.participantID = participantID;}
	public void setChoiceID(String choiceID) {this.choiceID = choiceID;}
	public void setName(String name) {this.username = name;}
	public void setPassword(String password) {this.password = password;}
	
	public Participant() {}
	
	public Participant(String choiceID, String name, String password) {
		this.participantID = 0;
		this.choiceID = choiceID;
		this.username = name;
		this.password = password;
	}
	
	
	public Participant(int participantID, String choiceID, String name, String password) {
		this.participantID = participantID;
		this.choiceID = choiceID;
		this.username = name;
		this.password = password;
	}
	
	public boolean equals(Object o) {
		boolean result = false;
		if(o instanceof Participant) {
			result = true;
			Participant other = (Participant) o;
			result &= (this.choiceID.equals(other.choiceID));
			result &= (this.username.equals(other.username));
		}
		
		return result;
	}
}
