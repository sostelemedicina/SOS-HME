<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="basicregistro" />
    <title><g:message code="demographic.agregar_paciente.title"/></title>
    <g:javascript library="prototype" />
    <r:require module="jquery-ui"/>
    <g:javascript library="jquery" />
    
    <jqui:resources themeCss="../css/jquery/jquery-ui-1.8.16.custom.css"/>
    <link rel="stylesheet" href="${resource(dir:'css',file:'jquery.Jcrop.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'estilo.css')}" />
    <g:javascript src="../js/jquery/jquery.Jcrop.js" />
    <g:javascript src="../js/jquery/jquery.Jcrop.min.js" />

    <script type="text/javascript" src="../js/jquery/jquery-ui-i18n.min.js"></script>
    <script type="text/javascript" src="../js/jquery/jquery-ui-timepicker-addon.js"> </script>
     
      <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery.form.js')}"></script>
    <g:javascript src='formToWizard.js' />
    <g:javascript src='funciones.js' />
    <g:javascript src='jquery.validate.js' />
    <link rel="stylesheet" href="${resource(dir:'css',file:'wizard.css')}" />
    
    <style>
      label {
        display: block;
      }
    </style>
    <script>
	

	
	
      jQuery(document).ready(function()
      {
          jQuery.datepicker.setDefaults(jQuery.datepicker.regional['${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}']);

                   jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',
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
          jQuery("#fechaNacimiento").attr("readonly",true);

          jQuery("#fechaNacimiento").click(function(){

            jQuery(this).val('');

          });
      });


       jQuery(document).ready(function()
      {
	  


        jQuery("#foto").attr("readonly",true);
        jQuery("#foto").click(function (){

          jQuery("#inputFotoPrevia").click();

          
        });
       jQuery("#inputFotoPrevia").change(function(){
 
       

         jQuery("#fotoPrevia").ajaxForm({success: mostrarRespuesta}).submit();
 

        });
        
      });
      function mostrarRespuesta(respuesta){

       if(respuesta != ""){
       
        jQuery("#imgPrevia").html("<img id='crop' src='${createLink(controller: "demographic",action: "mostrarFotoPrevia")}/"+respuesta+"' />")
        jQuery("#foto").val(respuesta);
        jQuery("#crop").Jcrop({
            aspectRatio: 1,
            setSelect: [0, 0, 100, 100],
            minSize: [30, 30],
            onSelect: cambiarCoordenadas,
            onChange: cambiarCoordenadas
        });
        }
      }

      function cambiarCoordenadas(c) {
     jQuery('#x1').val(c.x);
     jQuery('#y1').val(c.y);
     jQuery('#x2').val(c.x2);
     jQuery('#y2').val(c.y2);
 }

    </script>
    
    <g:javascript>
        
		function updateIdentidad( var1, var2 ){
            ${remoteFunction( 
              controller:'demographic', 
              action:'ajaxGetPrimerApellido', 
              update:'identidades', 
              params:'\'id=\' + var1 +\'-\' +var2')}
			
		
		}
		function updateNombres( root, extension, primerApellido, segundoApellido, primerNombre, segundoNombre, fechaNacimiento, sexo, fin){
			${remoteFunction( 
			controller:'demographic', 
			action:'ajaxGetNombres', 
			update:'nombres', 
			params:'\'id=\' + root +\'!\' +extension + \'!\' + primerApellido + \'!\' + segundoApellido + \'!\' + primerNombre + \'!\' + segundoNombre + \'!\' + fechaNacimiento + \'!\' + sexo + \'!\'+ fin' )}
			//params:'\'rt=\' +root+\'&ext=\'+extension' )}
			//params:'\'id=\' + root +\'-\' +extension + \'&pa=\' + primerApellido' )}
			//params:'\'id=\' + root +\'-\' +extension + \'&pa=\' + primerApellido + \'&sa=\' + segundoApellido' )}
		}
         function updateSubCats( category ){
              var selectpais = document.getElementById("paisnace");
              if (selectpais.options[selectpais.selectedIndex].value == 1){
              document.getElementById("entnace").style.visibility = "visible";
              document.getElementById("munnace").style.visibility = "visible";
              document.getElementById("ciunace").style.visibility = "visible";
              
              ${remoteFunction( 
              controller:'demographic', 
              action:'ajaxGetEstados', 
              update:'entidadnace', 
              params:'\'id=\' + category')} 
              
              }else{
                document.getElementById("entnace").style.visibility = "hidden";
                document.getElementById("munnace").style.visibility = "hidden";
                document.getElementById("ciunace").style.visibility = "hidden";
              }
              
          }
          
          function updateMunicipios(estado){
            ${remoteFunction( 
                controller:'demographic', 
                action:'ajaxGetMunicipios', 
                update:'municnace', 
                params:'\'id=\' + estado')}
          }
          function updateMunicipiosReside(estado){
            var selectparroquia = document.getElementById("parroresid");
            
            ${remoteFunction( 
                controller:'demographic', 
                action:'ajaxGetMunicipios', 
                update:'municresid', 
                params:'\'id=\' + estado')}
          }
          
          function updateParroquiaReside(municipio){
            ${remoteFunction( 
                controller:'demographic', 
                action:'ajaxGetMunicipios', 
                update:'direccion.id', 
                params:'\'id=\' + municipio')}
          }
      </g:javascript>
	  
    <script type="text/javascript">
      var $j = jQuery.noConflict();
      $j(document).ready(function(){
          $j("#nuevopaciente").formToWizard({ submitButton: 'doit' })
      });
   
      
	  

	  
	  
    </script>
  </head>
  
  <body>
    
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
   <div id="nivel2">
    <div id="contenido"> 
   
    <g:if test="${flash.message}">
      <div style="color:red;">
        <g:message code="${flash.message}" />
      </div>
    </g:if>
  <div id="mensaje" style="color:red;">&nbsp;
  </div>
    <h1><g:message code="demographic.agregar_paciente.agregar"/></h1>
    <g:form action="agregarPaciente" name="nuevopaciente" id="nuevopaciente" enctype="multipart/form-data" class="form1">

        <g:hiddenField name="x1" value="0" />
        <g:hiddenField name="y1" value="0" />
        <g:hiddenField name="x2" value="100" />
        <g:hiddenField name="y2" value="100" />


