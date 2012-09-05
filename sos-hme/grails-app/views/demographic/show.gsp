<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.directory.Folder" %>
<html>
  <head>
<%-- <meta name="layout" content="ehr-modal" /> --%>
<g:javascript library="jquery" />
  <title><g:message code="demographic.show.title" /></title>
  <g:javascript>

    $(document).ready( function() {


		$('.message').delay(3000).fadeOut();
    });
    function cargando(etiqueta){
		
		$(etiqueta).html("<img src='${createLinkTo(dir:"images", file:"spinner.gif")}'/> Cargando...");
    }

	function replaceT(obj){
		var newO=document.createElement('input');
		newO.setAttribute('type','password');
		newO.setAttribute('name',obj.getAttribute('name'));
		newO.setAttribute('class','userlogin')
		obj.parentNode.replaceChild(newO,obj);
		newO.focus();
	}

	function focused(obj){
		jQuery(obj).val('');
	
	}

  
  </g:javascript>

  <script type="text/javascript" src="${createLinkTo(dir:'js/fancybox', file:'jquery.mousewheel-3.0.4.pack.js')}"></script>
  <script type="text/javascript" src="${createLinkTo(dir:'js/fancybox', file:'jquery.fancybox-1.3.4.js')}"></script>
  <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'js/fancybox', file:'jquery.fancybox-1.3.4.css')}" media="screen" />
  <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/', file:'estilo.css')}"/>

  
<%--carga de codigos js y css para ventana modal--%>

<link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/', file:'basic.css')}"/>

<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'jquery.simplemodal.js')}"></script>

<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'basic.js')}"></script>





  <g:javascript>
		$(document).ready(function() {
    $('.pestana').hide();
	
    <%
    if(params.pestana){
    %>
    $('#${params.pestana}').show();
    $('#ficha${params.pestana}').addClass('selected');

<%
}else{
%>
    $('#pestanaRegistrosInternos').show();
    <%
    }
    %>


			/*
			*   Examples - images
			*/
    // alert('hola');
    $(".ficha").fancybox({
    ajax : {
    type	: "GET"

    },
    'transitionIn'	: 'elastic',
			'transitionOut'	: 'elastic'
    });

    $('.ping').click(function(){
    $('.ping').removeClass('selected');
    $(this).addClass('selected');
    });

    });
	

	
	
    function cambio(pestana){


		$('.pestana').hide();
		$(pestana).show();

    }


  </g:javascript>

	
	<g:javascript>
		function validImp( user, pass, fin){
			${remoteFunction( 
			controller:'demographic', 
			action:'impValidatee', 
			update:'osx-modal-content', 
			params:'\'id=\' + user + \'-\'+ pass + \'-\' + fin')}


		}
		
		
	</g:javascript>
</head>
<body>

