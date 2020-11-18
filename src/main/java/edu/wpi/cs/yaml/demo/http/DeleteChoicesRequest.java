package edu.wpi.cs.yaml.demo.http;

public class DeleteChoicesRequest {
	int howOld;
	
	public int returnHowOld() {return this.howOld;}
	public void setHowOld(int howOld) {this.howOld = howOld;}
	
	public DeleteChoicesRequest() {}
	public DeleteChoicesRequest(int howOld) {this.howOld = howOld;}
	
	public String toString() {
		return "Requesting to delete all choices older than "+ Integer.toString(howOld) + " days";
	}
}
