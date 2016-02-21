// A $( document ).ready() block.
$( document ).ready(function() {
	$('#submitButton').click(function(source) {
	  $('#submitForm').submit();
	});

	$('#translateForm').submit(function (event) {
	    var inputText = $("#inputText").val();
	    $.get("/api/translate?text=" + inputText, function(data, textStatus)
	    {
			$("#translatedText").text(data);
	    });

	    return false;
	});
});