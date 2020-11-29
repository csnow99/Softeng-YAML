package edu.wpi.cs.yaml.demo.model;

public class Vote {
	public int voteID;
    public int alternativeID;
    public int participantID;
    public int amendType;

    public int getAlternativeID() {return this.alternativeID;}
    public void setAlternativeID(int altID) {this.alternativeID = altID;}

    public int getParticipantID() {return this.participantID;}
    public void setParticipantID(int partID) {this.participantID = partID;}

    public int getAmendType() {return this.amendType;}
    public void setAmendType(int amendType) {this.amendType = amendType;}

    public Vote (){
    	voteID = 0;
    }

    public Vote(int altID, int partID, int amendType){
        this.alternativeID = altID;
        this.participantID = partID;
        this.amendType = amendType;
    }
    
    public Vote(int voteID, int altID, int partID, int amendType){
    	this.voteID = voteID;
        this.alternativeID = altID;
        this.participantID = partID;
        this.amendType = amendType;
    }
}
