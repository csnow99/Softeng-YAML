function handleChoiceCreateClick(e) {
  var form = document.createForm;

  var data = {};

  data["Choice Name"] = form.choiceName.value;
  data["Max Participants"] = form.partNum.value;
  data["Choice Description"] = form.choiceDesc.value;
  data["Alternative Name #1"] = form.choiceName.value;

  if (form.system.checked) {  // be sure to flag system constant requests...
     data["system"] = true;
  }

  data["value"] = form.constantValue.value;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", create_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate.
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    if (xhr.readyState == XMLHttpRequest.DONE) {
    	 if (xhr.status == 200) {
	      console.log ("XHR:" + xhr.responseText);
	      processCreateResponse(xhr.responseText);
    	 } else {
    		 console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["response"];
			  alert (err);
    	 }
    } else {
      processCreateResponse("N/A");
    }
  };
}