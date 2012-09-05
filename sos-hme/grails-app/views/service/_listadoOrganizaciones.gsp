<g:if test="${!conexionImp}">

          <p><g:message code="service.imp.conexionImp.false" /></p>
</g:if>

<g:elseif test="${!result}">

  <p><g:message code="service.imp.pacientesCoincidentes.false" /></p>
</g:elseif>
      <g:else>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla1">

          <tr>
          <th><g:message code="hce.service.listadoOrganizaciones.nombre" /></th>
          <th><g:message code="hce.service.listadoOrganizaciones.acciones" /></th>


          </tr>

          <g:each in="${result}" var="org">
	        <tr>
                  <td>${org.nombre}</td>

                  <td width="25%" scope="col"><g:link controller="service" action="buscarCdaByPacienteAndOrganizacion" params="[org:org.numeroOrg ,pac: pacienteId]" class="boton2"><g:message code="hce.cda.verCda" /> </g:link></td>

                </tr>
	      </g:each>
        </table>
      </g:else>

