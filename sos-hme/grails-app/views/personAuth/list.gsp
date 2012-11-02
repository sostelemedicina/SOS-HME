
<%@ page import="authorization.PersonAuth" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'personAuth.label', default: 'PersonAuth')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'personAuth.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="purpose" title="${message(code: 'personAuth.purpose.label', default: 'Purpose')}" />
                        
                            <th><g:message code="personAuth.person.label" default="Person" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${personAuthInstanceList}" status="i" var="personAuthInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${personAuthInstance.id}">${fieldValue(bean: personAuthInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: personAuthInstance, field: "purpose")}</td>
                        
                            <td>${fieldValue(bean: personAuthInstance, field: "person")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${personAuthInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
