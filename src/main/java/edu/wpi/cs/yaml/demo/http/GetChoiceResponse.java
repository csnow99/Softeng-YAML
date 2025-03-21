package edu.wpi.cs.yaml.demo.http;

import edu.wpi.cs.yaml.demo.model.Choice;

public class GetChoiceResponse extends GenericResponse{
	Choice choice;
	String participantName;
	
	public Choice getChoice() {return this.choice;}
	public String getParticipantName() {return this.participantName;}
	
	public void setChoice(Choice choice) {this.choice = choice;}
	public void setParticipantName(String participantName) {this.participantName = participantName;}
	
	//when code 200
	public GetChoiceResponse(String response, Choice c, String name) {
		super(response);
		this.choice = c;
		this.participantName = name;
	}
	
	public GetChoiceResponse(String response, Choice c) {
		super(response);
		this.choice = c;
		this.participantName = "";
	}
	
	//when code not 200 null is returned as the choice
	public GetChoiceResponse(int code, String response) {
		super(response, code);
		this.choice = null;
		this.participantName = "";
	}
	
	public GetChoiceResponse(int code, String response, Choice c) {
		super(response,code);
		this.choice = c;
		this.participantName = "";
	}
	
}
