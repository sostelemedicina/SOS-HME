<%@ page import="demographic.party.Organization" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
       
        <div class="body">
            <h2><g:message code="default.edit.label" args="[entityName]" /></h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${organizationInstance}">
            <div class="errors">
                <g:renderErrors bean="${organizationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${organizationInstance?.id}" />
                <g:hiddenField name="version" value="${organizationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                       
                          
                          <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name">Nombre</label>
                                </td>
                                <td valign="top" >
                                    <g:textField name="name" value="${organizationInstance?.identities.name[0]}" style="width: 250px;" />
                                </td>
                          </tr>
                          <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="subType">Tipo</label>
                                </td>
                                <td valign="top" >
                                    <g:textField name="subType" value="${organizationInstance?.subType}" style="width: 250px;" />
                                </td>
                          </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ubicacion">Ubicacion</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'contacts.addresses.asString', 'errors')}">
                                    <g:textArea name="ubicacion" value="${organizationInstance?.contacts?.addresses[0]?.asString[0]}" rows="5" cols="40" />
                                </td>
                           </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="entidad">Entidad</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'contacts.addresses.entidad', 'errors')}">
                                    <g:textField name="entidad" value="${organizationInstance?.contacts?.addresses[0]?.entidad[0]}" style="width: 250px;"  />
                                </td>
                           </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="municipio">Municipio</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'contacts.addresses.municipio', 'errors')}">
                                    <g:textField name="municipio" value="${organizationInstance?.contacts?.addresses[0]?.municipio[0]}" style="width: 250px;"  />
                                </td>
                           </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="parroquia">Parroquia</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'contacts.addresses.parroquia', 'errors')}">
                                    <g:textField name="parroquia" value="${organizationInstance?.contacts?.addresses[0]?.parroquia[0]}" style="width: 250px;"  />
                                </td>
                           </tr>
                           <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="localidad">Localidad</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'contacts.addresses.localidad', 'errors')}">
                                    <g:textField name="localidad" value="${organizationInstance?.contacts?.addresses[0]?.localidad[0]}" style="width: 250px;"  />
                                </td>
                           </tr>
                          
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                   <%-- <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--%>
                </div>
            </g:form>
        </div>
    </body>
</html>