<div id='salida'>
</div>
	<div id='content'>
		

		
		<div id="osx-modal-content-addImpPatient">
			<div class="close"><a href="#" class="simplemodal-close">x</a></div>
			<div id="osx-modal-data">
				<h2>SOS Historias Medicas</h2>
				<h4>Por favor identif&iacute;quese como m&eacute;dico:</h4>
				<form action="/sos/demographic/impValidate" method="post">
					<input name="idaddp" id="idaddp" value="${persona.id}" style="display:none;" >
					<input name="actionType" id="actionType" value="addImpPatient" style="display:none;">
					<div id="userlogin" class="userlogin">
						<input type="text" id="user" name="user" class="userlogin" value="Usuario" onfocus="focused(this)"/>
					</div>
					<div id="passlogin" class="userlogin">
						<input name="pass" type="text" value="Contrase&ntilde;a" class="userlogin" onfocus="replaceT(this)"/>
					</div>
					
					<div id="ingresarboton" class="ingresarboton">
						<%-- i18n --%>
						<input type="submit" name="doit" id="doit" value="Confirmar" class="buttonlogin"/>
					</div>
				</form>
				
			</div>
		</div>
		
		
		<div id="osx-modal-content-delImpPatient">
			<div class="close"><a href="#" class="simplemodal-close">x</a></div>
			<div id="osx-modal-data">
				<h2>SOS Historias Medicas</h2>
				<h4>Por favor identif&iacute;quese como m&eacute;dico:</h4>
				<form action="/sos/demographic/impValidate" method="post">
					<input name="iddelp" id="iddelp" value="${persona.id}" style="display:none;" >
					<input name="actionType" value="delImpPatient" style="display:none;" >
					<div id="userlogin" class="userlogin">
						<input type="text" id="user" name="user" class="userlogin" value="Usuario" onfocus="focused(this)"/>
					</div>
					<div id="passlogin" class="userlogin">
						<input name="pass" type="text" value="Contrase&ntilde;a" class="userlogin" onfocus="replaceT(this)"/>
					</div>
					<div id="ingresarboton" class="ingresarboton">
						<%-- i18n --%>
						<input type="submit" name="doit" id="doit" value="Confirmar" class="buttonlogin"/>
					</div>
				</form>
			</div>
		</div>	

		<%-- esta ventana modal se invoca desde el template candidatos en views/service --%>
		<div id="osx-modal-content-addImpRelation" style="display:none;">
			<div class="close"><a href="#" class="simplemodal-close">x</a></div>
			<div id="osx-modal-data">
				<h2>SOS Historias Medicas</h2>
				<h4>Por favor identif&iacute;quese como m&eacute;dico:</h4>
				<form action="/sos/demographic/impValidate" method="post">
					<input name="idaddr1" id="idaddr1" value="${persona.id}" style="display:none;">
					<input name="idaddr2" id="idaddr2" value="${persona.id}" style="display:none;">
					<input name="idaddr3" id="idaddr3" value="${persona.id}" style="display:none;">
					<input name="actionType" value="addImpRelation" style="display:none;">
					<div id="userlogin" class="userlogin">
						<input type="text" id="user" name="user" class="userlogin" value="Usuario" onfocus="focused(this)"/>
					</div>
					<div id="passlogin" class="userlogin">
						<input name="pass" type="text" value="Contrase&ntilde;a" class="userlogin" onfocus="replaceT(this)"/>
					</div>
					<div id="ingresarboton" class="ingresarboton">
						<input type="submit" name="doit" id="doit" value="Confirmar" class="buttonlogin"/>
						
					</div>
				</form>
			</div>
		</div>	

		
		<div id="osx-modal-content-delImpRelation">
			<div class="close"><a href="#" class="simplemodal-close">x</a></div>
			<div id="osx-modal-data">
				<h2>SOS Historias Medicas</h2>
				<h4>Por favor identif&iacute;quese como m&eacute;dico:</h4>
				<form action="/sos/demographic/impValidate" method="post">
					<input name="iddelr" id="iddelr" value="${persona.id}" style="display:none;">
					<input name="actionType" value="delImpRelation" style="display:none;">
					<div id="userlogin" class="userlogin">
						<input type="text" id="user" name="user" class="userlogin" value="Usuario" onfocus="focused(this)"/>
					</div>
					<div id="passlogin" class="userlogin">
						<input name="pass" type="text" value="Contrase&ntilde;a" class="userlogin" onfocus="replaceT(this)"/>
					</div>
					<div id="ingresarboton" class="ingresarboton">
						<input type="submit" name="doit" id="doit" value="Confirmar" class="buttonlogin"/>
						
					</div>
				</form>
			</div>
		</div>
		
	</div>
	

<g:set var="person_id" value="${persona.id}" />
<%--<input id="person_id2" value="${persona.id}" > style="display:none;"--%>
<g:set var="name" value="${persona.identities.find{ it.purpose == 'PersonNamePatient'} }" />
<%--<input id="bla" value="${accesoValido}" >--%>

