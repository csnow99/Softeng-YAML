package edu.wpi.cs.yaml.demo.http;

public class DeleteChoicesRequest {
	float howOld;
	
	public float returnHowOld() {return this.howOld;}
	public void setHowOld(float howOld) {this.howOld = howOld;}
	
	public DeleteChoicesRequest() {}
	public DeleteChoicesRequest(float howOld) {this.howOld = howOld;}
	
	public String toString() {
		return "Requesting to delete all choices older than "+ Float.toString(howOld) + " days";
	}
}
