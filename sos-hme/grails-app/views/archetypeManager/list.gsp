<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
        <meta name="layout" content="main" />
        <title>Archetype list</title>
    </head>
    <body>
        <div class="nav">
            <g:link action="unloadAll">Unload all</g:link>
        </div>
        <div class="body">
            <h1>Archetype list</h1>
            <g:if test="${flash.message}">
              <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                  <thead>
                    <tr>
                      <th>ARCHETYPE</th>
                      <th>UTILIZADO</th>
                      <th>ACCIONES</th>
                    </tr>
                  </thead>
                  <tbody>
                  <g:each in="${archetypeMap.keySet()}" status="i" var="archetypeId">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                      <td>${archetypeId}</td>
                      <td>${lastUseList[archetypeId]}</td>
                      <td>
                        <!-- 
                        <g:link class="delete" action="unload" id="${archetypeId}">[bajar]</g:link>
                        -->
                      </td>
                    </tr>
                  </g:each>
                  </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
