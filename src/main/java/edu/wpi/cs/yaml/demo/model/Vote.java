package edu.wpi.cs.yaml.demo.model;

public class Vote {
    String alternativeID;
    String participantID;
    boolean amendType;

    public String getAlternativeID() {return this.alternativeID;}
    public void setAlternativeID(String altID) {this.alternativeID = altID;}

    public String getParticipantID() {return this.participantID;}
    public void setParticipantID(String partID) {this.participantID = partID;}

    public boolean getAmendType() {return this.amendType;}
    public void setAmendType(boolean amendType) {this.amendType = amendType;}

    public Vote (){
    }

    public Vote(String altID, String partID, boolean amendType){
        this.alternativeID = altID;
        this.participantID = partID;
        this.amendType = amendType;
    }
}
