package edu.wpi.cs.yaml.demo.http;

public class AmendVoteRequest {
	String alternativeID;
	String participantID;
	int amendType;
	
	public String getAlternativeID() {return alternativeID;}
	public void setAlternativeID(String alternativeID) {this.alternativeID = alternativeID;}
	
	public String getParticipantID() {return this.participantID;}
	public void setParticipantID(String participantID) {this.participantID = participantID;}
	
	public int getAmendType() {return this.amendType;}
	public void setAmendType(int amendType) {this.amendType = amendType;}
	
	public AmendVoteRequest() {}
	public AmendVoteRequest(String participantName, int amendType, String alternativeID){
		this.participantID = participantName;
		this.amendType = amendType;
		this.alternativeID = alternativeID;
	}
	
	public String toString() {
		return "Requesting to amend voteID: " + this.alternativeID;
	}
}
