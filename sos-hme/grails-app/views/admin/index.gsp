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
      <g:link class="list" controller="loginAuth" action="list">
          <div class="nav caja">
            
            <h1>Listado de usuarios</h1>
          
          </div>
        </g:link>
      <g:link class="list" controller="person" action="list" params="[role: Role.MEDICO]">
          <div class="nav caja">
            
            <h1>Listado de médicos</h1>
          
          </div>
       </g:link>
       <g:link class="list" controller="person" action="list" params="[role: Role.ENFERMERIA]">
          <div class="nav caja">
            
            <h1>Listado de enfermeras</h1>
          
          </div>
       </g:link>
        <g:link class="list" controller="person" action="list" params="[role: Role.ADMIN]">
          <div class="nav caja">
            
            <h1>Listado de administradores</h1>
          
          </div>
       </g:link>
       <g:link class="list" controller="person" action="list">
          <div class="nav caja">
            
            <h1>Listado personas</h1>
          
          </div>
       </g:link>
       <g:link class="list" controller="preguntaSecreta" action="index">
          <div class="nav caja">
            
            <h1>Administrar preguntas secretas</h1>
          
          </div>
       </g:link>
       
     
     </div>
  </body>
</html>
