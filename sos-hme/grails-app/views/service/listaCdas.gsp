<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <meta name="layout" content="ehr-modal" />
       <g:javascript library="jquery" />
    <title><g:message code="hce.service.listadCdas.title" /></title>
  </head>
  <body>

    <ul class="top_actions">
      <li>
      <g:link controller="demographic" action="show" params="[id: idPaciente ]" class="back">Atras</g:link>
      </li>
      
    </ul>

    <h1><g:message code="hce.service.listadCdas.title" /></h1>

     <div id="resultadoExterno" >


    <g:render template="registroExterno" model="[conexionImp:conexionImp,idPaciente: idPaciente,idOrg: idOrg,externo:result, total: total, llamar: llamar]" />

    
      </div>
      <div id="errorResultadoExterno" >
      </div>
  </body>
</html>
