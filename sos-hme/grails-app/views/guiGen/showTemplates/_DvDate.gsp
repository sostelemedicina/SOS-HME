<%--

in: dataValue (DvDate)

<h1>DvDate</h1>

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
                precision="day" />
--%>

  <%
  //Edit by Armando Prieto
  //Si dataValue.toDate() devuelve 'null' se muestra vacio "' '" en pantalla
  def valorDate = ""
  if(dataValue.toDate() !=null){
  valorDate = g.formatDate(date: dataValue.toDate(), format: g.message(code: 'default.date.format1'))
  }
  %>

   <p> <input name="${archetype.archetypeId.value +refPath+ aomNode.path()}" value="${valorDate}" type="text" class="DateSos" /> </p>

</g:if>
<g:else>

  <g:formatDate date="${dataValue.toDate()}" format="${g.message(code: 'default.date.format1')}" />
  
</g:else>
