function loadChoicePage() {

    let queryString = new URLSearchParams(window.location.search)

    let choiceQueryString = queryString.get("choice")
    let userQueryString = queryString.get("user")

    let finalChoiceID = choiceQueryString.toString()
    let finalParticipantID = userQueryString.toString()
    console.log(finalParticipantID)

    if(finalParticipantID === "0") {
        requestChoiceInfo(finalChoiceID, finalParticipantID, null)
        requestAlternativeInfo(finalChoiceID, null)
    } else {
        requestUsername(finalParticipantID, finalChoiceID, null)
    }
}

function checkQuality(response, finalParticipantID, finalChoiceID) {
    response = JSON.parse(response)
    let code = response["httpCode"]
    console.log(response)
    console.log(code)

    if(code === 404) { // Not found
        let element = document.getElementById("loginStuff")
        element.parentElement.removeChild(element)
        document.getElementById("choice").innerText = "Error with code: " + code
        document.getElementById("alternatives").innerText = response["response"]
        document.getElementById("mainMessage").innerText = code
    } else {
        let element = document.getElementById("loginStuff")
        element.parentElement.removeChild(element)
        requestUsername(finalParticipantID, finalChoiceID, requestChoiceInfo)
    }
}
