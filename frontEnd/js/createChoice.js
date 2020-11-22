function updatePageWithChoice() {

    console.log("Test")

}

function loadChoicePage(response) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", choicePage_url + "/" + response, true);
    xhr.send();

    console.log("Redirected");

    updatePageWithChoice();
}

function processCreateChoiceResponse(result) {
    console.log("result:" + result);

    loadChoicePage(result)

}

function handleChoiceCreateClick(e) {
  var form = document.createChoice;

  var data = {};
/*
{
    "name":"testChoice4",
    "maxParticipants":10,
    "description":"sample description",
    "alternatives":[
        {"alternativeID":"alt1_ID","name":"alt5_name","description":"alt1_description"},
        {"alternativeID":"alt2_ID","name":"alt6_name","description":"alt2_description"},
        {"alternativeID":"alt3_ID","name":"alt7_name","description":"alt3_description"}
        ]}
*/


  data["name"] = form.choiceName.value;
  data["maxParticipants"] = form.partNum.value;
  data["description"] = form.choiceDesc.value;

  var alt1 = {};
  var alt2 = {};
  alt1["name"] = form.altName1.value;
  alt1["description"] = form.altDesc1.value;
  alt2["name"] = form.altName2.value;
  alt2["description"] = form.altDesc2.value;

  var alts = [alt1, alt2];
  data["alternatives"] = alts;

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