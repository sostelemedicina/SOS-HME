<%--
in: rmNode (Locatable)
in: archetype que restringe este rmNode, pero puede tener hijos para los cuales se necesiten arquetipos referenciados por slots.
in: template que define toda la estructura que cuelga de este rmNode. FIXME: no viene desde _showRecord!
--%>
<!-- Locatable -->
<%--
  arhcID: ${rmNode.archetypeDetails.archetypeId},
  nodeID: ${rmNode.archetypeNodeId},
  id: ${rmNode.id}<br/><br/>
--%>
<%--<g:set var="templateName" value="${rmNode.getClass().getSimpleName()}" />--%>

<g:set var="templateName" value="${rmNode.getClassName()}" />

<g:render template="../guiGen/showTemplates/${templateName}"
          model="[rmNode: rmNode, archetype: archetype, template: template, fieldPaths: fieldPaths]" />



