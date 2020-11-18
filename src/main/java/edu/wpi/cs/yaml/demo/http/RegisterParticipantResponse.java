package edu.wpi.cs.yaml.demo.http;

public class RegisterParticipantResponse extends GenericResponse {
	public RegisterParticipantResponse(String response) {
		super(response);
	}
	public RegisterParticipantResponse(String response, int code) {
		super(response, code);
	}
}
