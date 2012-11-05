<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.directory.Folder" %>
<%--<?xml version="1.0" encoding="ISO-8859-1?>--%>
<!DOCTYPE html>
<html>
    <g:set var="startmsec" value="${System.currentTimeMillis()}"/>
  <head>
    
    
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>

    
    <META Http-Equiv="Cache-Control" Content="no-cache">
    <META Http-Equiv="Pragma" Content="no-cache">
    <META Http-Equiv="Expires" Content="0"> 

   

    <g:javascript>
      // Para evitar el boton de volver del navegador.
      window.history.go(1);

      
    </g:javascript>
    
    <title><g:layoutTitle/> | <g:message code="hce.nombre" /> | v${ApplicationHolder.application.metadata['app.version']}</title>
    <link rel="stylesheet" href="${createLinkTo(dir:'css', file:'ehr.css')}" />
    <g:layoutHead />
    
    <style>
      #body_table {
        background-color: #efefff;
        border: 1px solid #333399;
        width: 100%;
      }
      #body_td {
        width: auto;
      }
      #bottom_actions {
        /*text-align: right;*/
      }
    </style>
  </head>
  <body>
    <div id="user_bar">
      <b><g:message code="hce.nombre" /></b> v${ApplicationHolder.application.metadata['app.version']} |
      <g:datosUsuario userId="${session.traumaContext.userId}" />
      <span class="user_actions">
      
        <%-- FECHA ACTUAL --%>
        <span class="currentDate">
          <g:format date="${new Date()}" />
        </span>
        
        <ul class="userBar">
          <g:langSelector>
            <li ${(session.locale.getLanguage()==it)?'class="active"':''}>
              <a href="?sessionLang=${it}&templateId=${params.templateId}"><g:message code="common.lang.${it}" /></a>
            </li>
          </g:langSelector>
        </ul>

        <ul class="userBar">
          <li ${(['domain'].contains(controllerName))?'class="active"':''}>
            <g:link controller="domain" action="list"><g:message code="domain.action.list" /></g:link>
          </li>
          <li>
           <g:set var="folder" value="${Folder.findByPath(session.traumaContext.domainPath)}" />
           (${folder.name.value})
          </li>
          <li ${(['records'].contains(controllerName))?'class="active"':''}>
            <g:link controller="records" action="list"><g:message code="records.action.list" /></g:link>
          </li>
          <li ${(controllerName=='demographic')?'class="active"':''}>
            <g:link controller="demographic" action="admisionPaciente"><g:message code="demographic.action.admisionPaciente" /></g:link>
          </li>
        </ul>
        
        <g:link controller="authorization" action="logout"><g:message code="authorization.action.logout" /></g:link>
        
      </span>
    </div>
  
    <div id="body">
      <g:if test="${flash.message}">
        <div id="message" class="${flash.clase}">
          <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}" />
        </div>
      </g:if>
      
      <table cellpadding="0" cellspacing="0" id="body_table">
        <tr>
          <td id="body_td" rowspan="2">
            <g:layoutBody />
          </td>
        </tr>
      </table>
    </div>
  </body>
</html>