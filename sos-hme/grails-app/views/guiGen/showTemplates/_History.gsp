<%--

in: rmNode (History)

--%>

<g:each in="${rmNode.events}" var="event">
  <g:render template="../guiGen/showTemplates/Event"
            model="[rmNode: event, archetype: archetype, template: template]" />
</g:each>