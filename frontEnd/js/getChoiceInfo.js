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