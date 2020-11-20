package edu.wpi.cs.yaml.demo.http;

public class DeleteSingleChoiceByIDRequest {
	//for testing
	String choiceID;
	
	public String getChoiceID() {return this.choiceID;}
	public void setChoiceID(String choiceID) {this.choiceID = choiceID;}
	
	public DeleteSingleChoiceByIDRequest() {}
	public DeleteSingleChoiceByIDRequest(String choiceID) {this.choiceID = choiceID;}
}
