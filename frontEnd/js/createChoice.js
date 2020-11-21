function loadChoicePage(response) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", choicePage_url + "/" + name, true);
    xhr.send();

    console.log("sent");

    //Not complete
}

function processCreateChoiceResponse(result) {
    console.log("result:" + result);

    loadChoicePage(result)

}

function handleChoiceCreateClick(e) {
  var form = document.createForm;

  var data = {};

  data["Choice Name"] = form.choiceInfo.choiceName.value;
  data["Max Participants"] = form.choiceInfo.partNum.value;
  data["Choice Description"] = form.choiceInfo.choiceDesc.value;
  data["Alternative Name #1"] = form.choiceInfo.altName1.value;
  data["Alternative Description #1"] = form.choiceInfo.altDesc2.value;
  data["Alternative Name #2"] = form.choiceInfo.altName2.value;
  data["Alternative Description #2"] = form.choiceInfo.altDesc2.value;

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