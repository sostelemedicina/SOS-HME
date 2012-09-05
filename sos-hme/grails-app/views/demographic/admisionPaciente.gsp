<%@ page import="java.text.SimpleDateFormat" %>
<html>
  <head>
    <meta name="layout" content="basicrecord" />
    <title><g:message code="demographic.admision_paciente.title" /></title>
    <style>
      label {
        display: block;
      }
    </style>
    
  </head>
  <body>
 <div id="menu1">
      <ul>
        <li>
        <g:link controller="records" action="list" ><g:message code="records.action.list" /></g:link>
        </li>
        <li>
        <g:link controller="demographic" action="admisionPaciente"  class="selected" ><g:message code="demographic.action.admisionPaciente" /></g:link>
        </li>

        <li>
        <g:link controller="reportes"><g:message code="reportes.Reportes"/></g:link>
        </li>
      </ul>
    </div>
  <div id="nivel1">

    <div id="menu2">
      <ul class="top_actions">
      <g:if test="${session.traumaContext.episodioId}">
        <li><g:link controller="records" action="show" id="${session.traumaContext.episodioId}" class="home"><g:message code="demographic.lista_candidatos.action.backToEpisode" /></g:link></li>
      </g:if>

    </ul>

    </div>
    <div id="nivel2">

    <div id="contenido">
    <h1><g:message code="demographic.admision_paciente.title" /></h1>

    

  <g:compositionHasPatient episodeId="${session.traumaContext.episodioId}">
    <div style="color:red;">
      <g:message code="trauma.show.feedback.patientAlreadySelectedForThisEpisode" />
    </div><br/>
  </g:compositionHasPatient>

  <g:if test="${flash.message}">
    <div style="color:red;">
      <g:message code="${flash.message}" />
    </div>
  </g:if>

  <g:form action="findPatient" class="form1">
    <p><label for="identificador">
      <g:message code="persona.identificador" />
    </label>
    <g:textField name="identificador" value="${params.identificador}" />
    <g:select name="root" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" value="${((params.root) ? params.root : 'none')}" />
    </p>

    <p>
    <label for="primerNombre">
      <g:message code="persona.primerNombre" />
    </label>
    <g:textField name="personName.primerNombre" value="${params.('personName.primerNombre')}" />
    </p>
    <p>
    <label for="segundoNombre">
      <g:message code="persona.segundoNombre" />
    </label>
    <g:textField name="personName.segundoNombre" value="${params.('personName.segundoNombre')}" />
    </p>
    <p>
    <label for="primerApellido">
      <g:message code="persona.primerApellido" />
    </label>
    <g:textField name="personName.primerApellido" value="${params.('personName.primerApellido')}" />
    </p>
    <p>
    <label for="segundoApellido">
      <g:message code="persona.segundoApellido" />
    </label>
    <g:textField name="personName.segundoApellido" value="${params.('personName.segundoApellido')}" />
    </p>
    <p>
    <label for="fechaNacimiento">
      <g:message code="persona.fechaNacimiento" />
    </label>
    <g:datePicker name="fechaNacimiento" value="${((bd) ? bd : 'none')}" precision="day" noSelection="['':'']" />
    </p>

    <p>
    <g:submitButton name="doit" class="boton_submit" value="${message(code:'demographic.admision_paciente.buscar')}" />
    </p>
  </g:form>

    </div>
  </div>
</div>
</body>
</html>