// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "";

var createChoice_url = base_url + "createChoice";    // POST
var choicePage_url = base_url + "choice" //GET with {choiceID} so we avoid CORS issues