<#macro page title>
<!DOCTYPE html>
<html ng-app="crudNgApp">
<head>
  <meta charset="utf-8">
  <meta content="IE=edge" http-equiv="X-UA-Compatible">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title>Emojit</title>

  <#--jquery-->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <#--bootstrap-->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
          integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
          crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
        integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
        integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <#--font awesome-->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" crossorigin="anonymous">

  <#--Linkedin profiles-->
  <script src="//platform.linkedin.com/in.js" type="text/javascript"></script>
  
  <link href="${publicAt('css/style.css')}" rel="stylesheet">
  <link href="${publicAt('css/bootstrap-social.css')}" rel="stylesheet">
  <link href="${publicAt('css/docs.css')}" rel="stylesheet">
  <script src="${publicAt('js/translator.js')}"></script>
  <base href="${contextPath}/">

  <link rel="apple-touch-icon" sizes="57x57" href="${publicAt('img/apple-icon-57x57.png')}" >
  <link rel="apple-touch-icon" sizes="60x60" href="${publicAt('img/apple-icon-60x60.png')}">
  <link rel="apple-touch-icon" sizes="72x72" href="${publicAt('img/apple-icon-72x72.png')}">
  <link rel="apple-touch-icon" sizes="76x76" href="${publicAt('img/apple-icon-76x76.png')}">
  <link rel="apple-touch-icon" sizes="114x114" href="${publicAt('img/apple-icon-114x114.png')}">
  <link rel="apple-touch-icon" sizes="120x120" href="${publicAt('img/apple-icon-120x120.png')}">
  <link rel="apple-touch-icon" sizes="144x144" href="${publicAt('img/apple-icon-144x144.png')}">
  <link rel="apple-touch-icon" sizes="152x152" href="${publicAt('img/apple-icon-152x152.png')}">
  <link rel="apple-touch-icon" sizes="180x180" href="${publicAt('img/apple-icon-180x180.png')}">
  <link rel="icon" type="image/png" sizes="192x192"  href="${publicAt('img/android-icon-192x192.png')}">
  <link rel="icon" type="image/png" sizes="32x32" href="${publicAt('img/favicon-32x32.png')}">
  <link rel="icon" type="image/png" sizes="96x96" href="${publicAt('img/favicon-96x96.png')}">
  <link rel="icon" type="image/png" sizes="16x16" href="${publicAt('img/favicon-16x16.png')}">
  <link rel="manifest" href="${publicAt('img/manifest.json')}">
  <meta name="msapplication-TileColor" content="#ffffff">
  <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
  <meta name="theme-color" content="#ffffff">

  <#--google analytics-->
  <script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
          (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-39530399-2', 'auto');
    ga('send', 'pageview');
  </script>
</head>
<body>

<#--facebook integration-->
<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '217478565270571',
      xfbml      : true,
      version    : 'v2.5'
    });
  };

  (function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));
</script>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
              data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Emojit!</a>
      <img style="margin-top: 8px" class="sharebutton" src="https://simplesharebuttons.com/images/somacro/twitter.png" alt="Twitter"
           onclick="invokeShare('https://twitter.com/share?url={0}&amp;text={1}&amp;hashtags=emojit');"/>
      <img style="margin-top: 8px" class="sharebutton" src="https://simplesharebuttons.com/images/somacro/reddit.png" alt="Reddit"
           onclick="invokeShare('http://reddit.com/submit?url={0}&amp;title=Check%20out%20my%20emoji%20translation%20with%20emojit!')"/>
      <img style="margin-top: 8px" class="sharebutton" src="https://simplesharebuttons.com/images/somacro/facebook.png" alt="Facebook"
           onclick="invokeShare('http://www.facebook.com/sharer.php?u={0}')"/>
    </div>

      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li><a href="#">Translate <span class="sr-only">(current)</span></a></li>
          <li><a href="/about">About</a></li>
        </ul>

      </div><!-- /.navbar-collapse -->
    
  </div><!-- /.container-fluid -->
</nav>

<#nested/>

</body>
</html>
</#macro>