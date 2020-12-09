function loadChoicePage() {

    let queryString = new URLSearchParams(window.location.search)

    let choiceQueryString = queryString.get("choice")
    let userQueryString = queryString.get("user")

    let finalChoiceID = choiceQueryString.toString()
    let finalParticipantID = userQueryString.toString()

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

    if(code === 404) { // Not found
        let element = document.getElementById("loginStuff")
        element.parentElement.removeChild(element)
        document.getElementById("choice").innerText = "Error with code: " + code
        document.getElementById("alternatives").innerText = response["response"]
        document.getElementById("mainMessage").innerText = code
    } else {
        let isCompleted = response["choice"]["isCompleted"];
        let element = document.getElementById("loginStuff")
        element.parentElement.removeChild(element)
        // Callbacks:
        // Username -> ChoiceInfo -> AlternativeInfo -> VoteInfo -> FeedbackInfo
        requestUsername(finalParticipantID, finalChoiceID, requestChoiceInfo)
        setTimeout( function () {
            if (isCompleted) {
                let allInputsTags = document.querySelectorAll("input");
                let allATags = document.querySelectorAll("a");
                for (let i = 0; i < allInputsTags.length; i++) {
                    allInputsTags[i].onclick = function() { alert("The choice has been complete"); };
                }
                for (let i = 0; i < allInputsTags.length; i++) {
                    allATags[i].onclick = function() { alert("The choice has been complete"); };
                }
            }
        }, 5000);
    }
}

