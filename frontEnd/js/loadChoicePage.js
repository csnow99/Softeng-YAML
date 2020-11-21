function loadChoicePage(response) {

    window.location.href = "choice.html"
    var xhr = new XMLHttpRequest();
    xhr.open("GET", choicePage_url + "/" + name, true);
    xhr.send();

    console.log("sent");

    //Not complete

    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
          console.log ("XHR:" + xhr.responseText);
          processPageResponse(xhr.responseText);
        } else {
          processPageResponse("N/A");
        }
    };
}


/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processPageResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var choice = document.getElementById('choice');

  var output = "";
  console.log(js);

  var choiceName = js["Choice Name"];
  var choicePartNum = js["Max Participants"];
  var choiceDesc = js["Choice Description"]

  output = output + "<div id=\"choice"+ ChoiceName + "\"><h1>" + ChoiceName + "</h1><p>" + ChoiceDesc + "</p></div>"



  // Update computation result
  choice.innerHTML = output;
}