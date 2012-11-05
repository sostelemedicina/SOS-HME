<!DOCTYPE html>
<html>
  <head>
    <title><g:message code="auth.login.welcome"/></title>
    <style>
      .logo {
        padding: 5px;
        padding-bottom: 0px;
      }
      .logo img {
        border: 0px;
      }
      #cuerpo{
        padding: 10px;
        text-align: center;
      }
    </style>
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <g:layoutHead />
  <g:javascript library="application" />				
</head>
<body>
  <div id="spinner" class="spinner" style="display:none;">
    <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
  </div>	
  <div class="logo">
    <a href="#" target="_blank"><img src="${resource(dir:'images', file:'logoSos.jpg')}" alt="Open EHR-Gen Framework" /></a>
  </div>
  <div class="nav">
  <g:menuSession session="${session}" />
  </div>
<g:layoutBody />		
</body>	
</html>