<%@ page import="com.thoughtworks.xstream.XStream" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<html>
  <head>
    <meta name="layout" content="ehr" />
    

  <g:if test="${mode == 'edit'}">
    <link rel="stylesheet" href="${createLinkTo(dir:'css' ,file:'formularios.css')}" />
    <%-- Script por template, by Armando--%>
    <script type="text/javascript" src="${createLinkTo(dir:'js/template' ,file:template.id+'.js' ) }"></script>
  </g:if>
  <g:else>
    <link rel="stylesheet" href="${createLinkTo(dir:'css' ,file:'formularios_show.css')}" />
  </g:else>
  
  </head>
  <body>


    <div id="nivel3">

    <g:if test="${flash.message}">
      <div class="aviso">
      <ul><li>  <g:message code="${flash.message}" /></li></ul>
      </div>
    </g:if>

    
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
      <input id="mode" type="hidden" name="mode" value="${mode}" />
      
      <table class="contenido" cellpadding="0" cellspacing="3" style="width: 100%;">
        <tr>
           <td colspan="2" id="content" style="width: 100%;">
            <g:each in="${template.getArchetypesByZone('content')}" var="archRef">
              <g:if test="${index[archRef.id]}">
                <!-- FIXME: habria que arrancar del nodo que diga el template (p.e. esto es correcto si 
                            arranca de la raiz pero no si tiene un field con path distinta a "/"
                            http://code.google.com/p/open-ehr-gen-framework/issues/detail?id=19
                -->
                <%-- Paths de los fields del archRef para los que se debe mostrar GUI
                 El cequeo podria ser una taglib
                 el chequeo para saber si mostrar el nodo se hace con: fieldPaths.find{ rmNode.path.startsWith(it.path)} != null
                --%>
                <g:set var="fieldPaths" value="${['/']}" />
                <g:if test="${archRef.fields?.size()>0}">
                  <g:set var="fieldPaths" value="${archRef.getFieldPaths()}" />
                </g:if>
                <!-- RM -->
                
                <g:render template="../guiGen/showTemplates/Locatable"
                          model="[rmNode: index[archRef.id], fieldPaths: fieldPaths,
                                  archetype: archRef.getReferencedArchetype()]" />
              </g:if>
              <g:else><%-- No hay estructura del RM, voy por el AOM --%>
                <!-- AOM -->
                
                <g:each in="${archRef.getReferencedConstraints()}" var="node">
                  
                  <g:render template="../guiGen/templates2/cComplexObject"
                            model="[cComplexObject: node, params: params,
                                   archetype: archRef.getReferencedArchetype()]" />
                </g:each>
              </g:else>
            </g:each>
          </td>
        </tr>
        <tr>
           <td id="left" style="width: 50%;">
            <g:each in="${template.getArchetypesByZone('left')}" var="archRef">
              <g:if test="${index[archRef.id]}">
                <!-- RM -->
                <%-- Paths de los fields del archRef para los que se debe mostrar GUI
                 El cequeo podria ser una taglib
                 el chequeo para saber si mostrar el nodo se hace con: fieldPaths.find{ rmNode.path.startsWith(it.path)} != null
                --%>
                <g:set var="fieldPaths" value="${['/']}" />
                <g:if test="${archRef.fields?.size()>0}">
                  <g:set var="fieldPaths" value="${archRef.getFieldPaths()}" />
                </g:if>
                <g:render template="../guiGen/showTemplates/Locatable"
                          model="[rmNode: index[archRef.id], fieldPaths: fieldPaths,
                                 archetype: archRef.getReferencedArchetype()]" />
              </g:if>
              <g:else><%-- No hay estructura del RM, voy por el AOM --%>
                <!-- AOM -->
                <g:each in="${archRef.getReferencedConstraints()}" var="node">
                   <g:render template="../guiGen/templates2/cComplexObject"
                             model="[cComplexObject: node, params: params,
                                    archetype: archRef.getReferencedArchetype()]" />
                </g:each>
              </g:else>
            </g:each>
          </td>
           <td id="right" style="width: 50%;">
            <g:each in="${template.getArchetypesByZone('right')}" var="archRef">
              <g:if test="${index[archRef.id]}">
                <!-- RM -->
                <%-- Paths de los fields del archRef para los que se debe mostrar GUI
                 El cequeo podria ser una taglib
                 el chequeo para saber si mostrar el nodo se hace con: fieldPaths.find{ rmNode.path.startsWith(it.path)} != null
                --%>
                <g:set var="fieldPaths" value="${['/']}" />
                <g:if test="${archRef.fields?.size()>0}">
                  <g:set var="fieldPaths" value="${archRef.getFieldPaths()}" />
                </g:if>
                <g:render template="../guiGen/showTemplates/Locatable"
                          model="[rmNode: index[archRef.id], fieldPaths: fieldPaths,
                                 archetype: archRef.getReferencedArchetype()]" />
              </g:if>
              <g:else><%-- No hay estructura del RM, voy por el AOM --%>
              <!-- AOM -->
                <g:each in="${archRef.getReferencedConstraints()}" var="node">
                   <g:render template="../guiGen/templates2/cComplexObject"
                             model="[cComplexObject: node, params: params,
                                    archetype: archRef.getReferencedArchetype()]" />
                </g:each>
              </g:else>
            </g:each>
          </td>
        </tr>
        <tr>
           <td colspan="2" id="bottom" style="width: 100%;">
            <g:each in="${template.getArchetypesByZone('bottom')}" var="archRef">
              <g:if test="${index[archRef.id]}">
                <!-- RM -->
                <%-- Paths de los fields del archRef para los que se debe mostrar GUI
                 El cequeo podria ser una taglib
                 el chequeo para saber si mostrar el nodo se hace con: fieldPaths.find{ rmNode.path.startsWith(it.path)} != null
                --%>
                <g:set var="fieldPaths" value="${['/']}" />
                <g:if test="${archRef.fields?.size()>0}">
                  <g:set var="fieldPaths" value="${archRef.getFieldPaths()}" />
                </g:if>
                <g:render template="../guiGen/showTemplates/Locatable"
                          model="[rmNode: index[archRef.id], fieldPaths: fieldPaths,
                                 archetype: archRef.getReferencedArchetype()]" />
              </g:if>
              <g:else><%-- No hay estructura del RM, voy por el AOM --%>
              <!-- AOM -->
                <g:each in="${archRef.getReferencedConstraints()}" var="node">
                  <g:render template="../guiGen/templates2/cComplexObject"
                             model="[cComplexObject: node, params: params,
                                    archetype: archRef.getReferencedArchetype()]" />
                </g:each>
              </g:else>
            </g:each>
          </td>
        </tr>
      </table>
      <br/>
      <div class="bottom_actions">
        <g:isNotSignedRecord episodeId="${episodeId}">
        <g:if test="${mode=='edit'}">
          <g:submitButton name="doit" value="Guardar" />
        </g:if>
        <g:else>
          <g:link action="generarShow" id="${rmNode.id}" params="[mode:'edit']" class="boton1"><g:message code="trauma.show.action.edit" /></g:link>
        </g:else>
        |
        </g:isNotSignedRecord>
        <g:link controller="records" action="registroClinico" class="boton1"><g:message code="trauma.show.action.back"  /></g:link>
      </div>

    </g:form>
   </div>
    </div>
  </body>
</html>