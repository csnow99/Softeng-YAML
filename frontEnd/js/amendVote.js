function handleAmendVoteClick(id){
    console.log("Clicked!");
    let image = document.getElementById(id);
    console.log(image);
    if(image.alt === "like"){
        image.src = "../img/liked.png"
        image.alt = "liked"
    } else if(image.alt === "liked"){
        image.src = "../img/like.png"
        image.alt = "like"
    } else if (image.alt === "dislike") {
        image.src = "../img/disliked.png"
        image.alt = "disliked"
    } else if (image.alt === "disliked") {
        image.src = "../img/dislike.png"
        image.alt = "dislike"
    }
}