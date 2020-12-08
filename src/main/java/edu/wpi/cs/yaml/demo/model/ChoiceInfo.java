package edu.wpi.cs.yaml.demo.model;

import java.sql.Timestamp;

public class ChoiceInfo {
	String choiceID;
	long creationDate;
	long completionDate;
	boolean completed;
	
	public String getChoiceID() {return this.choiceID;}
	public void setChoiceID(String choiceID) {this.choiceID = choiceID;}

	public long getCreationDate() {return this.creationDate;}
	public void setCreationDate(long creationDate) {this.creationDate = creationDate;}

	public long getCompletionDate() {return this.completionDate;}
	public void setCompletionDate(long completionDate) {this.completionDate = completionDate;}

	public boolean getIsCompleted() {return this.completed;}
	public void setIsCompleted(boolean isCompleted) {this.completed = isCompleted;}
	
	public ChoiceInfo(String choiceID, long creationDate, long completionDate, boolean completed) {
		this.choiceID = choiceID;
		this.creationDate = creationDate;
		this.completionDate = completionDate;
		this.completed = completed;
	}

	public ChoiceInfo() {

	}
	
	public String toString() {
		Timestamp creationDate = new Timestamp(this.creationDate);
		Timestamp completionDate = new Timestamp(this.completionDate);

		String result = "";
		if (this.completionDate == 0) {
			result = ("ChoiceID: "+ choiceID +" \tCreation date: "+ creationDate.toString() + "\tIs Completed: "+ Boolean.toString(completed));
		} else {
			result = ("ChoiceID: "+ choiceID +" \tCreation date: "+ creationDate.toString() + "\tIs Completed: "+ Boolean.toString(completed) + "\tCompletion date: "+ completionDate.toString());
		}
		
		return result;
	}
}
