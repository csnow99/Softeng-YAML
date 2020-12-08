let base_url = "https://65y680psjl.execute-api.us-east-2.amazonaws.com/beta/";

let createChoice_url = base_url + "createChoice";       // POST
let register_url = base_url + "registerParticipant";    // POST
let getChoice_url = base_url + "choice"                 // GET + /{choiceID}
let getAlternative_url = base_url + "alternative"       // GET + /{choiceID}
let createReport_url = base_url + "admin"               // GET
let getVote_url = base_url + "vote"                     // GET + /{choiceID}
let amendVote_url = base_url + "vote"                   // POST + /{choiceID}/{participantID}
let getUsername_url = base_url + "choice"               // GET + /{choiceID}/{participantID}
let getFeedback_url = base_url + "feedback"             // GET + /{choiceID}



