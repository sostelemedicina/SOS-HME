

<%@ page import="demographic.role.Role" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${roleInstance}">
            <div class="errors">
                <g:renderErrors bean="${roleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="performer"><g:message code="role.performer.label" default="Performer" /></label>
                                </td>
								<td valign="top" class="value"><g:link controller="person" action="show" id="${person?.id}">${person?.identities?.toString()}</g:link></td>
                                <%-- si no tiene un id person asignado--%>
                                <g:if test="${personid == null}">
                                    <%--se le pide al usuario que le asigne uno--%>
									<td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'performer', 'errors')}">
                                    <g:select name="performer.id" from="${personUsers}" optionKey="id" value="${roleInstance?.performer?.id}"  />

                                </td>
                                </g:if>
                                <g:else>
                                  <!--en caso contrario se le asigna el id person al rol-->
                                  <g:hiddenField name="performer.id" value="${personid}" />
                                </g:else>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status"><g:message code="role.status.label" default="status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'status', 'errors')}">
                                    <g:checkBox name="status"  value="${true}"  />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="timeValidityFrom"><g:message code="role.timeValidityFrom.label" default="Time Validity From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'timeValidityFrom', 'errors')}">
                                    <g:datePicker name="timeValidityFrom" precision="day" value="${roleInstance?.timeValidityFrom}"  />
                                </td>
                            </tr>
							
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="timeValidityTo"><g:message code="role.timeValidityTo.label" default="Time Validity To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'timeValidityTo', 'errors')}">
                                    <g:datePicker name="timeValidityTo" precision="day" value="${roleInstance?.timeValidityTo}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type"><g:message code="role.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'type', 'errors')}">
                                    <g:select name="type" from="${demographic.role.Role.getRoleCodes()}" multiple="No" size="4" value="type" />
                                </td>
                                <%--<td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'type', 'errors')}">
                                    <g:textField name="type" value="${roleInstance?.type}" />
                                </td>--%>
                            </tr>



                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
					<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
