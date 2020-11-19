package edu.wpi.cs.yaml.demo;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.LambdaTest;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateChoiceHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	CreateChoiceHandler handler = new CreateChoiceHandler();
    	CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);
       
        CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	CreateChoiceHandler handler = new CreateChoiceHandler();
    	CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

    	CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

   
    // NOTE: Manually remove the added choice every time
    @Test
    public void testShouldBeOk() {
    	ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
    	Alternative alt1 = new Alternative("alt1_ID", "alt1_name", "alt1_description");
    	Alternative alt2 = new Alternative("alt2_ID", "alt2_name", "alt2_description");
    	Alternative alt3 = new Alternative("alt3_ID", "alt3_name", "alt3_description");
    	alternatives.add(alt1);
    	alternatives.add(alt2);
    	alternatives.add(alt3);
    	CreateChoiceRequest ccr = new CreateChoiceRequest("testChoice2", 10, "sample description", alternatives);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
        
        
    }
    
    
    
}
