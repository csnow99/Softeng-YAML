function handleAltCreateClick(e){
    var output = document.getElementById("alternatives").innerHTML;
    var count = (output.match(/<label>/g) || []).length;
    console.log(count);
    if (count < 8){
        let num = count / 2 + 1;
        output = output + "<label>Alternative Name: </label><input type=\"text\" name=\"altName" + num + "\" value=\"\"><br>\n" +
            "<label>Alternative Description: </label><input type=\"text\" name=\"altDesc" + num + "\" value=\"\"><br><br>\n" +
            "<input type=\"button\" value=\"Create another alternative\" onclick=\"JavaScript:handleAltCreateClick\">";
    } else if (count == 8) {
        let num = count / 2 + 1;
        output = output + "<label>Alternative Name: </label><input type=\"text\" name=\"altName" + num + "\" value=\"\"><br>\n" +
            "<label>Alternative Description: </label><input type=\"text\" name=\"altDesc" + num + "\" value=\"\"><br><br>\n";
    } else {
        output = ""
    }
    document.getElementById("alternatives").innerHTML = output;
}