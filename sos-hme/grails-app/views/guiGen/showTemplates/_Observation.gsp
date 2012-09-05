<%@ page import="templates.TemplateManager" %>
<%@ page import="archetype_repository.ArchetypeManager" %>

<%--

in: rmNode (Observation)
in: template que define toda la estructura que cuelga de este rmNode.
in: fieldPaths paths de los fields del archRef del template para el que se esta generando GUI (necesario por http://code.google.com/p/open-ehr-gen-framework/issues/detail?id=19) 

--%>
<!-- Observation -->
<g:if test="${!template}">
  <g:set var="template"
         value="${TemplateManager.getInstance().getTemplate( rmNode.archetypeDetails.templateId )}" />
</g:if>

<%-- puede ser el mismo que me pasan como parametro, ahora es al dope que me lo pasen silo calculo de nuevo aqui. --%>
<g:set var="archetype"
       value="${ArchetypeManager.getInstance().getArchetype( rmNode.archetypeDetails.archetypeId )}" />

<%-- http://code.google.com/p/open-ehr-gen-framework/issues/detail?id=19 --%>
<g:set var="generarGUI" value="${fieldPaths.find{ rmNode.path.startsWith(it)} != null}" />
<g:if test="${generarGUI}">
  <div class="OBSERVATION">
    <g:set var="aomNode" value="${archetype.node(rmNode.path)}" />
    <g:set var="archetypeTerm" value="${archetype.ontology.termDefinition(session.locale.language, aomNode.nodeID)}" />
	  
    <span class="label">
      ${archetypeTerm?.text}:
    </span>
    <span class="content">
      <g:render template="../guiGen/showTemplates/History"
                model="[rmNode: rmNode.data, archetype: archetype, template: template]" />
    </span>
  </div>
</g:if>
<g:else>
  <g:render template="../guiGen/showTemplates/History"
	              model="[rmNode: rmNode.data, archetype: archetype, template: template]" />
</g:else>