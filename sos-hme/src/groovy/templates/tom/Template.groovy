/**
 * 
 */
package templates.tom

import templates.tom.constraints.*

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Template {

    String id
    String name
    
    ArchetypeReference rootArchetype
    List<ArchetypeReference> includedArchetypes
    
    /**
     * Retorna los identificadores de todos los arquetipos del template.
     * @return
     */
    List<String> getArchetypeIds()
    {
        def ret = []
        ret << rootArchetype.id
        ret.addAll( includedArchetypes.id )
        return ret
    }
    
    /**
     * Retorna el identificador del arquetipo raiz del template.
     * @return
     */
    String getRootArchetypeId()
    {
        return rootArchetype.id
    }
    
    /**
     * Devuelve el campo que coincida con la path, tal que la path
     * del campo sea el prefijo mas largo de path.
     */
    ArchetypeField getField( String archetypeId, String path )
    {
        ArchetypeReference archRef
        
        if (rootArchetype.id == archetypeId) archRef = rootArchetype
        else
        {
            archRef = includedArchetypes.find{ it.id == archetypeId }
        }
        
        //return archRef?.fields.find{ it.path == path }
        def alternatives = archRef?.fields.findAll{ path.startsWith(it.path) }
        
        return alternatives.max{ it.path.length() }
    }
    
    List<ArchetypeReference> getArchetypesByZone( String pageZone )
    {
        def ret = []
        if (rootArchetype.pageZone == pageZone) ret << rootArchetype
        def ret2 = includedArchetypes.findAll{ it.pageZone == pageZone }
        ret2.each {
            ret << it
        }
        return ret
    }
    
    List getTransformations()
    {
        def ret = []
        
        //println includedArchetypes.fields
        //println includedArchetypes.fields.constraints.flatten().findAll{ it instanceof Transform }
        //println rootArchetype.fields.constraints.findAll{ it.getClass().getSimpleName() == "Transform" }
        //println includedArchetypes.fields.constraints.findAll{ it.getClass().getSimpleName() == "Transform" }
        
        ret.addAll( rootArchetype.fields.constraints.findAll{ it instanceof Transform } )
        ret.addAll( includedArchetypes.fields.constraints.flatten().findAll{ it instanceof Transform } )
        
        return ret
    }
}