<fieldset>
        <legend>Identificaci&oacute;n</legend>
		
	<div style="clear:both"></div>
	
                <p>
                <label for="identificador"><g:message code="persona.identificador" /></label>
                
                <g:if test="${params.root}">
                  <input type='hidden' id='identificadorUnico' value='valido' name='identificadorUnico'/>
                  <g:select name="root" from="${tiposIds}" value="${params.root}" optionKey="codigo" optionValue="nombreCorto" onchange="updateNombres( root.value, extension.value, primerApellido.value, segundoApellido.value, primerNombre.value, segundoNombre.value, fechaNacimiento.value, sexo.value, 'fin')"/>
                </g:if>
                <g:else>
                  <input type='hidden' id='identificadorUnico' value='novalido' name='identificadorUnico'/>
                  <g:select name="root" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" noSelection="['*1':'Seleccione Identificador']" onchange="updateNombres( root.value, extension.value, primerApellido.value, segundoApellido.value, primerNombre.value, segundoNombre.value, fechaNacimiento.value, sexo.value, 'fin')"/>
                </g:else>
                <g:textField name="extension" value="${params.identificador}" onchange="updateNombres( root.value, extension.value, primerApellido.value, segundoApellido.value, primerNombre.value, segundoNombre.value, fechaNacimiento.value, sexo.value, 'fin')"/> <span class="obligatorio">*</span>
                
				</p>
                <%--<g:select name="root" class="selectci" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" noSelection="['-1':'Seleccione Identificador']"/>	--%>
    <div id="nombres"><div style="clear:both"></div>           
                   <p>
                    <label for="primerApellido"> <g:message code="persona.primerApellido" /></label>
                   <g:textField name="primerApellido" value="${params.primerApellido}"/><span class="obligatorio">&nbsp;*</span>
                   </p>   
                   <p>
                    <label for="segundoApellido"> <g:message code="persona.segundoApellido" /></label>
                    <g:textField name="segundoApellido" value="${params.segundoApellido}" />
                    </p>
                    <p>
                    
                    <label for="primerNombre"><g:message code="persona.primerNombre" /></label>
                    <g:textField name="primerNombre" value="${params.primerNombre}" /><span class="obligatorio">&nbsp;*</span>
                    </p>
                    <p>
                    <label for="segundoNombre"><g:message code="persona.segundoNombre" /></label>
                    <g:textField name="segundoNombre" value="${params.segundoNombre}" />
                    </p>
                    <p>
                    <label for="fechaNacimiento"><g:message code="persona.fechaNacimiento" /></label>
                    <input name="fechaNacimiento" type="text" id="fechaNacimiento" class="Date" value="${params.fechaNacimiento}"/><span class="obligatorio">&nbsp;*</span>  <br /><br />
                    </p>
                    <p>
                    <label for="sexo"><g:message code="persona.sexo" /></label>
                    <g:select name="sexo" style="width: 175px;" noSelection="['':'Seleccione']" from="['Masculino', 'Femenino']" value="${params.sexo}" /><span class="obligatorio">&nbsp;*</span>
                    </p>
                    <p>
                    <label for="foto"><g:message code="persona.foto" /></label>
                    <input type="text" name="foto" id="foto" style='width: 300px;'/>
                    </p>
                </div>
                <span class="obligatorio">&nbsp;*</span> Campos Obligatorios                
        <div id="imgPrevia"></div>

      </fieldset>
	  
      <fieldset>
        <legend>Procedencia</legend>
        <p>
          <label for="etnia"><g:message code="persona.etnia" /></label>
          <g:select name="etnia.id" class="selectci" value="${params.etnia}" from="${etniasIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione Etnia']"/>
         </p>
         
         <p>
          <label for="nacionalidad"><g:message code="persona.nacionalidad" /></label>
          <g:select name="nacionalidad.id" class="selectci" from="${paisesIds}" optionKey="id" optionValue="nombre" />
          </p>
          
          <p>
          <label for="paisnacimiento"><g:message code="persona.paisnace"/></label>
          <g:select name="paisnace" class="selectci" from="${paisesIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione País']" onchange="updateSubCats(this.value)" />
          </p>
          
          <p>
          <div id="entnace" style="visibility: hidden;">
          <label for="entidadnacimiento"><g:message code="persona.entidadnace"/></label>
          <g:select name="entidadnace" class="selectci" disabled="false" noSelection="['':'Seleccione Entidad']" onchange="updateMunicipios(this.value)"/>
          </div> 
          </p>
          
          <p> 
          <div id="munnace" style="visibility: hidden;">         
            <label for="municipionacimiento"><g:message code="persona.municipionace"/></label>
            <g:select name="lugar.id" id="municnace" class="selectci" value="${params.lugarnacimiento}" disabled="false" noSelection="['':'Seleccione Municipio']"/>
          </div>
          </p>
          
          <p>
          <div id="ciunace" style="visibility: hidden;">        
            <label for="ciudadnacimiento"><g:message code="persona.ciudadnace"/></label>
            <g:textField name="ciudadnacimiento" class="selectci" value="${params.ciudadnacimiento}" />
          </div>  
          <br/>
          </p>
                  
      </fieldset>
      
      
      <fieldset>
        <legend>Datos Personales</legend>
        <p>
          <label for="situacionconyugal"><g:message code="persona.situacionConyugal"/></label>
          <g:select name="situacionconyugal" class="selectci" from="${conyugalIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione']"/>
        </p>
        <p>
          <label for="analfabeta"><g:message code="persona.analfabeta"/> &nbsp;&nbsp;</label>
          <g:select name="analfabeta" class="selectci" noSelection="['':'Seleccione']" from="['No', 'Si']" value="${params.analfabeta}" />
        </p> 
        <p>
          <label for="niveleducativo"><g:message code="persona.niveleducativo"/></label>
          <g:select name="niveleducativo" class="selectci" from="${nivelEducIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione']"/>
        </p>  
        <p>  
          <label for="anosaprobados"><g:message code="persona.anosaprobados"/></label>
          <g:textField name="anosaprobados" class="selectci" value="${params.anosaprobados}" title="Cuado aplique, años pasados desde su graduación"/>
        </p>	  
        <p>
          <label for="ocupacion"><g:message code="persona.ocupacion"/></label>
	  <g:select name="ocupacion.id" id="ocupacion" class="selectci" from="${ocupacionIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione Ocupación']" />  
        </p>  
      </fieldset>
      
      <fieldset>
        <legend>Direcci&oacute;n de Habitaci&oacute;n</legend>
          <p>
            <label for="entidadresidencia"><g:message code="persona.entidadreside"/></label>
            <g:select name="entidresid" class="selectci" from="${entidadesIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione Entidad']" onchange="updateMunicipiosReside(this.value)" /><span class="obligatorio">&nbsp;*</span>
          </p>   
          <p>
          <label for="municipioresidencia"><g:message code="persona.municipioreside"/></label>
          <g:select name="municresid" class="selectci" disabled="false" noSelection="['':'Seleccione Municipio']" onclick="updateParroquiaReside(this.value)"/><span class="obligatorio">&nbsp;*</span>
          </p>	  
          <p>
          <label for="parroquiresidencia"><g:message code="persona.parroquiareside"/></label>
            <%--g:select name="direccion.id" class="selectci" id="parroresid" disabled="false" noSelection="['':'Seleccione Parroquia']"/--%>
            <g:select name="direccion.id" class="selectci" disabled="false" noSelection="['':'Seleccione Parroquia']"/><span class="obligatorio">&nbsp;*</span>
            </p>	  
          <p>
          <label for="localidadreside"><g:message code="persona.localidadreside"/></label>
          <g:textField name="ciudad" class="selectci" value="${params.ciudad}" /><span class="obligatorio">&nbsp;*</span>
          </p>
          <p>
          <label for="urbreside"><g:message code="persona.urbreside"/></label>
          <g:textField name="urbasector" class="selectci" value="${params.urbasector}" /><span class="obligatorio">&nbsp;*</span>
          </p>
          <p>
          <label for="avenireside"><g:message code="persona.avenireside"/></label>
          <g:textField name="avenidacalle" class="selectci" value="${params.avenidacalle}" /><span class="obligatorio">&nbsp;*</span>
          </p>
          <p>
          <label for="casareside"><g:message code="persona.casareside"/></label>
          <g:textField name="casaedif" class="selectci" value="${params.casaedif}" /><span class="obligatorio">&nbsp;*</span>
          </p>
          <p>
          <label for="pisoreside"><g:message code="persona.pisoreside"/></label>
          <g:textField name="pisoplanta" class="selectci" value="${params.pisoplanta}" />
          </p>
          <p>
          <label for="ptoreferencia"><g:message code="persona.puntoreferencia"/></label>
          <g:textField name="ptoreferenica" class="selectci" value="${params.ptoreferenica}" />
          </p>
          <p>
          <label for="tiemporesidencia"><g:message code="persona.tiemporesidencia"/></label> 
          <g:textField name="tiemporesidencia" class="selectci" value="${params.tiemporesidencia}" title="Indicar el tiempo en años o meses que tiene en la residencia actual"/>
          </p>
          <span class="obligatorio">&nbsp;*</span> Campos Obligatorios                
        </fieldset>
		
        <fieldset>
          <p>
          <legend>Contacto</legend>
          <label for="telfhab"><g:message code="persona.telfhabitacion"/></label>
          <g:textField name="telfhabitacion" class="selectci" value="${params.telfhabitacion}" />
          </p>
          <p>
          <label for="telfcel"><g:message code="persona.telfcelular"/></label>
          <g:textField name="telfcelular" class="selectci" value="${params.telfcelular}" />
          </p>
          <p>
          <label for="email"><g:message code="persona.email"/></label>
          <g:textField name="email" class="selectci" value="${params.email}" />
          </p>
          <p>
          <label for="nombremadre"><g:message code="persona.nombremadre"/></label>
          <g:textField name="nombremadre" class="selectci" value="${params.nombremadre}" />
          </p>
          <p>
          <label for="nombrepadre"><g:message code="persona.nombrepadre"/></label>
          <g:textField name="nombrepadre" class="selectci" value="${params.nombrepadre}" />
          </p>
          <p>
          <label for="otradireccion"><g:message code="persona.otradireccion"/></label>
          <g:textArea name="otradireccion" class="inputtextfield" value="${params.otradireccion}" rows="1" cols="20"/>
          </p>
          <p>
          <label for="contactoemergencia"><g:message code="persona.contactoemergencia"/></label>
          <g:textArea name="contactoemergencia" class="inputtextfield" value="${params.contactoemergencia}" rows="1" cols="20"/>
          </p>
        </fieldset>
        
      
        <p>
          <g:submitButton name="doit" value="${message(code:'demographic.agregar_paciente.agregar')}" class="boton_submit"/>
          <%--g:link action="admisionPaciente"><g:message code="demographic.lista_candidatos.action.admisionPaciente" /></g:link--%>
        </p>    
      
    </g:form>

    <%-- Foto previa--%>
    <g:form action="fotoPrevia" name="fotoPrevia"  enctype="multipart/form-data">
     <input type="file" name="fotoPrevia" id="inputFotoPrevia" style="width: 300px; visibility: hidden;"/>
    </g:form>
    </div>
    </div>
 </div> 

  </body>
</html>