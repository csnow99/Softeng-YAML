package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.http.ChoiceInfoListResponse;


public class CreateReportsHandler implements  RequestHandler<Object, ChoiceInfoListResponse> {

    @Override
    public ChoiceInfoListResponse handleRequest(Object input, Context context) {
        return null;
    }
    //make DAO return a list of all choices
	//put these in a choiceInfo list
	//return the list
}
