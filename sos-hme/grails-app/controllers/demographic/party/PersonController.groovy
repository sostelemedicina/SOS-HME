package demographic.party
import hce.core.support.identification.UIDBasedID
import converters.DateConverter
import tablasMaestras.TipoIdentificador
import demographic.role.Role
import util.RandomGenerator
import demographic.identity.*
import util.FormatLog
import authorization.*
/*
 *@author Angel Rodriguez Leon
 */

class PersonController {

    /*
     *@author Angel Rodriguez Leon
     *
     *Funcion que genera entradas en log correspondiente al nivel que se le pase por parametro.
	 *error o info
     * */ 
	private void logged(String message, String level, userId){

		def bla = new FormatLog()
		
		if(level.equals("info"))
			log.info(bla.createFormat(message, "long",userId))
		if(level == "error")
			log.error(bla.createFormat(message, "long",userId))
	}

	//private List getSubsections( String section )



    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	
	
	
	
    def index = {
	
	
        redirect(action: "list", params: params)
    }

    def list = {
		//def tiposIds = TipoIdentificador.list()
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def pl = Person.createCriteria()
		def resultado = pl.list(params){
			
			like("class","%demographic.party.Person%")
			identities{
					like("purpose","%PersonNameUser%")
			
			}
			
		} 

        
        return [personInstanceList: resultado, personInstanceTotal: resultado.totalCount]
		
		
    }
	
    def create = {
	
		def tiposIds = TipoIdentificador.list()
        def personInstance = new Person()
        personInstance.properties = params
        return [personInstance: personInstance, tiposIds: tiposIds]
    }

	def find = {
		def tiposIds = TipoIdentificador.list()
        
		def pl = Person.createCriteria()
		def personPatients = pl.list(params){
			identities{
					like("purpose","%PersonNamePatient%")
					
					
			}
			
		} 
		
		
		//personInstance.properties = params
        return [personPatients: personPatients, tiposIds: tiposIds]
	}
	def copyperson = {
		def tiposIds = TipoIdentificador.list()
        def personInstance = new Person()
		def personInstance2 = new Person()
		def personPatientInstance = new PersonNameUser()
		
        //personInstance.properties = params
        println("id de la persona: "+params.personid)
		
		
		
		
		
		personInstance = Person.get(params.personid)
		
		
		
		//def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' )
        
		
		personInstance2.setFechaNacimiento( personInstance.getFechaNacimiento() )
		personInstance2.setSexo(personInstance.getSexo())
		
		
		def ids = personInstance.ids
		
		
		
		def id
		for(i in ids){
			id = UIDBasedID.findByValue(i.value)
			println("identificador: "+id+"\n")
			personInstance2.addToIds( id )
		}
		
		def identidad = PersonNamePatient.findById(personInstance.identities?.id[0])
		
		def identidad2 = new PersonNameUser()
		
		identidad2.setPrimerNombre(identidad.getPrimerNombre())
		identidad2.setSegundoNombre(identidad.getSegundoNombre())
		identidad2.setPrimerApellido(identidad.getPrimerApellido())
		identidad2.setSegundoApellido(identidad.getSegundoApellido())
		
		
		
		personInstance2.addToIdentities(identidad2)
		
		
		//return [personInstance: personInstance, tiposIds: tiposIds]
		
		
		
		
		if (personInstance2.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
				redirect(action: "show", id: personInstance2.id)
		}
		else {
				render(view: "create", model: [personInstance: personInstance2])
		}
		//redirect(action: "list", params: params)
	}
	
    def save = {
	
		

		if (params.create){	
			def tiposIds = TipoIdentificador.list()
		    def id = null
            if (params.root == TipoIdentificador.AUTOGENERADO)
            {
                // Verificar si este ID existe, para no repetir
                def extension = RandomGenerator.generateDigitString(8)
                id = UIDBasedID.create(params.root, extension)
                
                // Se deberia hacer con doWhile para no repetir el codigo pero groovy no tiene doWhile
                while ( UIDBasedID.findByValue(id.value) )
                {
                    extension = RandomGenerator.generateDigitString(8)
                    id = UIDBasedID.create(params.root, extension)
                }
            }else{
				// se verifica que ambos parametros no sean null
                if (params.extension && params.root)
                {
                    id = UIDBasedID.create(params.root, params.extension) // TODO: if !hasExtension => error
                    
                    // FIXME: verificar que no hay otro usuario con el mismo id
                    println "===================================================="
                    println "Busco por id para ver si existe: " + id.value
                    
					def existId = UIDBasedID.findByValue(id.value)
                    if (existId)
                    {
                        println "Ya existe!"
                        flash.message = "Ya existe la persona con id: " + id.value + ", verifique el id ingresado o vuelva a buscar la persona"
                        //
                        render(view: "create", model: [tiposIds: tiposIds])
						return
                    }
                    else
                    println "No existe!"
                }
                else
                {
					println "identificador obligatorio!!"
                    // Vuelve a la pagina
					
					//i18n
                    flash.message = "identificador obligatorio, si no lo tiene seleccione 'Autogenerado' en el tipo de identificador"

                    //return [tiposIds: tiposIds]
					render(view: "create", model: [tiposIds: tiposIds])
					return
                }
			}
            
			
			
			
			def personNameUserInstance = new PersonNameUser()
			personNameUserInstance.properties = params
			
			
			def personInstance = new Person( params ) // sexo, fechaNac (no mas)
            personInstance.addToIdentities( personNameUserInstance )
			
			println "personInstance.ids: "+personInstance.identities
			if(personInstance.identities.size()<1){
				
				//i18n
				flash.message = "se debe indicar una identidad (nombre y apellido) para la persona"
				render(view: "create", model: [personInstance: personInstance, tiposIds: tiposIds])
				return
			}
			
			
			
            def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' )
            personInstance.setFechaNacimiento( bd )

            personInstance.addToIds( id )
		
		
			
		
			
			if (personInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
				logged("Person creado correctamente, personId: "+personInstance.id+" ", "info", session.traumaContext.userId)
				redirect(action: "show", id: personInstance.id)
			}
			else {
				render(view: "create", model: [personInstance: personInstance,tiposIds: tiposIds])
			}
		
		
		
		
		}
    }
	

	


