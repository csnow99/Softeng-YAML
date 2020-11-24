function updatePageWithChoice() {

    let queryString = new URLSearchParams(window.location.search)

    console.log(queryString.toString())

    var choiceDiv = document.getElementById('choice')

    choiceDiv.innerHTML = queryString.toString()

    //Get request to get all the information for a choice

    //Update the page with choice information

}

function loadChoicePage(response) {

    var parsedResponse = JSON.parse(response);

    choiceID = parsedResponse["response"]

    console.log(choiceID);

    // window.location.href = "https://yamlcs3733bucket.s3.us-east-2.amazonaws.com/html/choice.html";

    var choiceURL = new URL("https://yamlcs3733bucket.s3.us-east-2.amazonaws.com/html/choice.html?")
    var choiceQueryString = new URLSearchParams(choiceURL.search)
    var urlParams = new URLSearchParams(choiceQueryString)

    urlParams.append("choice", choiceID)

    // window.location.href = choiceURL + urlParams;

    /*
    var xhr = new XMLHttpRequest();
    xhr.open("GET", choicePage_url + "/" + choiceID, true);
    xhr.send();
    */

    console.log("Redirected");
}

function processCreateChoiceResponse(result) {
    console.log("result:" + result);

    loadChoicePage(result)

}

function handleChoiceCreateClick(e) {
    var form = document.createChoice;
    /*
    {
    "name":"testChoice4",
    "maxParticipants":10,
    "description":"sample description",
    "alternatives":[
        {"name":"alt5_name","description":"alt1_description"},
        {"name":"alt6_name","description":"alt2_description"},
        {"name":"alt7_name","description":"alt3_description"}
        ]}
    */
    var data = {};
    data["name"] = form.choiceName.value;
    data["maxParticipants"] = form.partNum.value;
    data["description"] = form.choiceDesc.value;

    let alternatives = document.getElementById("alternatives").innerHTML;
    let count = (alternatives.match(/<label>/g) || []).length / 2;
    console.log(count);

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
/*
    var alt1 = {};
    var alt2 = {};
    alt1["name"] = form.altName1.value;
    alt1["description"] = form.altDesc1.value;
    alt2["name"] = form.altName2.value;
    alt2["description"] = form.altDesc2.value;

    var alts = [alt1, alt2];
    data["alternatives"] = alts;
*/
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", createChoice_url, true);

    // send the collected data as JSON
    xhr.send(js);

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        if (xhr.readyState == XMLHttpRequest.DONE) {
             if (xhr.status == 200) {
                  console.log ("XHR:" + xhr.responseText);
                  processCreateChoiceResponse(xhr.responseText);
             } else {
                console.log("actual:" + xhr.responseText)
                var js = JSON.parse(xhr.responseText);
                var err = js["response"];
                alert (err);
             }
        } else {
            processCreateChoiceResponse("N/A");
        }
    };
}