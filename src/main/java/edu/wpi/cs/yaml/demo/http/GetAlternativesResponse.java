package edu.wpi.cs.yaml.demo.http;

import java.util.List;

import edu.wpi.cs.yaml.demo.model.Alternative;

public class GetAlternativesResponse extends GenericResponse{
	public List<Alternative> alternatives;
	
	public GetAlternativesResponse(String response, List<Alternative> alternatives) {
		super(response);
		this.alternatives = alternatives;
	}
	
	public GetAlternativesResponse(int code, String response) {
		super(response, code);
		this.alternatives = null;
	}

}
