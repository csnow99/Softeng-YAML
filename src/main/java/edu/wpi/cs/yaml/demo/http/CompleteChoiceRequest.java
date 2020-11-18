package edu.wpi.cs.yaml.demo.http;

public class CompleteChoiceRequest {
	String choiceID;
	
	public String getChoiceID() {return this.choiceID;}
	public void setChoiceID(String ChoiceID) {this.choiceID = ChoiceID;}
	
	public CompleteChoiceRequest() {}
	
	public CompleteChoiceRequest(String choiceID) {
		this.choiceID = choiceID;
	}
	
	public String toString() {
		return "Requesting to complete ChoiceID: " + this.choiceID;
	}
}
