package edu.wpi.cs.yaml.demo.http;

public class AmendVoteRequest {
	String alternativeID;
	String participantID;
	boolean amendType;
	
	public String getAlternativeID() {return alternativeID;}
	public void setAlternativeID(String alternativeID) {this.alternativeID = alternativeID;}
	
	public String getParticipantID() {return this.participantID;}
	public void setParticipantID(String participantID) {this.participantID = participantID;}
	
	public boolean getAmendType() {return this.amendType;}
	public void setAmendType(boolean amendType) {this.amendType = amendType;}
	
	public AmendVoteRequest() {}
	public AmendVoteRequest(String participantName, boolean amendType, String alternativeID){
		this.participantID = participantName;
		this.amendType = amendType;
		this.alternativeID = alternativeID;
	}
	
	public String toString() {
		return "Requesting to amend voteID: " + this.alternativeID;
	}
}
