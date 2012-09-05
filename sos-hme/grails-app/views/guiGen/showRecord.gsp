<%@ page import="archetype_repository.ArchetypeManager" %>
<%@ page import="templates.TemplateManager" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<html>
  <head>
    <meta name="layout" content="ehr" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css' ,file:'formularios_show.css')}" />
  </head>
  <body>
    <%--
    <h1>Template: ${rmNode.archetypeDetails.templateId}</h1>
    --%>
    <div id="nivel3">


      
    <div class="ehrform">

      <table class="contenido" cellpadding="0" cellspacing="3" style="width: 100%;">
        <tr>
         <td colspan="2" id="content" style="width: 100%;">

              <g:if test="${!content}">

                <h2><p style="margin-left: 50px;"><g:message code="guiGen.showRecord.vacio" /></p></h2>
              </g:if>
              <g:else>
                <g:each in="${content}" var="content">

                <%-- ${content.archetypeDetails.archetypeId} --%>

                <g:set var="archetype"
                       value="${ArchetypeManager.getInstance().getArchetype( content.archetypeDetails.archetypeId )}" />

                <g:set var="template"
                       value="${TemplateManager.getInstance().getTemplate( content.archetypeDetails.templateId )}" />

                <g:render template="../guiGen/showTemplates/Locatable"
                          model="[rmNode: content, archetype: archetype, template: template]" />
                <hr />
                </g:each>
                
              </g:else>
          </td>
        </tr>
      </table>
      <br/>
    <g:if test="${content}">
      <div class="bottom_actions">
        <g:link controller="records" action="registroClinico" class="boton1"><g:message code="trauma.show.action.back"  /></g:link>
      </div>
    </g:if>
    </div>
    </div>
  </body>
</html>