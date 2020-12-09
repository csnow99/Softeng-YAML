package edu.wpi.cs.yaml.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yaml.demo.http.*;
import edu.wpi.cs.yaml.demo.model.FeedbackInfo;
import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ParticipantDAO;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Participant;

public class PostFeedbackHandlerTest extends LambdaTest {
	 @Test
	    public void testPostFeedbackHandler() {
		 	
	    	try {
				/*We first need to insert a choice in the database*/
				ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
				Alternative alt1 = new Alternative("alt1_name", "alt1_description");
				Alternative alt2 = new Alternative("alt2_name", "alt2_description");
				Alternative alt3 = new Alternative("alt3_name", "alt3_description");
				alternatives.add(alt1);
				alternatives.add(alt2);
				alternatives.add(alt3);
				String choiceID = null;

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
				
				/*Finally we can test the PostFeedbackHandler*/
				PostFeedbackRequest feedbackRequest1 = new PostFeedbackRequest(alt1ID, part1ID, "firstFeedback");
				PostFeedbackRequest feedbackRequest2 = new PostFeedbackRequest(alt2ID, part1ID, "secondFeedback");
				PostFeedbackRequest feedbackRequest3 = new PostFeedbackRequest(alt3ID, part1ID, "thirdFeedback");
				PostFeedbackRequest feedbackRequest4 = new PostFeedbackRequest(alt1ID, part2ID, "fourthFeedback");
				
				PostFeedbackHandler handler = new PostFeedbackHandler();

				GetFeedbackResponse feedbackResponse1 = handler.handleRequest(feedbackRequest1, createContext("feedback1"));
				assertEquals(200, feedbackResponse1.getHttpCode());
				assertEquals(3, feedbackResponse1.feedback.size()); // Since there are only three alternatives
				List<FeedbackInfo> listOfFeedbacks1 = feedbackResponse1.feedback;
				assertEquals(1, listOfFeedbacks1.get(0).getParticipantName().size()); // Since there is only one feedback
				assertEquals("user1", listOfFeedbacks1.get(0).getParticipantName().get(0));

				GetFeedbackResponse feedbackResponse2 = handler.handleRequest(feedbackRequest2, createContext("feedback1"));
				assertEquals(200, feedbackResponse2.getHttpCode());
				assertEquals(3, feedbackResponse2.feedback.size()); // Since there are only three alternatives
				List<FeedbackInfo> listOfFeedbacks2 = feedbackResponse2.feedback;
				assertEquals(1, listOfFeedbacks2.get(0).getParticipantName().size()); // Since there is only one feedback
				assertEquals("user1", listOfFeedbacks2.get(0).getParticipantName().get(0));
				assertEquals(1, listOfFeedbacks2.get(1).getParticipantName().size()); // Since there is only one feedback
				assertEquals("user1", listOfFeedbacks2.get(1).getParticipantName().get(0));
				assertEquals(0, listOfFeedbacks2.get(2).getParticipantName().size());

				GetFeedbackResponse feedbackResponse3 = handler.handleRequest(feedbackRequest3, createContext("feedback1"));
				assertEquals(200, feedbackResponse3.getHttpCode());
				assertEquals(3, feedbackResponse3.feedback.size()); // Since there are only three alternatives
				List<FeedbackInfo> listOfFeedbacks3 = feedbackResponse3.feedback;
				assertEquals(1, listOfFeedbacks3.get(0).getParticipantName().size()); // Since there is only one feedback
				assertEquals("user1", listOfFeedbacks3.get(0).getParticipantName().get(0));
				assertEquals(1, listOfFeedbacks3.get(1).getParticipantName().size()); // Since there is only one feedback
				assertEquals("user1", listOfFeedbacks3.get(1).getParticipantName().get(0));
				assertEquals(1, listOfFeedbacks3.get(2).getParticipantName().size()); // Since there is only one feedback
				assertEquals("user1", listOfFeedbacks3.get(2).getParticipantName().get(0));

				GetFeedbackResponse feedbackResponse4 = handler.handleRequest(feedbackRequest4, createContext("feedback1"));
				assertEquals(200, feedbackResponse4.getHttpCode());
				assertEquals(3, feedbackResponse4.feedback.size()); // Since there are only three alternatives
				List<FeedbackInfo> listOfFeedbacks4 = feedbackResponse4.feedback;
				assertEquals(2, listOfFeedbacks4.get(0).getParticipantName().size()); // Since there are two feedbacks now
				assertEquals("user2", listOfFeedbacks4.get(0).getParticipantName().get(1));
				assertEquals(1, listOfFeedbacks4.get(1).getParticipantName().size()); // Since there is only one feedback
				assertEquals("user1", listOfFeedbacks4.get(1).getParticipantName().get(0));
				assertEquals(1, listOfFeedbacks4.get(2).getParticipantName().size()); // Since there is only one feedback
				assertEquals("user1", listOfFeedbacks4.get(2).getParticipantName().get(0));

				/*Delete the inserted choice*/
				if (choiceID != null) {
					DeleteSingleChoiceByIDRequest dcr = new DeleteSingleChoiceByIDRequest(choiceID);
					DeleteSingleChoiceByIDResponse d_resp = new DeleteSingleChoiceByIDChoiceHandler().handleRequest(dcr, createContext("delete"));
					assertEquals("Succesfully deleted: "+choiceID, d_resp.getResponse());
				}

			}catch (Exception e) {
				Assert.fail();
			}
		}
}
