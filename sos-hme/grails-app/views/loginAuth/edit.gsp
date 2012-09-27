

<%@ page import="authorization.LoginAuth" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'loginAuth.label', default: 'LoginAuth')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${loginAuthInstance}">
            <div class="errors">
                <g:renderErrors bean="${loginAuthInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${loginAuthInstance?.id}" />
                <g:hiddenField name="version" value="${loginAuthInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="purpose"><g:message code="loginAuth.purpose.label" default="Purpose" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'purpose', 'errors')}">
                                    <g:textField name="purpose" value="${loginAuthInstance?.purpose}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="user"><g:message code="loginAuth.user.label" default="User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'user', 'errors')}">
                                    <g:textField name="user" maxlength="20" value="${loginAuthInstance?.user}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="loginAuth.pass.label" default="Pass" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'pass', 'errors')}">
                                    <g:textField name="pass" maxlength="120" value="${loginAuthInstance?.pass}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="idReset"><g:message code="loginAuth.idReset.label" default="Id Reset" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'idReset', 'errors')}">
                                    <g:textField name="idReset" value="${loginAuthInstance?.idReset}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pregunta"><g:message code="loginAuth.pregunta.label" default="Pregunta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'pregunta', 'errors')}">
                                    <g:select name="pregunta.id" from="${authorization.PreguntaSecreta.list()}" optionKey="id" value="${loginAuthInstance?.pregunta?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="person"><g:message code="loginAuth.person.label" default="Person" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'person', 'errors')}">
                                    <g:select name="person.id" from="${demographic.party.Person.list()}" optionKey="id" value="${loginAuthInstance?.person?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="repuesta"><g:message code="loginAuth.repuesta.label" default="Repuesta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'repuesta', 'errors')}">
                                    <g:textField name="repuesta" value="${loginAuthInstance?.repuesta}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
