function requestFeedbackInfo(choiceID) {
    let xhr = new XMLHttpRequest();
    xhr.open("GET", getFeedback_url + "/" + choiceID, true);
    xhr.send();
    console.log("Send request to retrieve Feedbacks")
    xhr.onloadend = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                updatePageWithFeedback(xhr.responseText);
            } else {
                let js = JSON.parse(xhr.responseText);
                let err = js["response"];
                alert (err);
            }
        } else {
            updatePageWithFeedback("N/A");
        }
    };
}

function updatePageWithFeedback(response) {
    console.log("The response after retrieving Feedbacks: " + response);
    let parseResponse = (JSON).parse(response);
    let feedbackInfo = parseResponse["feedback"];
    for (let i in feedbackInfo) {
        let anAlternative = feedbackInfo[i];
        console.log(anAlternative);
        let feedbackDiv = document.getElementById("feedback" + anAlternative["alternativeID"]);
        let names = anAlternative["participantName"];
        let text = anAlternative["feedbackText"];
        let timeStamps = anAlternative["feedbackTimestamp"];
        let output = "<h4>Feedbacks: </h4>";
        for (let j = 0; j < names.length; j++) {
            let aName = names[j];
            let aText = text[j];
            let aTimeStamp = new Date(timeStamps[j]);
            let time = aTimeStamp.toLocaleString();
            output +=
                "   <p>\n" +
                "       <b>" + aName + "</b> said: <br>" +
                "       " + aText + " <br>" +
                "       at " + time +
                "   </p>"
            feedbackDiv.innerHTML = output;
        }
    }
}