/**
 * 
 */
package templates

import templates.tom.*
import templates.tom.constraints.*
import templates.tom.controls.*

import org.codehaus.groovy.grails.commons.ApplicationHolder

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class TemplateManager {
    
    // FIXME> puede ser necesario sincronizar las estructuras porque son compartidas...
    // Cache: templateId => template
    private static Map<String, Template> cache = [:]
     
    // templateId => timestamp de cuando fue usado por ultima vez.
    // Sirve para saber si un template no fue utilizado por mucho tiempo, y bajarlo del cache par optimizar espacio en memoria.
    private static Map<String, Date> timestamps = [:]
     
    // SINGLETON
    private static TemplateManager instance = null
     
    private TemplateManager() {}
     
    public static TemplateManager getInstance()
    {
       if (!instance) instance = new TemplateManager()
       return instance
    }

    private Template parseTemplate( File templateFile )
    {
        //String fileContents = ""
        //templateFile.eachLine{line-> fileContents += line}   // Contenido del archivo a String
        //def xmlTemplate = new XmlSlurper().parseText( fileContents ) // XML parseado
        
        def xmlTemplate = new XmlSlurper().parseText( templateFile.getText() ) // XML parseado
        
        Template template = new Template()
        
        template.id = xmlTemplate.id.text()
        template.name = xmlTemplate.name.text()
        
        template.rootArchetype = new ArchetypeReference()
        
        // <archetype type="cluster" id="openEHR-EHR-CLUSTER.a1.v1" includeAll="false">
        template.rootArchetype.type       = ArchetypeTypeEnum.fromValue( xmlTemplate.root_archetype.archetype.'@type'.text() )
        template.rootArchetype.id         = xmlTemplate.root_archetype.archetype.'@id'.text()
        template.rootArchetype.includeAll = (xmlTemplate.root_archetype.archetype.'@includeAll'.text() == "true")
        
        def pageZone = xmlTemplate.root_archetype.archetype.'@pageZone'.text()
        if (pageZone) template.rootArchetype.pageZone = pageZone
        
        // FIXME: chequear si includeAll es false, debe tener algun nodo
        //        y si includeAll es true, no debe tener ningun nodo.
        
        // <field path="/items[at0001]/value/magnitude" />
        // <field path="/items[at0002]/value" />
        // <field path="/items[at0003]/value" />
        template.rootArchetype.fields = [] // List<ArchetypeField>
        
//        println "Root fields"
        xmlTemplate.root_archetype.archetype.field.each
        { xmlField ->

          def field = new ArchetypeField( path: xmlField.'@path'.text(),
                                          owner: template.rootArchetype,
                                          constraints: parseFieldConstraints( xmlField ),
                                          controls: parseFieldControls( xmlField )
                                        )
          
//          println "Controls: " + field.controls

          // Asociaciones constraint->field
          field.constraints.each { constraint ->
             constraint.owner = field
          }
          
          template.rootArchetype.fields << field
        }
//        println "/Root fields"
        
        
        template.includedArchetypes = [] // List<ArchetypeReference>
        
        
        xmlTemplate.included_archetypes.archetype.each
        { xmlArchetypeNode ->
        
           def ref = new ArchetypeReference()
           
           // <archetype type="cluster" id="openEHR-EHR-CLUSTER.a1.v1" includeAll="false">
           ref.type       = ArchetypeTypeEnum.fromValue( xmlTemplate.root_archetype.archetype.'@type'.text() )
           ref.id         = xmlArchetypeNode.'@id'.text()
           ref.includeAll = (xmlArchetypeNode.'@includeAll'.text() == "true")
           
           pageZone = xmlArchetypeNode.'@pageZone'.text()
           if (pageZone) ref.pageZone = pageZone
           
           // FIXME: chequear si includeAll es false, debe tener algun nodo
           //        y si includeAll es true, no debe tener ningun nodo.
           
           // <field path="/items[at0001]/value/magnitude" />
           // <field path="/items[at0002]/value" />
           // <field path="/items[at0003]/value" />
           ref.fields = [] // List<ArchetypeField>
           
//           println "Ref arch fields"
           xmlArchetypeNode.field.each
           { xmlField ->

             def field = new ArchetypeField( path: xmlField.'@path'.text(),
                                             owner: ref,
                                             constraints: parseFieldConstraints( xmlField ),
                                             controls: parseFieldControls( xmlField )
                                           )
//             println "Controls: " + field.controls

             // Asociaciones constraint->field
             field.constraints.each { constraint ->
                constraint.owner = field
             }

             ref.fields << field
           }
//           println "/Ref arch fields"
           
           template.includedArchetypes << ref
        }
        
        
        return template
    }
    
    def parseFieldConstraints( xml_field )
    {
        def constraints = []
        xml_field.transform?.each { xml_transform ->
           constraints << new Transform(
                                path: xml_transform.'@path'.text(),
                                operation: xml_transform.'@operation'.text(),
                                operand: xml_transform.'@operand'.text() )
        }
        xml_field.overwrite?.each { xml_transform ->
           constraints << new Overwrite(
                path: xml_transform.'@path'.text(),
                with: xml_transform.'@with'.text() )
        }
        return constraints
    }
    
    def parseFieldControls( xml_field )
    {
        def controls = []
        xml_field.control?.each { xml_control ->
           controls << new Control(
                                path: xml_control.'@path'.text(),
                                type: xml_control.'@type'.text() )
        }
        return controls
    }
    
    /*
    // TODO: implementar!
    private String getTemplatePath( String templateId )
    {
        // TODO:
        def etapa = "" // EVALUACION_PRIMARIA en EVALUACION_PRIMARIA-via_aerea
        switch (etapa)
        {
            case 'EVALUACION_PRIMARIA':
                return 'hce/trauma/evaluacion/evaluacion_primaria'
            break;
            case 'composition':
            case 'element':
            case 'section':
            case 'structure':
                return type
            break
            case 'item_tree':
                return 'structure'
            break
            break
            case 'action':
            case 'evaluation':
            case 'instruction':
            case 'observation':
                return 'entry/'+type
            break

            //default:
            //    throw new Exception('Tipo no conocido ['+ type +'], se espera uno de cluster, composition, element, section, structure, action, observation, instruction, evaluation' )
        }
    }
   */
    
    public Map getLoadedTemplates()
    {
        return this.cache
    }
    
    public Map getLastUse()
    {
        return this.timestamps
    }
    
    public void unloadAll()
    {
      // FIXME: debe estar sincronizada
        this.cache.clear()
        this.timestamps.clear()
    }
    public void unload( String templateId )
    {
        // FIXME: debe estar sincronizada
        this.cache.remove(templateId)
        this.timestamps.remove(templateId)
    }
    
    public Template getTemplate( String templateId )
    {
        // Si no esta cargado, lo intenta cargar
        
        /*PARA NO TENER TEMPLATES EN CACHE*/
       
        if (true)  // <-- !this.cache[templateId] buscar templates en cache, Armando
        {
            println "No se encuentra el template " + templateId + ", se intenta cargarlo"
            //def id = new templateID( templateId ) // a partir del ID saco la ruta que tengo que cargar
            //def type = id.rmEntity // cluster, entry, composition, etc...
            
            // FIXME: ojo que si es un subtipo la ruta no es directa (action esta en /ehr/entry/action no es /ehr/action!)
            
            // TODO: poder agrupar arquetipos en subdirectorios donde cada directorio es por cada seccion del hospital.
            // templates/templateId.xml
            //println "Carga desde: " + this.templateRepositoryPath+"/"+templateId+".xml"
            //def templateFile = new File( this.templateRepositoryPath+"/"+templateId+".xml" )
            
            //ApplicationHolder.application.config.domain // "templates/hce/trauma"
            //println "  Carga desde: "+ "templates/" + ApplicationHolder.application.config.domain +"/"+ templateId +".xml"
            //def templateFile = new File( "templates/" + ApplicationHolder.application.config.domain +"/"+ templateId +".xml" )
            
            // Nuevo!
            println "  Carga desde: "+ "templates/" + ApplicationHolder.application.config.templates2.path +"/"+ templateId +".xml"
            def templateFile = new File( "templates/" + ApplicationHolder.application.config.templates2.path +"/"+ templateId +".xml" )
            
            // PARSEAR template
            Template template = parseTemplate( templateFile )
            // /PARSEAR template
                
            if (template)
            {
               println "    Cargado el template: " + templateFile.name + " de " + templateFile.path
               cache[templateId] = template
               timestamps[templateId] = new Date()
            }
            else
            {
               println "    No se pudo cargar el template: " + templateFile.name + " de " + templateFile.path
            }
        }
        else
        {
            println "Template: ${templateId} esta en cache << " + this.cache[templateId] + " >>"
            this.timestamps[templateId] = new Date() // actualizo timestamp
        }
        
        return this.cache[templateId]
    }
    
}
