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
   <h1>Reset Password</h1>
     <p><g:message code="${flash.message}"/></p>
     <g:if test="${result == 1}" >
       
       <g:form id="form1" url="[controller:'loginAuth',action:'newPassword']" method="post" >
         <input type="hidden" name="idReset" value="${idReset}"/>
       
       <label>Ingrese su nueva contraseña</label>
         <input id="newPassword" type="password"  name="userPassword" class="password"  /> </br>
       <label>Repita su nueva contraseña</label> 
         <input id="repeatPassword" type="password"  name="userPasswordRepeat" class="password" /> </br>
       
       <input id="doit" type="submit"  name="doit" class="enviarBoton" value="${message(code:'loginAuth.lostPassword.enviar')}" />
     
        </g:form> 
         
     </g:if>
     <g:else>
       
       
     </g:else>  
                
  </body>
</html>
     