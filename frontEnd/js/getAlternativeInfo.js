function requestAlternativeInfo(choiceID) {

     let xhr = new XMLHttpRequest();
     xhr.open("GET", getAlternative_url + "/" + choiceID, true);
     xhr.send();

     xhr.onloadend = function () {
         console.log(xhr);
         console.log(xhr.request);
         if (xhr.readyState === XMLHttpRequest.DONE) {
             if (xhr.status === 200) {
              console.log ("XHR:" + xhr.responseText);
              updatePageWithAlternative(xhr.responseText);
             } else {
                 console.log("actual:" + xhr.responseText)
                  let js = JSON.parse(xhr.responseText);
                  let err = js["response"];
                  alert (err);
             }
         } else {
           updatePageWithAlternative("N/A");
         }
     };
}

function updatePageWithAlternative(response) {

    let parsedResponse = JSON.parse(response);
    let alternativeDiv = document.getElementById("alternatives")
    let output = ""
    let count = 0

    parsedResponse = parsedResponse["alternatives"]
    let choiceID

    for(let i in parsedResponse) {

        let alternative = parsedResponse[i]

        count = count + 1

        let alternativeID = alternative["alternativeID"]
        let alternativeName = alternative["title"]
        let alternativeDescription = alternative["description"]
        choiceID = alternative["choiceID"]

        output = output + "<label><b> Alternative #"+ count + ": " + alternativeName + "</b></label>"
        output = output + "<label> <b> Description: </b>" + alternativeDescription + "</label><br>"
        output = output + "<input id=\"likeAltID#"+ count +"\" type=\"image\" src=\"../img/like.png\" \n" +
            "           width=\"30\" height=\"30\" \n" +
            "           onclick=\"JavaScript:handleAmendVoteClick(\"likeAltID#"+ count +"\")\">\n" +
            "    <label id=\"numLikeAltID#"+ alternativeID +"\"></label><br>\n" +
            "    <input id=\"dislikeAltID#"+ count +"\" type=\"image\" src=\"../img/dislike.png\" \n" +
            "           width=\"30\" height=\"30\" \n" +
            "           onclick=\"JavaScript:handleAmendVoteClick(\"dislikeAltID#"+ count +"\")\">\n" +
            "    <label id=\"numDislikeAltID#"+ alternativeID +"\"></label><br>"
    }

    alternativeDiv.innerHTML = output

    requestVoteInfo(choiceID);
}