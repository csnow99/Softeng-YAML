function requestVoteInfo(choiceID){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", getVote_url + "/" + choiceID, true);
    xhr.send();

    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log ("XHR:" + xhr.responseText);
                updatePageWithVotes(xhr.responseText);
            } else {
                console.log("actual:" + xhr.responseText)
                let js = JSON.parse(xhr.responseText);
                let err = js["response"];
                alert (err);
            }
        } else {
            updatePageWithVotes("N/A");
        }
    };
}

function updatePageWithVotes(response){
    let parsedResponse = JSON.parse(response);
    parsedResponse = parsedResponse["votes"];

    for (let i in parsedResponse){
        let vote = parsedResponse[i]
        console.log(vote["alternativeID"])

        let altLikes = document.getElementById("likeDesc" + vote["alternativeID"])
        altLikes.innerText = "Number of Votes: " + vote["numUpvotes"] +
            " Users who approve: " + vote["upvoters"]
        let altDislikes = document.getElementById("dislikeDesc" + vote["alternativeID"])
        altDislikes.innerText = "Number of Votes: " + vote["numDownvotes"] +
            " Users who approve: " + vote["downvoters"]
    }
}