function handleDeleteOldChoicesClick() {
    let numDays = document.getElementById("numDays").value;
    console.log(numDays);

    let data = {};
    data["howOld"] = numDays;
    let js = JSON.stringify(data);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", deleteOldChoices, true);
    console.log("Sending a request to delete Choices with JS:" + js);
    xhr.send(js);

    xhr.onloadend = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                createReport();
            } else {
                let js = JSON.parse(xhr.responseText);
                let err = js["response"];
                alert (err);
            }
        } else {
            alert("Something went wrong, couldn't delete!");
        }
    };
}