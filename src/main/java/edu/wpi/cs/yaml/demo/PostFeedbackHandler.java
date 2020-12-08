package edu.wpi.cs.yaml.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.FeedbackDAO;
import edu.wpi.cs.yaml.demo.http.GetFeedbackResponse;
import edu.wpi.cs.yaml.demo.http.PostFeedbackRequest;
import edu.wpi.cs.yaml.demo.model.Feedback;
import edu.wpi.cs.yaml.demo.model.FeedbackInfo;

import java.util.List;

public class PostFeedbackHandler implements RequestHandler<PostFeedbackRequest, GetFeedbackResponse> {
    LambdaLogger logger;
    FeedbackDAO feedbackDAO = new FeedbackDAO();
    AlternativeDAO altDAO = new AlternativeDAO();

    @Override
    public GetFeedbackResponse handleRequest(PostFeedbackRequest req, Context context) {
        if (logger != null) logger.log("In lambda, trying to post feedback ");
        try{
            Feedback aFeedback = new Feedback(req.getAlternativeID(), req.getParticipantID(), req.getText());
            if (feedbackDAO.addFeedback(aFeedback)) {
                List<FeedbackInfo> list = feedbackDAO.getFeedback(altDAO.getChoiceIDA(req.getAlternativeID()));
                return new GetFeedbackResponse("Successfully posted Feedback: " + req.getText(), list);
            } else {
                return new GetFeedbackResponse(400, "Failed to post the feedback for alternative: "
                        + req.getAlternativeID());
            }
        } catch (Exception e) {
            return new GetFeedbackResponse(400, "Failed to post the feedback: " + e.getMessage());
        }
    }
}
