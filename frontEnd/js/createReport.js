function createReport() {
    let xhr = new XMLHttpRequest();
    xhr.open("GET", createReport_url, true);
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
                updatePageWithReport("N/A");
            }
        };

}

function updatePageWithReport(response) {

    let parsedResponse = JSON.parse(response);
    let reportDiv = document.getElementById("report")
    let i, output = ""
    let count = 0

    parsedResponse = parsedResponse["listOfChoiceInfos"]

    for (i in parsedResponse) {

        let choice = parsedResponse[i]

        count = count + 1

        let choiceID = choice["ID"]
        let choiceCompleted = choice["isCompleted"]
        let choiceDateCreated = choice["dateCreated"]

        output = output + "<h4> ChoiceID: " + choiceID + " | Date Created: " + choiceDateCreated + " Completed: " + choiceCompleted + "</h4>"
    }

    reportDiv.innerHTML = output
}