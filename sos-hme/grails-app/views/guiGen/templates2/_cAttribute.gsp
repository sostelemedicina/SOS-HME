<%@ page import="org.openehr.am.archetype.constraintmodel.*" %>

<%--
// in: cAttribute (${cAttribute.rmAttributeName}) (${cAttribute.class})<br/>
// in: refPath path del nodo arch internal ref que referencia al CObject duenio de este atributo.
--%>
<%
// refPath es nulo si no viene de un arch internal ref

def _refPath = ''
if (refPath) _refPath = refPath

%>
<%-- ATTR REFPATH: ${_refPath}<br/>
<g:if test="${cAttribute.rmAttributeName.startsWith('media_type')}">
      <a>DIME CUANDO ENTRO AQUI ${cAttribute.children}</a>
</g:if>
 --%>
  <g:render template="../guiGen/templates2/cObject"
            var="cObject"
            collection="${cAttribute.children}"
            model="[archetype: archetype,
                    archetypeService: archetypeService,
                    refPath: refPath,
                    params: params]" />