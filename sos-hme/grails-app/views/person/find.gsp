

<%@ page import="demographic.party.Person" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
		

		
    </head>
    <body>

        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${personInstance}">
            <div class="errors">
                <g:renderErrors bean="${personInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="copyperson" >
                <div class="dialog">
                    <table>
                        <tbody>
							
                          	<tr class="prop">
								<td valign="top" class="name">
									<label for="identities">
										<g:message code="person.select.label" default="seleccionar paciente"/>:
									</label>
									
								</td>
								<td valign="top" class="value ${hasErrors(bean: personPatients, field: 'identities', 'errors')}">
									<g:select name="personid" from="${personPatients}" multiple="No" optionKey="id" size="5" value="${personPatients?.identities*.id}" /><br>
        
		
		
									<%--<g:link controller="person" action="find">
														${message(code: 'default.add.label', args: [message(code: 'role.label', default: 'PersonName')])}
									
									</g:link>--%>
									
								</td>
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
