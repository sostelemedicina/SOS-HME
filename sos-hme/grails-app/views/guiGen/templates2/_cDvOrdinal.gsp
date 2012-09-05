<%@ page import="org.openehr.am.archetype.constraintmodel.*" %>

<%--
<b>${cDvOrdinal.path()}</b>
--%>

<%
// refPath es nulo si no viene de un arch internal ref

def _refPath = ''
if (refPath) _refPath = refPath

%>

<!-- armo lista de valores con textos -->
<g:set var="labels" value="${[]}" />
<g:each in="${cDvOrdinal.list.sort{ it.value }}" var="ordinal">
  <g:set var="archetypeTerm" value="${archetype.ontology.termDefinition(session.locale.language, ordinal.symbol.codeString)}" />
    <g:if test="${!archetypeTerm}">
    El termino con codigo [${ordinal.symbol.codeString}] no esta definido en el arquetipo, posiblemente el termino no esta definido para el lenguaje seleccionado.<br/>
  </g:if>
  <g:else>
    <% labels << archetypeTerm.items.text %>
  </g:else>
</g:each>

<g:set var="control" value="${template.getField( archetype.archetypeId.value, cDvOrdinal.path() )?.getControlByPath(cDvOrdinal.path())}" />

<%-- TODO: seleccionar valor por defecto. --%>

<g:if test="${control && control.type=='radioGroup'}">

  <g:set var="values" value="${cDvOrdinal.list.sort{ it.value }.symbol.codeString}" />
  <g:set var="i" value="${0}" />
  <g:each in="${values}" var="value">
    <label id="id_${value}"><!-- necesita id por el CSS -->
      <input type="radio" value="${value}" name="${archetype.archetypeId.value +_refPath+ cDvOrdinal.path()}" />
      ${labels[i]}
    </label>
    <% i++ %>
  </g:each>
  
  <%--
  <g:radioGroup name="${archetype.archetypeId.value +_refPath+ cDvOrdinal.path()}" values="${cDvOrdinal.list.sort{ it.value }.symbol.codeString}" labels="${labels}">
    <label>
      ${it.radio} <g:message code="${it.label}" />
    </label>
    <br/>
  </g:radioGroup>
  --%>
</g:if>
<g:else>
  <g:select from="${labels}"
            keys="${cDvOrdinal.list.sort{ it.value }.symbol.codeString}"
            name="${archetype.archetypeId.value +_refPath+ cDvOrdinal.path()}"
            noSelection="${['':'']}" />
</g:else>
