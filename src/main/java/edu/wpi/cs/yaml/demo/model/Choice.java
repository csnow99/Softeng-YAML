package edu.wpi.cs.yaml.demo.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Choice {
	public  String choiceID;
	public  String choiceName;
	public  int maxParticipants;
	public  String choiceDescription;
	public  Timestamp timeCreated;
	public  boolean isCompleted;
	public Timestamp timeCompleted;
	public Alternative chosenAlternative;
		
	/*Constructor when creating a choice*/
	public Choice(String choiceID, String choiceName, int maxParticipants,String choiceDescription) {
		this.choiceID = choiceID;
		this.choiceName = choiceName;
		this.maxParticipants = maxParticipants;
		this.choiceDescription = choiceDescription;
		timeCreated = new Timestamp(System.currentTimeMillis());
		this.isCompleted = false;
		this.timeCompleted = null;
		this.chosenAlternative = null;
	}
	
	public Choice() {
		timeCreated = null;
		timeCompleted = null;
		chosenAlternative = null;
	}
	
}
