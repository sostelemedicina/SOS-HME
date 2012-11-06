<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.change_control.Version" %>
<%@ page import="hce.core.composition.Composition" %>
<%@ page import="hce.core.common.directory.Folder" %>
<!DOCTYPE html>
<html>
<g:set var="startmsec" value="${System.currentTimeMillis()}"/>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <META Http-Equiv="Cache-Control" Content="no-cache">
    <META Http-Equiv="Pragma" Content="no-cache">
    <META Http-Equiv="Expires" Content="0"> 
   <%--
    <g:javascript>
      // Para evitar el boton de volver del navegador.
      window.history.go(1);
    </g:javascript>
   --%>
   <%-- <title><g:layoutTitle/> | <g:message code="hce.nombre" /> | v${ApplicationHolder.application.metadata['app.version']}</title>
   --%>
 <title><g:message code="auth.login.welcome"/></title>
   
   <link rel="stylesheet" href="${createLinkTo(dir:'css' ,file:'estilo.css')}" />

  <r:require module="jquery-ui"/>
  <g:javascript library="jquery" />
  <jqui:resources themeCss="${createLinkTo(dir:'css/jquery' ,file:'jquery-ui-1.8.16.custom.css')}"/>
  <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery.form.js')}"></script>
  <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery-ui-i18n.min.js')}"></script>
  <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery-ui-timepicker-addon.js')}"> </script>
  <script type="text/javascript" src="${createLinkTo(dir:'js' ,file:'efectos.js')}"> </script>

  <script src="${createLinkTo(dir:'js/jquery/jqueryalert' ,file:'jquery.alerts.js')}"  type="text/javascript"></script>
  <link href="${createLinkTo(dir:'js/jquery/jqueryalert' ,file:'jquery.alerts.css')}" rel="stylesheet" type="text/css" media="screen" />

  
<%--carga de codigos js y css para ventana modal--%>

