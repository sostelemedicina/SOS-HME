<%@ page import="com.thoughtworks.xstream.XStream" %>
<%@ page import="archetype_repository.ArchetypeManager" %>
<%--

in: rmNode (AdminEntry)

--%>

<g:set var="archetype"
       value="${ArchetypeManager.getInstance().getArchetype( rmNode.archetypeDetails.archetypeId )}" />

<div class="ADMIN_ENTRY">
  <g:set var="aomNode" value="${archetype.node(rmNode.path)}" />
  
  <g:set var="archetypeTerm" value="${archetype.ontology.termDefinition(session.locale.language, aomNode.nodeID)}" />
  
  <span class="label">
    ${archetypeTerm?.text}:
  </span>
  <span class="content">

    <g:if test="${mode && mode=='edit'}">
      <g:if test="${!rmNode.data}"><%-- Si no hay estructura RM para mostrar, voy por el AOM --%>
        <%
        def aomNode = archetype.node( rmNode.path ) // Si pido /description que es correcto, no me da el nodo!
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
                  model="[rmNode: rmNode.data, archetype: archetype]" />
      </g:else>
    </g:if>
    <g:else>
      <g:render template="../guiGen/showTemplates/ItemStructure"
                model="[rmNode: rmNode.data, archetype: archetype]" />
    </g:else>
  </span>
</div>