<div id="cabecera">
  <div id="cabColI">
    <div id="logo">
      <h1><img src="${createLinkTo(dir:'images' ,file:'SOS.gif')}" alt="SOS" width="97" height="53" align="texttop" />Historias Médicas</h1>
    </div>
    <div id="breadcrumbs">
      <g:link controller="domain" action="list" class="contextoEhr"><g:message code="domain.action.list" /></g:link>
      <g:set var="folder" value="${Folder.findByPath(session.traumaContext.domainPath)}" />
${folder.name.value}

    </div>
  </div>
  <div id="cabColD">
    <div id="infoSec"><g:formatDate date="${new Date()}" formatName="default.date.format.text" /> &nbsp; | &nbsp; Cambiar idioma
      <g:langSelector>
        <g:if test="${(session.locale.getLanguage()!=it)}">

          <a href="?sessionLang=${it}&templateId=${params.templateId}" class="contextoEhr"><img src="${createLinkTo(dir:'images' ,file:'ico_'+it+'.jpg')}" alt="${message(code:'common.lang.'+it)}" width="25" height="34" hspace="2" border="0" align="absmiddle" /></a>
        </g:if>
      </g:langSelector>

    </div>
    <div id="infoLogin">
      <g:datosUsuario userId="${userId}" /> &nbsp; | &nbsp;

      <g:link controller="authorization" action="logout" class="contextoEhr"><g:message code="authorization.action.logout" /></g:link>
    </div>

  </div>
</div>
<div id="menu1">
  <ul>
    <li>
      <a href="#" class="selected contextoEhr"><g:message code="demographic.show.paciente" /></a>
    </li>
    <li>
    <g:link controller="records" action="list" class="contextoEhr"><g:message code="records.action.list" /></g:link>
    </li>
    <li>
    <g:link controller="demographic" action="admisionPaciente" class="contextoEhr"><g:message code="demographic.action.admisionPaciente" /></g:link>
    </li>

    <li><g:link controller="reportes" action="index" class="contextoEhr"><g:message code="reportes.Reportes"/></a></g:link></li>
  </ul>
</div>
<div id="nivel1">
  <div id="detallePaciente">

