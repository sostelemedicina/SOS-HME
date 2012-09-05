<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.change_control.Version" %>
<%@ page import="hce.core.composition.Composition" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
  <head>
    <meta name="layout" content="basicregistro" />
    <title><g:message code="reportes.suite.title" /></title>
  </head>
  <body class="bodydomainlist">
    
    <div id="listadominios" class="listadominios">
        <g:message code="reportes.suite.title" />
    </div>
    <div id="menubar2" class="menubar2">
      <ul>
        <li>
          <g:link action="create" class="create"><g:message code="trauma.list.action.crearEpisodio" /></g:link>
        </li>
        <li>
          <g:link action="" class="find"><g:message code="trauma.list.action.buscarEpisodio" /></g:link>
        </li>
        <li>
          <g:link controller="demographic" action="admisionPaciente" class="find"><g:message code="demographic.admision_paciente.buscar_paciente" /></g:link>
        </li>
        <li>
          <g:link controller="demographic" action="reportIndex" class="find"><g:message code="reportes.link.title" /></g:link>
        </li>
      </ul>
    </div>
    <div id="contentid">
      <g:if test="${patient}">
        <%def i=0%>
        <div class="pacientereporte">
        <table style="margin-left: 0px">
          <tr>
            <td>cedula</td>
            <td>nombre completo</td>
            <td>direccion residencia</td>
            <td>ocupacion</td>
            <td>sexo</td>
            <td>edad</td>
          </tr>
            <g:each var="paciente" in="${patient}">
              <tr>
                <td>${paciente.ids.value[0].extension}</td>
                <td>
                 ${paciente.identities.primerNombre[0]}
                 ${paciente.identities.segundoNombre[0]}
                 ${paciente.identities.primerApellido[0]}
                 ${paciente.identities.segundoApellido[0]}
                </td>
                <td>${dircompleta[i]}</td>
                <td>${ocupacion[i]}</td>
                <td>${sexo[i]}</td>
                <td>${edad[i]}</td>
              </tr>   
          </g:each>
        </table>
        </div>
        <g:form controller="demographic" action="reportIndex" params="[id: person_id]">
          <br/>
          <label for="rangoFechas">
            <g:message code="buscar.rango_fechas" />
          </label>
          <br />
          <label for="desde">
            <g:message code="buscar.desde" />
          </label>
          <g:datePicker name="desde" value="" precision="day" noSelection="['':'']" />
          <br />
          <label for="hasta">
            <g:message code="buscar.hasta" />
          </label>
          <g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />
          <br/>
          <g:submitButton name="doit" value="${message(code:'buscar.filtro')}" />
        </g:form>
      </g:if>
    </div>
  </body>
</html>