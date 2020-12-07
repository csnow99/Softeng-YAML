package edu.wpi.cs.yaml.demo.model;

public class Feedback {
	public int feedbackID;
    public int alternativeID;
    public int participantID;
    public String feedbackText;
    public long feedbackTimestamp;

    public int getAlternativeID() {return this.alternativeID;}
    public void setAlternativeID(int altID) {this.alternativeID = altID;}

    public int getParticipantID() {return this.participantID;}
    public void setParticipantID(int partID) {this.participantID = partID;}

    public long getFeedbackText() {return this.feedbackTimestamp;}
    public void setFeedbackText(long feedbackTimestamp) {this.feedbackTimestamp = feedbackTimestamp;}
    
    public long getFeedbackTimestamp() {return this.feedbackTimestamp;}
    public void setFeedbackTimestamp(long feedbackTimestamp) {this.feedbackTimestamp = feedbackTimestamp;}


    public Feedback(){
    	feedbackID = 0;
    }

    public Feedback(int altID, int partID, String fText){
        this.alternativeID = altID;
        this.participantID = partID;
        this.feedbackText = fText;
        this.feedbackTimestamp = System.currentTimeMillis();;
    }
    
    public Feedback(int fID, int altID, int partID, String fText, long fStamp){
    	this.feedbackID = fID;
        this.alternativeID = altID;
        this.participantID = partID;
        this.feedbackText = fText;
        this.feedbackTimestamp = fStamp;
    }
}
