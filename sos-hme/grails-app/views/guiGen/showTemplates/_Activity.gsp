<%@ page import="com.thoughtworks.xstream.XStream" %>
<%--

in: rmNode (Activity)

Activity<br/>

--%>

<g:if test="${mode && mode=='edit'}">
  <g:if test="${!rmNode.description}"><%-- Si no hay estructura RM para mostrar, voy por el AOM --%>
    
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
              model="[rmNode: rmNode.description,
                      archetype: archetype]" />
  </g:else>
</g:if>
<g:else>
  <g:render template="../guiGen/showTemplates/ItemStructure"
            model="[rmNode: rmNode.description,
                    archetype: archetype]" />
</g:else>
