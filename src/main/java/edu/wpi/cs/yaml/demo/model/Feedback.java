package edu.wpi.cs.yaml.demo.model;

public class Feedback {
	public int feedbackID;
    public int alternativeID;
    public int participantID;
    public String feedbackText;

    public int getAlternativeID() {return this.alternativeID;}
    public void setAlternativeID(int altID) {this.alternativeID = altID;}

    public int getParticipantID() {return this.participantID;}
    public void setParticipantID(int partID) {this.participantID = partID;}

    public String getFeedbackText() {return this.feedbackText;}
    public void setFeedbackText(String fText) {this.feedbackText = fText;}

    public Feedback(){
    	feedbackID = 0;
    }

    public Feedback(int altID, int partID, String fText){
        this.alternativeID = altID;
        this.participantID = partID;
        this.feedbackText = fText;
    }
    
    public Feedback(int fID, int altID, int partID, String fText){
    	this.feedbackID = fID;
        this.alternativeID = altID;
        this.participantID = partID;
        this.feedbackText = fText;
    }
}
