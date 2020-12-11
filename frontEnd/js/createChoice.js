function processCreateChoiceResponse(response) {
    console.log("The response after creating Choice:" + response);
    let parsedResponse = JSON.parse(response);

    if (Math.floor(parsedResponse["httpCode"] / 100) !== 2) {
        alert(parsedResponse["response"]);
        return;
    }

    let choiceID = parsedResponse["response"];
    window.location.href = "https://yamlcs3733bucket.s3.us-east-2.amazonaws.com/html/choice.html";
    let choiceURL = new URL("https://yamlcs3733bucket.s3.us-east-2.amazonaws.com/html/choice.html?");
    let choiceQueryString = new URLSearchParams(choiceURL.search);
    let urlParams = new URLSearchParams(choiceQueryString);

    urlParams.append("choice", choiceID);
    urlParams.append("user","0");

    window.location.href = choiceURL + urlParams;

}

function handleChoiceCreateClick(e) {
    let form = document.createChoice;
    let data = {};
    data["name"] = form.choiceName.value;
    data["maxParticipants"] = form.partNum.value;
    data["description"] = form.choiceDesc.value;

    let alternatives = document.getElementById("alternatives").innerHTML;
    let count = (alternatives.match(/<label>/g) || []).length / 2;

    let alts = [];

    let alt1 = {};
    let alt2 = {};
    let alt3 = {};
    let alt4 = {};
    let alt5 = {};

    switch(count){
        case 2:
            alt1["name"] = form.altName1.value;
            alt1["description"] = form.altDesc1.value;
            alt2["name"] = form.altName2.value;
            alt2["description"] = form.altDesc2.value;
            alts = [alt1, alt2];
            break;
        case 3:
            alt1["name"] = form.altName1.value;
            alt1["description"] = form.altDesc1.value;
            alt2["name"] = form.altName2.value;
            alt2["description"] = form.altDesc2.value;
            alt3["name"] = form.altName3.value;
            alt3["description"] = form.altDesc3.value;
            alts = [alt1, alt2, alt3];
            break;
        case 4:
            alt1["name"] = form.altName1.value;
            alt1["description"] = form.altDesc1.value;
            alt2["name"] = form.altName2.value;
            alt2["description"] = form.altDesc2.value;
            alt3["name"] = form.altName3.value;
            alt3["description"] = form.altDesc3.value;
            alt4["name"] = form.altName4.value;
            alt4["description"] = form.altDesc4.value;
            alts = [alt1, alt2, alt3, alt4];
            break;
        case 5:
            alt1["name"] = form.altName1.value;
            alt1["description"] = form.altDesc1.value;
            alt2["name"] = form.altName2.value;
            alt2["description"] = form.altDesc2.value;
            alt3["name"] = form.altName3.value;
            alt3["description"] = form.altDesc3.value;
            alt4["name"] = form.altName4.value;
            alt4["description"] = form.altDesc4.value;
            alt5["name"] = form.altName5.value;
            alt5["description"] = form.altDesc5.value;
            alts = [alt1, alt2, alt3, alt4, alt5];
            break;
    }
    data["alternatives"] = alts;
    if (
        form.altName1.value === "" || form.altDesc1.value === "" || form.altName2.value === "" || form.altDesc2.value === ""
    ) {
        alert("Must have at least two Alternatives to create a Choice");
        return;
    }

    if(form.choiceName.value || form.partNum.value || form.choiceDesc.value) {
        alert("Please Fill out all choice information")
    }

    let js = JSON.stringify(data);
    console.log("Creating a choice with JSON: " + js);
    let xhr = new XMLHttpRequest();
    xhr.open("POST", createChoice_url, true);
    xhr.send(js);
    xhr.onloadend = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
             if (xhr.status === 200) {
                  processCreateChoiceResponse(xhr.responseText);
             } else {
                let js = JSON.parse(xhr.responseText);
                let err = js["response"];
                alert (err);
             }
        } else {
            processCreateChoiceResponse("N/A");
        }
    };
}