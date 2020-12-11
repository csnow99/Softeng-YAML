function requestChoiceInfo(choiceID, participantID, callback) {

     let xhr = new XMLHttpRequest();
     xhr.open("GET", getChoice_url + "/" + choiceID + "/" + participantID, true);
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
         }, 500 );
     }
}

function updatePageWithChoice(response) {

    console.log("The response after retrieving Choice Info: " + response)

    let parsedResponse = JSON.parse(response);
    let choiceDiv = document.getElementById("choice")
    let output = "";

    if (Math.floor(parsedResponse["httpCode"] / 100) !== 2) {
        alert(parsedResponse["response"]);
        return;
    }

    parsedResponse = parsedResponse["choice"]

    let choiceName = parsedResponse["choiceName"]
    let choiceID = parsedResponse["choiceID"]
    let choiceDescription = parsedResponse["choiceDescription"]

    output = output + "<h2 style='font-family: product_sansregular; font-size: 3rem; margin-bottom: 30px; font-weight: 600;'>" + choiceName + "</h2>"
    output = output + "<p class='light center'>" + choiceDescription + "</p>"

    choiceDiv.innerHTML = output
    document.getElementById("choiceID").innerText = "To invite your friends to participate in this choice, share with them the following link: https://yamlcs3733bucket.s3.us-east-2.amazonaws.com/html/choice.html?choice=" + choiceID + "&user=0";
}