<link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/', file:'basic.css')}"/>
<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'jquery.simplemodal.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'basic.js')}"></script>

  
  <script type="text/javascript">
   
    // 'modificado' establece si los valores de la seccion han sido modificados
    var modificado = false;
    // 'secc' establece seccion actual
    var secc = '${section}';
    function deseaGuardar(){

      //jAlert("hola","titulo");
      //jConfirm('Desea guardar los cambios efectuados?',"tutulo");



     //confirm("Desea guardar los cambios efectuados?");
    }
    function guardadoAutomatico(seccion, generarShow, itemId){
      if(!modificado){
        if(generarShow == false){
        $(location).attr('href',"${createLink(controller:'records', action:'registroClinico2')}"+"?section="+seccion);
        return;
        }else{
          $(location).attr('href',"${createLink(controller:'guiGen', action:'generarShow')}"+"?id="+itemId);
          return;
        }
      }

      jConfirm('¿Desea guardar los cambios efectuados?',"Guardar",function(r){

      if(r){


            //SI generarShow es true, significa que el registro ya está guardado previamente
            //<input id="mode" type="hidden" name="mode" value="show" />
            if ($(".ehrform").length > 0){

                if($('#mode').val() =='edit'){

                $(".ehrform").append("<input type='hidden' name='autoSave' value='"+seccion+"' />");
                $(".ehrform").submit();
                }else if($('#mode').val() =='show'){

                  if(generarShow==true){
                      $(location).attr('href',"${createLink(controller:'guiGen', action:'generarShow')}"+"?id="+itemId);
                  }else{
                      $(location).attr('href',"${createLink(controller:'records', action:'registroClinico2')}"+"?section="+seccion);
                  }

                }else{
                  //AQUI DEBERIA ENTRAR CUANDO SE GUARDA POR PRIMERA VEZ
                  $(".ehrform").append("<input type='hidden' name='autoSave' value='"+seccion+"' />");
                   $(".ehrform").submit();
                }

            }else if(generarShow==true){
              $(location).attr('href',"${createLink(controller:'guiGen', action:'generarShow')}"+"?id="+itemId);
            }else{
              $(location).attr('href',"${createLink(controller:'records', action:'registroClinico2')}"+"?section="+seccion);
            }
        }else{


          $(location).attr('href',"${createLink(controller:'records', action:'registroClinico2')}"+"?section="+seccion);

        }




      });
      
    }





   $(document).ready(function(){
       //Capturar cambios realizados en el registro
       $('input, textarea, select').change(function() {
        modificado = true;
       });

       $('.contextoEhr').click(function(event){
         
         //si mode no existe, estamos fuera de contexto de alguna seccion
         //si mode es diferente a show es porque se está editando
         if(($('#mode').length >= 0) && ($('#mode').val() !='show')){
         
         //capturar el valor del href, desde el controlador se realizará en direccioamiento
          var href = $(this).attr('href');
          if(href !='#'){
          
             if(!modificado){
              return;
              }
              
              if(deseaGuardar()){
                //detener el vinculo
                event.preventDefault();

              //SI generarShow es true, significa que el registro ya está guardado previamente
              //<input id="mode" type="hidden" name="mode" value="show" />
                  if ($(".ehrform").length > 0){
                 // alert('guardando');
                  //pasar el valor del href al controlador
                  $(".ehrform").append("<input type='hidden' name='autoSaveHref' value='"+href+"' />");
                  $(".ehrform").submit();
                
                }
              }
          }
        }

     });

 

    $.datepicker.setDefaults($.datepicker.regional['${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}']);

        $(".DateSos").datepicker({dateFormat: 'dd-mm-yy',
        
                                     changeYear: true,
                                     //altField: '#actualDate',
                                     buttonText: 'Calendario',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(2100, 9, 15),
                                     minDate: new Date(1900, 9, 15),
                                     yearRange: '1900:2100',
                                     constrainInput: true,
                                     showButtonPanel: true,
                                     showOn: 'button'
                                     


        });
        $(".DateSos").attr("readonly",true);
        $(".DateSos").click(function(){
            $(this).val('');
        });

        $('.DateTimeSos').datetimepicker({dateFormat: 'dd-mm-yy',
          
          
                                     ampm: true,
                                     changeYear: true,
                                     buttonText: 'Calendario',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(2100, 9, 15),
                                     minDate: new Date(1900, 9, 15),
                                     yearRange: '1900:2100',
                                     showButtonPanel: true,
                                     showOn: 'button'

        });
        $(".DateTimeSos").attr("readonly",true);
        $(".DateTimeSos").click(function(){
            $(this).val('');
        });


     $('.clone').click(function(){
       //alert('clonando');
       var nodeToClone = $(this).parent().prev();
       var newNode = nodeToClone.clone();

    //--------------------Clonando DatePickers---------------------------------------//
       //Los datepicker dan problemas al ser clonados automaticamente
       //por eso tengo que clonarlos paso a paso
       newNode.find('.DateSos').each(function(i){
        var p = $(this).parent();
        p.find(".ui-datepicker-trigger").remove();
            var path = $(this).attr('name');
            $(this).after("<p> <input name='"+path+"' type='text' class='DateSos' /> </p>");
            p.children().find(".DateSos").datepicker({dateFormat: 'dd-mm-yy',
                                     changeYear: true,
                                     //altField: '#actualDate',
                                     buttonText: 'Calendario',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(),
                                     minDate: new Date(1900, 9, 15),
                                     yearRange: '1900:2100',
                                     constrainInput: true,
                                     showButtonPanel: true,
                                     showOn: 'button'
            });
          $(this).remove();
        });
      newNode.find('.DateTimeSos').each(function(i){
        var p = $(this).parent();
        p.find(".ui-datepicker-trigger").remove();
            var path = $(this).attr('name');
            $(this).after("<p> <input name='"+path+"' type='text' class='DateTimeSos' /> </p>");
            p.children().find(".DateTimeSos").datetimepicker({dateFormat: 'dd-mm-yy',
                                     ampm: true,
                                     changeYear: true,
                                     buttonText: 'Calendario',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(),
                                     minDate: new Date(1900, 9, 15),
                                     yearRange: '1900:2100',
                                     showButtonPanel: true,
                                     showOn: 'button'
          });
          $(this).remove();
        });
        //--------------------Clonando DatePickers---------------------------------------//

       //Blanquear text areas
       newNode.find( 'textarea' ).each(function(i){
        $(this).val('');
       });
       //Blanquear combos
       newNode.find( 'select' ).each(function(i){
        $(this).val('');
       });
       //Blanquear inputs
       newNode.find( 'input' ).each(function(i){
        $(this).val('');
       });




       newNode.insertAfter(nodeToClone);


      });

 });
  </script>

