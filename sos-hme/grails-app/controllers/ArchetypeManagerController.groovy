/**
 * 
 */

import archetype_repository.ArchetypeManager


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class ArchetypeManagerController {
    
    def index = {
        redirect(action:'list')
    }
    
    def list = {
            
        def manager = ArchetypeManager.getInstance()
        def archetypes = manager.getLoadedArchetypes()
        def actualizaciones = manager.getLastUse()
        
        println "Arquetipos: " + archetypes.values()
        
        return [archetypeMap: archetypes, lastUseList: actualizaciones]
    }
    
    def unloadAll = {
        def manager = ArchetypeManager.getInstance()
        manager.unloadAll()
        redirect(action:'list')
    }
    
    /*
    def unload = {
            
        def manager = TemplateManager.getInstance()
        manager.unload(params.id)
        
        println "UNLOAD: " + params.id
        
        redirect(action:'list')
    }
    */
}
