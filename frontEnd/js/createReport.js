function createReport() {
    let xhr = new XMLHttpRequest();
    console.log("Sending a request to get all choices for report")
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

    let parsedResponse = JSON.parse(response);
    let reportDiv = document.getElementById("report")
    let i = ""
    let count = 0

    let output =
        "<table>" +
        "   <tr>" +
        "       <th>Choice ID</th>" +
        "       <th>Date Created</th>" +
        "       <th>Is Completed</th>" +
        "   </tr>"

    if (Math.floor(parsedResponse["httpCode"] / 100) !== 2) {
        alert(parsedResponse["response"]);
        return;
    }

    parsedResponse = parsedResponse["infos"]
    console.log("Successfully got a response to retrieve all the choices for the report")
    for (i in parsedResponse) {

        let choice = parsedResponse[i]

        count = count + 1

        let choiceID = choice["choiceID"]
        let choiceCompleted = choice["isCompleted"]
        let choiceDateCreated = choice["creationDate"]
        let date = new Date(choiceDateCreated)
        date = date.toLocaleString()

        output +=
            "<tr>" +
            "   <td>" + choiceID + "</td>" +
            "   <td>" + date + "</td>" +
            "   <td>" + choiceCompleted + "</td>" +
            "</tr>"
    }
    output += "</table>"
    reportDiv.innerHTML = output
}