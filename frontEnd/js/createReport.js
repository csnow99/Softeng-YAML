function createReport() {

    data = {};

    let js = JSON.stringify(data);
    console.log("JS: CreateReport");
    let xhr = new XMLHttpRequest();
    xhr.open("GET", createReport_url, true);
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
                updatePageWithReport("N/A");
            }
        };
    
}

function updatePageWithReport(response) {

    let parsedResponse = JSON.parse(response);
    let reportDiv = document.getElementById("report")
    var i, output = ""
    var count = 0

    parsedResponse = parsedResponse["listOfChoiceInfos"]

    for (i in parsedResponse) {

        var choice = parsedResponse[i]

        count = count + 1

        let choiceID = choice["ID"]
        let choiceCompleted = choice["isCompleted"]
        let choiceDateCreated = choice["dateCreated"]
        let choiceDateCompleted = choice["dateCompleted"]

        output = output + "<h4> ChoiceID: " + choiceID + " | Date Created: " + choiceDateCreated + " Completed: " + choiceCompleted + "</h4>"
    }

    reportDiv.innerHTML = output
}