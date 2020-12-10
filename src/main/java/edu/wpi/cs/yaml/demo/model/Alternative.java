package edu.wpi.cs.yaml.demo.model;

public class Alternative {
	int alternativeID;
	String choiceID;
	String title;
	String description;
	
	public int getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(int alternativeID) {this.alternativeID = alternativeID;}
	
	public String getChoiceID() {return this.choiceID ;}
	public void setChoiceID(String choiceID) {this.choiceID = choiceID;}
	
	public String getTitle() {return this.title;}
	public void setName(String title) {this.title = title;}
	
	public void setDescription(String description) { this.description = description;}
	public String getDescription() {return this.description;}
	
	public Alternative() {}
	
	public Alternative(String name, String description) {
		this.alternativeID = 0;
		this.choiceID = null;
		this.title = name;
		this.description = description;
	}
	
	public Alternative(String choiceID, String name, String description) {
		this.alternativeID = 0;
		this.choiceID = choiceID;
		this.title = name;
		this.description = description;
	}
	
	public Alternative(int alternativeID, String name, String description) {
		this.alternativeID = alternativeID;
		this.title = name;
		this.description = description;
	}
	
	public Alternative(int alternativeID, String choiceID, String name, String description) {
		this.alternativeID = alternativeID;
		this.title = name;
		this.choiceID = choiceID;
		this.description = description;
	}
	
	
}
