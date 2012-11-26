<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="basicrecord" />
    <title><g:message code="reportes.suite.title" /></title>
  </head>

  <body>

    <div id="menu1">
      <ul>
        <g:canNotFillAdmin>
        <li>
        <g:link controller="records" action="list" ><g:message code="records.action.list" /></g:link>
        </li>
        <li>
        <g:link controller="demographic" action="admisionPaciente" ><g:message code="demographic.action.admisionPaciente" /></g:link>
        </li>
        </g:canNotFillAdmin>
        
        <li>
        <g:link controller="reportes" class="selected"><g:message code="reportes.Reportes"/></g:link>
        </li>
        <g:canFillAdmin>
        <li>
        <g:link controller="admin" ><g:message code="loginAuth.user.adminPrincipal" /></g:link>
        </li>
        
        </g:canFillAdmin>  
          
      </ul>
    </div>


    <div id="nivel1">
      <div id="menu2">
      </div>
      <div id="nivel2">
        <div id="contenido">

          <h1>Control de Reportes EPI</h1>

          <div class="reportcentral">
            <%--<div class="opcionesrepo">
              <div class ="titulorepo"><g:message code="reporte.titulolista"/></div>
              <div class ="rangofechasrepo"><g:message code="reporte.rangofecha"/></div>
              <div class ="accionrepo"><g:message code="reporte.accion"/></div>
            </div>--%>
            
            <g:if test="${flash.message}">
      <div style="color:red;">
        <g:message code="${flash.message}" />
      </div>
    </g:if>
            <div class="listarepo">
<%--
              <div class="reportlist">
                
                <g:form action="epi10general" class="form1" >
                  <p>
                  <label><b><g:message code="reportes.epi10general" /></b></label>
                  </p>
                  <p>
                    <label for="desde"><g:message code="buscar.desde" /></label>

                    <input name="desde" value="" type="text" class="DateSos" />
                </p>
                <p>
                    <label for="hasta"><g:message code="buscar.hasta" /></label>

                    <input name="hasta" value="" type="text" class="DateSos" />
                  </p>
                    <p>
                    <g:submitButton name="generarreporte" class="boton_submit" value="${message(code:'reportes.generate')}" />
                    <g:if test="${params.creado10general=='true'}">
                      <g:link action="descargar" params="[archivo:params.tipo]">Descargar</g:link>
                    </g:if>
                    <g:if test="${params.creado10general=='false'}">
                      <div class="mensajereportefalla">
                        No Hay Registro en el Rango de Fechas Seleccionado
                      </div>
                    </g:if>
                  </p>
                  
                  
                </g:form>
                <hr />
              </div>

              <div class="reportlist">
                <g:form action="epi13morbilidad" class="form1">
                  <p><label><b><g:message code="reportes.epi13morbilidad" /></b></label></p>
                  <p>
                    <label for="desde"><g:message code="buscar.desde" /></label>
                    <input name="desde" value="" type="text" class="DateSos" />
                 </p>
                 <p>
                    <label for="desde"><g:message code="buscar.hasta" /></label>
                    <input name="hasta" value="" type="text" class="DateSos" />
                  </p>
                  <p>
                    <g:submitButton name="generarreporte" class="boton_submit" value="${message(code:'reportes.generate')}"/>
                    <g:if test="${params.creado13morbilidad=='true'}">
                      <g:link action="descargar" params="[archivo:params.tipo]">Descargar</g:link>
                    </g:if>
                    <g:if test="${params.creado13morbilidad=='false'}">
                      <div class="mensajereportefalla">
                        No Hay Registro en el Rango de Fechas Seleccionado
                      </div>

                    </g:if>
                  </p>
                  
                
                </g:form>
              </div>
              <hr />
              <div class="reportlist">
                <g:form action="epi12morbilidad" class="form1">
                  <label><b><g:message code="reportes.epi12morbilidad" /></b></label>
                  <p>
                    <label for="desde"><g:message code="buscar.desde" /></label>
                    <input name="desde" value="" type="text" class="DateSos" />
                  </p>
                  <p>
                    <label for="desde"><g:message code="buscar.hasta" /></label>
                    <input name="hasta" value="" type="text" class="DateSos" />
                  </p>
                  <p>
                    <g:submitButton name="generarreporte" class="boton_submit" value="${message(code:'reportes.generate')}"/>
                    <g:if test="${params.creado12morbilidad=='true'}">
                      <g:link action="descargar" params="[archivo:params.tipo]">Descargar</g:link>
                    </g:if>

                    <g:if test="${params.creado12morbilidad=='false'}">
                      <div class="mensajereportefalla">
                        No Hay Registro en el Rango de Fechas Seleccionado
                      </div>
                    </g:if>
                    </p>
               </div>
                </g:form>
              </div>
                      --%>
              <g:form class="form1" controller="reportes" action="desplegarReporte">
                 <label><b>Selecione el rango de fechas</b></label><br />
                  <p>
                    <label for="desde"><g:message code="buscar.desde" /></label>
                    <input name="desde" value="" type="text" class="DateSos" />
                  </p>
                  <p>
                    <label for="hasta"><g:message code="buscar.hasta" /></label>
                    <input name="hasta" value="" type="text" class="DateSos" />
                  </p>
                 <label><b>Selecione el tipo de reporte</b></label><br />
                 
                 <!-- COLOCAR TIPO DE REPORTES POSIBLES, DEPENDIENDO DEL TIPO DE USUARIO-->
                 <g:radioGroup name="reporte" labels="${listaReportes}" values="${listaReportes}" value="${listaReportes[0]}">
                   <p>
                      
                     <% out << " <label>"+ it.radio+" "+ it.label + "</label>" %>
                  </p> 
                 </g:radioGroup>
                
                 <g:submitButton name="arm" class="boton_submit" value="${message(code:'reportes.generate')}"/>
              </g:form>
              
             
            </div>
          </div>

        </div>
      </div>
   

  </body>
</html>
