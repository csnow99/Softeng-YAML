function requestUsername(participantID, choiceID) {

    let xhr = new XMLHttpRequest();
    xhr.open("GET", getUsername_url + "/" + choiceID + "/" + participantID, true);
    xhr.send();

    xhr.onloadend = function () {
     console.log(xhr);
     console.log(xhr.request);
     if (xhr.readyState === XMLHttpRequest.DONE) {
         if (xhr.status === 200) {
          console.log ("XHR:" + xhr.responseText);
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

    let parsedResponse = JSON.parse(response);

    parsedResponse = parsedResponse["participantName"]

    document.getElementById("mainMessage").innerText = "Welcome, " + parsedResponse

}