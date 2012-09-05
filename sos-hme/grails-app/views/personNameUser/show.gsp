
<%@ page import="demographic.identity.PersonNameUser" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'personNameUser.label', default: 'PersonNameUser')}" />
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
                    
                    <%--    <tr class="prop">
                            <td valign="top" class="name"><g:message code="personNameUser.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: personNameUserInstance, field: "id")}</td>
                            
                        </tr>--%>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="personNameUser.primerNombre.label" default="Primer Nombre" />:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: personNameUserInstance, field: "primerNombre")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="personNameUser.segundoNombre.label" default="Segundo Nombre" />:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: personNameUserInstance, field: "segundoNombre")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="personNameUser.primerApellido.label" default="Primer Apellido" />:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: personNameUserInstance, field: "primerApellido")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="personNameUser.segundoApellido.label" default="Segundo Apellido" />:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: personNameUserInstance, field: "segundoApellido")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="personNameUser.telfhabitacion.label" default="Telfhabitacion" />:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: personNameUserInstance, field: "telfhabitacion")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="personNameUser.telfcelular.label" default="Telfcelular" />:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: personNameUserInstance, field: "telfcelular")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="personNameUser.email.label" default="Email" />:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean: personNameUserInstance, field: "email")}</td>
                            
                        </tr>
                    

                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${personNameUserInstance?.id}" />
					<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
					<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
					<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
