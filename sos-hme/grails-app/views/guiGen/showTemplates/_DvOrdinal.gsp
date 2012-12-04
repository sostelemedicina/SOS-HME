<%--

in: dataValue (DvOrdinal)

<h1>DvOrdinal</h1>

--%>

<g:if test="${mode && mode=='edit'}">

  <g:set var="aomNode" value="${archetype.node(pathFromOwner)}" />
  <%-- cDvordinal: ${aomNode.getClass()}<br/> --%>
  
  <g:set var="selectedValue" value="${dataValue?.symbol?.definingCode?.codeString}" />
  
  <%-- Codigo similar a _cDvOrdinal --%>
  <g:set var="labels" value="${[]}" />
  <g:each in="${aomNode.list.sort{ it.value }}" var="ordinal">
      <g:set var="archetypeTerm" value="${archetype.ontology.termDefinition(session.locale.language, ordinal.symbol.codeString)}" />
      <g:if test="${!archetypeTerm}">
        El termino con codigo [${ordinal.symbol.codeString}] no esta definido en el arquetipo,
        posiblemente el termino no esta definido para el lenguaje seleccionado.<br/>
      </g:if>
      <g:else>
        <% labels << archetypeTerm.items.text %>
      </g:else>
  </g:each>
  
  <g:set var="values" value="${aomNode.list.sort{ it.value }.symbol.codeString}" />
  
  <g:set var="control" value="${template.getField( archetype.archetypeId.value, aomNode.path() )?.getControlByPath(aomNode.path())}" />
  <g:if test="${control && control.type=='radioGroup'}">
	<g:set var="i" value="${0}" />
	<g:each in="${values}" var="value">
	  <label id="id_${value}">
	    <input type="radio"
	           value="${value}"
	           name="${archetype.archetypeId.value +refPath+ aomNode.path()}"
	           ${((value==selectedValue) ? 'checked="true"' : '')} />
	    ${labels[i]}
	  </label>
	  <% i++ %>
	</g:each>
  </g:if>
  <g:else>
    
   
    <g:select
      from="${labels}"
      keys="${values}"
      name="${archetype.archetypeId.value +refPath+ aomNode.path()}"
      noSelection="${['':'']}"
      value="${dataValue?.symbol?.definingCode?.codeString}" />
   </g:else>

</g:if>
<g:else>
  <%-- no me interesa mostrar el indice de la opcion que eligio --%>
  <%-- ${dataValue.value} --%>
  <g:render template="../guiGen/showTemplates/DvCodedText"
             model="[dataValue: dataValue.symbol, archetype: archetype]" />
</g:else>
