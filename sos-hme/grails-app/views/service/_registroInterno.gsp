<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.change_control.Version" %>
<%@ page import="hce.core.composition.Composition" %>
<%@ page import="java.text.SimpleDateFormat" %>

<g:if test="${!compositions}">

  <p><g:message code="service.imp.episodiosCoincidentes.false" /></p>
</g:if>
<g:else>

  <table id="list1" width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla1">
    <tr>
      <th><g:message code="trauma.list.label.id" /></th>
    <th><g:message code="trauma.list.label.startTime" /></th>
    <th><g:message code="trauma.list.label.endTime" /></th>
    <th><g:message code="trauma.list.label.observations" /></th>
    <th><g:message code="trauma.list.label.state" /></th>
    <th><g:message code="trauma.list.label.actions" /></th>
    </tr>
    <g:each in="${compositions}" var="composition">
      <tr>
        <td>${composition.id}</td>
        <td><g:format date="${composition.context.startTime?.toDate()}" /></td>
      <td><g:format date="${composition.context.endTime?.toDate()}" /></td>
      <td>
<%-- OJO: Solo funciona si el otherContext es ItemSingle y el value del Element es DvText --%>
${composition.context.otherContext.item.value.value}
      </td>
      <td>
<%--
// El .toString es por esto:
// Exception Message: No signature of method:
// org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource.getMessage()
// is applicable for argument types: (org.codehaus.groovy.grails.web.util.StreamCharBuffer, null,
// org.codehaus.groovy.grails.web.util.StreamCharBuffer, java.util.Locale) values:
// [ehr.lifecycle.incomplete, null, ehr.lifecycle.incomplete, es]
--%>
      <g:message code="${g.stateForComposition(episodeId:composition.id).toString()}" />
      </td>
      <td>
      <g:link controller="records" action="show" id="${composition.id}" class="boton2"><g:message code="trauma.list.action.show" /></g:link>
      <br />
      <g:if test="${(g.stateForComposition(episodeId:composition.id) == Version.STATE_SIGNED)}">
        <g:set var="version" value="${Version.findByData(composition)}"/>
        <g:set var="archivoCDA" value="${new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '//' + version.nombreArchCDA)}"/>
       <%-- <g:if test="${!archivoCDA.exists()}">
          <g:link controller="cda" action="create" id="${composition.id}">Crear CDA</g:link>
        </g:if>
        <g:else>
          <g:message code="Documento Clinico Creado" /> <!-- TODO i18n -->
          <g:link controller="cda" action="ver" id="${version.nombreArchCDA}"><g:message code="Ver CDA" /></g:link> <!-- TODO i18n -->

        </g:else>--%>
      </g:if>
      </td>
      </tr>
    </g:each>
  </table>
  <util:remotePaginate controller="service"
                           action="${llamar}"
                           params="[id: idPaciente, desde: desde, hasta: hasta, marca: 'pag']"
                           total="${compositionsSize}"
                           update="[success: 'resultadoInterno', failure: 'errorResultadoInterno']"
                           max="${compositionsMax}"
                           />

</g:else>