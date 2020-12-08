package edu.wpi.cs.yaml.demo.http;

import java.util.List;

import edu.wpi.cs.yaml.demo.model.Alternative;

public class  GetAlternativesResponse extends GenericResponse{
	List<Alternative> alternatives;
	
	public List<Alternative> getAlternatives() {return this.alternatives;}
	public void setAlternatives(List<Alternative> alternatives) {this.alternatives = alternatives;}
	
	public GetAlternativesResponse(String response, List<Alternative> alternatives) {
		super(response);
		this.alternatives = alternatives;
	}
	
	public GetAlternativesResponse(int code, String response) {
		super(response, code);
		this.alternatives = null;
	}

}
