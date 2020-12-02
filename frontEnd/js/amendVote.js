function handleAmendVoteClick(number){
    console.log("Clicked!");
    let image = document.images[number];
    console.log(image);
    switch (image.src){
        case "../img/like.png":
            image.src = "../img/liked.png";
            break;
        case "../img/liked.png":
            image.src = "../img/like.png";
            break;
        case "https://yamlcs3733bucket.s3.us-east-2.amazonaws.com/img/dislike.png":
            image.src = "../img/disliked.png";
            break;
        case "https://yamlcs3733bucket.s3.us-east-2.amazonaws.com/img/disliked.png":
            image.src = "../img/dislike.png";
            break;
    }

}