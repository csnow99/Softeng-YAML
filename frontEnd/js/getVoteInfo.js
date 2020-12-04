function requestVoteInfo(choiceID){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", getVote_url + "/" + choiceID, true);
    xhr.send();

    xhr.onloadend = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                updatePageWithVotes(xhr.responseText);
            } else {
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
    console.log("The response after retrieving Votes Info: "+ response);
    let parsedResponse = JSON.parse(response);
    parsedResponse = parsedResponse["votes"];

    let count = 0;
    for (let i in parsedResponse){
        count = count + 1
        let vote = parsedResponse[i]

        // check if voted
        let partName = document.getElementById("mainMessage").innerText.split(" ")[1];
        if (vote["upvoters"].includes(partName)) {
            setImage("like:" + vote["alternativeID"]);
        }
        if (vote["downvoters"].includes(partName)) {
            setImage("dislike:" + vote["alternativeID"]);
        }

        let altLikes = document.getElementById("likeDesc" + count)
        altLikes.innerText = "Number of Votes: " + vote["numUpvotes"] +
            " Users who approve: " + vote["upvoters"]
        let altDislikes = document.getElementById("dislikeDesc" + count)
        altDislikes.innerText = "Number of Votes: " + vote["numDownvotes"] +
            " Users who disapprove: " + vote["downvoters"]
    }
}