function requestAlternativeInfo(choiceID, callback) {

     let xhr = new XMLHttpRequest();
     xhr.open("GET", getAlternative_url + "/" + choiceID, true);
     xhr.send();

     xhr.onloadend = function () {
         if (xhr.readyState === XMLHttpRequest.DONE) {
             if (xhr.status === 200) {
              updatePageWithAlternative(xhr.responseText);
             } else {
                  let js = JSON.parse(xhr.responseText);
                  let err = js["response"];
                  alert (err);
             }
         } else {
           updatePageWithAlternative("N/A");
         }
     };
     if (callback !== null) {
         setTimeout( function(){
             callback(choiceID, requestFeedbackInfo)
         }, 500 );
     }
}

function updatePageWithAlternative(response) {
    console.log("The response after retrieving Alternatives Info: " + response);

    let parsedResponse = JSON.parse(response);
    let alternativeDiv = document.getElementById("alternatives")
    let output = ""
    let count = 0

    let queryString = new URLSearchParams(window.location.search)
    let userQueryString = queryString.get("user")
    let finalParticipantID = userQueryString.toString()

    parsedResponse = parsedResponse["alternatives"]
    let choiceID

    for(let i in parsedResponse) {

        let alternative = parsedResponse[i]

        count = count + 1

        let alternativeID = alternative["alternativeID"]
        let alternativeName = alternative["title"]
        let alternativeDescription = alternative["description"]
        choiceID = alternative["choiceID"]

        output = output + "<label><b> Alternative #"+ count + ": " + alternativeName + "</b></label><br>"
        output = output + "<label> <b> Description: </b>" + alternativeDescription + "</label><br>"
        if (finalParticipantID !== "0"){
            output = output + "<div id=\"buttons"+ count +"\">\n" +
                "        <a onclick='handleAmendVoteClick(\"like:" + alternativeID + "\")'>\n" +
                "            <img src=\"../img/like.png\" id=\"like:" + alternativeID + "\" alt=\"like\">\n" +
                "        </a><label id=\"likeDesc" + count + "\"> LOADING ... </label><br>\n" +
                "        <a onclick='handleAmendVoteClick(\"dislike:" + alternativeID + "\")'>\n" +
                "            <img src=\"../img/dislike.png\" id=\"dislike:" + alternativeID + "\" alt=\"dislike\">\n" +
                "        </a><label id=\"dislikeDesc" + count + "\"> LOADING ... </label><br>\n" +
                "   </div>" +
                "       <div id='feedback" + alternativeID + "'></div>" +
                "       <input id='addFeedback" + alternativeID + "' type=\"button\" value=\"Add Feedback\" " +
                "           onclick='JavaScript:addFeedback(\"feedback" + alternativeID + "\")'>" +
                "<hr style=\"width:50%;text-align:left;margin-left:0\">"
        }
    }

    alternativeDiv.innerHTML = output;

}