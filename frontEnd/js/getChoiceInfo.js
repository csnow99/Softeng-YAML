function loadChoicePage() {

    let queryString = new URLSearchParams(window.location.search)

    queryString = queryString.get("choice")

    //get querystring with the key "user"

    //if this qs does not exist in the url then just load the normal page
        //else if the qs exists then
            //first check if the user with this id exists if not then load normal page
                //else if they exist then
                    // Go to database and find entry that matches the querystring return the username and or password in a response
                    // Use this response to then do the register user request and log the user in

    let finalChoiceID = queryString.toString()
    console.log(finalChoiceID)

    requestChoiceInfo(finalChoiceID)

    requestAlternativeInfo(finalChoiceID)

    //requestVoteInfo
}

function requestChoiceInfo(choiceID) {

     let xhr = new XMLHttpRequest();
     xhr.open("GET", getChoice_url + "/" + choiceID, true);
     xhr.send();

     xhr.onloadend = function () {
         console.log(xhr);
         console.log(xhr.request);
         if (xhr.readyState === XMLHttpRequest.DONE) {
             if (xhr.status === 200) {
              console.log ("XHR:" + xhr.responseText);
              updatePageWithChoice(xhr.responseText);
             } else {
                 console.log("actual:" + xhr.responseText)
                  let js = JSON.parse(xhr.responseText);
                  let err = js["response"];
                  alert (err);
             }
         } else {
           updatePageWithChoice("N/A");
         }
     };
}

function updatePageWithChoice(response) {

    let parsedResponse = JSON.parse(response);
    let choiceDiv = document.getElementById("choice")
    let output = document.getElementById("choice").innerHTML;

    parsedResponse = parsedResponse["choice"]

    let choiceName = parsedResponse["choiceName"]
    let choiceID = parsedResponse["choiceID"]
    let choiceDescription = parsedResponse["choiceDescription"]

    output = output + "<h2>" + choiceName + "</h2>"
    output = output + "<p>" + choiceDescription + "<p>"

    choiceDiv.innerHTML = output
    document.getElementById("choiceID").innerText = "Choice ID is: " + choiceID;
}