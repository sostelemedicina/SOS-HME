<html>
  <body>
    <h1>Test: lista pares clave-valor</h1>
    
    <ul>
      <g:each in="${params.sort{ it.key }}" var="entry">
        <li>${entry.key}: ${entry.value}</li>
      </g:each>
    </ul>
  </body>
</html>