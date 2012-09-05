/**
 * 
 */
package binding

import org.openehr.am.archetype.Archetype
import hce.core.common.archetyped.Locatable
import org.openehr.am.archetype.constraintmodel.ArchetypeSlot


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 * Referencia a un slot para enganchar estructuras del RM luego de bindear cada una por separado.
 */
class SlotRef
{
    // Arquetipo del objeto referenciado
    Archetype reference
    
    // Duenio del slot bindeado, es a quien hay que engancharle las estructuras referenciadas desde los slots, luego de bindeadas
    Locatable owner
    
    // Path del ArchetypeSlot en el arquetipo que bindea a owner.
    // Es el que tiene la referencia al arquetipo reference.
    String pathToSlot
    
    Archetype ownerArchetype
    ArchetypeSlot ownerSlot // Slot en el ownerArchetype, no lo puedo obtener para setear...
    
    /**
     * Devuelve el id del arquetipo referenciado.
     */
    def getRefArqId()
    {
        return reference.archetypeId.value
    }
    
    String toString()
    {
        return owner.getClass().getSimpleName() +"("+ pathToSlot +") -> "+ getRefArqId()
    }
}
