package edu.wpi.cs.yaml.demo.http;

import edu.wpi.cs.yaml.demo.model.Choice;

public class GetChoiceResponse extends GenericResponse{
	public Choice choice;

	//when code 200
	public GetChoiceResponse(String response, Choice c) {
		super(response);
		this.choice = c;
	}
	
	//when code not 200 null is returned as the choice
	public GetChoiceResponse(int code, String response) {
		super(response, code);
		this.choice = null;
	}
	
}
