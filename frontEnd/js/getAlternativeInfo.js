function requestAlternativeInfo(choiceID) {

    data = {};

    data["choiceID"] = choiceID;

     let js = JSON.stringify(data);
     console.log("JS: " + js);
     let xhr = new XMLHttpRequest();
     xhr.open("GET", getAlternative_url + "/" + choiceID, true);
     xhr.send(js);

     xhr.onloadend = function () {
         console.log(xhr);
         console.log(xhr.request);
         if (xhr.readyState == XMLHttpRequest.DONE) {
             if (xhr.status == 200) {
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
    var i, output = ""
    var count = 0
    //let output = document.getElementById("alternatives").innerHTML;

    parsedResponse = parsedResponse["alternatives"]

    for(i in parsedResponse) {

        var alternative = parsedResponse[i]

        count = count + 1

        let alternativeName = alternative["title"]
        let alternativeDescription = alternative["description"]

        output = output + "<h4> Alternative #"+ count + ": " + alternativeName + "</h4>"
        output = output + "<p> <b> Description </b>" + alternativeDescription + "<p>"
    }

    alternativeDiv.innerHTML = output
}