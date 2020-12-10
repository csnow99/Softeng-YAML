package edu.wpi.cs.yaml.demo.http;

import java.util.List;

import edu.wpi.cs.yaml.demo.model.FeedbackInfo;



public class GetFeedbackResponse extends GenericResponse{
	
	public List<FeedbackInfo> feedback;

	public List<FeedbackInfo> getFeedback(){return this.feedback;}
	public void setFeedback(List<FeedbackInfo> feedback){this.feedback = feedback;}
	
	public GetFeedbackResponse(String response, List<FeedbackInfo> feedback) {
		super(response);
		this.feedback = feedback;
	}
	
	public GetFeedbackResponse(int code, String response) {
		super(response, code);
		this.feedback = null;
	}
}
