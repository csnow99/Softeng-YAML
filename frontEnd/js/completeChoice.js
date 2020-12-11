function completeChoice(alternativeID) {

    let queryString = new URLSearchParams(window.location.search);
    let choiceQueryString = queryString.get("choice")
    let userQueryString = queryString.get("user")
    let finalChoiceID = choiceQueryString.toString()
    let finalParticipantID = parseInt(userQueryString.toString())

    let data = {};
    data["choiceID"] = finalChoiceID
    data["alternativeID"] = alternativeID
    data["participantID"] = finalParticipantID
    let js = JSON.stringify(data);
    console.log("Completing Choice:" + js);
    let xhr = new XMLHttpRequest();
    xhr.open("POST",completeChoice_url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(js);

    xhr.onloadend = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log(xhr.responseText)
                loadChoicePage()
            } else {
                let js = JSON.parse(xhr.responseText);
                let err = js["response"];
                alert (err);
            }
        } else {
            loadChoicePage()
        }
    };

}
