

<%@ page import="demographic.party.Organization" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${organizationInstance}">
            <div class="errors">
                <g:renderErrors bean="${organizationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                           <!-- <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type"><g:message code="organization.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'type', 'errors')}">
                                    <g:textField name="type" value="${organizationInstance?.type}" />
                                </td>
                            </tr>-->
                           <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nombre">Nombre</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'identities.name', 'errors')}">
                                    <g:textField name="nombre" value="${organizationInstance?.identities?.name}" style="width: 250px;" />
                                </td>
                           </tr>
                           
                           <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ubicacion">Ubicacion</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'contacts.addresses.asString', 'errors')}">
                                    <g:textArea name="ubicacion" value="${organizationInstance?.contacts?.addresses?.asString}" rows="5" cols="40" />
                                </td>
                           </tr>
                           
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
