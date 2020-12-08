package edu.wpi.cs.yaml.demo.http;

public abstract class GenericResponse {
	String response;
	int httpCode;
	
	public String getResponse() {return this.response;}
	public int getHttpCode() {return this.httpCode;}
	
	public void setResponse(String response) {this.response = response;}
	public void setHttpCode(int httpCode) {this.httpCode = httpCode;}
	
	
	public GenericResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public GenericResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
