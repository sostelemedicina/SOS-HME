<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="basic" />
    <title>Domain list</title>
  </head>
  <body>


    <div id="nivel1">
      <div id="nivel2">
      <g:if test="${flash.message}">
        <div class="message"><g:message code="${flash.message}"/></div>
      </g:if>

      <div class="bodydomainlist">
        <div id="listaDominio">
         
        
          <ul>
        <g:each in="${folders}" status="i" var="folder">
          <li>
         
            <g:link action="selectDomain" params="[path: folder.path]">
              <img src="${createLinkTo(dir: 'images', file: folder.path +'.gif')}" width="142" height="101" />
                ${folder.name.value}
            </g:link>
      
          </li>
        </g:each>
            
          </ul>
       </div>

      </div>
        </div>
    </div>
  </body>
</html>
