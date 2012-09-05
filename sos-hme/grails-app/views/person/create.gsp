

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
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
							
							<tr class="prop">
								<td valign="top" class="name">
									<label for="identities">
										<g:message code="person.copy.label" default="Desde pacientes"/>:
									</label>
									
								</td>
								<td valign="top" class="value ${hasErrors(bean: personInstance, field: 'identities', 'errors')}">
        <%--<g:select name="identities" from="${demographic.identity.PersonNamePatient.list()}" multiple="No" optionKey="id" size="5" value="${personInstance?.identities*.id}" /><br>--%>
        <g:link controller="person" action="find">
							${message(code: 'person.copy.label', args: [message(code: 'role.label', default: 'PersonName')])}
		
		</g:link>
									
								</td>
							</tr>
							
	<!--  						
							
							
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="identities"><g:message code="person.identities.label" default="Identidad" /></label>:
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'identities', 'errors')}">
                                    <g:select name="identities" from="${demographic.identity.PersonNameUser.list()}" multiple="No" optionKey="id" size="5" value="${personInstance?.identities*.id}" /><br>
                                     <g:link controller="personNameUser" action="create" params="['person.id': personInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'role.label', default: 'PersonName')])}</g:link>
                                </td>


                            </tr>-->
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="primerNombre"><g:message code="personNameUser.primerNombre.label" default="Primer Nombre" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'primerNombre', 'errors')}">
                                    <g:textField name="primerNombre" value="${personNameUserInstance?.primerNombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="segundoNombre"><g:message code="personNameUser.segundoNombre.label" default="Segundo Nombre" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'segundoNombre', 'errors')}">
                                    <g:textField name="segundoNombre" value="${personNameUserInstance?.segundoNombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="primerApellido"><g:message code="personNameUser.primerApellido.label" default="Primer Apellido" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'primerApellido', 'errors')}">
                                    <g:textField name="primerApellido" value="${personNameUserInstance?.primerApellido}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="segundoApellido"><g:message code="personNameUser.segundoApellido.label" default="Segundo Apellido" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'segundoApellido', 'errors')}">
                                    <g:textField name="segundoApellido" value="${personNameUserInstance?.segundoApellido}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="telfhabitacion"><g:message code="personNameUser.telfhabitacion.label" default="Telfhabitacion" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'telfhabitacion', 'errors')}">
                                    <g:textField name="telfhabitacion" value="${personNameUserInstance?.telfhabitacion}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="telfcelular"><g:message code="personNameUser.telfcelular.label" default="Telfcelular" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'telfcelular', 'errors')}">
                                    <g:textField name="telfcelular" value="${personNameUserInstance?.telfcelular}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email"><g:message code="personNameUser.email.label" default="Email" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personNameUserInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${personNameUserInstance?.email}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaNacimiento"><g:message code="person.fechaNacimiento.label" default="Fecha de nacimiento" /></label>:
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'fechaNacimiento', 'errors')}">
                                    <g:datePicker name="fechaNacimiento" precision="day" value="${personInstance?.fechaNacimiento}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sexo"><g:message code="person.sexo.label" default="Sexo" /></label>:
                                </td>
                               <!-- <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'sexo', 'errors')}">
                                    <g:textField name="sexo" value="${personInstance?.sexo}" />
                                </td>-->
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'sexo', 'errors')}">
                                    <g:select name="sexo" from="${Person.getSexCodes()}" multiple="No"  size="2" value="sexo" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="identificador"><g:message code="person.id.label" /></label>:
                                </td>
                                <td valign="top" class="name">
									  <g:textField name="extension" value="${params.identificador}" />
									  <g:select name="root"  from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" />
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
