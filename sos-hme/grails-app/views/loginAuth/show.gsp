
<%@ page import="authorization.LoginAuth" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'loginAuth.label', default: 'LoginAuth')}" />
        <title><g:message code="loginAuth.user.show" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
          <g:canFillAdmin>  
          <h1><g:message code="loginAuth.user.show" /></h1>
          </g:canFillAdmin>
          <g:canNotFillAdmin>  
          <h1><g:message code="loginAuth.user.adminPrincipal" /></h1>
          </g:canNotFillAdmin>    
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <%--<tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "id")}</td>
                            
                        </tr>--%>
                    
                       <%-- <tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.purpose.label" default="Purpose" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "purpose")}</td>
                            
                        </tr>--%>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.user.label" default="Usuario" />:</td>

                            <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "user")}</td>

                        </tr>
                        <%--
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.pass.label" default="Clave" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "pass")}</td>
                            
                        </tr>
                    --%>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.person.label" default="Person" />:</td>
                            
                            <td valign="top" class="value"><g:link controller="person" action="show" id="${loginAuthInstance?.person?.id}">${loginAuthInstance?.person?.identities?.asList().first()}</g:link></td>
                            
                        </tr>
                    

                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
               <g:canFillAdmin> 
                 <g:form>
                    
                    <g:hiddenField name="id" value="${loginAuthInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
                    <span class="button"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
                </g:form>
                </g:canFillAdmin>
            </div>
        <g:canNotFillAdmin>
           <g:link class="list" controller="person" action="show" id="${loginAuthInstance?.person?.id}">
            <div class="nav caja">

              <h1><g:message code="loginAuth.user.edit" /></h1>

            </div>
          </g:link>  
            <g:link class="list" controller="loginAuth" action="edit" id="${loginAuthInstance?.id}">
            <div class="nav caja">

              <h1><g:message code="loginAuth.user.changePasswordAndQuestion" /></h1>

            </div>
          </g:link>
        </g:canNotFillAdmin>
          
        </div>
      
     
      
    </body>
</html>
