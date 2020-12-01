package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.wpi.cs.yaml.demo.http.ChoiceInfoListResponse;

public class DeleteReportsHandler implements RequestHandler<Long, ChoiceInfoListResponse> {

    /*
    @Override
    public ChoiceInfoListResponse handleRequest(long input, Context context) {
        return null;
    }
    */

    @Override
    public ChoiceInfoListResponse handleRequest(Long input, Context context) {
        return null;
    }
}
