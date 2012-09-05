<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>

<g:if test="${!conexionImp}">
  <p><g:message code="service.imp.conexionImp.false" /></p>
</g:if>

<g:elseif test="${!result}">

  <p><g:message code="service.imp.pacientesCoincidentes.false" /></p>
</g:elseif>
<g:else>


  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla1">

    <tr>
<%--  <th>ID Paciente</th> --%>

      <th><g:message code="hce.service.candidatos.idOrganizacion" /></th>

    <th><g:message code="hce.service.candidatos.identificadores" /></th>

    <th><g:message code="hce.service.candidatos.nombre" /></th>
    
    <th>Fecha Nacimiento</th>

    <th><g:message code="hce.service.candidatos.acciones" /></th>

    </tr>

    <g:each in="${result}" var="paciente">
      <tr>
<%-- <td>${paciente.idPaciente}</td>--%>
        
        <td>${paciente.nombreCentro}</td>


        <td>
      <g:if test="${paciente.cedula}">
${paciente.cedula} [CI] <br/>
      </g:if>

      <g:if test="${paciente.pasaporte}">
${paciente.pasaporte}[Pasaporte] </td>
      </g:if>

      <td>
        
        <a class="ficha iframe" href="${ApplicationHolder.application.config.service.serverURL}/imp-cda/imagenPaciente/imagen?idPaciente=${paciente.idPaciente}&idOrg=${paciente.idCentro}"><img src="${ApplicationHolder.application.config.service.serverURL}/imp-cda/imagenPaciente/imagen?idPaciente=${paciente.idPaciente}&idOrg=${paciente.idCentro}" style="width: 30px; height: auto;"/></a>

        ${paciente.primerNombre} ${paciente.segundoNombre} ${paciente.primerApellido} ${paciente.segundoApellido}
        </td>
        <td>
          <%
                if(paciente.fechaNacimiento!=""){
                StringTokenizer token = new StringTokenizer(paciente.fechaNacimiento,"-")
                String fecha = ""
                String ano= token.nextToken()
                String mes= token.nextToken()
                String dia= token.nextToken()
                fecha = dia+"-"+mes+"-"+ano
                print fecha
                }
          %>

        </td>  



		<td><div class="addImpRelation"><g:link controller="service"
			action="agregarRelacionPaciente"
			params="[idCentroImp: paciente.idCentro,idPacienteImp: paciente.idPaciente ,idPacienteOrg: idPacienteOrg]"
			class="boton2">Seleccionar</g:link>
			<%--datos necesarios para la funcion invocada desde la modal.--%>
			<input id="param1" value="${paciente.idCentro}" style="display:none;" >
			<input id="param2" value="${paciente.idPaciente}" style="display:none;" >
			<input id="param3" value="${idPacienteOrg}" style="display:none;" >
			</div>
		</td>

      </tr>
    </g:each>
  </table>
  
<script>
jQuery(function ($) {

			$(".addImpRelation").click(function (e) {
				e.preventDefault();	
				
				/*coloca los datos dentro del objeto clikeado y los coloca en input ocultos
				dentro de la ventana modal.*/
				var a1 = jQuery(this).children("#param1").val();
				jQuery('#idaddr1').val(a1);
				var a2 = jQuery(this).children("#param2").val();
				jQuery('#idaddr2').val(a2);
				var a3 = jQuery(this).children("#param3").val();
				jQuery('#idaddr3').val(a3);
				
				
				$("#osx-modal-content-addImpRelation").modal();
			});
		});
</script>

  <util:remotePaginate controller="service"
                       action="buscarPaciente"
                       params="[id: idPacienteOrg]"
                       total="${total}"
                       update="[success: 'resultadoCandidatos', failure: 'errorResultadoCandidatos']"
                       max="10"
                       />

</g:else>