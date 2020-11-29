package edu.wpi.cs.yaml.demo.http;

public class GetVoteRequest {
	String alternativeID;
	String participantID;
	int voteType;
	
	public String getParticipantID() {return this.participantID;}
	public void setParticipantID(String ParticipantID) {this.participantID = ParticipantID;}
	
	public String getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(String AlternativeID) {this.alternativeID = AlternativeID;}
	
	public int getVoteType() {return this.voteType;}
	public void setName(int VoteType) {this.voteType = VoteType;}
	
	public GetVoteRequest() {}
	
	public GetVoteRequest(String alternativeID, String participantID, int voteType) {
		this.alternativeID = alternativeID;
		this.participantID = participantID;
		this.voteType = voteType;
	}
	
	public String toString() {
		return "Requesting to get vote for alternative ID " + alternativeID + " and participant ID " + participantID;
	}
}
