<%@ page contentType="text/html;charset=UTF-8" %>
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
  
      <h1 class="linkH1"><a href="#">Responda la pregunta secreta<a></h1>
  
    <h1>ó</h1>
  
    <h1>  <p><g:message code="loginAuth.lostPassword.ingreseEmail"/></p></h1>
     <p><g:message code="loginAuth.lostPassword.mensaje"/></p><br />
    <g:form id="form1" url="[controller:'loginAuth',action:'sendEmailLink']" method="post" >
     
     <div id="userlogin" class="userlogin">
       <input id="userEmail" type="text"  name="userEmail" class="email" value="${message(code:'loginAuth.lostPassword.email')}" onmousedown="javascript:this.value='';"/>
     </div>
      

       
      <div id="enviarBoton" class="enviarBoton">
          <input id="doit" type="submit"  name="doit" class="boton1" value="${message(code:'loginAuth.lostPassword.enviar')}" />
     </div>

    
  </g:form>  
    </div>
  </body>
</html>
