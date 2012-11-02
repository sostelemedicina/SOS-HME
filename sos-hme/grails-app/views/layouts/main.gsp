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
<%--     
<span class="menuButton"><g:link class="list" controller="loginAuth" action="list"><g:message code="default.loginAuth.label" args="[entityName]" /></g:link></span>
<span class="menuButton"><g:link class="list" controller="person" action="list"><g:message code="default.person.label" args="[entityName]" /></g:link></span>
--%>	
    <span class="menuButton menuButtonDerecha"><g:link class="list" controller="authorization" action="logout"><g:message code="authorization.action.logout" /></g:link></span>
    <g:canFillAdmin>   
      <span class="menuButton menuButtonIzquierda"><g:link class="list" controller="admin" action="index"><g:message code="admin.index.principal" /></g:link></span>
    </g:canFillAdmin>
    <g:canNotFillAdmin>
      <span class="menuButton menuButtonIzquierda">
        <g:link class="list" controller="loginAuth" action="show" id="${session.traumaContext.userId}">
          <g:message code="loginAuth.user.adminPrincipal" />
        </g:link>
      </span>  
      <span class="menuButton menuButtonIzquierda">
        <g:link class="list" controller="domain" action="list">
          <g:message code="loginAuth.user.exitAdministration" />
        </g:link>
      </span>



    </g:canNotFillAdmin>  
  </div>
<g:layoutBody />		
</body>	
</html>