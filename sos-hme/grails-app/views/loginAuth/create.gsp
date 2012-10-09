

<%@ page import="authorization.LoginAuth" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'loginAuth.label', default: 'LoginAuth')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
		




    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${loginAuthInstance}">
            <div class="errors">
                <g:renderErrors bean="${loginAuthInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                       <%-- <tr class="prop" >
                                <td valign="top" class="name">
                                    <label for="purpose"><g:message code="loginAuth.purpose.label" default="Purpose" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'purpose', 'errors')}">
                                    <g:textField name="purpose" value="${loginAuthInstance?.purpose}" />
                                </td> <g:message code="default.loginAuth.label" args="[entityName]" />
                            </tr> --%>
                        


                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user"><g:message code="loginAuth.user.label"/>:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'user', 'errors')}">
                                    
					<g:textField name="user" value="${loginAuthInstance?.user}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pass"><g:message code="loginAuth.pass.label"/>:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'pass', 'errors')}">
                                    <%--<input name="pass" type="text" value="${message(code:'auth.login.label.password')}" class="userlogin" onfocus="replaceT(this)"/>--%>
					<g:passwordField name="pass" type="text" value="" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="loginAuth.pass2.label" default="Confirmar Clave" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'pass2', 'errors')}">
                                    
					<g:passwordField name="pass2" type="text" value="" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="loginAuth.preguntaSecreta.label" default="Pregunta secreta" />:</label>
                                </td>
                                <td valign="top" class="value">
                                    
									
                                          <g:select name="pregunta" from="${listaPreguntas}" optionKey="id"/>
                                                                        
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="loginAuth.respuestaSecreta.label" default="Respuesta secreta" />:</label>
                                </td>
                                <td valign="top" class="value">
                                    
					 <g:textField name="respuesta" value="${loginAuthInstance.respuesta}" />			
                                        
                                                                        
                                </td>
                            </tr>
                            

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="person"><g:message code="loginAuth.person.label"/>:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'person', 'errors')}">
                                    <g:select name="person.id" from="${personUsers.asList()}" optionKey="id" optionValue="${it}"  />
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
