package edu.wpi.cs.yaml.demo.model;

public class ChoiceInfo {
	public String ID;
	public long creationDate;
	public long completionDate;
	public boolean completed;
	
	//May or may not need setters and getters plus empty constructor
	
	public ChoiceInfo(String ID, long creationDate, long completionDate, boolean completed) {
		this.ID = ID;
		this.creationDate = creationDate;
		this.completionDate = completionDate;
		this.completed = completed;
	}
}
