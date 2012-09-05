<html>
<body>
<h1>Listar arquetipos</h1>

<ul>
  <g:each in="${arqNames}" var="arq">
    <li><g:link action="generar" params="[archetypeId:arq]">${arq}</g:link></li>
  </g:each>
</ul>

</body>
</html>