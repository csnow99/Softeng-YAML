package edu.wpi.cs.yaml.demo.model;

public class Alternative {
	public int alternativeID;
	public String choiceID;
	public String name;
	public String description;
	
	public Alternative() {}
	
	public Alternative(String name, String description) {
		this.alternativeID = 0;
		this.choiceID = null;
		this.name = name;
		this.description = description;
	}
	
	public Alternative(String choiceID, String name, String description) {
		this.alternativeID = 0;
		this.choiceID = choiceID;
		this.name = name;
		this.description = description;
	}
	
	public Alternative(int alternativeID, String name, String description) {
		this.alternativeID = alternativeID;
		this.name = name;
		this.description = description;
	}
	
	public Alternative(int alternativeID, String choiceID, String name, String description) {
		this.alternativeID = alternativeID;
		this.name = name;
		this.choiceID = choiceID;
		this.description = description;
	}
	
	
	
	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}
	
}
