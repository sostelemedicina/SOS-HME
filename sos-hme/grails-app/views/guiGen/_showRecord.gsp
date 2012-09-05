<%@ page import="archetype_repository.ArchetypeManager" %>

    <div>
      <table cellpadding="0" cellspacing="3" style="width: 100%;">
        <tr>
          <td>
            <g:each in="${content}" var="content">
              <g:set var="archetype" value="${ArchetypeManager.getInstance().getArchetype( content.archetypeDetails.archetypeId )}" />
              <g:render template="../guiGen/showTemplates/Locatable"
                        model="[rmNode: content, archetype: archetype ]" />
               <hr />
            </g:each>
          </td>
        </tr>
      </table>
    </div>