<%-- Preguntar primero si tiene foto--%>

    <g:if test="${!name || !name.foto || !name.tipofoto}">

      <g:if test="${persona.sexo=='Masculino'}">

        <p><img src="${createLinkTo(dir:"images", file:"man.png")}" width="122" height="124" alt="Nombre Paciente" /></p>
      </g:if>
      <g:else>

        <p><img src="${createLinkTo(dir:"images", file:"woman.png")}" width="122" height="124" alt="Nombre Paciente" /></p>
      </g:else>
    </g:if>
    <g:else>


      <p><img src="${createLink(controller:"demographic", action: 'fotopaciente', params:[persona:person_id])}" width="122" height="124" alt="Nombre Paciente" /></p>
    </g:else>


    <h1><g:message code="demographic.show.title"/></h1>
    <p><span class="etiqueta">Nombre:</span> ${name.toString()} </p>
    <p><span class="etiqueta">Sexo:</span> ${persona.sexo}</p>
    <p><span class="etiqueta">Ids:</span>  <g:render template="UIDBasedID" collection="${persona.ids}" var="id" /></p>
    <p><span class="etiqueta">Fecha Nac.:</span> <g:if test="${persona?.fechaNacimiento}"><g:formatDate date="${persona.fechaNacimiento}" format="${g.message(code: 'default.date.format1')}" /></g:if></p>

  </div>

  <g:compositionHasPatient episodeId="${session.traumaContext.episodioId}">
    <div class="message aviso">
      <ul><li>
      <g:message code="trauma.show.feedback.patientAlreadySelectedForThisEpisode" />
      </li></ul>
    </div>
      
    <br/>
  </g:compositionHasPatient>

  <g:if test="${flash.message}">
    <div id="message" class="message ${flash.clase}">
      <ul><li>
      <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}" />
      </li></ul>
    </div>
  </g:if>





  <div id="menu2">

    <ul class="top_actions">

      <g:compositionHasPatient episodeId="${session.traumaContext.episodioId}">
        <li>
        <g:link controller="records" action="show" id="${session.traumaContext.episodioId}" class="home"><g:message code="demographic.lista_candidatos.action.backToEpisode" /></g:link>
        </li>
      </g:compositionHasPatient>
      <li>
      <g:link controller="records" action="create" params="[root:root, extension:extension]" class="create"><g:message code="demographic.show.action.createEpisode" /></g:link>
      </li>
      <li>
        <g:link controller="demographic" action="edit" params="[id:person_id]" class="create"><g:message code="demographic.show.action.editPatient" /></g:link>
      </li>

    </ul>

  </div>





  <div id="nivel2">
    <div id="menu4">
      <ul>

        <li><a  id="fichapestanaRegistrosInternos" class="ping" href="javascript:cambio('#pestanaRegistrosInternos')" ><g:message code="service.imp.registrosInternos" /></a></li>
        
         <g:if test="${conexionImp && agregadoImp}">
        <li><a id="fichapestanaRegistrosExternos" class="ping" href="javascript:cambio('#pestanaRegistrosExternos')"><g:message code="service.imp.registrosExternos" /></a></li>
        <li><a id="fichapestanaOrganizaciones" class="ping" href="javascript:cambio('#pestanaOrganizaciones')"><g:message code="service.imp.listadoOrganizaciones" /></a></li>
        </g:if>
        <li><g:canFillClinicalImp><a id="fichapestanaOpcionesImp" class="ping" href="javascript:cambio('#pestanaOpcionesImp')" ><g:message code="demographic.show.opcionesImp" /></a></li>
			</g:canFillClinicalImp>
	  </ul>
    </div>






    <div id="contenido">

      <div id="pestanaOpcionesImp" class="pestana" >




        <g:if test="${conexionImp}">
          <div class="filtros">
<%-- DATOS OPCIONES DE IMP--%>

            <div id="imp">




				<g:if test="${!agregadoImp}">
					<div id="addPatient"><g:link controller="service" action="agregarPaciente" params="[id: person_id]" class="boton1"><g:message code="service.imp.agregarPaciente" /></g:link></div>
				</g:if>
				<g:else>
					<div id="delPatient"><g:link controller="service" action="eliminarPaciente" params="[id: person_id]" class="boton1"><img src="${createLinkTo(dir:'images/', file:'ico_eliminar.png')}" alt="(+)" width="16" height="16" align="absmiddle" /><g:message code="service.imp.eliminarPaciente" /></g:link>
					
					</div>

						
						<g:if test="${!relacionadoImp}">
						<div id="addRelPac">
											<g:remoteLink controller="service"
											action="buscarPaciente"
											params="[id: person_id, offset: '0']"
											update="[success:'resultadoCandidatos',failure:'errorResultadoCandidatos']"
											onLoading="cargando('#resultadoCandidatos')"
											class="boton1"><img src="${createLinkTo(dir:'images/', file:'ico_agregar.png')}" alt="(+)" width="16" height="16" align="absmiddle" /><g:message code="demographic.show.agregarRelacionPaciente" /></g:remoteLink>
								
						</div>
						</g:if>
						<g:else>
							<div id="delImpRelation">
								<g:link controller="service" action="eliminarRelacionPaciente" params="[id: person_id]" class="boton1"><g:message code="service.imp.eliminarRelacionPaciente" /></g:link>
							</div>
						</g:else>
					
				</g:else>


            </div>






          </div>
		
		
		
			<div id="resultadoCandidatos">


			</div>

          <div id="errorResultadoCandidatos">


          </div>

        </g:if>
        <g:else>
          <p><g:message code="service.imp.conexionImp.false" /></p>
        </g:else>


      </div>

      <div id="pestanaRegistrosInternos" class="pestana" >

        <div class="filtros">

