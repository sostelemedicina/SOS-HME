<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
        <meta name="layout" content="main" />
        <title>Template list</title>
    </head>
    <body>
        <div class="nav">
            <g:link action="unloadAll">Unload all</g:link>
            <%--
            <span class="menuButton"><a class="home" href="\${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New ${className}</g:link></span>
            --%>
        </div>
        <div class="body">
            <h1>Template list</h1>
            <g:if test="${flash.message}">
              <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>NAME</th>
                      <th>UTILIZADO</th>
                      <th>ACCIONES</th>
                    </tr>
                  </thead>
                  <tbody>
                  <g:each in="${templateMap.values()}" status="i" var="template">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                      <td>${template.id}</td>
                      <td>${template.name}</td>
                      <td>${lastUseList[template.id]}</td>
                      <td>
                        <g:link class="delete" action="unload" id="${template.id}">[bajar]</g:link>
                      </td>
                    </tr>
                  </g:each>
                  </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
