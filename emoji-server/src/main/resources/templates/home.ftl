<#import "base.ftl" as base/>
<@base.page title="Emojit">
<div class="container">

  <form id="translateForm" class="form-signin">
    <h2 style="margin-top: 0px;" class="form-group-heading">Translate to Emoji</h2>
    <div class="form-group">
      <label for="inputText" class="sr-only">Text to translate</label>
      <input type="text" id="inputText" class="form-control" placeholder="Enter text here" autofocus required>
      <label id="translationResult" class="sr-only">translated text</label>
    </div>
    <div class="form-group" style="position:relative">
      <div class="center-block" style="max-width:200px">
        <button id="submitButton" class="btn btn-lg btn-primary btn-block" type="submit">Emoj-it!</button>
      </div>
      <button id="copyButton" class="btn btn-secondary" type="button" data-clipboard-target="#translatedText" style="position:absolute;right:0px;display:none;" >
        <span class="glyphicon glyphicon-copy"></span>
      </button>
    </div>
    <div class="form-group" style="position:relative;">
      <button id="copyButton" class="btn btn-secondary" type="button" data-clipboard-target="#translatedText" style="position:absolute;right:0px; top:0px;display:none;" >Copy</button>
      <h2 id="translatedText"></h2>
    </div>
  </form>
</div>

</@base.page>

