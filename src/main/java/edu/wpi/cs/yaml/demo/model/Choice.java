package edu.wpi.cs.yaml.demo.model;

import java.sql.Timestamp;

public class Choice {
	public String choiceID;
	public String choiceName;
	public int maxParticipants;
	public String choiceDescription;
	public Timestamp dateCreated;
	public boolean isCompleted;
	public Timestamp dateCompleted;
	public String chosenAlternativeID;
	
	
	public String getChoiceID() {return this.choiceID;}
	public void setChoiceID(String choiceID) {this.choiceID = choiceID;}
	
	public String getChoiceName() {return this.choiceName;}
	public void setChoiceName(String choiceName) { this.choiceName = choiceName;}
	
	public int getMaxParticipants() {return this.maxParticipants;}
	public void setMaxParticipants(int maxParticipants) {this.maxParticipants = maxParticipants;}
	
	public String getChoiceDescription() {return this.choiceDescription;};
	public void setChoiceDescription(String choiceDescription) {this.choiceDescription = choiceDescription;}
 	
	public Timestamp getDateCreated() {return this.dateCreated;}
	public void setDateCreated(int dateCreated) {this.dateCreated = new Timestamp(dateCreated);}
	
	public boolean getIsCompleted() {return this.isCompleted;}
	public void setIsCompleted(boolean isCompleted) {this.isCompleted = isCompleted;}
	
	public Timestamp getDateCompleted() {return this.dateCompleted;}
	public void setDateCompleted(int dateCompleted) {this.dateCompleted = new Timestamp(dateCompleted);}
	
	public String getChosenAlternativeID() {return this.chosenAlternativeID;}
	public void setChosenAlternativeID(String chosenAlternativeID) {this.chosenAlternativeID = chosenAlternativeID;}
	
	
		
	/*Constructor when creating a choice*/
	public Choice(String choiceID, String choiceName, int maxParticipants,String choiceDescription) {
		this.choiceID = choiceID;
		this.choiceName = choiceName;
		this.maxParticipants = maxParticipants;
		this.choiceDescription = choiceDescription;
		dateCreated = new Timestamp(System.currentTimeMillis());
		this.isCompleted = false;
		this.dateCompleted = null;
		this.chosenAlternativeID = null;
	}
	
	public Choice() {
		dateCreated = null;
		dateCompleted = null;
		chosenAlternativeID = null;
	}
	
}
