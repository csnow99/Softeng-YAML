package edu.wpi.cs.yaml.demo.http;

import java.util.List;

import edu.wpi.cs.yaml.demo.model.VoteInfo;



public class GetVotesResponse extends GenericResponse{
	
	public List<VoteInfo> votes;
	
	public GetVotesResponse(String response, List<VoteInfo> votes) {
		super(response);
		this.votes = votes;
	}
	
	public GetVotesResponse(int code, String response) {
		super(response, code);
		this.votes = null;
	}
}
