package edu.wpi.cs.yaml.demo.http;

public class PostFeedbackResponse extends GenericResponse {
	public PostFeedbackResponse(String response) {
		super(response);
	}
	public PostFeedbackResponse(String response, int code) {
		super(response, code);
	}
}