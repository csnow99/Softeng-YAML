package edu.wpi.cs.yaml.demo.http;

public class CompleteChoiceResponse extends GenericResponse {
	public CompleteChoiceResponse(String response) {
		super(response);
	}
	public CompleteChoiceResponse(String response, int code) {
		super(response, code);
	}
}