<%@ page import="com.thoughtworks.xstream.XStream" %><%@ page import="org.openehr.am.openehrprofile.datatypes.text.CCodePhrase" %>

<%--
  in: dataValue (DvCodedText)
  in: aomNode (viene del Element, puede ser a un internal ref o el aomNode del Element.value)
  <h1>DvCodedText</h1>
--%>

<g:if test="${mode && mode=='edit'}">
  <%--
  pathFromowner: ${pathFromOwner} <br/>
  node (CComplexObject): ${archetype.node(pathFromOwner)} <br/>
  node2 (CodePhrase): ${archetype.node(pathFromOwner+"/defining_code")} <br/>
  value: ${dataValue.value} <br/>
  <g:if test="${dataValue && dataValue.value}">< %- - Hay valor, muestro el select con ese valor seleccionado - - % >
    creo que como viene en params, el valor se muestra automaticamente de ahi en cCodePhrase...
  </g:if>
  --%>
  <%--
    FIXME:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    ERROR: si tengo valores multiples deberia generar todo el edit desde el RM,
           el AOM no sabe cuantos nodos del RM hay creados.<br/>
  --%>
  <%--
    aomNode.path(): ${aomNode.path()}<br/>
    pathFromOwner: ${pathFromOwner}<br/>
    <textarea style="width: 700px; height: 200px;">
    ${new XStream().toXML(aomNode.attributes[0].children.find{it instanceof CCodePhrase})}
    </textarea>
  --%>
  <g:def var="cCodePhrase" value="${aomNode?.attributes[0].children.find{it instanceof CCodePhrase}}" />
  <%-- Prueba pedir el CCodePhrase tanto si el padre es arch internal ref como si es un CObject cualquiera.
  <g:if test="${!refPath}">NO ES INTERNAL REF: ${refPath}
    aomNode.attributes[0].children.each{ print it.getClass().toString()+"<br/>" }
    def pepepe = aomNode.attributes[0].children.find{it instanceof CCodePhrase}
  </g:if>
  <g:else>ES INTERNAL REF: ${refPath}
  <%
    def pepepexx = aomNode.attributes[0].children.find{it instanceof CCodePhrase}
  %>
  </g:else>
  --%>
  <g:if test="${dataValue.value}">
    <g:render template="../guiGen/templates2/cCodePhrase"
        model="[cCodePhrase: cCodePhrase,
                archetype: archetype,
                refPath: refPath,
                selectedValue: dataValue?.definingCode?.codeString]" />
    <%--<textarea style="width: 700px; height: 200px;">${new XStream().toXML(aomNode)}</textarea>--%>
  </g:if>
  <g:else>
   <g:render template="../guiGen/templates2/cCodePhrase"
        model="[cCodePhrase: cCodePhrase,
                archetype: archetype,
                refPath: refPath,
                selectedValue: dataValue?.definingCode?.codeString]" />
   <%--<textarea style="width: 700px; height: 200px;">${new XStream().toXML(pepepexx)}</textarea>--%>
  </g:else>
  
</g:if>
<g:else>

  ${dataValue.value}

</g:else>
