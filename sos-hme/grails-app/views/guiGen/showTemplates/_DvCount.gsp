<%@ page import="org.openehr.am.archetype.constraintmodel.*" %>
<%--

in: dataValue (DvCount)

<h1>DvCount</h1>

--%>

<g:if test="${mode && mode=='edit'}">

  <g:set var="selectedValue" value="${dataValue.magnitude}" />

  <g:hasErrors bean="${dataValue}">
  
    <div class="error">
      <g:renderErrors bean="${dataValue}" as="list" />
    </div>
    
    <%-- Me fijo si tiene error para saber que valor mostrar, siempre sera el valor ingresado --%>
    <g:set var="selectedValue" value="${dataValue.errors.getFieldError('magnitude').rejectedValue}" />

  </g:hasErrors>

  <g:set var="aomNode" value="${archetype.node(pathFromOwner)}" />
  
  <g:if test="${aomNode instanceof CComplexObject}">
    <g:if test="${aomNode.attributes?.size()==0}"><%-- no hay restricciones --%>
      (*..*)
    </g:if>
    <g:else>
      <%-- hay restriccion para magnitude? --%>
      <g:set var="cattr" value="${aomNode.attributes.find{it.rmAttributeName=='magnitude'}}" />
      <g:if test="${cattr}">
        <g:set var="interval" value="${cattr.children[0].item.interval}" />
        (${interval.lower}..${interval.upper})
      </g:if>
      <g:else>
        (*..*)
      </g:else>
    </g:else>
  </g:if>
  <g:else>
    <%-- Para DvCount la restriccion es siempre CComplexObject --%>
  </g:else>
  
  <input type="text"
         name="${archetype.archetypeId.value +refPath+ aomNode.path()}"
         value="${selectedValue}" />  

</g:if>
<g:else>
  ${dataValue.magnitude}
</g:else>
