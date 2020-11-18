package edu.wpi.cs.yaml.demo.http;

public class CreateReportRequest {
	String currentDate;
	
	public String getDate() {return this.currentDate;}
	public void setDate(String currentDate) {this.currentDate = currentDate;}
	
	public CreateReportRequest() {}
	public CreateReportRequest(String currentDate) {this.currentDate =currentDate;}
	
	public String toString() {
		return "Requesting to create a report, today's date: " + currentDate;
	}
}
