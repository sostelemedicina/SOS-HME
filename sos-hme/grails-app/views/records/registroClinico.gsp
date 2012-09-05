<?xml version="1.0" encoding="ISO-8859-1" ?>
<html>
  <head>
    <meta name="layout" content="ehr" />
    <title>
      <g:message code="trauma.title.registroClinico" />
    </title>
  </head>
  <body>
    <div id="nivel3">
    <h2><g:message code="trauma.title.registroClinico" /></h2>
    <div class="ehrform">
      <%--
      ${sections}
      imprime: {ACCIONES=[ACCIONES-adm_sust], DIAGNOSTICO=[DIAGNOSTICO-diagnosticos]}
      --%>
      <g:each in="${sections.keySet()}" var="section">
        <g:if test="${sections[section].size()==1}">
          <g:hasContentItemForTemplate episodeId="${episodeId}" templateId="${sections[section][0]}">

            <%-- it.hasItem proviene de la etiqueta hasContentItemForTemplate --%>

            <g:if test="${it.hasItem}">
              <%-- No deberia ir a edit, deberia ir a show y si quiere en show hace edit.
              <g:link controller="guiGen" action="generarShow" id="${it.itemId}" params="[mode:'edit']">
              --%>
              <g:link controller="guiGen" action="generarShow" id="${it.itemId}"><g:message code="${'section.'+section}" /></g:link>
              (*)
            </g:if>

            <g:else>
            
              <%-- Si el regsitro esta completo, no se puede editar --%>

              <g:isIncompleteRecord episodeId="${episodeId}">
	              <g:if test="${it.answer}">
	                <g:link controller="guiGen" action="generarTemplate" params="[templateId: sections[section][0]]"><g:message code="${'section.'+section}" /></g:link>
	              </g:if>
	              <g:else>
	                <g:message code="${'section.'+section}" />
	              </g:else>
              </g:isIncompleteRecord>
              
            </g:else>
          </g:hasContentItemForTemplate>
          <br/><br/>
        </g:if>
        <g:else>
          <g:message code="${'section.'+section}" />:
          <ul>
            <g:each in="${sections[section]}" var="subSection">
               <li>
                 <g:hasContentItemForTemplate episodeId="${episodeId}" templateId="${subSection}">
                   <%--${it}--%>
                   <g:if test="${it.hasItem}">
                     <%-- No deberia ir a edit, deberia ir a show y si quiere en show hace edit.
                     <g:link controller="guiGen" action="generarShow" id="${it.itemId}" params="[mode:'edit']">
                     --%>
                     <g:link controller="guiGen" action="generarShow" id="${it.itemId}"><g:message code="${'section.'+subSection}" /></g:link>
                     (*)
                   </g:if>
                   <g:else>
                   
                     <%-- Si el regsitro esta completo, no se puede editar --%>
                     <g:isIncompleteRecord episodeId="${episodeId}">
                       <g:if test="${it.answer}">
                         <g:link controller="guiGen" action="generarTemplate" params="[templateId: subSection]"><g:message code="${'section.'+subSection}" /></g:link>
                       </g:if>
                       <g:else>
                         <g:message code="${'section.'+subSection}" />
                       </g:else>
                     </g:isIncompleteRecord>
                     
                   </g:else>
                 </g:hasContentItemForTemplate>
               </li>
            </g:each>
          </ul>
        </g:else>
      </g:each>
      
    </div>
    </div>
  </body>
</html>