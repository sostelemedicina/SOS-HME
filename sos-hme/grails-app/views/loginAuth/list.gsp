
<%@ page import="authorization.LoginAuth" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'loginAuth.label', default: 'LoginAuth')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'loginAuth.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="purpose" title="${message(code: 'loginAuth.purpose.label', default: 'Purpose')}" />
                        
                            <g:sortableColumn property="user" title="${message(code: 'loginAuth.user.label', default: 'User')}" />
                        
                            <g:sortableColumn property="pass" title="${message(code: 'loginAuth.pass.label', default: 'Pass')}" />
                        
                            <g:sortableColumn property="idReset" title="${message(code: 'loginAuth.idReset.label', default: 'Id Reset')}" />
                        
                            <th><g:message code="loginAuth.pregunta.label" default="Pregunta" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${loginAuthInstanceList}" status="i" var="loginAuthInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${loginAuthInstance.id}">${fieldValue(bean: loginAuthInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: loginAuthInstance, field: "purpose")}</td>
                        
                            <td>${fieldValue(bean: loginAuthInstance, field: "user")}</td>
                        
                            <td>${fieldValue(bean: loginAuthInstance, field: "pass")}</td>
                        
                            <td>${fieldValue(bean: loginAuthInstance, field: "idReset")}</td>
                        
                            <td>${fieldValue(bean: loginAuthInstance, field: "pregunta")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${loginAuthInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
