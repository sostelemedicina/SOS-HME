/**
 * 
 */
package templates.tom

import archetype_repository.ArchetypeManager
import org.openehr.am.archetype.Archetype
import org.openehr.am.archetype.constraintmodel.ArchetypeConstraint

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class ArchetypeReference {

    ArchetypeTypeEnum type // section, observation, cluster
    String id // openEHR-EHR-SECTION.medications.v1
    
    // Auxiliar para vincular el arquetipo a una zona de
    // una vista y poder mostrar el contenido donde debe estar.
    String pageZone = "content" // Valor por defecto por si no se le asigna zona en el template.
    
    // RNE:
    // if (includeAll) fields.size==0
    // else fields.size > 0
    boolean includeAll = true // por defectoincluyo todos los nodos "hojas" del arquetipo
    List<ArchetypeField> fields // nodos particulares del arquetipo que quiero incluir en el template

    List<String> getFieldPaths()
    {
        def ret = fields.path
    }
    
    List<ArchetypeConstraint> getReferencedConstraints()
    {
        def manager = ArchetypeManager.getInstance()
        def archetype = manager.getArchetype(this.id)
        def ret = []
        if (this.includeAll) ret << archetype.definition
        else
        {
            fields.path.each{ path ->
                ret << archetype.node(path)
            }
        }
        return ret
    }
    
    Archetype getReferencedArchetype()
    {
        def manager = ArchetypeManager.getInstance()
        def archetype = manager.getArchetype(this.id)
        return archetype
    }
}
