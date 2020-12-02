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
        output = output + "<div id='buttons" + alternativeID + "'><a href='javaScript:handleAmendVoteClick(\"likeImage" + alternativeID + "\")'><img " +
            "id=\"likeImage" + alternativeID + "\" src=\"../img/like.png\"></a>\n" +
            "        <label id=\"likeDesc" + alternativeID + "\"></label><br>\n" +
            "        <a href='javaScript:handleAmendVoteClick(\"dislikeImage" + alternativeID + "\")'><img " +
            "id=\"dislikeImage" + alternativeID + "\" src=\"../img/dislike.png\"></a>\n" +
            "        <label id=\"dislikeDesc" + alternativeID + "\"></label><br></div>"
    }

    alternativeDiv.innerHTML = output

    requestVoteInfo(choiceID);
}