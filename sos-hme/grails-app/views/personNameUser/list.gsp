
<%@ page import="demographic.identity.PersonNameUser" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'personNameUser.label', default: 'PersonNameUser')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <%--<g:sortableColumn property="id" title="${message(code: 'personNameUser.id.label', default: 'Id')}" />--%>
                        
                            <g:sortableColumn property="primerNombre" title="${message(code: 'personNameUser.primerNombre.label', default: 'Primer Nombre')}" />
                        
                            <g:sortableColumn property="segundoNombre" title="${message(code: 'personNameUser.segundoNombre.label', default: 'Segundo Nombre')}" />
                        
                            <g:sortableColumn property="primerApellido" title="${message(code: 'personNameUser.primerApellido.label', default: 'Primer Apellido')}" />
                        
                            <g:sortableColumn property="segundoApellido" title="${message(code: 'personNameUser.segundoApellido.label', default: 'Segundo Apellido')}" />
                        
                            <g:sortableColumn property="telfhabitacion" title="${message(code: 'personNameUser.telfhabitacion.label', default: 'Telf habitacion')}" />

                            <g:sortableColumn property="telfcelular" title="${message(code: 'personNameUser.telfcelular.label', default: 'Telf celular')}" />							
                        							
							<g:sortableColumn property="email" title="${message(code: 'personNameUser.email.label', default: 'Direccion email')}" />
							
							
							<th><g:message code="loginAuth.person.detail" default="Detalles:" /></th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${personNameUserInstanceList}" status="i" var="personNameUserInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <%--<td><g:link action="show" id="${personNameUserInstance.id}">${fieldValue(bean: personNameUserInstance, field: "id")}</g:link></td>--%>
                        
                            <td>${fieldValue(bean: personNameUserInstance, field: "primerNombre")}</td>
                        
                            <td>${fieldValue(bean: personNameUserInstance, field: "segundoNombre")}</td>
                        
                            <td>${fieldValue(bean: personNameUserInstance, field: "primerApellido")}</td>
                        
                            <td>${fieldValue(bean: personNameUserInstance, field: "segundoApellido")}</td>
                        
                            <td>${fieldValue(bean: personNameUserInstance, field: "telfhabitacion")}</td>
							
							<td>${fieldValue(bean: personNameUserInstance, field: "telfcelular")}</td>
							
							<td>${fieldValue(bean: personNameUserInstance, field: "email")}</td>
							
							<td><g:link action="show" id="${personNameUserInstance.id}">${message(code: 'loginAuth.person.detail', default: 'Detalles')}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
				<g:paginate total="${personNameUserInstanceTotal}" />
				<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            </div>
        </div>
    </body>
</html>
