<%@ page import="org.openehr.am.archetype.constraintmodel.*" %>
<%@ page import="tablasMaestras.OpenEHRConcept" %>

<%--
<b>${cDvQuantity.path()}</b>
--%>

<%-- TODO: ponerlo en una taglib --%>
<%-- no muestro la propiedad...
<g:if test="${cDvQuantity.property}">
  <%
    def concepts = OpenEHRConcept.withCriteria {
      eq('conceptId', cDvQuantity.property.codeString)
      eq('lang', session.locale.language)
    }
    if (concepts.size()==1)
      print concepts[0].rubric
    else
      print 'TODO: pedirselo al servicio de terminologia "' + cDvQuantity.property + '"'
  %>
</g:if>
--%>

<%
// refPath es nulo si no viene de un arch internal ref

def _refPath = ''
if (refPath) _refPath = refPath

%>

<%-- FIXME: esto funciona para un par unidad-rango, si hubieran varias
            unidades el rango que se muestra deberia variar en funcion
            de la unidad seleccionada. --%>
            
<%-- FIXME: el rango para la magnitud depende de la unidad seleccionada, 
            y deberia cambiar al cambiar la unidad (si hay mas de una 
            unidad para seleccionar). --%>
            
<g:set var="interval" value="${null}" />
<g:each in="${cDvQuantity.list}" var="item">

  <%-- FIXME: ojo que el intervalo depende de la unidad que se seleccione, me gustaria 
              que al seleccionar una unidad u otra, mediante JS muestre el rango correspondiente, 
              si es que existe. --%>
  <g:if test="${item.magnitude != null}">
    <g:set var="interval" value="${item.magnitude}" />
  </g:if>
  <%-- units: ${item.units} --%>
</g:each>

<%-- muestra (min..max) para la magnitude, si hay restriccion --%>
<g:if test="${interval != null}">
  <g:if test="${interval.lower != null}">
    <g:set var="lower" value="${interval.lower}" />
  </g:if>
  <g:else>
    <g:set var="lower" value="*" />
  </g:else>
  <g:if test="${interval.upper != null}">
    <g:set var="upper" value="${interval.upper}" />
  </g:if>
  <g:else>
    <g:set var="upper" value="*" /> 
  </g:else>
  (${lower}..${upper})
</g:if>
<g:else>(*..*)</g:else>

<input type="text" name="${archetype.archetypeId.value +_refPath+ cDvQuantity.path()}/magnitude" value="" />

<%-- ${cDvQuantity.list.units*.encodeAsHTML()} --%>
<%-- TODO: seleccionar unidad por defecto --%>

<%-- Muestra las unidades como una lista para seleccionar --%>
<g:if test="${cDvQuantity.list}">
  <%-- Si tiene un solo elemento lo pongo como texto, no hay nada que seleccionar --%>
  <g:if test="${cDvQuantity.list.units.size()==1}">
  
    <g:set var="constraint" value="${template.getField( archetype.archetypeId.value, cDvQuantity.path() )?.getConstraintByPath(cDvQuantity.path()+'/units')}" />
    <g:if test="${constraint}">
      ${constraint.process( cDvQuantity.list.units[0] )} <!-- (sobre-escrita) -->
    </g:if>
    <g:else>
      ${cDvQuantity.list.units[0]}
    </g:else>
  </g:if>
  <g:else>
    <%--
    ${cDvQuantity.list.units.size()}
    --%>
    
    <%-- FIXME: que pasa si no tiene ninguna unidad? capaz hay que ver que tipo de propiedad es y cargar las unidades para esa propiedad, p.e. volumen --%>
    <g:select from="${cDvQuantity.list.units}"
              name="${archetype.archetypeId.value +_refPath+ cDvQuantity.path()}/units"
              noSelection="${['':'']}" />
  </g:else>
</g:if>