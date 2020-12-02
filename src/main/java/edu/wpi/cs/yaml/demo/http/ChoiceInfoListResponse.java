package edu.wpi.cs.yaml.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.model.ChoiceInfo;

public class ChoiceInfoListResponse {
	public int statusCode;
	public String response;
	public List<ChoiceInfo> infos;
	
	//Setter and getters

	public ChoiceInfoListResponse(List<ChoiceInfo> infos, int code) {
		this.statusCode = code;
		this.infos = infos;
		this.response = "";
	}

	public ChoiceInfoListResponse(int code, String errorMessage) {
		this.statusCode = code;
		this.response = errorMessage;
		this.infos = new ArrayList<ChoiceInfo>();
	}
}
