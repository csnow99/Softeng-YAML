function setImageD(id) {
    let image = document.getElementById(id);
    let isDislike = id.includes("dislike");
    if (isDislike) {
        image.src = "../img/disliked.png";
        image.alt = "disliked";
    } else {
        image.src = "../img/liked.png";
        image.alt = "liked";
    }
}
function setImage(id) {
    let image = document.getElementById(id);
    let isDislike = id.includes("dislike");
    if (isDislike) {
        image.src = "../img/dislike.png";
        image.alt = "dislike";
    } else {
        image.src = "../img/like.png";
        image.alt = "like";
    }
}
function handleAmendVoteClick(id) {
    let voteType = null;
    let voteKind = id.split(":")[0];
    let altID = id.split(":")[1];
    if (voteKind.includes("dislike")) {
        voteType = 0;
    } else if (voteKind.includes("like")) {
        voteType = 1;
    }
    if (voteType !== null) {
        sendVote(voteType, altID);
    }
}

function sendVote(voteType, altID) {
    let queryString = new URLSearchParams(window.location.search);
    let choiceQueryString = queryString.get("choice")
    let userQueryString = queryString.get("user")
    let finalChoiceID = choiceQueryString.toString()
    let finalParticipantID = userQueryString.toString()

    let data = {};
    data["participantID"] = finalParticipantID;
    data["alternativeID"] = altID;
    data["amendType"] = voteType;

    let js = JSON.stringify(data);
    console.log("Sending vote with JSON:" + js);
    let xhr = new XMLHttpRequest();
    xhr.open("POST",amendVote_url + "/" + finalChoiceID + "/" + finalParticipantID, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(js);

    xhr.onloadend = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                processVote(xhr.responseText);
            } else {
                let js = JSON.parse(xhr.responseText);
                let err = js["response"];
                alert (err);
            }
        } else {
            processVote("N/A");
        }
    };
}

function processVote(response) {
    console.log("The response after sending Vote: " + response);
    updatePageWithVotes(response);
}