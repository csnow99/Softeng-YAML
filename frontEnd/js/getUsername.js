function requestUsername(participantID, choiceID) {

    let xhr = new XMLHttpRequest();
    xhr.open("GET", getUsername_url + "/" + choiceID + "/" + participantID, true);
    xhr.send();

    xhr.onloadend = function () {
     if (xhr.readyState === XMLHttpRequest.DONE) {
         if (xhr.status === 200) {
          updatePageWithUsername(xhr.responseText);
         } else {
             console.log("actual:" + xhr.responseText)
              let js = JSON.parse(xhr.responseText);
              let err = js["response"];
              alert (err);
         }
     } else {
       updatePageWithUsername("N/A");
     }
    };
}

function updatePageWithUsername(response) {

    console.log("The response after getting the User " + response)
    let parsedResponse = JSON.parse(response);
    parsedResponse = parsedResponse["participantName"]
    document.getElementById("mainMessage").innerText = "Welcome, " + parsedResponse

}