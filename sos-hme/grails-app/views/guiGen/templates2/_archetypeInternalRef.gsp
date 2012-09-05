<%@ page import="org.openehr.am.archetype.constraintmodel.*" %>

<%--
// in: ref (${ref.rmAttributeName}) (${cref.class})<br/>
// in: archetype
--%>
<%--
ARCHETYPE INTERNAL REF: <br/>
--%>
<%--

Path: ${ref.path()} <br/>
Ref. Path: ${ref.targetPath} <br/>

TODO: la path la debe resolver desde este nodo, no desde el nodo referenciado.<br/>
--%>

<g:set var="node" value="${archetype.node( ref.targetPath )}" />
<g:set var="strclass" value='${node.getClass().getSimpleName()}'/>
<g:set var="templateName" value="${strclass[0].toLowerCase()+strclass.substring(1)}" />

<%-- Ref Node Type: ${templateName}<br/> --%>

<%-- refPath va cuando hay internal ref, para que sepa desde donde salio esa referencia,
     lo bueno es que como es internal ref, el id del arquetipo es el mismo. --%>
<g:render template="../guiGen/templates2/${templateName}"
          model="[refPath: ref.path(), 
                  (templateName): node,
                  archetype: archetype,
                  archetypeService: archetypeService,
                  params: params]" />
