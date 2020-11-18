package edu.wpi.cs.yaml.demo.http;

public class RegisterParticipantRequest {
	String choiceID;
	String name;
	String password;
	
	public String getChoiceID() {return this.choiceID;}
	public void setChoiceID(String ChoiceID) {this.choiceID = ChoiceID;}
	
	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}
	
	public String getPassword() {return this.password;}
	public void setPassword(String password) {this.password = password;}
	
	public RegisterParticipantRequest() {}
	
	public RegisterParticipantRequest(String choiceID, String name, String password) {
		this.choiceID = choiceID;
		this.name = name;
		this.password = password;
	}
	
	public String toString() {
		return "Requesting to register participant: "+ name + " for choiceID " + choiceID;
	}
	
}
