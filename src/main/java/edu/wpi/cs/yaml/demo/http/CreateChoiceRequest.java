package edu.wpi.cs.yaml.demo.http;

import java.util.ArrayList;

import edu.wpi.cs.yaml.demo.model.Alternative;

/*A request for creating a choice contains the name of the choice, 
 * the maximum number of participants allowed to participate,
 * the description of the choice
 * the alternatives of the choice
 * */

public class CreateChoiceRequest {
	String name;
	int maxParticipants;
	String description;
	ArrayList<Alternative> alternatives;

	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public int getMaxParticipants() {return maxParticipants;}
	public void setMaxParticipants(int maxParticipants) {this.maxParticipants = maxParticipants;}
	
	public String getDescription() {return this.description;}
	public void setDescription(String description) {this.description = description;}
	
	public ArrayList<Alternative> getAlternatives() {return this.alternatives;}
	public void setAlternatives(ArrayList<Alternative> alternatives) {this.alternatives = alternatives;}
	public boolean addAlternative(Alternative alternative) {return this.alternatives.add(alternative);}
	
	
	public CreateChoiceRequest() {this.alternatives = new ArrayList<Alternative>();}
	
	public CreateChoiceRequest(String name, int maxParticipants) {
		this.name = name;
		this.maxParticipants = maxParticipants;
		this.alternatives = new ArrayList<Alternative>();
	}
	
	public CreateChoiceRequest(String name, int maxParticipants, String description) {
		this.name = name;
		this.maxParticipants = maxParticipants;
		this.description = description;
		this.alternatives = new ArrayList<Alternative>();
	}
	
	public CreateChoiceRequest(String name, int maxParticipants, String description, ArrayList<Alternative> alternatives) {
		this.name = name;
		this.maxParticipants = maxParticipants;
		this.description = description;
		this.alternatives = alternatives;
	}
	
	public String toString() {
		return "Creating choice:" + name;
	}
}