<g:javascript>
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

 <g:layoutHead />

  </head>

<body>
	<div id='content'>
		<div id="osx-modal-content-firmarEpisodio">
			<div class="close"><a href="#" class="simplemodal-close">x</a></div>
			<div id="osx-modal-data">
				<h2>SOS Historias Medicas</h2>
				<h4>Porfavor identif&iacute;quese como m&eacute;dico:</h4>
				<form action="/sos/records/signRecord" method="post">
					<input name="id" id="id" value="${episodeId}" style="display:none;"><%-- style="display:none;" --%>
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

		<div id="osx-modal-content-reabrirEpisodio">
			<div class="close"><a href="#" class="simplemodal-close">x</a></div>
			<div id="osx-modal-data">
				<h2>SOS Historias Medicas</h2>
				<h4>Porfavor identif&iacute;quese como m&eacute;dico:</h4>
				<%--<g:form url="[controller: 'records', action:'reopenRecord', id:episodioId]" method="post">--%>
				<form action="/sos/records/reopenRecord" method="post">
					<input name="id" id="id" value="${episodeId}" style="display:none;"><%-- style="display:none;" --%>
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
				<%--</g:form>--%>
			</div>
		</div>
	</div>

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
   	 <%-- <div id="infoSec"><g:formatDate date="${new Date()}" formatName="default.date.format.text" /> &nbsp; | &nbsp; Cambiar idioma
          <g:langSelector>
            <g:if test="${(session.locale.getLanguage()!=it)}">

            <a href="?sessionLang=${it}&templateId=${params.templateId}" class="contextoEhr"><img src="${createLinkTo(dir:'images' ,file:'ico_'+it+'.jpg')}" alt="${message(code:'common.lang.'+it)}" width="25" height="34" hspace="2" border="0" align="absmiddle" /></a>
            </g:if>
          </g:langSelector>

          </div>--%>
        <div id="infoLogin"> 
        <g:datosUsuario userId="${userId}" /> &nbsp; | &nbsp;

        <g:link controller="authorization" action="logout" class="contextoEhr"><g:message code="authorization.action.logout" /></g:link>
        </div>

    </div>
</div>

   

  <div id="menu1">
  <ul>
    <li>
      <a href="#" class="selected contextoEhr"><g:message code="records.registroActual"/></a>
    </li>
    <li>
       <g:link controller="records" action="list" class="contextoEhr"><g:message code="records.action.list" /></g:link>
    </li>
    <li>
      <g:link controller="demographic" action="admisionPaciente" class="contextoEhr"><g:message code="demographic.action.admisionPaciente" /></g:link>
    </li>
   
    <li>
       <g:link controller="reportes" class="contextoEhr"><g:message code="reportes.Reportes"/></g:link>
    </li>
  </ul>
