<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="demographic.role.Role" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main" />
    <title>Administración SOS-HME</title>
  </head>
  <body>
     <div class="body">
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
       
      <g:link class="list" controller="loginAuth" action="list">
          <div class="nav caja">
            
            <h2>Listado de usuarios</h2>
          
          </div>
        </g:link>
      <g:link class="list" controller="person" action="list" params="[role: Role.MEDICO]">
          <div class="nav caja">
            
            <h2>Listado de médicos</h2>
          
          </div>
       </g:link>
       <g:link class="list" controller="person" action="list" params="[role: Role.ENFERMERIA]">
          <div class="nav caja">
            
            <h2>Listado de enfermeras</h2>
          
          </div>
       </g:link>
        <g:link class="list" controller="person" action="list" params="[role: Role.ADMIN]">
          <div class="nav caja">
            
            <h2>Listado de administradores</h2>
          
          </div>
       </g:link>
       <g:link class="list" controller="person" action="list">
          <div class="nav caja">
            
            <h2>Listado personas</h2>
          
          </div>
       </g:link>
       <g:link class="list" controller="preguntaSecreta" action="index">
          <div class="nav caja">
            
            <h2>Administrar preguntas secretas</h2>
          
          </div>
       </g:link>
       <g:link class="list" controller="domain">
          <div class="nav caja">
            <h2>Generar Reportes</h2>
          </div>
       </g:link>
       
     
     </div>
  </body>
</html>
