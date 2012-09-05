<%--

in: dataValue (DvMultimedia)

<h1>DvMultimedia</h1>

TODO: edicion de multimedia!

--%>

<img src="${request.contextPath}/records/fetch_mm/${dataValue.id}" />
<br/><br/>
<g:link controller="records" action="fetch_mm" id="${dataValue.id}" target="_blank" class="right"><g:message code="image.action.fullSize" /></g:link>
