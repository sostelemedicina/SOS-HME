/**
 * 
 */
package demographic

import hce.core.support.identification.UIDBasedID

import demographic.identity.PersonName
import demographic.party.Person
import demographic.role.Role

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class LocalDemographicAccess extends DemographicAccess {


    /**
     * Encuentra otros identificadores de la persona, a partir de un identificador conocido.
     * Se corresponde a PIX ids query con un solo identificador como criterio de busqueda.
     */
    public List<UIDBasedID> findIdsById( UIDBasedID id )
    {
        // TODO
        // Buscar las personas con ese id
        // Devolver todos los ids de esas personas
        
        // OBS: si la base no tiene buena calidad puedo obtener varias personas.

		Person.executeQuery("select e.primer from Person e where e.nacionalidad = ?","Bolivia")
		
        return []
    }
    
    /**
     * Encuentra otros identificadores de la persona, a partir de los identificadores conocidos.
     * Se corresponde a PIX ids query con muchos identificadores como criterio de busqueda.
     * El resultado debe ser el mismo que si se invoca findIdsById para cada id de ids y luego se hace merge de los resultados.
     */
    public List<UIDBasedID> findIdsByIds( List<UIDBasedID> ids )
    {
        // TODO
        return []
    }
    
    /**
     * Busca id (root y ext).
     */
    public List<Person> findPersonById( UIDBasedID id )
    {
        def candidatos = Person.withCriteria {
           ids {
              //like('value', '%'+id.extension+'%')
              //like('value', '%::%'+id.extension+'%')
              eq('value', id.value) //id.root+'::'+id.extension) // si no hago busqueda exacta con ids que uno es substring del otro me tira a las 2 personas, el id, si existe deberia tirar una sola.
           }
        }
        
        // TODO: ver que es mas rapido si as Set as List o Collection.unique
        //return (candidatos as Set) as List // Saco duplicados
        return candidatos.unique{it.id}
    }
    
    /**
     * Los parametros pueden ser todos nulos.
     */
    public List<Person> findByPersonData( PersonName n, Date bithdate, String sex )
    {
        //if (n.primerNombre) println "Tiene primer nombre: " + n.primerNombre
       // if (n.segundoNombre) println "Tiene segundo nombre: " + n.segundoNombre
       // if (n.primerApellido) println "Tiene primer apellido: " + n.primerApellido
        //if (n.segundoApellido) println "Tiene segundo apellido: " + n.segundoApellido
        
        def persons_by_name = []
        if ( n || bithdate || sex )
        {
           def names = []
           if (n)
           {
              names = PersonName.withCriteria {
                if (n.primerNombre)
                  like('primerNombre','%'+n.primerNombre+'%')
                if (n.segundoNombre)
                  like('segundoNombre','%'+n.segundoNombre+'%')
                if (n.primerApellido)
                  like('primerApellido','%'+n.primerApellido+'%')
                if (n.segundoApellido)
                  like('segundoApellido','%'+n.segundoApellido+'%')
              }
           }
           
           // TODO: encontrar las personas con estos nombres
           persons_by_name = Person.withCriteria {
              or {
                 names.each{ name ->
                     identities {
                        eq('id', name.id)
                     }
                 }
              }
              if (bithdate)
                eq('fechaNacimiento', bithdate)
              if (sex)
                eq('sexo', sex)
           }
        }
        
        
        // TODO: la solucion es el intersec de persons_by_name y persons_by_bd_and_sex
        
        return persons_by_name
    }
    
    public List<Person> findByPersonDataAndId( PersonName n, Date bithdate, String sex, UIDBasedID id )
    {
        def result = []
        if ( n || bithdate || sex || id )
        {
           def names = []
           if (n)
           {
              names = PersonName.withCriteria {
                if (n.primerNombre)
                  like('primerNombre','%'+n.primerNombre+'%')
                if (n.segundoNombre)
                  like('segundoNombre','%'+n.segundoNombre+'%')
                if (n.primerApellido)
                  like('primerApellido','%'+n.primerApellido+'%')
                if (n.segundoApellido)
                  like('segundoApellido','%'+n.segundoApellido+'%')
              }
           }
           
           result = Person.withCriteria {
              if (id)
              {
                ids {
                  eq('value', id.root+'::'+id.extension) // si no hago busqueda exacta con ids que uno es substring del otro me tira a las 2 personas, el id, si existe deberia tirar una sola.
                }
              }
              if (names.size()>0)
              {
                 or {
                    names.each{ name ->
                       identities {
                          eq('id', name.id)
                       }
                    }
                 }
              }
              if (bithdate)
                 eq('fechaNacimiento', bithdate)
              if (sex)
                 eq('sexo', sex)
           }
        }
        
        return result
    }
    
    public List<Person> findByPersonDataAndIds( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids )
    {
        // TODO
        return []
    }
    
    public List<Person> findByPersonDataAndRole( PersonName n, Date bithdate, String sex, Role role )
    {
        // TODO
        return []
    }
    
    /**
     * El rol debe ser especificado junto a alguno de los demas parametros para obtener algun resultado,
     * sin ese binomio minimo, no se devuelve ningun resultado. http://code.google.com/p/open-ehr-sa/issues/detail?id=61
     * 
     */
    public List<Person> findByPersonDataAndIdAndRole( PersonName n, Date bithdate, String sex, UIDBasedID id, String roleType )
    {
        def result = []
        
        def names = []
        if (n)
        {
           names = PersonName.withCriteria {
             or{
             if (n.primerNombre)
               like('primerNombre','%'+n.primerNombre+'%')
             if (n.segundoNombre)
               like('segundoNombre','%'+n.segundoNombre+'%')
             if (n.primerApellido)
               like('primerApellido','%'+n.primerApellido+'%')
             if (n.segundoApellido)
               like('segundoApellido','%'+n.segundoApellido+'%')
             }
           }
        }
        
        if ( (names.size()>0 || bithdate || sex || id) && roleType )
        {
           def result_roles = Role.withCriteria {

              eq('type', roleType)
              performer {
                 or{
                    // FIXME: deberia ser person si no no va a tener todos los atributos
                 if (id)
                 {
                    ids {
                       //'in'("value", )
					   eq('value', id.root+'::'+id.extension) // si no hago busqueda exacta con ids que uno es substring del otro me tira a las 2 personas, el id, si existe deberia tirar una sola.
                    }
                 }
                 if (names.size()>0)
                 {
                    or {
                       names.each{ name ->
                          identities {
                             eq('id', name.id)
                          }
                       }
                    }
                 }
                 if (bithdate) eq('fechaNacimiento', bithdate)
                 if (sex) eq('sexo', sex)
              }
            }
           }
           
           result = result_roles.performer
        }
        
        return result
    }
    
    
    public List<Person> findByPersonDataAndIdsAndRole( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids, Role role )
    {
        // TODO
        return []
    }
    
}
