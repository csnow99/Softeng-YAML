function handleAltCreateClick(e){
    let alt = document.getElementById("alternatives");
    alt.removeChild(document.getElementById("altCreate"));

    let output = document.getElementById("alternatives").innerHTML;
    let count = (output.match(/<label>/g) || []).length / 2;
    console.log(count);
    console.log(output);

    if (count < 4){
        let num = count + 1;
        output = output + "<label>Alternative Name: </label><input type=\"text\" name=\"altName" + num + "\" value=\"\"><br>\n" +
            "<label>Alternative Description: </label><input type=\"text\" name=\"altDesc" + num + "\" value=\"\"><br><br>\n" +
            "<input type=\"button\" id=\"altCreate\" value=\"Create another alternative\" " +
            "onclick=\"JavaScript:handleAltCreateClick(this)\">";
    } else if (count == 4) {
        let num = count + 1;
        output = output + "<label>Alternative Name: </label><input type=\"text\" name=\"altName" + num + "\" value=\"\"><br>\n" +
            "<label>Alternative Description: </label><input type=\"text\" name=\"altDesc" + num + "\" value=\"\"><br><br>\n";
    } else {

    }


    document.getElementById("alternatives").innerHTML = output;
}