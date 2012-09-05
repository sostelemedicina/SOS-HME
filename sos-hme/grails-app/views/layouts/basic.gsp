<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><g:message code="auth.login.welcome"/></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'estilo.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <g:layoutHead />
  <g:javascript library="application" />
</head>
<body class="bodybasic">


  <div id="cabecera">
    <div id="cabColI">
      <div id="logo">
        <h1><img src="${createLinkTo(dir:'images' ,file:'SOS.gif')}" alt="SOS" width="97" height="53" align="texttop" />Historias Médicas</h1>
      </div>
      <div id="breadcrumbs">
        <g:link controller="domain" action="list" ><g:message code="domain.action.list" /></g:link>


      </div>

    </div>
    <div id="cabColD">
      <div id="infoSec"><g:formatDate date="${new Date()}" formatName="default.date.format.text" /> &nbsp; | &nbsp; Cambiar idioma
        <g:langSelector>
          <g:if test="${(session.locale.getLanguage()!=it)}">

            <a href="?sessionLang=${it}&templateId=${params.templateId}" ><img src="${createLinkTo(dir:'images' ,file:'ico_'+it+'.jpg')}" alt="${message(code:'common.lang.'+it)}" width="25" height="34" hspace="2" border="0" align="absmiddle" /></a>
          </g:if>
        </g:langSelector>

      </div>
      <div id="infoLogin">
        <g:datosUsuario userId="${session.traumaContext.userId}" /> &nbsp; | &nbsp;

        <g:link controller="authorization" action="logout" ><g:message code="authorization.action.logout" /></g:link>
      </div>

    </div>
  </div>



<g:canFillAdmin>
  <g:link controller="loginAuth" action="list">administrar</g:link>
</g:canFillAdmin>




<g:layoutBody />


<div id="footer" class="footercontenido">
  Centro de An&aacute;lisis de Im&aacute;genes Biom&eacute;dicas Computarizadas CAIBCO - Instituto de Medicina Tropical </br>
  Facultad de Medicina. Universidad Central de Venezuela </br>
  Tel&eacute;fonos: (0212) 605.37.46 / 35.94 </br>
  <a href="mailto:sostelemedicina&#64;ucv.ve">sostelemedicina&#64;ucv.ve
  </a></div>
</body>
</html>
