<%@ page import="authorization.LoginAuth" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <meta name="layout" content="lostPassword" />
    <title>Sample title</title>
    <style>
 
      .userlogin input {
        color:#4077ab;
        font-size:18px;
        padding:10px;
        margin-bottom:15px;
        width:25%;
        text-align: center;}
      .boton1 {    /* Boton con fondo amarillo degradado  */
        display:inline-block;
        padding: 5px 10px;
        background:#f5cb59 url(../images/bkg_boton1.gif) repeat-x;
        background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#f4e2b3), to(#f5c645));
        background-image: -webkit-linear-gradient(top, #f4e2b3, #f5c645);
        background-image: -moz-linear-gradient(top, #f4e2b3, #f5c645);
        background-image: -ms-linear-gradient(top, #f4e2b3, #f5c645);
        background-image: -o-linear-gradient(top, #f4e2b3, #f5c645);
        color:#3f3f3f;
        border:solid 1px #dddddd;
        text-decoration:none;
        margin: 5px;
        font-weight: bold;
        cursor: pointer;
      }

      .boton1 img {
        padding-right:5px;
        border:none;}
      .boton1:hover {
        background:#ebb420;}
      .linkH1 a{
        
        text-decoration-line: underline !important;
      }

    
    </style>  
  </head>
  <body>
    <div id="cuerpo">
      <g:if test="${flash.message}">
      <div class="message"><g:message code="${flash.message}"/></div>
      </g:if>
   <h1>Restablecer Contraseña</h1><br />
     <g:hasErrors bean="${loginAuth}">
            <div class="errors">
                <g:renderErrors bean="${loginAuth}" as="list" />
            </div>
     </g:hasErrors>
           
     <g:if test="${result == 1}" >
       
       <g:form id="form1" url="[controller:'loginAuth',action:'restablecerPassword']" method="post" >
        <!-- <input type="hidden" name="idReset" value="${idReset}"/>-->
       
       <label>${loginAuth.user} ingrese su nueva contraseña</label>
        <div id="userlogin" class="userlogin">
          <g:passwordField id="newPassword" name="pass" type="text" value="" class="password"/>
        
        </div>
       <label>Repita su nueva contraseña</label> 
        <div id="userlogin" class="userlogin"> 
          <g:passwordField id="repeatPassword" name="pass2" type="text" value="" class="password email" />
      
        </div>
        <div id="enviarBoton" class="enviarBoton">
       <input id="doit" type="submit"  name="doit" class="boton1" value="${message(code:'loginAuth.lostPassword.enviar')}" />
       </div>
      
        <input id="user" type="hidden"  name="id" value="${loginAuth.id}" />
      
        </g:form> 
         
     </g:if>
     <g:else>
       
       
     </g:else>  
  </div>            
  </body>
</html>
     