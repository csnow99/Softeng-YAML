package edu.wpi.cs.yaml.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.model.ChoiceInfo;

public class ChoiceInfoListResponse {
	int statusCode;
	String response;
	List<ChoiceInfo> infos;
	
	//Setter and getters
	public List<ChoiceInfo> getInfos() {return this.infos;}
	public void setInfos(List<ChoiceInfo> infos) {this.infos = infos;}

	public int getStatusCode() {return this.statusCode;}
	public void setStatusCode(int statusCode) {this.statusCode = statusCode;}

	public String getResponse() {return this.response;}
	public void setResponse(String response) {this.response = response;}

	public ChoiceInfoListResponse(List<ChoiceInfo> infos, int code) {
		this.statusCode = code;
		this.infos = infos;
		this.response = "Success";
	}

	public ChoiceInfoListResponse(int code, String errorMessage) {
		this.statusCode = code;
		this.response = errorMessage;
		this.infos = new ArrayList<>();
	}
}
