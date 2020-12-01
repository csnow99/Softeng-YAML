package edu.wpi.cs.yaml.demo.http;

public class RegisterParticipantResponse extends GenericResponse {
	int participant_ID;
	
	public int getParticipantID() {return this.participant_ID;}
	public void setParticipantID(int participant_ID) {this.participant_ID = participant_ID;}

	public RegisterParticipantResponse(String response, int code, int participantID) {
		super(response, code);
		this.participant_ID = participantID;
	}
}
