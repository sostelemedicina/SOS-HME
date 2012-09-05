
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla1">

   <% def odd = 1 %>

          <g:each in="${codigos}" var="codigo">
         

          <g:if test="${!codigo.codigo}" >

            <tr class="group" id="cie_${codigo.id}">

          </g:if>

          <g:else>

            <tr ${((odd) ?" class='odd'":" class='even'") } id="cie_${codigo.id}">

          </g:else>
          
            <td> ${codigo.subgrupo}</td>
            <td> ${((codigo.codigo) ? codigo.codigo : '')} </td> <%--// si no muestra 'null'--%>
            <td > <%print codigo.nombre %></td>
            <td class="select_code">
              <a href="javascript:select(${codigo.id});">Seleccionar</a>
            </td>

            </tr>

           <% odd = (odd+1)%2 %>


          </g:each>

</table>

<util:remotePaginate controller="cie10"
                           action="index"
                           params="[text: text]"
                           total="${total}"
                           update="[success: 'resultadoCie', failure: 'errorResultadoCandidatos']"
                           max="10"
                           />