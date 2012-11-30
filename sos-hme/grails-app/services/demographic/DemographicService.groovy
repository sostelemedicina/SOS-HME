/**
 * 
 */
package demographic

import demographic.DemographicAccess
import demographic.LocalDemographicAccess
import sos.EnfermedadesNotificables


import hce.core.support.identification.UIDBasedID

import demographic.identity.PersonName
import demographic.identity.*
import demographic.party.Person
import demographic.party.Organization
import demographic.role.Role
import tablasMaestras.TipoIdentificador

// Configuracion de consulta local o remota
import org.codehaus.groovy.grails.commons.ApplicationHolder


import java.util.*;



//creacion de archivos xml
import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.*
import java.lang.Object.*
import groovy.xml.XmlUtil
import groovy.util.XmlSlurper
import javax.xml.parsers.*
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class DemographicService {

    DemographicAccess demographicAccess
    def grailsApplication
    //int queryCount = 0
    
    // FIXME: ojo, capaz necesita instanciarse distinto por GRAILS, los servicesson singletons...
    def DemographicService()
    {
        if (ApplicationHolder.application.config.hce.patient_administration.serviceType.local)
        {
            println "DemographicService - PA LOCAL"
            // TODO: instanciar demographicAccess segun configuracion
            demographicAccess = new LocalDemographicAccess()
        }
        else
        {
            println "DemographicService - PA REMOTO"
            demographicAccess = new PixPdqDemographicAccess()
        }
    }
    
    /**
     * True si la persona tiene 1 id, primer nombre, primer apellido, sexo y fecha nacimiento.
     * @param p
     * @return
     */
    public boolean personHasAllData( Person p )
    {
        //println "Person: "+ p
        //println "Identities: " + p.identities
        
        if (!p.identities || p.identities.size()==0) return false
        
        def pn = p.identities.find{ it.purpose == 'PersonName' }
        if (!pn) return false
        
        return ( p.sexo && p.fechaNacimiento && pn.primerNombre && pn.primerApellido && p.ids.size()>0 )
    }
    
    /**
     * Encuentra otros identificadores de la persona, a partir de un identificador conocido.
     * Se corresponde a PIX ids query con un solo identificador como criterio de busqueda.
     */
    public List<UIDBasedID> findIdsById( UIDBasedID id )
    {
        //this.queryCount++
        //println queryCount
        return demographicAccess.findIdsById(id)
    }
    
    /**
     * Encuentra otros identificadores de la persona, a partir de los identificadores conocidos.
     * Se corresponde a PIX ids query con muchos identificadores como criterio de busqueda.
     * El resultado debe ser el mismo que si se invoca findIdsById para cada id de ids y luego se hace merge de los resultados.
     */
    public List<UIDBasedID> findIdsByIds( List<UIDBasedID> ids )
    {
        return demographicAccess.findIdsByIds(ids)
    }
    
    /**
     * Busca por extension, Pacientes. 
	 * Cambiar nombre a: findPersonPatientById
     */
    public List<Person> findPersonById( UIDBasedID id )
    {
        //this.queryCount++
        //println queryCount
			
			
			//este query es necesario para filtrar la busqueda de personas por  un id asociado
			//y por el tipo de identidad (patient) que esta tenga.
			def existPerson = Person.withCriteria{
				ids{
					eq("value", id.value)
				}
				identities{
					eq("purpose", "PersonNamePatient")
				}
			
			}
        //return  demographicAccess.findPersonById(id)
		
		return existPerson
    }
	
    /**
     * Busca por extension, Usuarios.
     */
    public List<Person> findPersonUserById( UIDBasedID id )
    {

			def existPerson = Person.withCriteria{
				ids{
					eq("value", id.value)
				}
				identities{
					eq("purpose", "PersonNameUser")
				}
			
			}
		
		return existPerson
    }    
    public List<Person> findByPersonData( PersonName n, Date bithdate, String sex )
    {
        //this.queryCount++
        //println queryCount
        return demographicAccess.findByPersonData(n, bithdate, sex)
    }
    
    public List<Person> findByPersonDataAndId( PersonName n, Date bithdate, String sex, UIDBasedID id )
    {
        return demographicAccess.findByPersonDataAndId(n, bithdate, sex, id)
    }
    
    public List<Person> findByPersonDataAndIds( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids )
    {
        return demographicAccess.findByPersonDataAndIds(n, bithdate, sex, ids)
    }
    
    public List<Person> findByPersonDataAndRole( PersonName n, Date bithdate, String sex, Role role )
    {
        return demographicAccess.findByPersonDataAndRole(n, bithdate, sex, role)
    }
	
    /*
     *@author Angel Rodriguez Leon
     *
     *Busca en la BD los valores de nombres de usuario identificados por el id
	 *y los trae para la creacion de un paciente a partir de estos datos.
     * */  
    public List<Person> findPatientById(UIDBasedID id){
		def candidatosPacientes = Person.withCriteria{

				ids{
					eq("value", id.value)
				}
				identities{

					eq("purpose", "PersonNamePatient")
				}
			
			}
		return candidatosPacientes
	
	}
	
    public List<Person> findUserById(UIDBasedID id){
		def candidatosUsuarios = Person.withCriteria{

				ids{
					eq("value", id.value)
				}
				identities{

					eq("purpose", "PersonNameUser")
				}
			
			}
		return candidatosUsuarios
	
	}
    public List<Person> findByPersonDataAndIdAndRole( PersonName n, Date bithdate, String sex, UIDBasedID id, String roleType )
    {
        return demographicAccess.findByPersonDataAndIdAndRole(n, bithdate, sex, id, roleType)
		/*
				if(id==null){
					id.value="1"
				}

				println "primer nombre: "+n.primerNombre
				if(n.primerNombre == null){
					n.primerNombre = "22"}
				println "segundo nombre: "+n.segundoNombre
				if(n.segundoNombre == null){
					n.segundoNombre="22"}
				println "primer Apellido: "+n.primerApellido
				if(n.primerApellido == null){
					n.primerApellido="22"}
				println "segundo apellido: "+n.segundoApellido
				if(n.segundoApellido == null){
					n.segundoApellido="22"}
				
				println "id: "+id+" date: "+bithdate
				println "sexo: "+sex 
				def candidatosPacientes = Person.withCriteria{
					//eq("sexo",sex)
					and{
						or{
						
							ids{
								eq("value", id.value)
							}
							
							//or{
								identities{
									eq("primerNombre", n.primerNombre)
									
								}
								identities{
									eq("segundoNombre", n.segundoNombre)
									
								}
								identities{
									eq("primerApellido", n.primerApellido)
									
								}
								identities{
									eq("segundoApellido", n.segundoApellido)
									
								}
							//}

						}
						identities{
							eq("purpose", "PersonNamePatient")
						}
						

					}
				}
				println "candidatos: "+candidatosPacientes
				
		return candidatosPacientes*/
	}
    
    public List<Person> findByPersonDataAndIdsAndRole( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids, Role role )
    {
        return demographicAccess.findByPersonDataAndIdsAndRole(n, bithdate, sex, ids, role)
    }
    
    /*
     *@author Juan Carlos Escalante
     *regresa el nombre en los lugares recursivos a partir del id del ultimo hijo 
     *dicho id esta identificado con el nombre Lugar y Direccion de la clase PersonNamePatient*/
    
    public String findFullAddress(Integer lugar){
        def parroquia = null
        def municipio = null
        def estado = null
        def pais = null
        def fullAddress = null
        
        if(lugar){
            def sitio=Lugar.get(lugar)
            //println(sitio.tipolugar)
            if(sitio.tipolugar=="Municipio"){
                municipio =  sitio.nombre
                estado = Lugar.get(sitio.padre.id)
                fullAddress = "municipio "+ municipio + ", estado "+estado.nombre
            }
            if(sitio.tipolugar=="Parroquia"){
                parroquia = sitio.nombre
                municipio = Lugar.get(sitio.padre.id)
                estado = Lugar.get(municipio.padre.id)
                fullAddress = "parroquia "+ parroquia + ", municipio "+ municipio.nombre + ", estado "+estado.nombre
            }
        }
        return fullAddress
    }
    
    public String tokenDireccion(Integer lugar){
        def parroquia = null
        def municipio = null
        def estado = null
        def fullAddress = null
        if(lugar){
           def sitio=Lugar.get(lugar) 
           if(sitio.tipolugar=="Municipio"){
                municipio =  sitio.nombre
                estado = Lugar.get(sitio.padre.id)
                fullAddress = municipio + "#"+estado.nombre
            }
            if(sitio.tipolugar=="Parroquia"){
                parroquia = sitio.nombre
                municipio = Lugar.get(sitio.padre.id)
                estado = Lugar.get(municipio.padre.id)
                fullAddress = parroquia + "#"+ municipio.nombre + "#"+estado.nombre
            }
        }
      return fullAddress  
    }
    
    public String getOcupacion(Integer ocupacion){
        def ocupa = Ocupacion.get(ocupacion)
        return ocupa.nombre
    }
 
    public List getSections(String ruta){
        def sections = []
        this.getDomainTemplates(ruta).keySet().each {

            sections << it
        }
        
        return sections
    }
    
    public Map getDomainTemplates(String path){
        // Nuevo: para devolver los templates del dominio seleccionado
        //def domain = session.traumaContext.domainPath
        def domain = path
        def domainTemplates = grailsApplication.config.templates2."$domain"
        
        return domainTemplates
    }
    
    public List getSubsections( String section, String ruta ){
        def subsections = []
        
        this.getDomainTemplates(ruta)."$section".each { subsection ->
           subsections << section + "-" + subsection
        }
        return subsections
    }
   
    public String tipoIdentificador (String rootIdentificador){
        def tipo = null
        def identificadorTemp = TipoIdentificador.withCriteria{
                eq('codigo',rootIdentificador)
        }
        if(identificadorTemp){
            tipo = identificadorTemp.nombreCorto
        }
      return tipo
    }
}
