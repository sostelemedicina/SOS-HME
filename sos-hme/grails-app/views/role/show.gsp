
<%@ page import="demographic.role.Role" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
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
                            <td valign="top" class="name"><g:message code="role.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: roleInstance, field: "id")}</td>
                            
                        </tr>--%>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.performer.label" default="Performer" /></td>
							
								<td valign="top" class="value"><g:link controller="person" action="show" id="${roleInstance?.performer?.id}">${roleInstance?.performer?.identities?.toString()}</g:link></td>

                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.status.label" default="status" /></td>
                            
                            <td valign="top" class="value">${roleInstance?.status}</td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.timeValidityFrom.label" default="Time Validity From" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${roleInstance?.timeValidityFrom}" /></td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.timeValidityTo.label" default="Time Validity To" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${roleInstance?.timeValidityTo}" /></td>
                            
                        </tr>
                    <%--
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.capabilities.label" default="Capabilities" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${roleInstance.capabilities}" var="c">
                                    <li><g:link controller="capability" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.contacts.label" default="Contacts" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${roleInstance.contacts}" var="c">
                                    <li><g:link controller="contact" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.identities.label" default="Identities" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${roleInstance.identities}" var="i">
                                    <li><g:link controller="partyIdentity" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.ids.label" default="Ids" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${roleInstance.ids}" var="i">
                                    <li><g:link controller="UIDBasedID" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    --%>

                    <%--
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.relationships.label" default="Relationships" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${roleInstance.relationships}" var="r">
                                    <li><g:link controller="partyRelationship" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    --%>

                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="role.type.label" default="Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: roleInstance, field: "type")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
				
                    <g:hiddenField name="id" value="${roleInstance?.id}"/>
					<g:hiddenField name="personid" value="${roleInstance?.performer.id}" />
					<!--<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
					<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>-->
					<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
					</g:form>
            </div>
        </div>
    </body>
</html>
