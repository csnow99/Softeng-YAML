function voteCheck(){
    let queryString = new URLSearchParams(window.location.search);
    let userQueryString = queryString.get("user");
    let finalParticipantID = userQueryString.toString();

    if (finalParticipantID !== "0"){
        let count = document.getElementsByTagName('a').length / 2;
        let userName = document.getElementById("mainMessage").innerText.split(" ")[1];
        for (let i = 1; i < count + 1; i++) {
            let like = document.getElementById("likeDesc" + count).innerText;
            like = like.split("Users who approve:")[1];
            let dislike = document.getElementById("dislikeDesc" + count).innerText;
            dislike = dislike.split("Users who disapprove:")[1];
            if (like.includes(userName)) {
                return;
            } else if (dislike.includes(userName)) {
                return;
            }
        }
    }
}