package edu.wpi.cs.yaml.demo.model;

public class Feedback {
	int feedbackID;
    int alternativeID;
    int participantID;
    String feedbackText;
    long feedbackTimestamp;

    public int getFeedbackID() {return this.feedbackID;}
    public void setFeedbackID(int feedbackID) {this.feedbackID = feedbackID;}

    public int getAlternativeID() {return this.alternativeID;}
    public void setAlternativeID(int altID) {this.alternativeID = altID;}

    public int getParticipantID() {return this.participantID;}
    public void setParticipantID(int partID) {this.participantID = partID;}

    public String getFeedbackText() {return this.feedbackText;}
    public void setFeedbackText(String feedbackText) {this.feedbackText = feedbackText;}
    
    public long getFeedbackTimestamp() {return this.feedbackTimestamp;}
    public void setFeedbackTimestamp(long feedbackTimestamp) {this.feedbackTimestamp = feedbackTimestamp;}


    public Feedback(){
    	feedbackID = 0;
    }

    public Feedback(int altID, int partID, String fText){
        this.alternativeID = altID;
        this.participantID = partID;
        this.feedbackText = fText;
        this.feedbackTimestamp = System.currentTimeMillis();
    }
    
    public Feedback(int altID, int partID, String fText, long fStamp){
        this.alternativeID = altID;
        this.participantID = partID;
        this.feedbackText = fText;
        this.feedbackTimestamp = fStamp;
    }
    public Feedback(int fID, int altID, int partID, String fText, long fStamp){
    	this.feedbackID = fID;
        this.alternativeID = altID;
        this.participantID = partID;
        this.feedbackText = fText;
        this.feedbackTimestamp = fStamp;
    }
}
