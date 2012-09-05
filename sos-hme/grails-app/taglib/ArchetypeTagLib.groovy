
import archetype_repository.ArchetypeManager
import org.openehr.am.archetype.Archetype
import org.openehr.am.archetype.constraintmodel.*

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class ArchetypeTagLib {

    // Viene archetypeId o (rmtype y idMarchingKey)
    // in: archetypeId
    // in: rmtype
    // in: idMatchingKey
    def displayArchetype = { attrs, body ->
    
       def manager = ArchetypeManager.getInstance()
       Archetype archetype = null
       
       if (attrs.archetypeId)
           archetype = manager.getArchetype( attrs.archetypeId )
       else if (attrs.rmtype && attrs.idMatchingKey)
           archetype = manager.getArchetype(attrs.rmtype, attrs.idMatchingKey ) // FIXME: podria ser una lista de arquetipos
       else
           throw new Exception("Debe venir archetypeId o (rmtype y idMarchingKey)")
       
       if (archetype)
       {
          out << '<div class="cobjects">'
          out << render(template:"templates/cComplexObject", model:['cComplexObject': archetype.definition, 'archetype': archetype])
          out << '</div>'
       }
    }
    
    // Obtiene el termino asociado al id del nodo en el arquetipo.
    // Si el nodo no tiene id, se busca por los padres hasta llegar
    // a uno con id y con el termino declarado en el arquetipo.
    // Retorna null si no lo encuentra.
    // in: archetypeId
    // in: nodePath
    def displayLabel = { attrs ->
            
       if (!attrs.archetypeId) throw new Exception("Parametro 'archetypeId' es obligatorio")
       if (!attrs.nodePath) throw new Exception("Parametro 'nodePath' es obligatorio")
       
       def manager = ArchetypeManager.getInstance()
       Archetype archetype = manager.getArchetype( attrs.archetypeId )
       def node = null
       if (archetype)
       {
           node = archetype.node( attrs.nodePath )
           out << this.findTerm( archetype, node,  attrs.nodePath )
       }
    }
    
    // recursivo sobre la path, auxiliar de taglig displayLabel.
    private String findTerm( Archetype archetype, CObject node, String path )
    {
       //println "FindTerm: " + path
       if (node)
       {
          if (node.nodeID)
          {
             //println "nodeId: " + node.nodeID + " " + session.locale.language
             def archetypeTerm = archetype.ontology.termDefinition(session.locale.language, node.nodeID) // podria ser null si el termino no esta definido en el arquetipo
             if (!archetypeTerm) return null // FIXME: deberia seguir recursiva
             return archetypeTerm.items.text // + " ("+ path +")"
          }
          else // recursivo en path
          {
             def i = path.lastIndexOf("/")
             if (i>0)
             {
                 def newPath = path[0..(i-1)]
                 def newNode = archetype.node( newPath )
                 return findTerm( archetype, newNode, newPath )
             }
             else // no queda path para llamada recursiva
             {
                return null
             }
          }
       }
       return null
    }
    
    // Devuelve el body si el CObject ELEMENT ancestro del nodo nodePath tiene ocurrences unboundedUpper 
    // in: archetypeId
    // in: nodePath
    def parentElementIsMultiple = { attrs, body ->
    
        if (!attrs.archetypeId) throw new Exception("Parametro 'archetypeId' es obligatorio")
        if (!attrs.nodePath) throw new Exception("Parametro 'nodePath' es obligatorio")
        
        def manager = ArchetypeManager.getInstance()
        Archetype archetype = manager.getArchetype( attrs.archetypeId )
        def node = null
        if (archetype)
        {
            node = archetype.node( attrs.nodePath )
            if ( this.parentElementIsMultipleRecursive(archetype, node,  attrs.nodePath ) )
               out << body()
        }
     }
    
    private boolean parentElementIsMultipleRecursive( Archetype archetype, CObject node, String path )
    {
       //println "parentElementIsMultipleRecursive: " + path
       // El multiple es para element, cluster, de ahi para arriba,
       // no para los objetos basicos, como pasa en el arquetipo.
       if (node && !isBasicType(node.rmTypeName))
       {
          if (node.rmTypeName.toLowerCase() == "element")
          {
             // FIXME: si tiene upperBound > 1 tambien es multiple pero con cota de repeticion.
             //println "  es element y " + ((node.occurrences.isUpperUnbounded())?"MULTIPLE":"NO MULTIPLE")
             return node.occurrences.isUpperUnbounded()
          }
          else if (node.rmTypeName.toLowerCase() == "cluster")
          {
              // FIXME: si tiene upperBound > 1 tambien es multiple pero con cota de repeticion.
              //println "  es element y " + ((node.occurrences.isUpperUnbounded())?"MULTIPLE":"NO MULTIPLE")
              return node.occurrences.isUpperUnbounded()
           }
          else // recursivo en path
          {
             def i = path.lastIndexOf("/")
             if (i>0)
             {
                 def newPath = path[0..(i-1)]
                 def newNode = archetype.node( newPath )
                 return parentElementIsMultipleRecursive( archetype, newNode, newPath )
             }
             else // no queda path para llamada recursiva
             {
                return false
             }
          }
       }
       return false
    }
    
    
    private isBasicType( String type )
    {
        // TODO: faltan tipos
        return ["DV_CODED_TEXT","DV_TEXT","DV_COUNT","DV_QUANTIY","DV_DATE"].contains(type)
    }
}
