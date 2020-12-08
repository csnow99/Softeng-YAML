function addFeedback(id) {
    let feedbackDiv = document.getElementById(id);
    let alternativeID = id.split("back")[1];
    let output = feedbackDiv.innerHTML;
    let otherAttempt = document.getElementById("newFeedback");
    if (otherAttempt !== null) {
        otherAttempt.parentElement.removeChild(otherAttempt);
    }
    let element = document.getElementById("addFeedback" + alternativeID);
    element.parentElement.removeChild(element);
    output += "<div id='newFeedback'><label>Enter Feedback text:</label><br>" +
                "<textarea id='feedbackInput' rows='4' cols='50'></textarea><br>" +
                "<input type='button' value='Post Feedback' onclick='postFeedback(" + alternativeID + ")'></div>";
    feedbackDiv.innerHTML = output;
}

function postFeedback(alternativeID) {
    let queryString = new URLSearchParams(window.location.search);
    let choiceQueryString = queryString.get("choice")
    let userQueryString = queryString.get("user")
    let finalChoiceID = choiceQueryString.toString()
    let finalParticipantID = userQueryString.toString()

    let data = {};
    data["alternativeID"] = alternativeID;
    data["text"] = document.getElementById("feedbackInput").value;
    let js = JSON.stringify(data);
    console.log("Posting Feedback with JSON:" + js);
    let xhr = new XMLHttpRequest();
    xhr.open("POST",postFeedback_url + "/" + finalChoiceID + "/" + finalParticipantID, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(js);

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