package edu.wpi.cs.yaml.demo.model;

import java.util.List;

public class VoteInfo {
	int alternativeID;
	String alternativeName;
	int numUpvotes;
	int numDownvotes;
	List<String> upvoters;
	List<String> downvoters;
	
	public int getAlternativeID() {return this.alternativeID;}
	public void setAlternativeID(int alternativeID) {this.alternativeID = alternativeID;}
	
	public String getAlternativeName() {return this.alternativeName;}
	public void setAlternativeName(String alternativeName) {this.alternativeName = alternativeName;}
	
	public int getNumUpvotes() {return this.numUpvotes;}
	public void setNumUpvotes(int numUpvotes) {this.numUpvotes = numUpvotes;}

	public int getNumDownvotes() {return this.numDownvotes;}
	public void setNumDownvotes(int numDownvotes) {this.numDownvotes = numDownvotes;}
	
	public List<String> getUpvoters() {return this.upvoters;}
	public void setupvoters(List<String> upvoters) {this.upvoters = upvoters;}
	
	public List<String> getdownvoters() {return this.downvoters;}
	public void setDownvoters(List<String> downvoters) {this.downvoters = downvoters;}
	
	VoteInfo(int alternativeID, String alternativeName, int numUpvotes, int numDownvotes, List<String> upvoters, List<String> downvoters){
		this.alternativeID = alternativeID;
		this.alternativeName = alternativeName;
		this.numUpvotes = numUpvotes;
		this.numDownvotes = numDownvotes;
		this.upvoters = upvoters;
		this.downvoters = downvoters;
	}
}
