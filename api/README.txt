Part 1) API
/admin    		Contains the functionality for the admin page
get: 	The response is a ChoiceInfosList which contains a list of Choice Infos and a HTTP status code
     	**each choice info contains the choiceID, a boolean value whether it is completed and a completion date

post: 	Used to delete Choices older than a specified amount of days
      	The request contains solely how old the Choices are which we want to delete
	The response is again a ChoiceInfosList that has been updated to exlude the deleted choices

/registerParticipant  	Contains fucntionality for registering a participant to participate in a choice
post:	Sends a RegisterParticipantRequest which contains everything that is neccesary to register a participant to a choice
	The RegisterParticipantResponse contains the HTTP status code and a response message ("success"/"fail")
	The HTTP code is ... if duplicate name or ... if there can be no more participants for the choice


/choice/{choiceID}	Contains functionality for constructing a representation of a choice in the browser
get: 	Returns the basic info for a given choice ID which are neccessary for the browser to replicate, such as  list of alternatives and the HTTP status code
	whether it is completed and on which date is has been completed
	The Choice response contains all info mentioned above

/completeChoice:	Contains functionality for completing a choice
post:	The CompleteChoiceRequest contains the ID of the choice and the username and password of the participant for verification
	The response is again a Choice, however this time updated to show that it is completed and with a completion date

/createChoice: 		Contains functionality for creating a choice in the Database
post: 	The CreateChoiceRequest contains everything which is necessary for creating a choice (name, alternatives, maxParticipants etc.)
	The lambda function assings it a unique ID and stores it in the database
	The response CreateChoiceResponse contains a HTTP status code and a response message ("success"/"fail")
	We imagine that then the participant is then redirected to the correct page using javascript (which in turn would call the "/choice/{choiceID} get" request

/feedback/{choiceID}:	Contains functionality for retrieving feedbacks used to update the HTML in the browser
get:	The response returns a FeedbackList which contains a list of Feedbacks and a HTTP status code
	A Feedback contains  the alternativeID to which it belongs, the participantName who wrote it and the actual text i.e. contents of the feedback

/feedback:		Contains functionality for posting feedbacks
post:	The PostFeebackRequest contains the ID of the alternative, the participant who is writing the feedback and the text i.e. contents of the feedback
	The response is again a FeedbackList which is now updated to contain the newly added feedback

/vote/{choiceID}:	Contains functionality for retrieving votes used to update the HTML in the browser
get:	The response is a VotesList which contains a list of Votes and a HTTP status code 
	A Votes contains the ID of the alternative to which it belongs, the name of the participant who left it and its type (true=upvote, false=downvote)
	An upvote and downvote buttons would also update based on this -> the upvote button would light up green if the user upvoted for the corresponding alternative,
	and the downvote button would light up red if the user downvoted the corresponding alternative

/vote:			Contains functionality for deleting and posting votes 
post:	The amendVoteRequest contains the ID of the alternative, the participant who is voting and the type of vote (true = upvote, false = downvote)
	It was envisioned that the functionality would work as follows: Initially the participant has not voted for anything. Once he clicks on the upvote
	button, the vote is registered and the upvote button lights up. If he now clicks on the downvote button, the vote is now changed to a downvote,
	the upvote button is no longer lit up, but now the downvote button is. If he now clicks on the downvote button again, the vote is now deleted from
	the RDS, and both the upvote/downvote buttons are not lit up anymore
	The response is again a VoteList



