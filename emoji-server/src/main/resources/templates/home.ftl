<#import "base.ftl" as base/>
<@base.page title="Emojit">
<div class="container">

  <form id="translateForm" class="form-signin">
    <h2 class="form-group-heading">Translate English to Emoji</h2>
    <div class="form-group">
      <label for="inputText" class="sr-only">Text to translate</label>
      <input type="text" id="inputText" class="form-control" placeholder="Enter text here" autofocus required>
      <label id="translationResult" class="sr-only">translated text</label>
    </div>
    <div class="form-group">
      <div class="center-block" style="max-width:200px">
        <button id="submitButton" class="btn btn-lg btn-primary btn-block" type="submit">Emoj-it!</button>
      </div>
    </div>
    <div class="form-group">
      <h2 id="translatedText"></h2>
    </div>
  </form>
</div>
</@base.page>