<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="converters.DateConverter" %>
<%
  // Formateador para las fechas
  SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
%>
<html>
  <head>
    <meta name="layout" content="basicrecord" />
    <title><g:message code="demographic.lista_candidatos.title" /></title>
    
  </head>
  <body>

<div id="menu1">
      <ul>
        <li>
        <g:link controller="records" action="list" ><g:message code="records.action.list" /></g:link>
        </li>
        <li>
        <g:link controller="demographic" action="admisionPaciente" class="selected"><g:message code="demographic.action.admisionPaciente" /></g:link>
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
      <li>
        <% // Pasarle los parametros para que no tenga que vea lo que ya ha ingresado en la busqueda.

        def search_params = [:]

        def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' ) // Si no vienen todos los datos, que sea null

        if (bd)
        {
          def bdstring = format.format(bd)
          search_params['fechaNacimiento'] = bdstring
        }

        if (params.identificador) search_params['identificador'] = params.identificador
        if (params.root) search_params['root'] = params.root
        if (params.personName.primerNombre) search_params['personName.primerNombre'] = params.personName.primerNombre
        if (params.personName.segundoNombre) search_params['personName.segundoNombre'] = params.personName.segundoNombre
        if (params.personName.primerApellido) search_params['personName.primerApellido'] = params.personName.primerApellido
        if (params.personName.segundoApellido) search_params['personName.segundoApellido'] = params.personName.segundoApellido

        %>

        <g:link action="admisionPaciente" class="back" params="${search_params}"><g:message code="demographic.lista_candidatos.action.admisionPaciente" /></g:link>
      </li>
      <%-- Solo se puede agregar un nuevo paciente si el repositorio es local --%>
      <g:if test="${ApplicationHolder.application.config.hce.patient_administration.serviceType.local}">


        <g:if test="${bd}">
        <li><g:link action="agregarPaciente" params="[primerNombre:params.personName.primerNombre,
                                                      segundoNombre:params.personName.segundoNombre,
                                                      primerApellido:params.personName.primerApellido,
                                                      segundoApellido:params.personName.segundoApellido,
                                                      identificador:params.identificador,
                                                      root:params.root,
                                                      fechaNacimiento:format.format(bd)]" class="create"><g:message code="demographic.lista_candidatos.action.agregarPaciente" /></g:link></li>
        </g:if>
        <g:else>
          <li><g:link action="agregarPaciente" params="[primerNombre:params.personName.primerNombre,
                                                      segundoNombre:params.personName.segundoNombre,
                                                      primerApellido:params.personName.primerApellido,
                                                      segundoApellido:params.personName.segundoApellido,
                                                      identificador:params.identificador,
                                                      root:params.root,
                                                      fechaNacimiento:'']" class="create"><g:message code="demographic.lista_candidatos.action.agregarPaciente" /></g:link></li>
        </g:else>

      </g:if>
    </ul>
    </div>

    <div id="nivel2">

      <div id="contenido">
    <h1><g:message code="demographic.lista_candidatos.title" /></h1>
    
   

    <g:if test="${flash.message}">
      <div style="color:red;">
        <g:message code="${flash.message}" />
      </div>
    </g:if>
    
    <table id="list"  width="100%" border="0" cellpadding="0" cellspacing="0"  class="tabla1">
      <tr>
        <th><g:message code="persona.identificadores" /></th>
        <th><g:message code="hce.service.candidatos.nombre" /></th>
        
        <th><g:message code="persona.fechaNacimiento" /></th>
        <th><g:message code="persona.sexo" /></th>
        <th><g:message code="demographic.lista_candidatos.label.acciones" /></th>
      </tr>
      <g:if test="${candidatos.size()==0}">
        <tr>
          <td colspan="8">
            <div class="feedback">
              <g:message code="demographic.lista_candidatos.noHayCandidatos" />
            </div>
          </td>
        </tr>
      </g:if>
      <g:else>
		<g:set var="ids" value="${idss}" />	
	      <g:each in="${candidatos}" var="persona">
	        <tr>
				<g:if test="${idss.size()!=0}">
					<td><g:render template="UIDBasedID" collection="${ids}" var="id"/></td>
				</g:if>
				<g:else>
					<td><g:render template="UIDBasedID" collection="${persona.ids}" var="id"/></td>
				</g:else>
	          <g:set var="name" value="${persona.identities.find{ it.purpose == 'PersonNamePatient'} }" />
	          <td>${name?.primerNombre} ${name?.segundoNombre} ${name?.primerApellido} ${name?.segundoApellido}</td>
	          
	          <%-- TODO: taglib --%>
	          <td><g:if test="${persona?.fechaNacimiento}"><g:formatDate date="${persona.fechaNacimiento}" format="${g.message(code: 'default.date.format1')}" /></g:if></td>
	          <td>${persona.sexo}</td>
	          <td>
	            <!-- Si la persona esta en la base tiene id pero si no (p.e. consulta a imp reomoto),
	                 necesito un identificador como cedula. -->
	            <%-- Si se usa la cedula u otro identificador y no se guarda la persona en la base local.
	            <g:set var="id" value="${persona.ids.toArray()[0]}" />
	            <g:if test="${id}">
	              <g:link action="seleccionarPaciente" params="[root:id.root, extension:id.extension]">
	                <g:message code="demographic.lista_candidatos.action.seleccionarPaciente" />
	              </g:link>
	            </g:if>
	            <g:else>
	              El paciente no tiene ningun identificador.
	            </g:else>
	            --%>
	            
                  
                    <g:link action="show" id="${persona.id}" class="boton2">
	              <g:message code="demographic.show.title" />
	            </g:link>  
                    <g:compositionHasNotPatient episodeId="${session.traumaContext.episodioId}">
                    | <g:link action="seleccionarPaciente" id="${persona.id}" class="boton2">
	              <g:message code="demographic.lista_candidatos.action.seleccionarPaciente" />
	            </g:link>
                    </g:compositionHasNotPatient>
                   


	          </td>
	        </tr>
	      </g:each>
	    </g:else>
    </table>

    </div>
  </div>
 </div>
    
  </body>
</html>