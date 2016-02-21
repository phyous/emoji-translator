<!DOCTYPE html>
<html ng-app="crudNgApp">
    <head>
        <meta charset="utf-8">
        <meta content="IE=edge" http-equiv="X-UA-Compatible">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Emoji Translator</title>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

        <link href="${publicAt('css/style.css')}" rel="stylesheet">
        <script src="${publicAt('js/translator.js')}"></script>
        <base href="${contextPath}/">
  </head>
  <body>
    <div class="container">

      <form id="translateForm" class="form-signin">
        <h2 class="form-signin-heading">Emojihose</h2>
        <div>
        	<label for="inputText" class="sr-only">Text to translate</label>
        	<input type="text" id="inputText" class="form-control" placeholder="Text goes here" autofocus required>
        	<label id="translationResult" class="sr-only">translated text</label>
        </div>
        <button id="submitButton" class="btn btn-lg btn-primary btn-block" type="submit">Emojify</button>
      </form>

      <div>
        <p id="translatedText"/></p>
      <div>

    </div>
    </body>
</html>
