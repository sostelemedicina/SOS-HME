<%--

in: rmNode (ItemStructure)

--%>

<%--<g:set var="templateName" value="${rmNode.getClass().getSimpleName()}" /> --%>
<g:set var="templateName" value="${rmNode.getClassName()}" />

<g:render template="../guiGen/showTemplates/${templateName}"
          model="[rmNode: rmNode, archetype: archetype, template: template]" />