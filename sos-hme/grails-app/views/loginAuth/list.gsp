
<%@ page import="authorization.LoginAuth" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'loginAuth.label', default: 'LoginAuth')}" />
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
                        

                            <g:sortableColumn property="user" title="${message(code: 'loginAuth.user.label', default: 'User')}" />

                            
							<g:sortableColumn property="person" title="${message(code: 'loginAuth.person.label', default: 'Persona')}" />
							
						
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${loginAuthInstanceList}" status="i" var="loginAuthInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <!--<td><g:link action="show" id="${loginAuthInstance.id}">${fieldValue(bean: loginAuthInstance, field: "id")}</g:link></td>
                        -->
                            <!--<td>${fieldValue(bean: loginAuthInstance, field: "purpose")}</td>-->
                        
                            <td><g:link action="show" id="${loginAuthInstance.id}">${fieldValue(bean: loginAuthInstance, field: "user")}</g:link></td>

                            
                        
                            <td>${loginAuthInstance?.person?.identities?.toString()}</td>
							
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${loginAuthInstanceTotal}" />
                <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            </div>
        </div>
    </body>
</html>
