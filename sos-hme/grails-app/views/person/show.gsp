<%@ page import="demographic.party.Person" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>
          <tr class="prop">
            <td valign="top" class="name"><g:message code="person.ids.label" />:</td>
            <td valign="top" class="value">
              <g:render template="UIDBasedID" collection="${personInstance.ids}" var="id" />
            </td>
          </tr>
          <tr class="prop">
            <td valign="top" class="name">
              <label for="primerNombre"><g:message code="personNameUser.primerNombre.label" default="Primer Nombre" />:</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'primerNombre', 'errors')}">
        <g:if test="${personInstance.identities.primerNombre[0]!=null}">
${personInstance.identities.primerNombre[0]}
        </g:if>
        </td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name">
            <label for="segundoNombre"><g:message code="personNameUser.segundoNombre.label" default="Segundo Nombre" />:</label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'segundoNombre', 'errors')}">
        <g:if test="${personInstance.identities.segundoNombre[0]!=null}">
${personInstance.identities.segundoNombre[0]}
        </g:if>
        </td>
        </tr>						
        <tr class="prop">
          <td valign="top" class="name">
            <label for="primerApellido"><g:message code="personNameUser.primerApellido.label" default="Primer Apellido" />:</label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'primerApellido', 'errors')}">
        <g:if test="${personInstance.identities.primerApellido[0]!=null}">
${personInstance.identities.primerApellido[0]}
        </g:if>
        </td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name">
            <label for="segundoApellido"><g:message code="personNameUser.segundoApellido.label" default="Segundo Apellido" />:</label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'segundoApellido', 'errors')}">
        <g:if test="${personInstance.identities.segundoApellido[0]!=null}">
${personInstance.identities.segundoApellido[0]}
        </g:if>
        </td>
        </tr>
        <tr  class="prop">
          <td valign="top" class="name"><g:message code="personNameUser.telfhabitacion.label" default="Identities" />:</td>
        <td valign="top" style="text-align: left;" class="value">
${personInstance.identities.telfhabitacion[0]}
        </td>
        </tr>
        <tr  class="prop">
          <td valign="top" class="name"><g:message code="personNameUser.telfcelular.label" default="Identities" />:</td>
        <td valign="top" style="text-align: left;" class="value">
${personInstance.identities.telfcelular[0]}
        </td>
        </tr>
        <tr  class="prop">
          <td valign="top" class="name"><g:message code="personNameUser.email.label" default="Identities" />:</td>
        <td valign="top" style="text-align: left;" class="value">${personInstance.email}</td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name"><g:message code="person.fechaNacimiento.label" default="Fecha Nacimiento" />:</td>
        <td valign="top" class="value"><g:formatDate date="${personInstance?.fechaNacimiento}"  format="${g.message(code: 'person.show.fechaNacimiento')}"  /></td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name"><g:message code="person.sexo.label" default="Sexo" />:</td>
        <td valign="top" class="value">${fieldValue(bean: personInstance, field: "sexo")}</td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name"><g:message code="person.roles.label" default="Roles" />:</td>
        <td valign="top" style="text-align: left;" class="value">
          <ul>
            <g:each in="${personInstance.roles}" var="r">
              <li><g:link controller="role" action="show" params="['id': r.id, 'person.id': personInstance?.id]">${r?.type}</g:link></li>
            </g:each>
          </ul>
        </td>
        </tr>
        
        </tbody>
      </table>
    </div>
    <div class="buttons">
      <g:form>
        <g:hiddenField name="id" value="${personInstance?.id}" />
        <g:hiddenField name="roles" value="${personInstance?.roles}" />

        <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.edit.label', default: 'Edit')}" /></span>
        <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
       <%-- <span class="button"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        <span class="button"><g:link class="create" action="create" ><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        --%>
      </g:form>
    </div>
  </div>
</body>
</html>
