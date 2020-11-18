package edu.wpi.cs.yaml.demo.http;

public class AmendVoteRequest {
	String voteID;
	String participantName;
	boolean amendType;
	
	public String getAlternativeID() {return voteID;}
	public void setAlternativeID(String alternativeID) {this.voteID = alternativeID;}
	
	public String getParticipantName() {return this.participantName;}
	public void setParticipantName(String participantName) {this.participantName = participantName;}
	
	public boolean getAmendType() {return this.amendType;}
	public void setAmendType(boolean amendType) {this.amendType = amendType;}
	
	public AmendVoteRequest() {}
	public AmendVoteRequest(String participantName, boolean amendType, String voteID){
		this.participantName = participantName;
		this.amendType = amendType;
		this.voteID = voteID;
	}
	
	public String toString() {
		return "Requesting to amend voteID: " + this.voteID;
	}
}
