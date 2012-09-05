<%@ page import="templates.TemplateManager" %>
<%--

in: rmNode (Section)
in: archetype que restringe este rmNode, pero puede tener hijos para los cuales se necesiten arquetipos referenciados por slots.
in: template que define toda la estructura que cuelga de este rmNode. FIXME: no deberia venir, lo deberia calcular 
    (ojo si pido showRecord desde la HCE funciona bien, pero si lo pido al generar el CDA tira except), por las 
    dudas agrego el if, pero hay que verificar...

--%>
<!-- Section -->
<g:if test="${!template}">
  <g:set var="template"
         value="${TemplateManager.getInstance().getTemplate( rmNode.archetypeDetails.templateId )}" />
</g:if>
<g:each in="${rmNode.items}" var="item">
  <%--<g:set var="templateName" value="${item.getClass().getSimpleName()}" />--%>
  <g:set var="templateName" value="${item.getClassName()}" />
  <g:render template="../guiGen/showTemplates/${templateName}"
            model="[rmNode: item, archetype: archetype, template: template, fieldPaths: fieldPaths]" />
</g:each>