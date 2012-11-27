
<%@ page import="demographic.party.Organization" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>

  <div class="body">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
      <table>
        <thead>
          <tr>

        <g:sortableColumn property="id" title="${message(code: 'organization.id.label', default: 'Id')}" />

        <g:sortableColumn property="type" title="${message(code: 'organization.type.label', default: 'Type')}" />

        </tr>
        </thead>
        <tbody>
        <g:each in="${organizationInstanceList}" status="i" var="organizationInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show" id="${organizationInstance.id}">${fieldValue(bean: organizationInstance, field: "id")}</g:link></td>

          <td>${organizationInstance.identities.name}</td>

          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${organizationInstanceTotal}" />
   
   
              <span class="menuButton"><g:link class="create" action="create" > <g:message code="default.new.label" args="[entityName]" /></g:link></span>
           
       </div>
    
  </div>
</body>
</html>
