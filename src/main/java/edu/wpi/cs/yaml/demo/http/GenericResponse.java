package edu.wpi.cs.yaml.demo.http;

public abstract class GenericResponse {
	public String response;
	public int httpCode;
	
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
