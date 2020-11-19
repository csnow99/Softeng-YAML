package edu.wpi.cs.yaml.demo.model;

public class Alternative {
	public final String alternativeID;
	public String choiceID;
	public final String name;
	public final String description;
	
	public Alternative(String alternativeID, String name, String description) {
		this.alternativeID = alternativeID;
		this.name = name;
		this.description = description;
	}
	
	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}
	
}
