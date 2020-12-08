package edu.wpi.cs.yaml.demo.model;

import java.sql.Timestamp;

public class Choice {
	String choiceID;
	String choiceName;
	int maxParticipants;
	String choiceDescription;
	long dateCreated;
	boolean isCompleted;
	long dateCompleted;
	String selectedAlternativeID;
	
	
	public String getChoiceID() {return this.choiceID;}
	public void setChoiceID(String choiceID) {this.choiceID = choiceID;}
	
	public String getChoiceName() {return this.choiceName;}
	public void setChoiceName(String choiceName) { this.choiceName = choiceName;}
	
	public int getMaxParticipants() {return this.maxParticipants;}
	public void setMaxParticipants(int maxParticipants) {this.maxParticipants = maxParticipants;}
	
	public String getChoiceDescription() {return this.choiceDescription;};
	public void setChoiceDescription(String choiceDescription) {this.choiceDescription = choiceDescription;}
 	
	public long getDateCreated() {return this.dateCreated;}
	public void setDateCreated(long dateCreated) {this.dateCreated = dateCreated;}
	
	public boolean getIsCompleted() {return this.isCompleted;}
	public void setIsCompleted(boolean isCompleted) {this.isCompleted = isCompleted;}
	
	public long getDateCompleted() {return this.dateCompleted;}
	public void setDateCompleted(long dateCompleted) {this.dateCompleted = dateCompleted;}
	
	public String getSelectedAlternativeID() {return this.selectedAlternativeID;}
	public void setSelectedAlternativeID(String chosenAlternativeID) {this.selectedAlternativeID = chosenAlternativeID;}
	
	
		
	/*Constructor when creating a choice*/
	public Choice(String choiceID, String choiceName, int maxParticipants,String choiceDescription) {
		this.choiceID = choiceID;
		this.choiceName = choiceName;
		this.maxParticipants = maxParticipants;
		this.choiceDescription = choiceDescription;
		this.dateCreated = System.currentTimeMillis();
		this.isCompleted = false;
		this.dateCompleted = 0;
		this.selectedAlternativeID = null;
	}
	
	/*For testing purposes*/
	public Choice(String choiceID, String choiceName, int maxParticipants, String choiceDescription,
				long dateCreated, boolean isCompleted,	long dateCompleted,	String selectedAlternativeID) {
		this.choiceID = choiceID;
		this.choiceName = choiceName;
		this.maxParticipants = maxParticipants;
		this.choiceDescription = choiceDescription;
		this.dateCreated = dateCreated;
		this.isCompleted = isCompleted;
		this.dateCompleted = dateCompleted;
		this.selectedAlternativeID = selectedAlternativeID;
	}
	
	public Choice() {
		this.dateCompleted = 0;
		selectedAlternativeID = null;
	}
	
}
