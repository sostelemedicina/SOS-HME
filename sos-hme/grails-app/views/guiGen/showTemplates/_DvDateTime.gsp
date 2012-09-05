<%--

in: dataValue (DvDateTime)

<h1>DvDateTime</h1>

--%>

<g:if test="${mode && mode=='edit'}">

  <%-- ${dataValue.toDate()} --%>

  <g:hasErrors bean="${dataValue}">
  
    <div class="error">
      <g:renderErrors bean="${dataValue}" as="list" />
    </div>
    
    <%-- Me fijo si tiene error para saber que valor mostrar, siempre sera el valor ingresado --%>
    <g:set var="selectedValue" value="${dataValue.errors.getFieldError('value').rejectedValue}" />
   
  </g:hasErrors>
  
  <g:set var="aomNode" value="${archetype.node(pathFromOwner)}" />
  
 <%-- <g:datePicker name="${archetype.archetypeId.value +refPath+ aomNode.path()}"
                value="${dataValue.toDate()}"
                precision="minute" />
--%>
   
  <p> <input name="${archetype.archetypeId.value +refPath+ aomNode.path()}" value="${g.format(date: dataValue.toDate())}" type="text" class="DateTimeSos" /> </p>


</g:if>
<g:else>
  
  ${dataValue.value}
</g:else>
