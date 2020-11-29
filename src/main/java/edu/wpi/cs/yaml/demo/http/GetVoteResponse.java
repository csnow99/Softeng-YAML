package edu.wpi.cs.yaml.demo.http;

import edu.wpi.cs.yaml.demo.model.Vote;

public class GetVoteResponse extends GenericResponse {
	
	public Vote vote;
	
	//when code 200
	public GetVoteResponse(String response, Vote vote) {
		super(response);
		this.vote = vote;
	}
	
	//when code not 200 null is returned as the choice
	public GetVoteResponse(int code, String response) {
		super(response, code);
		this.vote = null;
	}
	
}