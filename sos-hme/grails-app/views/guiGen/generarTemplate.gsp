<?xml version="1.0" encoding="ISO-8859-1" ?>
<html>
  <head>
    
    <meta name="layout" content="ehr" />
    
    <link rel="stylesheet" href="${createLinkTo(dir:'css' ,file:'formularios.css')}" />

    <%-- Estilo propio por template, by Armando--%>
    <link rel="stylesheet" href="${createLinkTo(dir:'css/template' ,file:template.id+'.css' ) }" />
    <%-- Script por template, by Armando--%>
    <script type="text/javascript" src="${createLinkTo(dir:'js/template' ,file:template.id+'.js' ) }"></script>
   

  </head>
  <body>

<div id="nivel3">
    

    
    <%-- ${archetypeList} --%>
    
    <%-- SUBMENU DE SECCIONES SI EXISTEn --%>
    <g:if test="${subsections.size()>1}">
      <div id="menu4">
        <ul>
          <g:each in="${subsections}" var="subsection">
            <li ${((template.id==subsection)?"class='selected'":'')}>
	          <g:hasContentItemForTemplate episodeId="${episodeId}" templateId="${subsection}">
	            <g:if test="${it.hasItem}">
	              <g:link controller="guiGen" action="generarShow" id="${it.itemId}" class="contextoEhr"><g:message code="${'section.'+subsection}" /> <img src="${createLinkTo(dir:'images' ,file:'check-icon.png')}" style="width: 15px; height: auto;"/></g:link>
	            </g:if>
	            <g:else>
		          <g:link controller="guiGen" action="generarTemplate" params="[templateId:subsection]" class="contextoEhr">
		            <g:message code="${'section.'+subsection}" />
		          </g:link>
		    </g:else>
	          </g:hasContentItemForTemplate>
	        </li>
          </g:each>
        </ul>
      </div>
    </g:if>

    <div id="contenido">
        <g:form action="save" class="ehrform" method="post" enctype="multipart/form-data">

          <input type="hidden" name="templateId" value="${template.id}" />
              <!-- TODO: sacar, es solo para test -->
          <%-- TemplateId: ${template.id}<br/> --%>


          <table class="contenido" cellpadding="0" cellspacing="3" style="width: 100%;">
            <tr >
              <td colspan="2" id="content" style="width: 100%;">

                <%--Se recorre cada ARQUETIPO del TEMPLATE, tag by Armando--%>

                <g:each in="${template.getArchetypesByZone('content')}" var="archRef">

                  <%--Se recorre cada NODO del arquitipo, tag by Armando--%>

                  <g:each in="${archRef.getReferencedConstraints()}" var="node">
                    <g:if test="${node}">
                      <g:set var="strclass" value='${node.getClass().getSimpleName()}'/>
                      <g:set var="templateName" value="${strclass[0].toLowerCase()+strclass.substring(1)}" />


                      <g:render template="templates2/${templateName}"
                                model="[(templateName): node,
                                        archetype: archRef.getReferencedArchetype(),
                                        archetypeService: archetypeService,
                                        params: params]" />
                    </g:if>
                    <g:else>
                      Dice que el node es nulo<br/>
                      ArchRef: ${archRef.id}<br/>
                    </g:else>
                  </g:each>
                </g:each>
              </td>
            </tr>
            <tr>
              <td id="left" style="width: 50%;">
                <g:each in="${template.getArchetypesByZone('left')}" var="archRef">
                  <g:each in="${archRef.getReferencedConstraints()}" var="node">
                    <g:if test="${node}">
                      <g:set var="strclass" value='${node.getClass().getSimpleName()}'/>
                      <g:set var="templateName" value="${strclass[0].toLowerCase()+strclass.substring(1)}" />
                      <g:render template="templates2/${templateName}"
                                model="[(templateName): node,
                                        archetype: archRef.getReferencedArchetype(),
                                        archetypeService: archetypeService,
                                        params: params]" />
                    </g:if>
                    <g:else>
                      Dice que el node es nulo<br/>
                      ArchRef: ${archRef.id}<br/>
                    </g:else>
                  </g:each>
                </g:each>
              </td>
              <td id="right" style="width: 50%;">
                <g:each in="${template.getArchetypesByZone('right')}" var="archRef">
                  <g:each in="${archRef.getReferencedConstraints()}" var="node">
                    <g:if test="${node}">
                      <g:set var="strclass" value='${node.getClass().getSimpleName()}'/>
                      <g:set var="templateName" value="${strclass[0].toLowerCase()+strclass.substring(1)}" />
                      <g:render template="templates2/${templateName}"
                                model="[(templateName): node,
                                        archetype: archRef.getReferencedArchetype(),
                                        archetypeService: archetypeService,
                                        params: params]" />
                    </g:if>
                    <g:else>
                      Dice que el node es nulo<br/>
                      ArchRef: ${archRef.id}<br/>
                    </g:else>
                  </g:each>
                </g:each>
              </td>
            </tr>
            <tr>
              <td colspan="2" id="bottom" style="width: 100%;">
                <g:each in="${template.getArchetypesByZone('bottom')}" var="archRef">
                  <g:each in="${archRef.getReferencedConstraints()}" var="node">
                    <g:set var="strclass" value='${node.getClass().getSimpleName()}'/>
                    <g:set var="templateName" value="${strclass[0].toLowerCase()+strclass.substring(1)}" />
                    <g:render template="templates2/${templateName}"
                              model="[(templateName): node,
                                      archetype: archRef.getReferencedArchetype(),
                                      archetypeService: archetypeService,
                                      params: params]" />
                  </g:each>
                </g:each>
              </td>
            </tr>
          </table>
          <br/>

          <div class="bottom_actions">
            <g:submitButton name="doit" value="Guardar" />
          </div>
        </g:form>
    </div>
</div>
<p style="clear:both">&nbsp;</p>
  </body>
</html>