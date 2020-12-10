package edu.wpi.cs.yaml.demo.model;

public class Vote {
	int voteID;
    int alternativeID;
    int participantID;
    int amendType;

    public int getVoteID() {return this.voteID;}
    public void setVoteID(int voteID) {this.voteID = voteID;}

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
