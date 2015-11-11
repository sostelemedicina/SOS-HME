<%--

in: dataValue (DvText)
in: pathFromOwner paht del nodo padre con el nombre del atributo al final

  <h1>DvText</h1>
  
--%>

<g:if test="${mode && mode=='edit'}">

  <%--
  <g:set var="aomNode" value="${archetype.node(pathFromOwner)}" />
  --%>
  
  <%-- FIXME: refPath deberia venir si hay una internal ref --%>
  <textarea name="${archetype.archetypeId.value +refPath+ pathFromOwner}">${dataValue.value}</textarea>
</g:if>
<g:else>


  <%--
  <textarea disabled name="${archetype.archetypeId.value +refPath+ pathFromOwner}">${dataValue.value}</textarea>
  --%>
   <textarea disabled name="${archetype.archetypeId.value +refPath+ pathFromOwner}">${dataValue.value}</textarea>
</g:else>
