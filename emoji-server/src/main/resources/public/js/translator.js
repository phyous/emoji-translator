function invokeShare(urlFormat)
{
    var shareUrl = urlFormat.replace("{0}", encodeURI(window.location)).replace("{1}",  $("#translatedText").text());
    window.open(shareUrl, "_blank");
}

function translateText(textToTranslate) {
    history.pushState(null, null, window.location.origin + "?text=" + encodeURI(textToTranslate));
    $.get("/api/translate?text=" + textToTranslate, function(data, textStatus)
    {
    	$("#translatedText").text(data);
    	$("#copyButton").show();
    });	
}

function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable) {
        	return pair[1];
        }
    }
    
    return null;
}

function translateFromQuery() {
	var textValue = getQueryVariable("text");
	if (textValue) {
		var decodedText = decodeURI(textValue);
	    $("#inputText").val(decodedText);
	    translateText(decodedText);
	} else {
		$("#translatedText").text("");
		$("#inputText").val("");
	}
}

$( document ).ready(function() {
	clipboard = new Clipboard('#copyButton');
    $(window).bind('popstate', translateFromQuery);
    translateFromQuery();

	$('#submitButton').click(function(source) {
	  $('#submitForm').submit();
	});

	$('#translateForm').submit(function (event) {
		translateText($('#inputText').val());
	    return false;
	});
});