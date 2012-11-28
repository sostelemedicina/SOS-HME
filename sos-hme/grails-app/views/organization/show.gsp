
<%@ page import="demographic.party.Organization" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
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
                            <td valign="top" class="name">Nombre</td>
                               
                            <td valign="top"> ${organizationInstance.identities.name[0]}</td>
                            
                        </tr>
                      
                         <tr class="prop">
                                <td valign="top" class="name">Localidad</td>
                                <td valign="top" class="name">${organizationInstance?.contacts?.addresses[0]?.localidad[0]}</td>
                         </tr>
                         <tr class="prop">
                                <td valign="top" class="name">Entidad</td>
                                <td valign="top" class="name">${organizationInstance?.contacts?.addresses[0]?.entidad[0]}</td>
                         </tr>
                      <tr class="prop">
                                <td valign="top" class="name">Municipio</td>
                                <td valign="top" class="name">${organizationInstance?.contacts?.addresses[0]?.municipio[0]}</td>
                         </tr>
                      <tr class="prop">
                                <td valign="top" class="name">Parroquia</td>
                                <td valign="top" class="name">${organizationInstance?.contacts?.addresses[0]?.parroquia[0]}</td>
                         </tr>
                      <tr class="prop">
                                <td valign="top" class="name">Ubicacion</td>
                                <td valign="top" class="name">${organizationInstance?.contacts?.addresses[0]?.asString[0]}</td>
                         </tr>
                      
                      
                      
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${organizationInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                  <%--  <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--%>
                </g:form>
            </div>
        </div>
    </body>
</html>
