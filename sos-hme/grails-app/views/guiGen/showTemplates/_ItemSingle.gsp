<%--

in: rmNode (ItemSingle)

--%>

<g:set var="aomNode" value="${archetype.node(rmNode.path)}" />

<g:set var="childNodeID" value="${aomNode.attributes[0].getChildren()[0].nodeID}" />

<g:hasErrors bean="${rmNode}">
  <div class="error">
    <g:renderErrors bean="${rmNode}" as="list" />
  </div>
</g:hasErrors>

<g:render template="../guiGen/showTemplates/Element"
          model="[rmNode: rmNode.item, pathFromParent: rmNode.path+'/item['+childNodeID+']', archetype: archetype, template: template]" />