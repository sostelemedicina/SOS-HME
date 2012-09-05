/**
 * 
 */

import templates.TemplateManager


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class TemplateManagerController {
    
    def index = {
        redirect(action:'list')
    }
    
    def list = {
            
        def manager = TemplateManager.getInstance()
        def templates = manager.getLoadedTemplates()
        def actualizaciones = manager.getLastUse()
        
        println "Templates: " + templates.values()
        
        return [templateMap: templates, lastUseList: actualizaciones]
    }
    
    def unloadAll = {
        def manager = TemplateManager.getInstance()
        manager.unloadAll()
        redirect(action:'list')
    }
    def unload = {
            
        def manager = TemplateManager.getInstance()
        manager.unload(params.id)
        
        println "UNLOAD: " + params.id
        
        redirect(action:'list')
    }
}