    def show = {
	
		def tiposIds = TipoIdentificador.list()
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
	
		
            return [personInstance: personInstance, tiposIds: tiposIds]
        }
		
	}

    def edit = {
		def tiposIds = TipoIdentificador.list()
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personInstance: personInstance, tiposIds: tiposIds]
        }
    }

	

	
	
    def update = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (personInstance.version > version) {
                    
                    personInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'person.label', default: 'Person')] as Object[], "Another user has updated this Person while you were editing")
                    render(view: "edit", model: [personInstance: personInstance])
                    return
                }
            }
//ini
		    def id = null
            if (params.root == TipoIdentificador.AUTOGENERADO)
            {
                // Verificar si este ID existe, para no repetir
                def extension = RandomGenerator.generateDigitString(8)
                id = UIDBasedID.create(params.root, extension)
                
                // Se deberia hacer con doWhile para no repetir el codigo pero groovy no tiene doWhile
                while ( UIDBasedID.findByValue(id.value) )
                {
                    extension = RandomGenerator.generateDigitString(8)
                    id = UIDBasedID.create(params.root, extension)
                }
            }else{
                if (params.extension && params.root)
                {
                    id = UIDBasedID.create(params.root, params.extension) // TODO: if !hasExtension => error
                    
                    // FIXME: verificar que no hay otro paciente con el mismo id
                    println "===================================================="
                    println "Busco por id para ver si existeee: " + id.value
                    def existId = UIDBasedID.findByValueLike(id.value)
                    // id.value.split("::")[0]
					
					
					if (existId)
                    {
                        println "Ya existe!"
                        flash.message = "Ya existe la persona con id: " + id.value + ", verifique el id ingresado o vuelva a buscar la persona"

						def tiposIds = TipoIdentificador.list()
						render(view: "edit", model: [personInstance: personInstance, tiposIds: tiposIds])
						
						return
                    }
                    else
                    println "No existe!"
                }
                else
                {
                    // Vuelve a la pagina
                    flash.message = "identificador obligatorio, si no lo tiene seleccione 'Autogenerado' en el tipo de identificador"
                    def tiposIds = TipoIdentificador.list()
                    //return [tiposIds: tiposIds]
                }
			}

//fin		
			if(id){
				def existExtension = false
				for( i in personInstance.ids){
					println(i.value.split('::')[0]+"\n\t"+params.root+"\n")
				   if( i.value.split('::')[0].toString() == (params.root)){
					   println("son iguales\n")
						i.value = id.value
						existExtension = true
						break
				   }

					
				}
				
				
				//verificar si existe

				if(!existExtension){
					personInstance.addToIds( id )
					println("no exite y fue agregado!! \n")
				}else{

					println("existe y no fue agregado!!\n")

				}
			}
			
			println "identidad "+personInstance.identities
			if( personInstance.identities!=[]){
					
				def personNameUserInstance = PersonNameUser.get(personInstance.identities.id[0])
				
				personNameUserInstance.properties = params
				
				

				if(!personNameUserInstance.hasErrors() && personNameUserInstance.save(flush: true)){
					
					
					println "Se salvo la identidad"
					
				}else{
					
					
					println "No se salvo la identidad"
					
				}
			}else{
			
				personInstance.addToIdentities(personNameUserInstance)
				println "no hay identidades en personinstance"
				
			}
			
			def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' )
            personInstance.setFechaNacimiento( bd )
            //personInstance.addToIdentities( personNameUserInstance )
			println "params: "+params
			personInstance.sexo = params.sexo
            if (!personInstance.hasErrors() && personInstance.save(flush: true)) {

                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
                logged("Person actualizado correctamente, personId: "+personInstance.id+" ", "info", session.traumaContext.userId)
				redirect(action: "show", id: personInstance.id)


            }
            else {
                              
                render(view: "edit", model: [personInstance: personInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }

	
	
	
	
	
	
	
	
    def delete = {
		println("identidades:::"+params.identities)
        def personInstance = Person.get(params.id)
		def existLoginAuth = false
		def loginAuthInstance
		def la
		//def loginAuthId = LoginAuth.executeQuery("select la.id from LoginAuth as la  where la.person =?",10)
		def loginAuthId = LoginAuth.executeQuery("select la.id from LoginAuth as la left join la.person as p where p.id =?",personInstance.id)
		println "size: "+loginAuthId.size()
		if(loginAuthId.size()>0){
			loginAuthInstance = LoginAuth.get(loginAuthId[0])
			existLoginAuth = true
		}
		/*def usuario = LoginAuth.createCriteria() {
					eq("person",personInstance.id)			
		} */
		println "authorization"+loginAuthId
      //  def rolid = personInstance.roles.get(1)

     //   def rol = Role.get(rolid)
		
        
        if (personInstance) {
            try {
               // rol.delete(flush: true)
			   if(existLoginAuth){
					loginAuthInstance.delete(flush: true)
			   }
                personInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
                logged("Person eliminado correctamente, personId: "+personInstance.id+" ", "info", session.traumaContext.userId)
				redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }
}
