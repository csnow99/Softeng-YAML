package edu.wpi.cs.yaml.demo.http;

import java.util.ArrayList;

import edu.wpi.cs.yaml.demo.model.ChoiceInfo;

public class CreateReportResponse extends GenericResponse {
	ArrayList<ChoiceInfo> reports;
	
	public CreateReportResponse(String response, ArrayList<ChoiceInfo> reports) {
		super(response);   //200 OK
		this.reports = reports;
	}
	public CreateReportResponse(String response, int code) {
		super(response, code);
		reports = new ArrayList<ChoiceInfo> (); //an error occurred, no list returned
	}
}