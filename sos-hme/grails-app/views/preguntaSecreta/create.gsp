

<%@ page import="authorization.PreguntaSecreta" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <%--<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            
        </div>
        --%>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${preguntaSecretaInstance}">
            <div class="errors">
                <g:renderErrors bean="${preguntaSecretaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pregunta"><g:message code="preguntaSecreta.pregunta.label" default="Pregunta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: preguntaSecretaInstance, field: 'pregunta', 'errors')}">
                                    <g:textField name="pregunta" value="${preguntaSecretaInstance?.pregunta}" class="pregunta"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
                
                </div>
            </g:form>
        </div>
    </body>
</html>
