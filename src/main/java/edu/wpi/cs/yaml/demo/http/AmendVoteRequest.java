package edu.wpi.cs.yaml.demo.http;

public class AmendVoteRequest {
	int alternativeID;
	int participantID;
	int amendType;
	
	public int getAlternativeID() {return alternativeID;}
	public void setAlternativeID(int alternativeID) {this.alternativeID = alternativeID;}
	
	public int getParticipantID() {return this.participantID;}
	public void setParticipantID(int participantID) {this.participantID = participantID;}
	
	public int getAmendType() {return this.amendType;}
	public void setAmendType(int amendType) {this.amendType = amendType;}
	
	public AmendVoteRequest() {}
	public AmendVoteRequest(int participantID, int amendType, int alternativeID){
		this.participantID = participantID;
		this.amendType = amendType;
		this.alternativeID = alternativeID;
	}
	
	public String toString() {
		return "Requesting to amend vote for alternativeID: " + Integer.toString(this.alternativeID)+ " and participantID: "+ Integer.toString(this.participantID);
	}
}
