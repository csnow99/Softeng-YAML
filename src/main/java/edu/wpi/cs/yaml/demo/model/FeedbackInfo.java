package edu.wpi.cs.yaml.demo.model;

import java.util.List;

public class FeedbackInfo {
	int alternativeID;
	String alternativeName;
	List<String> participantName;
	List<String> feedbackText;
	
	public int getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(int alternativeID) {this.alternativeID = alternativeID;}
	
	public String getAlternativeName() {return this.alternativeName;}
	public void setAlternativeName(String alternativeName) {this.alternativeName = alternativeName;}
	
	public List<String> getParticipantName() {return this.participantName;}
	public void setParticipantName(List<String> participantName) {this.participantName = participantName;}

	public List<String> getFeedbackText() {return this.feedbackText;}
	public void setFeedbackText(List<String> feedbackText) {this.feedbackText = feedbackText;}

	public FeedbackInfo() {}

	public FeedbackInfo(int alternativeID, String alternativeName, List<String> participantName, List<String> feedbackText){
		this.alternativeID = alternativeID;
		this.alternativeName = alternativeName;
		this.participantName = participantName;
		this.feedbackText = feedbackText;
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof FeedbackInfo){
			FeedbackInfo other = (FeedbackInfo) o;
			if (
					this.alternativeID == other.alternativeID &&
					this.alternativeName.equals(other.alternativeName) &&
					this.participantName == other.participantName &&
					this.feedbackText == other.feedbackText
			) {
				return true;
			}
		}
		return false;
	}
}
