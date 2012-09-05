
<%@ page import="demographic.role.Role" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
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
                        
                            <%--<g:sortableColumn property="id" title="${message(code: 'role.id.label', default: 'Id')}" />--%>

                            <g:sortableColumn property="type" title="${message(code: 'role.type.label', default: 'Tipo')}" />
															
                            <g:sortableColumn property="performer" title="${message(code: 'role.performer.label', default: 'Persona')}" />

                            

                            <g:sortableColumn property="timeValidityFrom" title="${message(code: 'role.timeValidityFrom.label', default: 'Valido Desde')}" />
                        
                            <g:sortableColumn property="timeValidityTo" title="${message(code: 'role.timeValidityTo.label', default: 'Time Validity To')}" />
							
							<th><g:message code="loginAuth.person.detail" default="Detalles:" /></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${roleInstanceList}" status="i" var="roleInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <%--<td><g:link action="show" id="${roleInstance.id}">${fieldValue(bean: roleInstance, field: "id")}</g:link></td>--%>

                            <td>${fieldValue(bean: roleInstance, field: "type")}</td>

                            <td>${fieldValue(bean: roleInstance, field: "performer")}</td>

                            

                            <td><g:formatDate date="${roleInstance.timeValidityFrom}" /></td>

                            <td><g:formatDate date="${roleInstance.timeValidityTo}" /></td>
                        
                            <td><g:link action="show" id="${roleInstance.id}">${message(code: 'loginAuth.person.detail', default: 'Detalles')}</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${roleInstanceTotal}" />
				<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            </div>
        </div>
    </body>
</html>
