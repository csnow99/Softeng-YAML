function processCreateChoiceResponse(result) {
    console.log("result:" + result);

    loadChoicePage(result)

}

function handleChoiceCreateClick(e) {
  var form = document.createChoice;

  var data = {};

  data["Choice Name"] = form.choiceName.value;
  data["Max Participants"] = form.partNum.value;
  data["Choice Description"] = form.choiceDesc.value;
  data["Alternative Name #1"] = form.altName1.value;
  data["Alternative Description #1"] = form.altDesc2.value;
  data["Alternative Name #2"] = form.altName2.value;
  data["Alternative Description #2"] = form.altDesc2.value;

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