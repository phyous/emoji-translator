<!DOCTYPE html>
<html ng-app="crudNgApp">
    <head>
        <meta charset="utf-8">
        <meta content="IE=edge" http-equiv="X-UA-Compatible">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Emojit</title>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

        <link href="${publicAt('css/style.css')}" rel="stylesheet">
        <script src="${publicAt('js/translator.js')}"></script>
        <base href="${contextPath}/">
  </head>
  <body>
    <div class="container">

      <form id="translateForm" class="form-signin">
        <h2 class="form-group-heading">Emojit</h2>
        <div class="form-group">
        	<label for="inputText" class="sr-only">Text to translate</label>
        	<input type="text" id="inputText" class="form-control" placeholder="Text goes here" autofocus required>
        	<label id="translationResult" class="sr-only">translated text</label>
        </div>
        <div class="form-group">
            <button id="submitButton" class="btn btn-lg btn-primary btn-block" type="submit">Emoj-it!</button>
        </div>
        <div class="form-group">
            <h2 id="translatedText"></h4>
        </div>
      </form>
    </div>
    </body>
</html>
