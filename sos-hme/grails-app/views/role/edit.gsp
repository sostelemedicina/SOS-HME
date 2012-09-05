

<%@ page import="demographic.role.Role" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${roleInstance}">
            <div class="errors">
                <g:renderErrors bean="${roleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${roleInstance?.id}" />
                <g:hiddenField name="version" value="${roleInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>

                                <%--<g:if test="${personid == null}">--%>

                                   <%-- <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'performer', 'errors')}">
                                        <g:select name="performer.id" from="${demographic.identity.PersonNameUser.list()}" optionKey="id" value="${roleInstance?.performer?.id}"  />
                                    </td>--%>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.performer.label" default="Performer" /></td>
							
							<td valign="top" class="value"><g:link controller="person" action="show" id="${roleInstance?.performer?.id}">${roleInstance?.performer?.identities?.toString()}</g:link></td>

                        </tr>
                                <g:hiddenField name="performer" value="${personid}" />    
                                <%--</g:if>
                                <g:else>
                                  
                                </g:else>--%>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status"><g:message code="role.status.label" default="status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'status', 'errors')}">
                                    <g:checkBox name="status"  value="${roleInstance?.status}"  />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timeValidityFrom"><g:message code="role.timeValidityFrom.label" default="Time Validity From" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'timeValidityFrom', 'errors')}">
                                    <g:datePicker name="timeValidityFrom" precision="day" value="${roleInstance?.timeValidityFrom}"  />
                                </td>
                            </tr>							

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timeValidityTo"><g:message code="role.timeValidityTo.label" default="Time Validity To" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'timeValidityTo', 'errors')}">
                                    <g:datePicker name="timeValidityTo" precision="day" value="${roleInstance?.timeValidityTo}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        <%--
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="capabilities"><g:message code="role.capabilities.label" default="Capabilities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'capabilities', 'errors')}">
                                    <g:select name="capabilities" from="${demographic.role.Capability.list()}" multiple="yes" optionKey="id" size="5" value="${roleInstance?.capabilities*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="contacts"><g:message code="role.contacts.label" default="Contacts" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'contacts', 'errors')}">
                                    <g:select name="contacts" from="${demographic.contact.Contact.list()}" multiple="yes" optionKey="id" size="5" value="${roleInstance?.contacts*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="identities"><g:message code="role.identities.label" default="Identities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'identities', 'errors')}">
                                    <g:select name="identities" from="${demographic.identity.PartyIdentity.list()}" multiple="yes" optionKey="id" size="5" value="${roleInstance?.identities*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ids"><g:message code="role.ids.label" default="Ids" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'ids', 'errors')}">
                                    <g:select name="ids" from="${hce.core.support.identification.UIDBasedID.list()}" multiple="yes" optionKey="id" size="5" value="${roleInstance?.ids*.id}" />
                                </td>
                            </tr>
                            --%>

                        
                       <%--     <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="relationships"><g:message code="role.relationships.label" default="Relationships" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'relationships', 'errors')}">
                                    
<ul>
<g:each in="${roleInstance?.relationships?}" var="r">
    <li><g:link controller="partyRelationship" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="partyRelationship" action="create" params="['role.id': roleInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'partyRelationship.label', default: 'PartyRelationship')])}</g:link>

                                </td>
                            </tr>--%>
                        

                        
                        <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type"><g:message code="role.type.label" default="Type" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'type', 'errors')}">
                                    <g:select name="type" from="${demographic.role.Role.getRoleCodes()}" multiple="No" size="4" value="${roleInstance?.type}" />
                                </td>
                                <%--<td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'type', 'errors')}">
                                    <g:textField name="type" value="${roleInstance?.type}" />
                                </td>--%>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
 					<%--<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
					<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>--%>
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
