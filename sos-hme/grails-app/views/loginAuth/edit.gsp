

<%@ page import="authorization.LoginAuth" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'loginAuth.label', default: 'LoginAuth')}" />
        <title><g:message code="loginAuth.edit.title" /></title>
	<script type="text/javascript">
		function replaceT(obj){
		var newO=document.createElement('input');
		newO.setAttribute('type','password');
		newO.setAttribute('name',obj.getAttribute('name'));
		newO.setAttribute('class','userlogin')
		obj.parentNode.replaceChild(newO,obj);
		newO.focus();}
	</script>
    </head>
    <body>
        <div class="body">
            <h2><g:message code="loginAuth.edit.title"/></h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${loginAuthInstance}">
            <div class="errors">
                <g:renderErrors bean="${loginAuthInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" action="update" >
                <g:hiddenField name="id" value="${loginAuthInstance?.id}" />
                <g:hiddenField name="version" value="${loginAuthInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <!--<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="purpose"><g:message code="loginAuth.purpose.label" default="Purpose" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'purpose', 'errors')}">
                                    <g:textField name="purpose" value="${loginAuthInstance?.purpose}" />
                                </td>
                            </tr>-->

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="user"><g:message code="loginAuth.user.label" default="Nombre de usuario" />:</label>
                                </td>
                                <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "user")}</td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="loginAuth.pass.label" default="Nueva Clave" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'pass', 'errors')}">
                                    <g:passwordField name="pass" type="text" value="" />
                                </td>
                            </tr>


                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="loginAuth.pass2.label" default="Confirmar Clave" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loginAuthInstance, field: 'pass', 'errors')} ${hasErrors(bean: loginAuthInstance, field: 'pass2', 'errors')}">
                                    
				    <g:passwordField name="pass2" type="text" value="" />
                                </td>
                            </tr>
                            
                              <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="loginAuth.preguntaSecreta.label" default="Pregunta secreta" />:</label>
                                </td>
                                <td valign="top" class="value">
                                  					
                                  <g:select name="pregunta" from="${listaPreguntas}" noSelection="${['':'']}" optionKey="id" optionValue="pregunta" value="${loginAuthInstance?.preguntaSecreta?.id}"/>
                                                                        
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="loginAuth.respuestaSecreta.label" default="Respuesta secreta" />:</label>
                                </td>
                                <td valign="top" class="value">
                                    
					 <g:textField name="respuesta" type="text" value="${loginAuthInstance?.respuesta}" />			
                                        
                                                                        
                                </td>
                            </tr>
                            

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="person"><g:message code="loginAuth.person.label" default="Person" />:</label>
                                </td>
                                <td valign="top" class="value"><g:link controller="person" action="show" id="${loginAuthInstance?.person?.id}">${loginAuthInstance?.person?.identities?.asList().first()}</g:link></td>
                            </tr>
                        

                        
                        </tbody>
                    </table>
                </div>
                <g:hiddenField name="person.id" value="${loginAuthInstance?.person?.id}" />
                <div class="buttons">
                  <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.update.label', default: 'Update')}" /></span>
               <g:canFillAdmin> 
                  <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                  <span class="button"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
                  <span class="button"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
               </g:canFillAdmin>    
                </div>
            </g:form>
        </div>
    </body>
</html>
