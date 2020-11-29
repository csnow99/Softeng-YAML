function handleSignInClick(e){
    let form = document.registerForm;
    data = {};
    let url = window.location.href;
    let choiceID = url.split("=")[1];
    data["choiceID"] = choiceID;
    data["name"] = form.partName.value;
    data["password"] = form.partPass.value;

    let js = JSON.stringify(data);
    console.log("JS: " + js);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", register_url, true);
    xhr.send(js);
    xhr.onloadend = function () {
        console.log(xhr);
        if (xhr.readyState == XMLHttpRequest.DONE) {
        	 if (xhr.status == 200) {
    	      console.log ("XHR:" + xhr.responseText);
    	      processLogIn(xhr.responseText);
        	 } else {
        		 console.log("actual:" + xhr.responseText)
    			  let js = JSON.parse(xhr.responseText);
    			  let err = js["response"];
    			  alert (err);
        	 }
        } else {
          processLogIn("N/A");
        }
    };
}
function processLogIn(result){
    console.log(result);
    let newData = JSON.parse(result);
    let name = newData["response"].split(":")[1];
    console.log(name);
    document.getElementById("mainMessage").innerText = "Welcome," + name;
}