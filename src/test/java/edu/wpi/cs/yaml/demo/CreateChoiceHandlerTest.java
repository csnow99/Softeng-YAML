package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.yaml.demo.http.CreateChoiceRequest;
import edu.wpi.cs.yaml.demo.http.CreateChoiceResponse;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDRequest;
import edu.wpi.cs.yaml.demo.http.DeleteSingleChoiceByIDResponse;
import edu.wpi.cs.yaml.demo.model.Alternative;

import edu.wpi.cs.yaml.demo.LambdaTest;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateChoiceHandlerTest extends LambdaTest {

    String testSuccessInput(String incoming) throws IOException {
    	CreateChoiceHandler handler = new CreateChoiceHandler();
    	CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);
       
        CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
        
        return resp.response;			//return choiceID
    }
	
    String testFailInput(String incoming, int failureCode) throws IOException {
    	CreateChoiceHandler handler = new CreateChoiceHandler();
    	CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

    	CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
        
        return resp.response;
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
    	CreateChoiceRequest ccr = new CreateChoiceRequest("testChoice4", 10, "sample description", alternatives);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        System.out.print(SAMPLE_INPUT_STRING);
        String choiceID = null;
        try {
        	 choiceID = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
        if (choiceID != null) {
        DeleteSingleChoiceByIDRequest dcr = new DeleteSingleChoiceByIDRequest(choiceID);
        DeleteSingleChoiceByIDResponse d_resp = new DeleteChoiceHandler().handleRequest(dcr, createContext("delete"));
        assertEquals("Succesfully deleted: "+choiceID, d_resp.response);
        }
    }
    
    
    
}
