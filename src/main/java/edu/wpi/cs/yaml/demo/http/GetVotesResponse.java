package edu.wpi.cs.yaml.demo.http;

import java.util.HashMap;
import java.util.List;



public class GetVotesResponse extends GenericResponse{
	
	public List<Object> votes;
	public HashMap<String, List<Object>> voteMap = new HashMap<>();
	
	public GetVotesResponse(String response, List<Object> votes) {
		super(response);
		this.votes = votes;
	}
	
	public GetVotesResponse(int code, String response) {
		super(response, code);
		this.votes = null;
	}
}
