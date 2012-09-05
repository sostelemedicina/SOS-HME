<html>
  <body>
  <h1>Listar template</h1>
  
  <ul>
    <g:each in="${templateNames}" var="template">
      <li><g:link action="generarTemplate" params="[templateId:template]">${template}</g:link></li>
    </g:each>
  </ul>
  
  </body>
</html>