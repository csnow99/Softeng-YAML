package edu.wpi.cs.yaml.demo.model;

public class Vote {
	public int voteID;
    public String alternativeID;
    public String participantID;
    public int amendType;

    public String getAlternativeID() {return this.alternativeID;}
    public void setAlternativeID(String altID) {this.alternativeID = altID;}

    public String getParticipantID() {return this.participantID;}
    public void setParticipantID(String partID) {this.participantID = partID;}

    public int getAmendType() {return this.amendType;}
    public void setAmendType(int amendType) {this.amendType = amendType;}

    public Vote (){
    	voteID = 0;
    }

    public Vote(String altID, String partID, int amendType){
        this.alternativeID = altID;
        this.participantID = partID;
        this.amendType = amendType;
    }
    
    public Vote(int voteID, String altID, String partID, int amendType){
    	this.voteID = voteID;
        this.alternativeID = altID;
        this.participantID = partID;
        this.amendType = amendType;
    }
}
