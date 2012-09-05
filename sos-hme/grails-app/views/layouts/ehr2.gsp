<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.directory.Folder" %>
<%--<?xml version="1.0" encoding="ISO-8859-1?>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<g:set var="startmsec" value="${System.currentTimeMillis()}"/>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>

    <%-- No quiero paginas cacheadas --%>
    <%--
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Expires" CONTENT="-1" />
    <!-- META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" /-->
    <META HTTP-EQUIV="Cache-Control" content="no-cache, must-revalidate" />
    --%>
    <%-- en FF no funca --%>
    <META Http-Equiv="Cache-Control" Content="no-cache">
    <META Http-Equiv="Pragma" Content="no-cache">
    <META Http-Equiv="Expires" Content="0"> 
    
    <g:javascript>
      // Para evitar el boton de volver del navegador.
      window.history.go(1);
    </g:javascript>
    
    <g:javascript library="prototype/prototype" />
    <g:javascript>
      Event.observe(window, 'load', function () {
 
        //alert( $$('a.clone') );
      
        $$('a.clone').each( function(item) {
        
          // item es cada link con class=clone
          item.observe('click', function(event) {
            
            //alert('clic');
            
            //alert( item.parentNode ); // parentNode es de DOM
            //alert( $(item.parentNode).previous() ); // parentNode es de DOM
            
            nodeToClone = $(item.parentNode).previous();
            
            // Extiendo DOM para .previous de prototype
            /*
            nodeToClone.setStyle({
              //backgroundColor: '#900',
              border: '3px solid #ffff00'
            });
            */
            
            // Object.clone tira un Object y necesito un Element para hacer insert
            //newNode = Object.clone(nodeToClone); //nodeToClone.clone();
            newNode = nodeToClone.cloneNode(true); // cloneNode tira un Element y me deja hacer insert
            
            //alert(newNode);
            
            nodeToClone.insert({
              after: newNode
            });
            
            /*
            item.insert({
              before: newNode
            });
            */
          });
        });
      });
    </g:javascript>
    
     <title><g:message code="auth.login.welcome"/></title>
    <%--
    <link rel="stylesheet" href="${createLinkTo(dir:'css', file:'ehr.css')}" />
    --%>
    <link rel="stylesheet" href="${createLinkTo(dir:'css' ,file:'ehr_contenido_grande.css')}" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css' ,file:'main.css')}" />
    <g:layoutHead />
  </head>
  <body class="cuerpolayoutmain">
    <div id="header">
            <div id="spinner" class="spinner" style="display:none;">
              <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
            </div>	
            <div class="logo">
              <a href="" target=""><img src="${resource(dir:'images', file:'bannerSOS.jpg')}" alt="Open EHR-Gen Framework" /></a>
            </div>
            <div class="time">
              <script type="text/javascript">
                  var d=new Date();
                  var weekday=new Array("Domingo","Lunes","Martes","Mi&eacute;rcoles","Jueves","Viernes","Sa&acute;bado");
                  var monthname=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
                  document.write(weekday[d.getDay()] + " ");
                  document.write(d.getDate() + " de ");
                  document.write(monthname[d.getMonth()] + " de ");
                  document.write(d.getFullYear());
              </script>
              | <g:datosUsuario userId="${session.traumaContext.userId}" /> | <a href="${createLink(controller:'authorization',action:'logout')}">Salir</a>
            </div>
        </div>
    <div class="menurecordlist">
      <a href="${createLink(controller:'domain',action:'list')}">Dominos</a> &nbsp;&nbsp; | &nbsp;&nbsp; <a href="${createLink(controller:'demographic',action:'admisionPaciente')}">Admisi&oacute;n Paciente</a>
    </div>
    <div id="body">
    
      <%-- El registro clinico ya tiene un flash para mostrar mensajes, saco este para que no muestre doble.
      <g:if test="${flash.message}">
        <div id="message" class="error">
          <g:message code="${flash.message}" args="${flash.args}" />
        </div>
      </g:if>
      --%>
      
      <table cellpadding="0" cellspacing="0" >
        <tr>
          <td id="body_table" rowspan="2">
            <g:resumenEpisodio episodeId="${episodeId}"/>
            <g:layoutBody />
          </td>
          <td>
            <div id="infoPaciente">
              <h2><g:message code="trauma.title.informacionPaciente" /></h2>
              <%-- A patient lo manda como modelo guiGenController.generarTemplate --%>
              <g:if test="${patient}">
                <g:render template="../demographic/Person" model="[person:patient]" />
                
                <g:canEditPatient patient="${patient}">
                  <g:link controller="demographic" action="edit" id="${patient.id}">Completar datos</g:link>
                </g:canEditPatient>
              </g:if>
              <g:else>
                <g:message code="trauma.layout.pacienteNoIdentificado.label" />:<br/>
                <g:link controller="demographic" action="admisionPaciente">
                  <g:message code="trauma.layout.identificarPaciente.action" />
                </g:link>
              </g:else>
            </div>
            <div id="menu">
              <ul>
                <li>
                  <g:link controller="records" action="list">
                    <g:message code="trauma.menu.list" />
                  </g:link>
                </li>
                
                <li ${((controllerName=='records'&&['show'].contains(actionName)) ? 'class="selected"' : '')}>
                  <g:link controller="records" action="show" id="${episodeId}">
                    <g:message code="trauma.menu.show" />
                  </g:link>
                </li>
                <br />
                <g:canFillClinicalRecord>
                
                  <li ${((controllerName=='records'&&['registroClinico'].contains(actionName)) ? 'class="selected"' : '')}>
                    <g:link controller="records" action="registroClinico" id="${episodeId}">
                      <g:message code="trauma.menu.registroClinico" />
                    </g:link>
                  </li>
                  
                  <%--
                  TODO: desde lo estudios img hasta el registro clinico no puede ser
                        visto por un administrativo.
                  --%>
                  
                  <g:if test="${( ['guiGen','records','ajaxApi'].contains(controllerName) && ['generarShow','generarTemplate','show','saveDiagnostico','showRecord'].contains(actionName) )}">
                    
                    <g:each in="${sections}" var="section">
                      <li ${(( template?.id?.startsWith(section) ) ? 'class="selected"' : '')}>
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
                            <g:link controller="guiGen" action="generarShow" id="${it.itemId}">
                              <g:message code="${'section.'+section}" /> (+) <%-- + es que se hizo algun registro en la seccion --%>
                            </g:link>
                          </g:if>
                          <g:else>
                            <g:link controller="records" action="registroClinico2" params="[section:section]">
                              <g:message code="${'section.'+section}" />
                            </g:link>
                          </g:else>
                        </g:hasContentItemForTemplate>
                      </li>
                    </g:each>
                  </g:if>
                  
                  <li ${((controllerName=='records'&&['signRecord'].contains(actionName)) ? 'class="selected"' : '')}>
                    <g:link controller="records" action="signRecord" id="${episodeId}">
                      <g:message code="registro.menu.close" />
                      <g:isSignedRecord episodeId="${episodeId}">
                        (+)                     
                      </g:isSignedRecord>
                    </g:link>
                  </li>
                  
                </g:canFillClinicalRecord>
              </ul>
            </div>
            
          </td>
        </tr>
      </table>
    </div>
    <div id="footer" class="footerdomainlist"> 
          Universidad Central de Venezuela | Facultad de Medicina | Instituto de Medicina Tropical | <a href="http://caibco.ucv.ve" target="_blank">Centro de An&aacute;lisis de Im&aacute;genes Biom&eacute;dicas Computarizadas</a> | Caracas 2011
    </div>
  </body>
</html>