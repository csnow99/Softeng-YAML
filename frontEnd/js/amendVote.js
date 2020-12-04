function handleAmendVoteClick(id){
    console.log("Clicked!");
    let image = document.getElementById(id);
    let voteType = null;
    let altID = id.split(":")[1];
    if(image.alt === "like"){
        image.src = "../img/liked.png";
        image.alt = "liked";
        voteType = 1;
    } else if(image.alt === "liked"){
        image.src = "../img/like.png";
        image.alt = "like";
        voteType = 1;
    } else if (image.alt === "dislike") {
        image.src = "../img/disliked.png";
        image.alt = "disliked";
        voteType = 0;
    } else if (image.alt === "disliked") {
        image.src = "../img/dislike.png";
        image.alt = "dislike";
        voteType = 0;
    }
    if (voteType !== null){
        sendVote(voteType, altID);
    }
}

function sendVote(voteType, altID) {
    let queryString = new URLSearchParams(window.location.search);
    let userQueryString = queryString.get("user");
    let finalParticipantID = userQueryString.toString();
    console.log(finalParticipantID);

    let data = {};
    data["alternativeID"] = altID;
    data["amendType"] = voteType;

    let js = JSON.stringify(data);

    let xhr = new XMLHttpRequest();
    xhr.open("POST",amendVote_url + "//" + finalParticipantID);
    xhr.send(js)

    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log ("XHR:" + xhr.responseText);
                processVote(xhr.responseText);
            } else {
                console.log("actual:" + xhr.responseText)
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
    console.log(response);
    updatePageWithVotes(response);
}