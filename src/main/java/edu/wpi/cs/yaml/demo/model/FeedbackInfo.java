package edu.wpi.cs.yaml.demo.model;

import java.util.List;

public class FeedbackInfo {
	int alternativeID;
	String alternativeName;
	List<String> participantName;
	List<String> feedbackText;
	List<Long> feedbackTimestamp;
	
	public int getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(int alternativeID) {this.alternativeID = alternativeID;}
	
	public String getAlternativeName() {return this.alternativeName;}
	public void setAlternativeName(String alternativeName) {this.alternativeName = alternativeName;}
	
	public List<String> getParticipantName() {return this.participantName;}
	public void setParticipantName(List<String> participantName) {this.participantName = participantName;}

	public List<String> getFeedbackText() {return this.feedbackText;}
	public void setFeedbackText(List<String> feedbackText) {this.feedbackText = feedbackText;}
	
	public List<Long> getFeedbackTimestamp() {return this.feedbackTimestamp;}
	public void setFeedbackTimestamp(List<Long> feedbackTimestamp) {this.feedbackTimestamp = feedbackTimestamp;}

	public FeedbackInfo() {}

	public FeedbackInfo(int alternativeID, String alternativeName, List<String> participantName, List<String> feedbackText, List<Long> feedbackTimestamp){
		this.alternativeID = alternativeID;
		this.alternativeName = alternativeName;
		this.participantName = participantName;
		this.feedbackText = feedbackText;
		this.feedbackTimestamp = feedbackTimestamp;
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof FeedbackInfo){
			FeedbackInfo other = (FeedbackInfo) o;
			if (
					this.alternativeID == other.alternativeID &&
					this.alternativeName.equals(other.alternativeName) &&
					this.participantName == other.participantName &&
					this.feedbackText == other.feedbackText &&
					this.feedbackTimestamp == other.feedbackTimestamp
			) {
				return true;
			}
		}
		return false;
	}
}
