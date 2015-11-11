<%@ page import="org.openehr.am.archetype.constraintmodel.*" %>
<%@ page import="org.openehr.am.archetype.constraintmodel.primitive.*" %>

<%--
in: cObject (${cObject.class}) (${cObject.rmTypeName})<br/>
--%>

<%--
Object: ${cObject.rmTypeName}<br/><br/>
--%>

<%
// refPath es nulo si no viene de un arch internal ref

def _refPath = ''
if (refPath) _refPath = refPath

%>
  <%-- TODO: se puede simplificar a una sola llamada en funcion de la clase halla el nombre del template --%>
  <g:if test="${cObject instanceof CComplexObject}">
      <g:render template="../guiGen/templates2/cComplexObject"
                model="[cComplexObject: cObject,
                        archetype: archetype,
                        archetypeService:archetypeService,
                        refPath: refPath,
                        params: params]" />
  </g:if>
  <g:if test="${cObject instanceof CDomainType}">

      <g:render template="../guiGen/templates2/cDomainType"
                model="[cDomainType: cObject,
                        archetype: archetype,
                        archetypeService:archetypeService,
                        refPath: refPath,
                        params: params]" />
  </g:if>
  <g:if test="${cObject instanceof CPrimitiveObject}">

      <g:render template="../guiGen/templates2/cPrimitiveObject"
                model="[cPrimitiveObject: cObject,
                        archetype: archetype,
                        archetypeService:archetypeService,
                        refPath: refPath,
                        params: params]" />
  </g:if>
  
  <%-- FIXME: http://code.google.com/p/open-ehr-sa/issues/detail?id=2
  <g:if test="${cObject instanceof ArchetypeSlot}">
      <g:render template="../guiGen/templates2/archetypeSlot"
                model="[archetypeSlot: cObject,
                        archetype: archetype,
                        archetypeService:archetypeService,
                        refPath: refPath,
                        params: params]" />
  </g:if>
  --%>
  
  <g:if test="${cObject instanceof ArchetypeInternalRef}">

      <g:render template="../guiGen/templates2/archetypeInternalRef"
                model="[ref: cObject,
                        archetype: archetype,
                        archetypeService:archetypeService,
                        refPath: refPath,
                        params: params]" />
  </g:if>
