<%--

in: dataValue (DvBoolean)

<h1>DvBoolean</h1>
--%>

<div class="DV_BOOLEAN">

<g:if test="${mode && mode=='edit'}">

  <%-- cPrimitiveObject --%>
  <g:set var="aomNode" value="${archetype.node(pathFromOwner+'/value')}" />
  <%--
  ${pathFromOwner}<br/>
  ${aomNode.getClass()}<br/>
  --%>
  
  <g:set var="selectedValue" value="${dataValue.value}" />
  
  <g:hasErrors bean="${dataValue}">
  
    <div class="error">
      <g:renderErrors bean="${dataValue}" as="list" />
    </div>
    
    <%-- Me fijo si tiene error para saber que valor mostrar, siempre sera el valor ingresado --%>
    <%-- no deberia agarrar el rejected value???
    <g:set var="selectedValue" value="${dataValue.errors.getFieldError('value').rejectedValue}" />
    --%>
  </g:hasErrors>

<%--
Valor show bool es null?: ${dataValue.value==null}<br/> 
--%>

  <select name="${archetype.archetypeId.value +refPath+ aomNode.path()}">
    <option value="" ${((selectedValue==null)?'selected="true"':'')}></option>
    <option value="true" ${((selectedValue==true)?'selected="true"':'')}>
      <g:message code="label.boolean.true" />
    </option>
    <option value="false" ${((selectedValue==false)?'selected="true"':'')}>
      <g:message code="label.boolean.false" />
    </option>
  </select>

</g:if>
<g:else>

<%--
Valor show bool es null?: ${dataValue.value==null}<br/> 
--%>

  <g:if test="${dataValue.value}">
    <g:message code="label.boolean.true" />
  </g:if>
  <g:else>
    <g:message code="label.boolean.false" />
  </g:else>
</g:else>

</div>