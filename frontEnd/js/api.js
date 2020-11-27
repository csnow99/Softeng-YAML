// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
let base_url = "https://65y680psjl.execute-api.us-east-2.amazonaws.com/beta/";

let createChoice_url = base_url + "createChoice";    // POST
let choicePage_url = base_url + "html/choice.html";
let register_url = base_url + "registerParticipant"; // POST
let getChoice_url = base_url + "choice" //GET + /{choiceID}
let getAlternative_url = base_url + "alternative" //GET
let createReport_url = base_url + "admin" //GET

