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
    <g:if test="${result == 1}" >
      
      <g:link controller="authorization" action="login"> <g:message code="loginAuth.newPassword.iniciarSesion" /> </g:link>
    
    </g:if>
   </body>
</html>
