<%@ page import="org.openehr.am.archetype.constraintmodel.*" %>

<%--
in: cPrimitiveObject (${cPrimitiveObject.class}) (${cPrimitiveObject.rmTypeName})<br/>

<b>${cPrimitiveObject.path()}</b><br/>
--%>

<%
// refPath es nulo si no viene de un arch internal ref

def _refPath = ''
if (refPath) _refPath = refPath

%>

<%-- PRIMITIVE REFPATH: ${_refPath}<br/> --%>

<%--
Primitive: ${cPrimitiveObject.rmTypeName}<br/><br/>
--%>

<g:if test="${cPrimitiveObject.rmTypeName == 'DvBoolean'}">
<%-- Si assumed value es null o false deschequea, si es true chequea. --%>
<%-- Modelarlo como radio me genera un problema cuando hay mas de uno porquen
no me deja seleccionar mas de uno a la vez, porque todos los radios tienen
el mismo name.
    <label>
<span><g:message code="label.boolean.true" /></span>
<g:radio name="${archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()}"
value="true"
checked="${cPrimitiveObject.item.hasAssumedValue() && cPrimitiveObject.item.assumedValue}" />
</label>
<br/>
<label>
<span><g:message code="label.boolean.false" /></span>
<g:radio name="${archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()}"
value="false"
checked="${cPrimitiveObject.item.hasAssumedValue() && !cPrimitiveObject.item.assumedValue}" />
</label>
--%>
<%-- modelado como select --%>
  <select name="${archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()}">
    
    <option value=""></option> 
    
   

    <option value="false" ${((cPrimitiveObject.item.hasAssumedValue() && !cPrimitiveObject.item.assumedValue || params[archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()]=="false")?'selected="true"':'')}>
    <g:message code="label.boolean.false" />
    </option>
    <option value="true" ${((cPrimitiveObject.item.hasAssumedValue() && cPrimitiveObject.item.assumedValue || params[archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()]=="true")?'selected="true"':'')}>
    <g:message code="label.boolean.true" />
    </option>
  </select>
</g:if>

<g:if test="${cPrimitiveObject.rmTypeName == 'Integer'}">

<%-- SI el RMTYPE es Integer, el primitiveobject.item es CInteger. --%>
  <g:if test="${cPrimitiveObject.item.list != null}">
<%-- TODO --%>
  </g:if>

<%-- FIXME: hacer un mostrador general de Interval con un template --%>
  <g:if test="${cPrimitiveObject.item.interval != null}">
    (
    <g:if test="${cPrimitiveObject.item.interval.lower != null}">${cPrimitiveObject.item.interval.lower}</g:if>
    <g:else>*</g:else>
    ..
    <g:if test="${cPrimitiveObject.item.interval.upper != null}">${cPrimitiveObject.item.interval.upper}</g:if>
    <g:else>*</g:else>
    )
  </g:if>
${cPrimitiveObject.item.assumedValue}
  <input type="text" name="${archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()}"
         value="${params[archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()]}" />
</g:if>

<g:if test="${cPrimitiveObject.rmTypeName == 'DvDate'}">
<%-- TODO: considerar estos tipos de restricciones
${cPrimitiveObject.item.interval}
${cPrimitiveObject.item.list}
--%>
<%-- FIXME: ajustar patron para mostrar bien al ingreso de la fecha en espanol --%>
<%-- como el control de entrada no es un input no necesito mostrar el formato de como se deberia ingresar --%>
<%--(${cPrimitiveObject.item.pattern})--%>


 

<%--  <g:datePicker name="${archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()}" value="${new Date()}" precision="day" /> --%>

 <p> <input name="${archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()}" type="text" class="DateSos"> </p>


</g:if>

<g:if test="${cPrimitiveObject.rmTypeName == 'DvDateTime'}">
<%-- TODO: considerar estos tipos de restricciones
${cPrimitiveObject.item.interval}
${cPrimitiveObject.item.list}
--%>
<%-- FIXME: ajustar patron para mostrar bien al ingreso de la fecha en espanol --%>
<%-- como el control de entrada no es un input no necesito mostrar el formato de como se deberia ingresar --%>
<%-- (${cPrimitiveObject.item.pattern}) --%>

<%--  <g:datePicker name="${archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()}" value="${new Date()}" precision="minute" />--%>
 
  
 <p> <input name="${archetype.archetypeId.value +_refPath+ cPrimitiveObject.path()}" type="text" class="DateTimeSos"> </p>

</g:if>

<%-- el multiple se muestra en complexobject
<g:parentElementIsMultiple archetypeId="${archetype.archetypeId.value}" nodePath="${cPrimitiveObject.path()}">
<div class="multiple">
<a href="#" class="clone">Agregar entrada</a>
</div>
</g:parentElementIsMultiple>
--%>
