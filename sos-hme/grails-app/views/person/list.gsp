<%@ page import="demographic.party.Person" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h2><g:message code="default.list.label" args="[entityName]" /></h2>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
      <table>
        <thead>
          <tr>
<%--<g:sortableColumn property="id" title="${message(code: 'person.id.label', default: 'Id')}" />--%>
        <g:sortableColumn property="identities" title="${message(code: 'person.identitie.label', default: 'Identidad')}" params="[role: role]" />
        <g:sortableColumn property="fechaNacimiento" title="${message(code: 'person.fechaNacimiento.label', default: 'Fecha Nacimiento')}" params="[role: role]" />
        <g:sortableColumn property="sexo" title="${message(code: 'person.sexo.label', default: 'Sexo')}" params="[role: role]" />
        <g:sortableColumn property="Role" title="${message(code: 'person.type.label', default: 'Rol')}" params="[role: role]" />
        </tr>
        </thead>
        <tbody>
        <g:each in="${personInstanceList}" status="i" var="personInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${personInstance.id}" params="[role: role]">
              <g:if test="${personInstance.identities}">
                        ${personInstance.identities.asList().first()}
              </g:if>
          </g:link></td>
          <td><g:formatDate date="${personInstance.fechaNacimiento}" format="${g.message(code: 'default.date.format1')}" /></td>
          <td>${fieldValue(bean: personInstance, field: "sexo")}</td>
          <td><g:if test="${personInstance.roles}">
                ${demographic.role.Role.get(personInstance.roles.id [0]).type}
              </g:if>
          </td>
          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    
    <div class="paginateButtons">
      
     <g:paginate controller="person" action="list" total="${personInstanceTotal}" params="[role: role]" />
                 
      
      <g:if test="${role}">
      <span class="menuButton"><g:link class="create" action="create" params="[role: role]"> <g:message code="default.new.label" args="[entityName]" /></g:link></span>
      </g:if>
    </div>
  </div>
</body>
</html>