</div>
<div id="nivel1">
  <div id="resumenPaciente">
    <g:if test="${patient}">
      <g:link controller="demographic" action="show" id="${patient.id}">

        <g:set var="name" value="${patient.identities.find{ it.purpose == 'PersonNamePatient'} }" />


        <%-- Preguntar primero si tiene foto--%>

    <g:if test="${!name || !name.foto || !name.tipofoto}">

      <g:if test="${patient.sexo=='Masculino'}">

        <img src="${createLinkTo(dir:"images", file:"man.png")}" style="width:52px; height: auto;" />
      </g:if>
      <g:else>

        <img src="${createLinkTo(dir:"images", file:"woman.png")}" style="width:52px; height: auto;" />
      </g:else>
    </g:if>
    <g:else>


       <img src="${createLink(controller:"demographic", action: 'fotopaciente', params:[persona:patient.id])}" style="width:52px; height: auto;"/>
    </g:else>






       
      
      <h2><g:message code="trauma.title.informacionPaciente" /></h2>
      </g:link>
      <g:render template="../demographic/Person" model="[person:patient]" />
      <div class="verMas"><g:link controller="demographic" action="edit" id="${patient.id}" class="contextoEhr"><g:message code="records.show.completarDatos" /></g:link></div>

    </g:if>
    <g:else>
      <g:message code="trauma.layout.pacienteNoIdentificado.label" />:<br/>
      <g:link controller="demographic" action="admisionPaciente" class="contextoEhr">
      <g:message code="trauma.layout.identificarPaciente.action" />
      </g:link>
    </g:else>

  </div>

 <%-- <div id="infoPaciente">
              <h2><g:message code="trauma.title.informacionPaciente" /></h2>
             
              <g:if test="${patient}">
                <g:render template="../demographic/Person" model="[person:patient]" />

                <g:canEditPatient patient="${patient}">
                  <g:link controller="demographic" action="edit" id="${patient.id}" class="contextoEhr">Completar datos</g:link>
                </g:canEditPatient>
              </g:if>
              <g:else>
                <g:message code="trauma.layout.pacienteNoIdentificado.label" />:<br/>
                <g:link controller="demographic" action="admisionPaciente" class="contextoEhr">
                  <g:message code="trauma.layout.identificarPaciente.action" />
                </g:link>
              </g:else>
 </div>--%>

  <div id="resumenEpisodio">
    
    <g:resumenEpisodio episodeId="${episodeId}" />
    <p></p>

  </div>

