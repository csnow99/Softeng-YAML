function handleSignInClick(e){
    /*
    Working JSON
    {
      "choiceID": "35912134573272282210196478825629454780",
      "name": "Bill",
      "password": "Test1"
    }
    */
    var form = document.registerForm;
    data = {};
    var url = window.location.href;
    var choiceID = url.split("=")[1];
    data["choiceID"] = choiceID;
    data["name"] = form.partName.value;
    data["password"] = form.partPass.value;

    var js = JSON.stringify(data);
    console.log("JS: " + js);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", register_url, true);
    xhr.send(js);
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        if (xhr.readyState == XMLHttpRequest.DONE) {
        	 if (xhr.status == 200) {
    	      console.log ("XHR:" + xhr.responseText);
    	      processLogIn(xhr.responseText);
        	 } else {
        		 console.log("actual:" + xhr.responseText)
    			  var js = JSON.parse(xhr.responseText);
    			  var err = js["response"];
    			  alert (err);
        	 }
        } else {
          processLogIn("N/A");
        }
    };
}
function processLogIn(result){
    console.log(result);
}