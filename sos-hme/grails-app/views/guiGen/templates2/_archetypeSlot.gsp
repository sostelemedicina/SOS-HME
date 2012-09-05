<%@ page import="org.openehr.am.archetype.constraintmodel.*" %>
<%@ page import="java.util.regex.*" %>
<%@ page import="se.acode.openehr.parser.*" %>
<%@ page import="org.openehr.am.archetype.Archetype" %>
<%@ page import="hce.ArchetypeService" %>

Hay un tema con los slots, ahora estaba haciendo la referencia a
cada slot desde el template, o sea que la referencia en el guigen
cuando encuentra un archSlot no se sigue, el tema es si quiero
mostrar un slot pero no pongo la referencia en el template, no se
muestra.

SLOT!<br/>

<%--
in: archetypeSlot (${archetypeSlot.class}) (${archetypeSlot.rmTypeName})
--%>

<%
// refPath es nulo si no viene de un arch internal ref
// FIXME: si es slot no creo que vaya a tener internal ref...

def _refPath = ''
if (refPath) _refPath = refPath

%>

<g:each in="${archetypeSlot.includes}" var="assertion">
  <div class="slot">
    <%-- FIXME: supongo que ass.exp es BinaryExpression --%>
    ${assertion.expression.leftOperand.item} <%-- FIXME: supongo que es siempre "archetype_id/value" --%>
    ${assertion.expression.operator} <%-- FIXME: NO LE ESTOY DANDO BOLA AL OPERATOR! --%>
    ${assertion.expression.rightOperand.item.pattern}<br/><%-- Tiene que ser un CString --%>
    
    <% System.out.println("ArchetypeSlot: " + assertion.expression.rightOperand.item.pattern); %>
    <g:displayArchetype
        rmtype="${archetypeSlot.rmTypeName}"
        idMatchingKey="${assertion.expression.rightOperand.item.pattern}" />
  </div>
</g:each>

/SLOT!<br/>
