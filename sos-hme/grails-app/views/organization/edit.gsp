

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
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
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
                        <%--
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="contacts"><g:message code="organization.contacts.label" default="Contacts" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'contacts', 'errors')}">
                                    <g:select name="contacts" from="${demographic.contact.Contact.list()}" multiple="yes" optionKey="id" size="5" value="${organizationInstance?.contacts*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="identities"><g:message code="organization.identities.label" default="Identities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'identities', 'errors')}">
                                    <g:select name="identities" from="${demographic.identity.PartyIdentity.list()}" multiple="yes" optionKey="id" size="5" value="${organizationInstance?.identities*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ids"><g:message code="organization.ids.label" default="Ids" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'ids', 'errors')}">
                                    <g:select name="ids" from="${hce.core.support.identification.UIDBasedID.list()}" multiple="yes" optionKey="id" size="5" value="${organizationInstance?.ids*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="relationships"><g:message code="organization.relationships.label" default="Relationships" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'relationships', 'errors')}">
                                    
<ul>
<g:each in="${organizationInstance?.relationships?}" var="r">
    <li><g:link controller="partyRelationship" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="partyRelationship" action="create" params="['organization.id': organizationInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'partyRelationship.label', default: 'PartyRelationship')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="roles"><g:message code="organization.roles.label" default="Roles" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'roles', 'errors')}">
                                    
<ul>
<g:each in="${organizationInstance?.roles?}" var="r">
    <li><g:link controller="role" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="role" action="create" params="['organization.id': organizationInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'role.label', default: 'Role')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="type"><g:message code="organization.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'type', 'errors')}">
                                    <g:textField name="type" value="${organizationInstance?.type}" />
                                </td>
                            </tr>
                        --%>
                          
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
                                    <label for="ubicacion">Ubicacion</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: organizationInstance, field: 'contacts.addresses.asString', 'errors')}">
                                    <g:textArea name="ubicacion" value="${organizationInstance?.contacts?.addresses[0]?.asString[0]}" rows="5" cols="40" />
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
