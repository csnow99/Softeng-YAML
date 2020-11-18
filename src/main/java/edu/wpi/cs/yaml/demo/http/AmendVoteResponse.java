package edu.wpi.cs.yaml.demo.http;

public class AmendVoteResponse extends GenericResponse {
	public AmendVoteResponse(String response) {
		super(response);
	}
	public AmendVoteResponse(String response, int code) {
		super(response, code);
	}
}
