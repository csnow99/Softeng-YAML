function loadChoicePage() {

    let queryString = new URLSearchParams(window.location.search)

    let choiceQueryString = queryString.get("choice")
    let userQueryString = queryString.get("user")

    let finalChoiceID = choiceQueryString.toString()
    let finalParticipantID = userQueryString.toString()
    console.log(finalParticipantID)

    if(finalParticipantID === "0") {
        requestChoiceInfo(finalChoiceID, null)
        requestAlternativeInfo(finalChoiceID, null)
    } else {
        let responseCode = requestUsername(finalParticipantID, finalChoiceID, null)
        setTimeout(function() {
            let code = responseCode["httpCode"]
            console.log(responseCode)
            console.log(code)

            if(code === "404") { // Not found
                alert(responseCode["response"])
            } else {
                let element = document.getElementById("loginStuff")
                element.parentElement.removeChild(element)
                requestUsername(finalParticipantID, finalChoiceID, requestChoiceInfo)
            }
        }, 5000)
    }

    //get querystring with the key "user"

    //if this qs does not exist in the url then just load the normal page
        //else if the qs exists then
            //first check if the user with this id exists if not then load normal page
                //else if they exist then
                    // Go to database and find entry that matches the querystring return the username and or password in a response
                    // Use this response to then do the register user request and log the user in

    //requestVoteInfo
}

function requestChoiceInfo(choiceID, callback) {

     let xhr = new XMLHttpRequest();
     xhr.open("GET", getChoice_url + "/" + choiceID, true);
     xhr.send();

     xhr.onloadend = function () {
         if (xhr.readyState === XMLHttpRequest.DONE) {
             if (xhr.status === 200) {
              updatePageWithChoice(xhr.responseText);
             } else {
                  let js = JSON.parse(xhr.responseText);
                  let err = js["response"];
                  alert (err);
             }
         } else {
           updatePageWithChoice("N/A");
         }
     };
     if (callback !== null) {
         setTimeout( function(){
             callback(choiceID, requestVoteInfo)
         }, 1000 );
     }
}

function updatePageWithChoice(response) {

    console.log("The response after retrieving Choice Info: " + response)

    let parsedResponse = JSON.parse(response);
    let choiceDiv = document.getElementById("choice")
    let output = "";

    parsedResponse = parsedResponse["choice"]

    let choiceName = parsedResponse["choiceName"]
    let choiceID = parsedResponse["choiceID"]
    let choiceDescription = parsedResponse["choiceDescription"]

    output = output + "<h2>" + choiceName + "</h2>"
    output = output + "<p>" + choiceDescription + "</p>"

    choiceDiv.innerHTML = output
    document.getElementById("choiceID").innerText = "To invite your friends to participate in this choice, share with them the following link: https://yamlcs3733bucket.s3.us-east-2.amazonaws.com/html/choice.html?choice=" + choiceID + "&user=0";
}