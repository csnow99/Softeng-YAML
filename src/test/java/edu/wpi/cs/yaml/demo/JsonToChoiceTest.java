package edu.wpi.cs.yaml.demo;

import com.google.gson.Gson;

import edu.wpi.cs.yaml.demo.model.Choice;

import org.junit.Assert;
import org.junit.Test;

public class JsonToChoiceTest {
	
	@Test
	public void testJsonToChoice() {
		String inputString = "{\r\n" + 
				"  \"choiceID\": \"1080016323003461109688296882\",\r\n" + 
				"  \"choiceName\": \"qwe12\",\r\n" + 
				"  \"maxParticipants\": 10,\r\n" + 
				"  \"choiceDescription\": \"asdw\",\r\n" + 
				"  \"dateCreated\": 1606002170260,\r\n" + 
				"  \"isCompleted\": false\r\n" + 
				"}";;
		
		Choice choice = new Gson().fromJson(inputString, Choice.class);
		
		Assert.assertEquals(choice.choiceID, "1080016323003461109688296882");
		Assert.assertEquals(choice.choiceName, "qwe12");
		Assert.assertEquals(choice.maxParticipants, 10);
		Assert.assertEquals(choice.choiceDescription, "asdw");
		Assert.assertEquals(choice.dateCreated, 1606002170260l);
		Assert.assertEquals(choice.isCompleted, false);
		
	}
	}
