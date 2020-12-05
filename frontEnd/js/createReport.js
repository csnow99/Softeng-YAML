function createReport() {
    let xhr = new XMLHttpRequest();
    xhr.open("GET", createReport_url, true);
    xhr.send();

    xhr.onloadend = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    updatePageWithReport(xhr.responseText);
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

    console.log("The response after retrieving the Choices Info for the report: " + response)
    let parsedResponse = JSON.parse(response);
    let reportDiv = document.getElementById("report")
    let i, output = ""
    let count = 0

    parsedResponse = parsedResponse["infos"]

    for (i in parsedResponse) {

        let choice = parsedResponse[i]

        count = count + 1

        let choiceID = choice["choiceID"]
        let choiceCompleted = choice["isCompleted"]
        let choiceDateCreated = choice["creationDate"]
        let date = new Date(choiceDateCreated)
        date = date.toLocaleString()

        output = output + "<p> " +
            "<b>ChoiceID: </b>" + choiceID +
            " | <b>Date Created: </b>" + date +
            " | <b>Completed: </b>" + choiceCompleted + "</p>"
    }

    reportDiv.innerHTML = output
}