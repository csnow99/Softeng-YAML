function handleAmendVoteClick(imgID){
    console.log("Clicked!");
    let image = document.getElementById(imgID).src
    switch (image){
        case "../img/like.png":
            image = "../img/liked.png";
            break;
        case "../img/liked.png":
            image = "../img/like.png";
            break;
        case "../img/dislike.png":
            image = "../img/disliked.png";
            break;
        case "../img/disliked.png":
            image = "../img/dislike.png";
            break;
    }

}