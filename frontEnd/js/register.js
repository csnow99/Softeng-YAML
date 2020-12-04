function handleSignInClick(e){
    let form = document.registerForm;
    let data = {};
    let queryString = new URLSearchParams(window.location.search)
    queryString = queryString.get("choice")
    let finalChoiceID = queryString.toString()
    data["choiceID"] = finalChoiceID;
    data["name"] = form.partName.value;
    data["password"] = form.partPass.value;

    let js = JSON.stringify(data);
    console.log("Requesting to register/log in the user with JSON: " + js);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", register_url, true);
    xhr.send(js);
    xhr.onloadend = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
        	 if (xhr.status === 200) {
    	      processLogIn(xhr.responseText);
        	 } else {
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

    console.log("The response after logging in / signing up: " + result);
    let stateObj = {user: "0"}

    let queryString = new URLSearchParams(window.location.search)
    let newData = JSON.parse(result);
    let name = newData["response"].split(":")[1]
    let user = newData["participantID"]

    queryString.set("user", user)
    queryString.toString()

    document.getElementById("mainMessage").innerText = "Welcome, " + name

    window.history.replaceState(stateObj,"", "choice.html?" + queryString)

    loadChoicePage();

}