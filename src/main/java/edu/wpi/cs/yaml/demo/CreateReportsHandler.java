package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.http.ChoiceInfoListResponse;
import edu.wpi.cs.yaml.demo.model.ChoiceInfo;

import java.util.List;


public class CreateReportsHandler implements  RequestHandler<Object, ChoiceInfoListResponse> {

    public LambdaLogger logger;

    List<ChoiceInfo> getAllChoices() throws Exception {
        logger.log("in getAllChoices");
        ChoiceDAO dao = new ChoiceDAO();

        return dao.getAllChoices();
    }

    @Override
    public ChoiceInfoListResponse handleRequest(Object input, Context context) {

        logger = context.getLogger();
        logger.log("Loading Java Lambda handler to list all choices");

        

        try{
            List<ChoiceInfo> list = getAllChoices();
            return new ChoiceInfoListResponse(list, 200);
        } catch (Exception e) {
        	return new ChoiceInfoListResponse(403, e.getMessage());
        }
        
    }

}
