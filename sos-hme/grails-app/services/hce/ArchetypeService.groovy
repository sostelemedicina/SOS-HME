/**
 * 
 */
package hce

import org.openehr.am.archetype.Archetype
import archetype_repository.ArchetypeManager 

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 * Este servicio implementa las funcionalidades necesarias 
 * para acceder a los arquetipos presentes en el sistema.
 *
 */
class ArchetypeService {
    
    /**
     * @param String type tipo de concepto OpenEHR al que refiere el arquetipo (ELEMENT, CLUSTER, ITEM_STRUCTURE, OBSERVATION, etc)
     * @param String idMatchingKey parte de una expresion regular que se utilizara para bucar arquetipos, en general es la presente en un ARCHETYPE_SLOT.
     */
    def getArchetype( String type, String idMatchingKey )
    {
       // TODO
       // Por el tipo, ubica el directorio
       // Por la regexp los arquetipos que matcheen
        
       // Test archetype manager
       def manager = ArchetypeManager.getInstance()
       Archetype archetype = manager.getArchetype( type, idMatchingKey )
       // /Test archetype manager
        
       return archetype
    }
    
}
