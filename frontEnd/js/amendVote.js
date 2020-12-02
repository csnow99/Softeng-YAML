function handleAmendVoteClick(id){
    console.log("Clicked!");
    //let image = document.images[number];
    let image = document.getElementById(id);
    //image.src = "../img/liked.png";
    console.log(image);
    if(image.alt == "like"){
        image.src = "../img/liked.png"
        image.alt = "liked"
    }
    else if(image.alt == "liked"){
        image.src = "../img/like.png"
        image.alt = "like"
    }
    /*switch (image.src){
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
    }*/

}