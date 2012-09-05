<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.change_control.Version" %>
<%@ page import="hce.core.composition.Composition" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="hce.HceService" %>
<html>
  <head>
    <meta name="layout" content="basicrecord" />
    <title><g:message code="episodio.list.title" /></title>
  </head>
  <body>


    <div id="menu1">
      <ul>
        <li>
        <g:link controller="records" action="list" class="selected"><g:message code="records.action.list" /></g:link>
        </li>
        <li>
        <g:link controller="demographic" action="admisionPaciente"><g:message code="demographic.action.admisionPaciente" /></g:link>
        </li>

        <li>
        <g:link controller="reportes"><g:message code="reportes.Reportes"/></g:link>
        </li>
      </ul>
    </div>


    <div class="bodydomainlist">




      <g:if test="${flash.message}">
        <div style="color:red; align:right;">
          <g:message code="${flash.message}" />
        </div>
      </g:if>




      <div id="nivel1">


        <div id="menu2">
          <ul>
            <li>
            <g:link action="create" class="create"><g:message code="trauma.list.action.crearEpisodio" /></g:link>
            </li>



          </ul>
        </div>
        <div id="nivel2">

          <div id="contenido">
            <h1>     <g:message code="episodio.list.title" /></h1>

<%-- BUSQUEDA REGISTROS--%>
            <div id="registroInterno">

              <g:formRemote name="busquedaRegistros"
                            url="[controller:'records',action:'listFiltro']"
                            update="[success: 'resultadoInterno', failure: 'errorResultadoInterno']"
                            >
                <br/>
                <label for="rangoFechas">
                  <b> <g:message code="buscar.rango_fechas" /></b>
                </label>



                <label for="desde">
                  <g:message code="buscar.desde" />
                </label>
<%--<g:datePicker name="desde" value="" precision="day" noSelection="['':'']" />--%>
                <input name="desde" value="" type="text" class="DateSos" />


                <label for="hasta">
                  <g:message code="buscar.hasta" />
                </label>
<%--<g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />--%>
                <input name="hasta" value="" type="text" class="DateSos" />

                <select name="estado">
                  <option value="todos"><g:message code="records.list.find.select.todos" /></option>
                  <option value="ehr.lifecycle.incomplete"><g:message code="records.list.find.select.incompletos" /></option>
                  <option value="ehr.lifecycle.signed"><g:message code="records.list.find.select.firmados" /></option>


                </select>
                <g:submitButton class="boton1" name="doit" value="${message(code:'buscar.filtro')}" />


              </g:formRemote>





              <div id="resultadoInterno">

                <g:render template="listado" model="['compositions':compositions,'userId':userId, 'domain': domain, 'total': total, 'estado':estado]"/>

              </div>
              <div id="errorResultadoInterno"></div>

            </div>

          </div>
        </div>
      </div>
  </body>
</html>