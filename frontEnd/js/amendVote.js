function amendVote(alternativeID, username, type) {
    let data = {};
    data["amendType"] = type;
    data["participantID"] = username;
    data["alternativeID"] = alternativeID;

    let js = JSON.stringify(data);
    console.log("JS:" + js);
    let xhr = new XMLHttpRequest();
    xhr.open("POST", amendVote_url, true);

    // send the collected data as JSON
    xhr.send(js);

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if (xhr.statusCode == 200) {
                console.log ("XHR:" + xhr.responseText);
                processGetVotesResponse(xhr.responseText);
            } else {
                console.log("actual:" + xhr.responseText)
                var js = JSON.parse(xhr.responseText);
                var err = js["response"];
                alert (err);
            }
        } else {
            processCreateChoiceResponse("N/A");
        }
    }
}