<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sample title</title>
  </head>
  <body>
    <p><g:message code="${flash.message}"/></p>
    <p><g:message code="loginAuth.lostPassword.mensaje"/></p>
    <p><g:message code="loginAuth.lostPassword.ingreseEmail"/></p>
    
  <g:form id="form1" url="[controller:'loginAuth',action:'sendEmailLink']" method="post" >
     <div id="email" class="email">
          <input id="userEmail" type="text"  name="userEmail" class="email" value="${message(code:'loginAuth.lostPassword.email')}" onmousedown="javascript:this.value='';"/>
     </div>
     <div id="enviarBoton" class="enviarBoton">
          <input id="doit" type="submit"  name="doit" class="enviarBoton" value="${message(code:'loginAuth.lostPassword.enviar')}" />
     </div>

    
  </g:form>  
    
  </body>
</html>
