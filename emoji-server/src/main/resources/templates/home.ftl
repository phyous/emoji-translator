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


<div class="navbar navbar-default navbar-fixed-bottom">
  <div id="wrapper" class="container-fluid" style="text-align: center">    
    <div style="padding-top: 8px">
      <img class="sharebutton" src="https://simplesharebuttons.com/images/somacro/facebook.png" alt="Facebook" onclick="invokeShare('http://www.facebook.com/sharer.php?u={0}')"/>
      <img class="sharebutton" src="https://simplesharebuttons.com/images/somacro/linkedin.png" alt="LinkedIn" onclick="invokeShare('http://www.linkedin.com/shareArticle?mini=true&amp;url={0}')"/>
      <img class="sharebutton" src="https://simplesharebuttons.com/images/somacro/google.png" alt="Google" onclick="invokeShare('https://plus.google.com/share?url={0}')"/>
      <img class="sharebutton" src="https://simplesharebuttons.com/images/somacro/pinterest.png" alt="Pinterest" onclick="var e=document.createElement('script');e.setAttribute('type','text/javascript');e.setAttribute('charset','UTF-8');e.setAttribute('src','http://assets.pinterest.com/js/pinmarklet.js?r='+Math.random()*99999999);document.body.appendChild(e);"/>
      <img class="sharebutton" src="https://simplesharebuttons.com/images/somacro/reddit.png" alt="Reddit" onclick="invokeShare('http://reddit.com/submit?url={0}&amp;title=Check%20out%20my%20emoji%20translation%20with%20emojit!')"/>
      <img class="sharebutton" src="https://simplesharebuttons.com/images/somacro/twitter.png" alt="Twitter" onclick="invokeShare('https://twitter.com/share?url={0}&amp;text=Check%20out%20my%20emoji%20translation%20with%20emojit!&amp;hashtags=emojit');"/>
    </div>
  </div>
</div>


</@base.page>

