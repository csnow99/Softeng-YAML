package edu.wpi.cs.yaml.demo.model;


public class Choice {
	public final String choiceID;
	public final String choiceName;
	public final int maxParticipants;
	public final String choiceDescription;
	public final boolean isCompleted;
	
	public Choice(String choiceID, String choiceName, int maxParticipants,String choiceDescription, boolean isCompleted) {
		this.choiceID = choiceID;
		this.choiceName = choiceName;
		this.maxParticipants = maxParticipants;
		this.choiceDescription = choiceDescription;
		this.isCompleted = isCompleted;
	}
	
	
}
