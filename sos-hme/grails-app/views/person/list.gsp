
<%@ page import="demographic.party.Person" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
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
                        
                            <%--<g:sortableColumn property="id" title="${message(code: 'person.id.label', default: 'Id')}" />--%>

                            <g:sortableColumn property="identities" title="${message(code: 'person.identitie.label', default: 'Identidad')}" />
                            
                            <g:sortableColumn property="fechaNacimiento" title="${message(code: 'person.fechaNacimiento.label', default: 'Fecha Nacimiento')}" />
                        
                            <g:sortableColumn property="sexo" title="${message(code: 'person.sexo.label', default: 'Sexo')}" />

                            <g:sortableColumn property="Role" title="${message(code: 'person.type.label', default: 'Rol')}" />

                            <%--<th><g:message code="loginAuth.person.detail" default="Detalles:" /></th>--%>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${personInstanceList}" status="i" var="personInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <%--<td><g:link action="show" id="${personInstance.id}">${fieldValue(bean: personInstance, field: "id")}</g:link></td>--%>

							<td><g:link action="show" id="${personInstance.id}">${personInstance.identities?.toString()}</g:link></td>
                            <td><g:formatDate date="${personInstance.fechaNacimiento}" /></td>
                        
                            <td>${fieldValue(bean: personInstance, field: "sexo")}</td>
                            <%--
                             se debe indicar el id de el objeto person (personInstance) al metodo get
                            personInstance.identities.primerNombre
                            --%>
                            
                            <%--angel: se verifica que el objeto person tenga un role de lo contrario
                                muestra.
                            --%>
                            <g:if test="${demographic.role.Role.get(personInstance.roles.id [0]) != null}">

                              <td>${demographic.role.Role.get(personInstance.roles.id [0]).type}</td>
                            </g:if>
							<g:else>
									<td></td>
							</g:else>
							
							<%--<td><g:link action="show" id="${personInstance.id}">${message(code: 'loginAuth.person.detail', default: 'Detalles')}</g:link></td>--%>
							

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${personInstanceTotal}" />
				<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            </div>
        </div>
    </body>
</html>