<div id="menu2">



    <ul class="top_actions">

      <g:isSignedRecord episodeId="${episodeId}">
      <g:set var="version" value="${Version.findByData(Composition.get(episodeId))}"/>
      <li>
                   <g:link controller="cda" action="ver" id="${version.nombreArchCDA}" params="[idComposition:episodeId]"><g:message code="hce.cda.verCda" /></g:link> <!-- TODO i18n -->
      </li>
      </g:isSignedRecord>

    </ul>

  </div>
   <div id="menu3">

              <ul>
                
                <li>

                  
                  <a href="${createLink(controller: 'guiGen', action: 'showRecord',id:episodeId)}" ${((controllerName=='guiGen'&&['showRecord'].contains(actionName)) ? "class='selected contextoEhr'" : "class='contextoEhr'")}>
                    <g:message code="trauma.menu.resumen" />
                  </a>
                  
                </li>

                <g:canFillClinicalRecord>

                  <%--<li >

                    <a href="${createLink(controller: 'records', action: 'registroClinico',id:episodeId)}" ${((controllerName=='records'&&['registroClinico'].contains(actionName)) ? "class='selected contextoEhr'" : "class='contextoEhr'")}>
                     <g:message code="trauma.menu.registroClinico" />
                  </a>

                   
                  </li> --%>

                  <%--
                  TODO: desde lo estudios img hasta el registro clinico no puede ser
                        visto por un administrativo.
                  --%>

                  <g:if test="${( ['guiGen','records','ajaxApi'].contains(controllerName) && ['generarShow','generarTemplate','show','saveDiagnostico','showRecord','save'].contains(actionName) )}">
                    <br />

                    <g:each in="${sections}" var="section">

                      <li>

                       <g:if test="${template?.id?.startsWith(section)}">
                       <%--Valor de la seccion actual--%>
                       <g:javascript> secc = '${section}';</g:javascript>
                       </g:if>

                        <%-- allSubsections: ${allSubsections}<br/> --%>
                        <%-- se fija si el registro ya fue hecho --%>
                        <%
                        //def subsection = subsections.find{it.startsWith(section)}
                        def subsection = allSubsections[section][0]
                        if (!subsection) subsection = " " // para que no sea null o vacia en la llamada a g:hasContentItemForTemplate
                                                          // que espera no null y no vacio el templateId.
                        %>
                        <%-- subsection: ${subsection}<br/> --%>
                        
                        <g:hasContentItemForTemplate episodeId="${episodeId}" templateId="${section+'-'+subsection}">
                          <g:if test="${it.hasItem}">
                           <%-- GUARDADO PREVIAMENTE, GENERAR SHOW --%>

                           <%-- <g:link controller="guiGen" action="generarShow" id="${it.itemId}">
                              <g:message code="${'section.'+section}" /> (+) <%-- + es que se hizo algun registro en la seccion --%>
                           <%-- </g:link>
                            --%>
                         <a href="#" onClick="guardadoAutomatico('${section}',true,${it.itemId});" ${(( template?.id?.startsWith(section) ) ? "class='selected contextoEhr'" : "class='contextoEhr'")} ><g:message code='${"section."+section}' /> <img src="${createLinkTo(dir:'images' ,file:'check-icon.png')}" style="width: 15px; height: auto;"/></a>
                          </g:if>
                          <g:else>
                            <%-- SIN GUARDAR, GENERAR RECORDS INPUTS (registrao clinico2) --%>

                         <a href="#" onClick="guardadoAutomatico('${section}',false,false);" ${(( template?.id?.startsWith(section) ) ? "class='selected contextoEhr'" : "class='contextoEhr'")}><g:message code='${"section."+section}' /></a>
                           <%--<g:link controller="records" action="registroClinico2" params="[section:section]">
                              <g:message code="${'section.'+section}" />
                            </g:link>
                            --%>


                          </g:else>
                        </g:hasContentItemForTemplate>
                      </li>
                    </g:each>
                  </g:if>
                  <br />

                  <% def firmado = false %>
                  <g:isSignedRecord episodeId="${episodeId}">
                  <% firmado = true %>
                  </g:isSignedRecord>
                  <g:if test="${firmado == false}">
                   <div id="firmar">
				   <li>
					   <a href="${createLink(controller: 'records', action: 'signRecord',id:episodeId)}"  ${((controllerName=='records'&&['signRecord'].contains(actionName)) ? "class='selected contextoEhr'" : "class='contextoEhr'")}>
						  <g:message code="registro.menu.close" />
					   </a>
                  </li>
				  </div>
                  </g:if>
                  <g:else>
                    <div id="reabrir">
					<li>

						<a href="${createLink(controller: 'records', action: 'reopenRecord',id:episodeId)}"  ${((controllerName=='records'&&['reopenRecord'].contains(actionName)) ? "class='selected'" : '')}>
						<g:message code="registro.menu.open" />
						</a>

                    </li>
					</div>
                  </g:else>
                  


                </g:canFillClinicalRecord>
              </ul>
            </div>
           
  
           
      <g:layoutBody />
     <p style="clear:both"> </p>
  
</div>

  <div id="footer" class="footercontenido">
      Centro de An&aacute;lisis de Im&aacute;genes Biom&eacute;dicas Computarizadas CAIBCO - Instituto de Medicina Tropical </br>
      Facultad de Medicina. Universidad Central de Venezuela </br>
      Tel&eacute;fonos: (0212) 605.37.46 / 35.94 </br>
      <a href="mailto:sostelemedicina&#64;ucv.ve">sostelemedicina&#64;ucv.ve
    </a></div>
</body>

</html>