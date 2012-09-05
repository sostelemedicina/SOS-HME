<%@ page import="com.thoughtworks.xstream.XStream" %>
<%@ page import="archetype_repository.ArchetypeManager" %>
<%@ page import="templates.TemplateManager" %>
<%--

in: rmNode (Action)

--%>
<g:if test="${!template}">
  <g:set var="template"
         value="${TemplateManager.getInstance().getTemplate( rmNode.archetypeDetails.templateId )}" />
</g:if>

<g:set var="archetype"
       value="${ArchetypeManager.getInstance().getArchetype( rmNode.archetypeDetails.archetypeId )}" />

<%-- http://code.google.com/p/open-ehr-gen-framework/issues/detail?id=19 --%>
<g:set var="generarGUI" value="${fieldPaths.find{ rmNode.path.startsWith(it)} != null}" />
<g:if test="${generarGUI}">
	<div class="ACTION">
	  <g:set var="aomNode" value="${archetype.node(rmNode.path)}" />
	  <g:set var="archetypeTerm" value="${archetype.ontology.termDefinition(session.locale.language, aomNode.nodeID)}" />
	  
	  <span class="label">
	    ${archetypeTerm?.text}:
	  </span>
	  <span class="content">
	    <g:if test="${mode && mode=='edit'}">
	      <g:if test="${!rmNode.description}"><%-- Si no hay estructura RM para mostrar, voy por el AOM --%>
	        <%
	        def aomNode = archetype.node( rmNode.path ) // Si pido /description que es correcto, no me da el nodo!
	        //println "AOM CLASS: " + aomNode.getClass()
	        //println rmNode.path + "description"
	        %>

	        <%-- sacado de _cComplexObject --%>
	        <g:render template="../guiGen/templates2/cAttribute"
	                  var="cAttribute"
	                  collection="${aomNode.attributes}"
	                  model="[archetype: archetype,
	                          refPath: '',
	                          params: params]" />
	      </g:if>
	      <g:else><%-- Hay estructura RM para mostrar, no voy al AOM --%>
	        <g:render template="../guiGen/showTemplates/ItemStructure"
	                  model="[rmNode: rmNode.description, archetype: archetype, template: template]" />
	      </g:else>
	    </g:if>
	    <g:else>
	      <g:render template="../guiGen/showTemplates/ItemStructure"
	                model="[rmNode: rmNode.description, archetype: archetype, template: template]" />
	    </g:else>
	  </span>
	</div>
</g:if>
<g:else>
	<g:if test="${mode && mode=='edit'}">
      <g:if test="${!rmNode.description}"><%-- Si no hay estructura RM para mostrar, voy por el AOM --%>
        <%
        def aomNode = archetype.node( rmNode.path ) // Si pido /description que es correcto, no me da el nodo!
        //println "AOM CLASS: " + aomNode.getClass()
        //println rmNode.path + "description"
        %>

        <%-- sacado de _cComplexObject --%>
        <g:render template="../guiGen/templates2/cAttribute"
                  var="cAttribute"
                  collection="${aomNode.attributes}"
                  model="[archetype: archetype,
                          refPath: '',
                          params: params]" />
      </g:if>
      <g:else><%-- Hay estructura RM para mostrar, no voy al AOM --%>
        <g:render template="../guiGen/showTemplates/ItemStructure"
                  model="[rmNode: rmNode.description, archetype: archetype, template: template]" />
      </g:else>
    </g:if>
    <g:else>
      <g:render template="../guiGen/showTemplates/ItemStructure"
                model="[rmNode: rmNode.description, archetype: archetype, template: template]" />
    </g:else>
</g:else>