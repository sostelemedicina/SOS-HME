<%@ page import="org.openehr.am.openehrprofile.datatypes.text.*" %>
<%@ page import="org.openehr.am.openehrprofile.datatypes.basic.*" %>
<%@ page import="org.openehr.am.openehrprofile.datatypes.quantity.*" %>

<%--
// in: cDomainType (${cDomainType.class}) (${cDomainType.rmTypeName})<br/>

<b>${cDomainType.path()}</b>

CDomainType<br/>
--%>

<%
// refPath es nulo si no viene de un arch internal ref

def _refPath = ''
if (refPath) _refPath = refPath

%>

<g:if test="${cDomainType instanceof CCodePhrase}">
    <g:render template="../guiGen/templates2/cCodePhrase"
              model="[cCodePhrase: cDomainType,
                      archetype: archetype,
                      archetypeService:archetypeService,
                      refPath: refPath,
                      params: params]" />
</g:if>
<g:if test="${cDomainType instanceof CDvOrdinal}">
    <g:render template="../guiGen/templates2/cDvOrdinal"
              model="[cDvOrdinal: cDomainType,
                      archetype: archetype,
                      archetypeService:archetypeService,
                      refPath: refPath,
                      params: params]" />
</g:if>
<g:if test="${cDomainType instanceof CDvQuantity}">
    <g:render template="../guiGen/templates2/cDvQuantity"
              model="[cDvQuantity: cDomainType,
                      archetype: archetype,
                      archetypeService:archetypeService,
                      refPath: refPath,
                      params: params]" />
</g:if>
