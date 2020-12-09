package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import edu.wpi.cs.yaml.demo.http.*;
import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Participant;

public class GetFeedbackHandlerTest extends LambdaTest{

    @Test
    public void testGetFeedbackHandlerTest() {
    	ChoiceDAO choiceDAO = new ChoiceDAO();
        String choiceID = null;

        try {
            /*We first need to insert a choice in the database*/
            ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
            Alternative alt1 = new Alternative("alt1_name", "alt1_description");
            Alternative alt2 = new Alternative("alt2_name", "alt2_description");
            Alternative alt3 = new Alternative("alt3_name", "alt3_description");
            alternatives.add(alt1);
            alternatives.add(alt2);
            alternatives.add(alt3);

            CreateChoiceHandler createHandler = new CreateChoiceHandler();
            CreateChoiceRequest ccr = new CreateChoiceRequest("testPostFeedback", 3, "sample description", alternatives);
            CreateChoiceResponse createResp = createHandler.handleRequest(ccr, createContext("create"));
            choiceID = createResp.getResponse();
            if(choiceID == null) {Assert.fail("Created ChoiceID is null");}

            //We need alternativeID's
            AlternativeDAO altDAO = new AlternativeDAO();
            int alt1ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt1_name");
            int alt2ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt2_name");
            int alt3ID = altDAO.getAlternativeIDFromChoiceIDandTitle(choiceID, "alt3_name");

            RegisterParticipantHandler registerHandler = new RegisterParticipantHandler();
            ParticipantDAO partDAO = new ParticipantDAO();

            /*Register first user*/
            Participant participant1 = new Participant(choiceID, "user1", "password1");
            RegisterParticipantRequest reg1 = new RegisterParticipantRequest(participant1.getChoiceID(), participant1.getName(), participant1.getPassword());
            registerHandler.handleRequest(reg1, createContext("register1"));
            //We need user ID to post feedback
            int part1ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "user1");

            /*Register second user */
            registerHandler = new RegisterParticipantHandler();
            Participant participant2 = new Participant(choiceID, "user2", "password2");
            RegisterParticipantRequest reg2 = new RegisterParticipantRequest(participant2.getChoiceID(), participant2.getName(), participant2.getPassword());
            registerHandler.handleRequest(reg2, createContext("register2"));
            //We need user ID to post feedback
            int part2ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "user2");

            /*Register third user */
            registerHandler = new RegisterParticipantHandler();
            Participant participant3 = new Participant(choiceID, "user3", "password3");
            RegisterParticipantRequest reg3 = new RegisterParticipantRequest(participant3.getChoiceID(), participant3.getName(), participant3.getPassword());
            registerHandler.handleRequest(reg3, createContext("register3"));
            //We need user ID to post feedback
            int part3ID = partDAO.getParticipantIDFromChoiceIDAndParticipantName(choiceID, "user3");

            // Posting Feedback to test the get all Feedback
            PostFeedbackRequest feed1 = new PostFeedbackRequest(alt1ID, part1ID, "firstFeedback");
            PostFeedbackRequest feed2 = new PostFeedbackRequest(alt2ID, part1ID, "secondFeedback");
            PostFeedbackRequest feed3 = new PostFeedbackRequest(alt1ID, part1ID, "thirdFeedback"); // changed from PostFeedbackHandlerTest
            PostFeedbackRequest feed4 = new PostFeedbackRequest(alt1ID, part2ID, "fourthFeedback");
            PostFeedbackRequest feed5 = new PostFeedbackRequest(alt2ID, part2ID, "fifthFeedback");

            PostFeedbackHandler postHandler = new PostFeedbackHandler();
            postHandler.handleRequest(feed1, createContext("feed1"));
            postHandler.handleRequest(feed2, createContext("feed2"));
            postHandler.handleRequest(feed3, createContext("feed3"));
            postHandler.handleRequest(feed4, createContext("feed4"));
            postHandler.handleRequest(feed5, createContext("feed5"));

            /*Finally we can test the GetFeedbackHandler*/
            GetFeedbackHandler handler = new GetFeedbackHandler();
            GetFeedbackResponse feedbackResponse = handler.handleRequest(choiceID, createContext("getFeedback"));
            assertEquals(200, feedbackResponse.getHttpCode());
            assertEquals(3, feedbackResponse.feedback.size());
            assertEquals(3, feedbackResponse.feedback.get(0).getParticipantName().size());
            assertEquals(2, feedbackResponse.feedback.get(1).getFeedbackText().size());
            assertEquals(0, feedbackResponse.feedback.get(2).getParticipantName().size());
            assertEquals("firstFeedback", feedbackResponse.feedback.get(0).getFeedbackText().get(0));

            choiceDAO.deleteChoice(choiceID);
         	
        }catch (Exception e) {
        	try {
             	choiceDAO.deleteChoice(choiceID);
             } catch (Exception e2) {
             	Assert.fail();
             }
            Assert.fail();
        }
    }
}