<%-- REGISTROS INTERNOS --%>
          <div id="registroInterno">

            <g:formRemote name="busquedaInterna"
                          url="[controller:'service',action:'busquedaInterna', params: [id: person_id,marca: 'fil']]"
                          update="[success: 'resultadoInterno', failure: 'errorResultadoInterno']"
                          onLoading="cargando('#resultadoInterno')">
              <g:remoteLink name="busquedaAllInterna"
                            url="[controller:'service',action:'busquedaAllInterna',params: [id: person_id, offset:'0', marca:'fil' ]]"
                            update="[success: 'resultadoInterno', failure: 'errorResultadoInterno']"
                            onLoading="cargando('#resultadoInterno')"
                            class="boton1"><g:message code="service.imp.todosRegistros" />

              </g:remoteLink>


              <label for="rangoFechas">
                <b> <g:message code="buscar.rango_fechas" /></b>
              </label>



              <label for="desde">
                <g:message code="buscar.desde" />
              </label>
              <g:datePicker name="desde" value="" precision="day" noSelection="['':'']" />



              <label for="hasta">
                <g:message code="buscar.hasta" />
              </label>
              <g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />


              <g:submitButton name="doit" value="${message(code:'buscar.filtro')}" class="boton1" />


            </g:formRemote>
          </div>

        </div>

        <div id="resultadoInterno"></div>
        <div id="errorResultadoInterno"></div>



      </div>

      <g:if test="${conexionImp && agregadoImp}">




        <div id="pestanaRegistrosExternos" class="pestana" >

<%-- REGISTROS EXTERNOS --%>
          <div class="filtros">
            <div id="registroExterno">

              <div id="busquedaExterna">


                <g:formRemote name="busquedaExterna"
                              url="[controller:'service',action:'busquedaExterna',params: [id: person_id, offset:0, marca: 'fil']]"
                              update="[success: 'resultadoExterno', failure: 'errorResultadoExterno']"
                              onLoading="cargando('#resultadoExterno')">

                  <g:remoteLink name="busquedaAllExterna"
                                url="[controller:'service',action:'busquedaAllExterna',params: [id: person_id, offset:'0', marca:'fil' ]]"
                                update="[success: 'resultadoExterno', failure: 'errorResultadoExterno']"
                                onLoading="cargando('#resultadoExterno')"
                                class="boton1"><g:message code="service.imp.todosCdas" /> </g:remoteLink>

                  <label for="rangoFechas">
                    <b> <g:message code="buscar.rango_fechas" /></b>
                  </label>



                  <label for="desde">
                    <g:message code="buscar.desde" />
                  </label>
                  <g:datePicker name="desde" value="" precision="day" noSelection="['':'']" />



                  <label for="hasta">
                    <g:message code="buscar.hasta" />
                  </label>
                  <g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />

                  <g:submitButton name="doit" type="submit" value="${message(code:'buscar.filtro')}" class="boton1" />
                </g:formRemote>




              </div>




            </div>



          </div>

          <div id="resultadoExterno" ></div>

          <div id="errorResultadoExterno"></div>





        </div>
        <div id="pestanaOrganizaciones" class="pestana">


<%-- ORGANIZACIONES RELACIONADAS--%>
          <div class="filtros">
            <div id="listadoOrganizaciones"  >



              <g:remoteLink controller="service"
                            action="listarOrganizaciones"
                            id="${person_id}"
                            update="[success:'resultadoOrganizaciones',failure:'errorResultadoOrganizaciones']"
                            on404="alert('not found');"
                            class="boton1"><g:message code="service.imp.verOrganizaciones" /></g:remoteLink>



            </div>

          </div>
          <div id="resultadoOrganizaciones">
          </div>
          <div id="errorResultadoOrganizaciones">
          </div>
        </div>

      </g:if>


    </div>
  </div>
  <p style="clear:both">&nbsp;</p>

</div>




</body>
</html>