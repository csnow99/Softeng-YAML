package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs.yaml.demo.http.ChoiceInfoListResponse;
import edu.wpi.cs.yaml.demo.http.DeleteChoicesRequest;

public class DeleteReportsHandler implements RequestHandler<DeleteChoicesRequest, ChoiceInfoListResponse> {


    @Override
    public ChoiceInfoListResponse handleRequest(DeleteChoicesRequest input, Context context) {
        return null;
    }

}
