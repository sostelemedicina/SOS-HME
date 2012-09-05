<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><g:message code="auth.login.welcome"/></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'estilos.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'estilo.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <g:layoutHead />
    <g:javascript library="application" />
  </head>
  <body class="bodybasic">
    <div id="cabecera" class="cabecera">
      <div id="cabColI">
    	<div id="logo">
                <h1><img src="${createLinkTo(dir:'images',file:'SOS.gif')}" alt="SOS" width="97" height="53" align="texttop" />Historias Médicas</h1>
        </div>
        <div id="breadcrumbs">
        	
                <g:link controller="domain" action="list" class="contextoEhr">Dominios</g:link>> Historia Integral
        </div>
    </div>
      <div id="cabColD">
        <div id="infoSec"><g:formatDate format="dd-MM-yyyy" date="${new Date()}"/> | &nbsp; Cambiar idioma <a href="#"><img src="${createLinkTo(dir:'images',file:'ico_ingles.jpg')}" alt="Inglés" width="25" height="34" hspace="2" border="0" align="absmiddle" /></a> <a href="#"><img src="${createLinkTo(dir:'images',file:'ico_port.jpg')}" alt="Portugués" width="25" height="34" hspace="2" border="0" align="absmiddle" /></a></div>
        <div id="infoLogin" class="infoLogin">
          <g:datosUsuario userId="${session.traumaContext.userId}" /> | <g:link controller="authorization" action="logout"><g:message code="authorization.action.logout" /></g:link>
        </div>
      </div>  
      
    </div>
    
    
    <div id="contenidobasic" class="basicregistro">
      
      <g:layoutBody />
    </div>
    
    <div id="footerbasic" class="footercontenido">
      Centro de An&aacute;lisis de Im&aacute;genes Biom&eacute;dicas Computarizadas CAIBCO - Instituto de Medicina Tropical </br>
      Facultad de Medicina. Universidad Central de Venezuela </br>
      Tel&eacute;fonos: (0212) 605.37.46 / 35.94 </br>
      sostelemedicina&#64;ucv.ve
    </div>
  </body>
</html>
