<%--

in: dataValue (DvQuantity)

<h1>DvQuantity</h1>
<h1>X${aomNode}X</h1>
--%>

<%--
${pathFromOwner}
--%>
<g:if test="${mode && mode=='edit'}">

  <g:set var="aomNode" value="${archetype.node(pathFromOwner)}" />
  
  <%-- cDvQuantity: ${aomNode.getClass()}<br/> --%>
  <g:set var="selectedValueMagnitude" value="${dataValue.magnitude}" />
  <g:set var="selectedValueUnits" value="${dataValue.units}" />
  
  <g:hasErrors bean="${dataValue}">
    <div class="error">
      <g:renderErrors bean="${dataValue}" as="list" />
    </div>
    
    <%-- Me fijo si tiene error para saber que valor mostrar, siempre sera el valor ingresado --%>
    <g:if test="${dataValue.errors.hasFieldErrors('magnitude')}">
      <g:set var="selectedValueMagnitude" value="${dataValue.errors.getFieldError('magnitude')?.rejectedValue}" />
    </g:if>
    <g:if test="${dataValue.errors.hasFieldErrors('units')}">
      <g:set var="selectedValueUnits" value="${dataValue.errors.getFieldError('units')?.rejectedValue}" />
    </g:if>

  </g:hasErrors>

  <%-- FIXME: esto funciona para un par unidad-rango, si hubieran varias
            unidades el rango que se muestra deberia variar en funcion
            de la unidad seleccionada. --%>
  <g:set var="interval" value="${null}" />
  <g:each in="${aomNode.list}" var="item">
    <g:if test="${item.magnitude != null}">
      <g:set var="interval" value="${item.magnitude}" />
    </g:if>
    <%-- units: ${item.units} --%>
  </g:each>
  
  <g:if test="${interval != null}">
  (
    <g:if test="${interval.lower != null}">${interval.lower}</g:if>
    <g:else>*</g:else>
  ..
    <g:if test="${interval.upper != null}">${interval.upper}</g:if>
    <g:else>*</g:else>
  )
  </g:if>
  <g:else>(*..*)</g:else>
  
  
  <%-- magnitud --%>
  <input type="text"
         name="${archetype.archetypeId.value +refPath+ aomNode.path()}/magnitude"
         value="${selectedValueMagnitude}" />
  
  <%-- unidades --%>
  <g:if test="${aomNode.list}">
    <%-- Si tiene un solo elemento lo pongo como texto, no hay nada que seleccionar --%>
    <g:if test="${aomNode.list.units.size()==1}">
    
      <g:set var="constraint" value="${template.getField( archetype.archetypeId.value, aomNode.path() )?.getConstraintByPath(aomNode.path()+'/units')}" />
      <g:if test="${constraint}">
        ${constraint.process( aomNode.list.units[0] )} <!-- (sobre-escrita) -->
      </g:if>
      <g:else>
        ${aomNode.list.units[0]}
      </g:else>
    </g:if>
    <g:else>
      <%-- FIXME: que pasa si no tiene ninguna unidad? capaz hay que ver que tipo de propiedad es y cargar las unidades para esa propiedad, p.e. volumen --%>
      <g:select from="${aomNode.list.units}"
                name="${archetype.archetypeId.value +refPath+ aomNode.path()}/units"
                noSelection="${['':'']}"
                value="${selectedValueUnits}" />
    </g:else>
  </g:if>
</g:if>
<g:else>
  ${dataValue.magnitude}
  
  <%-- parent es ELEMENT --%>
  <g:set var="aomNode" value="${archetype.node(parent.path+'/value')}" />
  
  <%-- Si la unidad esta sobreescrita, muestro su transformacion --%>
  <%-- FIXME: deberia encontrar la sobreescrita correspondiente a la unidad
              guardada dataValue.units, no pedirle la que tenga en el nodo
              del arquetipo (podria tener muchas unidades sobreescritas).
   --%>
  <%-- aomNode viene del ELEMENT padre --%>
  
  <g:if test="${aomNode.list.units.size()==1}">
    <g:set var="constraint" value="${template.getField( archetype.archetypeId.value, aomNode.path() )?.getConstraintByPath(aomNode.path()+'/units')}" />
    <g:if test="${constraint}">
      ${constraint.process( aomNode.list.units[0] )} <!-- (sobre-escrita) -->
    </g:if>
    <g:else>
      ${dataValue.units}
    </g:else>
  </g:if>
  <g:else>
    ${dataValue.units}
  </g:else>
</g:else>