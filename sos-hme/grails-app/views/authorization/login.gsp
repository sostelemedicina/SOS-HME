<%@ page import="org.codehaus.groovy.grails.commons.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="login" />
    <title><g:message code="auth.login.title" /></title>
    <g:javascript library="prototype/prototype" />

    <link rel="stylesheet" href="${createLinkTo(dir:'css', file:'estilo.css')}" />
    <script type="text/javascript">
 function replaceT(obj){
   var newO=document.createElement('input');
   newO.setAttribute('type','password');
   newO.setAttribute('name',obj.getAttribute('name'));
   newO.setAttribute('class','userlogin')
   obj.parentNode.replaceChild(newO,obj);
   newO.focus();
 }
    </script>
  </head>
  <body class="login_fondo">

<%--    <div id="outer" class="outerlogin">
<ul class="langBar">
<g:langSelector>
<li ${(session.locale.getLanguage()==it)?'class="active"':''}>

<a href="?sessionLang=${it}"><g:message code="common.lang.${it}" /></a>

</li>
</g:langSelector>
</ul>
<br />
</div>
--%>
    <div id="cabLogin">
      <h1><img src="${createLinkTo(dir:'images',file:'sos_login.png')}" alt="SOS" width="163" height="95" align="absmiddle" />Historias Médicas</h1>
    </div>
    <div id="formLogin">
      <h2>Ingrese a SOS </h2>
      
        <g:if test="${flash.message}">
          <div class="error"><g:message code="${flash.message}" /></div>
        </g:if>
      

      <g:form url="[action:'login']" method="post" id="form1">


        <div id="userlogin" class="userlogin">

          <input type="text" id="user" name="user" class="userlogin" value="${message(code:'auth.login.label.userid')}" onmousedown="javascript:this.value='';"/>
        </div>

        <div id="passlogin" class="userlogin">

          <input name="pass" type="text" value="${message(code:'auth.login.label.password')}" class="userlogin" onfocus="replaceT(this)"/>

        </div>


        <div id="ingresarboton" class="ingresarboton">
          <input type="submit" name="doit" id="doit" value="${message(code:'auth.login.action.signin')}" class="buttonlogin"/>
        </div>


      </g:form>

    </div>


  </body>
</html>
