function handleAltCreateClick(e){
    let alt = document.getElementById("alternatives");
    let output = alt.innerHTML;
    let count = (output.match(/<label>/g) || []).length / 2;
    console.log(count);

    if (count < 4){
        let num = count + 1;
        let altField = "<label>Alternative Name: </label><input type=\"text\" name=\"altName" + num + "\" value=\"\"><br>\n" +
            "<label>Alternative Description: </label><input type=\"text\" name=\"altDesc" + num + "\" value=\"\"><br><br>\n";
        let altHTML = document.getElementById("alternative" + num);
        console.log(altHTML);
        altHTML.innerHTML = altField;
    } else if (count == 4) {
        let num = count + 1;
        let altField = "<label>Alternative Name: </label><input type=\"text\" name=\"altName" + num + "\" value=\"\"><br>\n" +
            "<label>Alternative Description: </label><input type=\"text\" name=\"altDesc" + num + "\" value=\"\"><br><br>\n";
        let altHTML = document.getElementById("alternative" + num);
        console.log(altHTML);
        altHTML.innerHTML = altField;
        alt.removeChild(document.getElementById("altCreate"));
    } else {

    }
}