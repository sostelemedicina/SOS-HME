import hce.core.common.change_control.Version
import hce.core.composition.* // Composition y EventContext
import hce.core.data_types.quantity.date_time.*
import converters.DateConverter
import demographic.role.Role
import authorization.LoginAuth
import hce.core.data_types.encapsulated.DvMultimedia
import org.codehaus.groovy.grails.commons.ApplicationHolder
import cda.*
import imp.*
import util.*

import tablasMaestras.TipoIdentificador
import hce.core.support.identification.UIDBasedID
import hce.core.common.directory.Folder
import hce.core.support.identification.ObjectID
import hce.core.support.identification.ObjectRef

import java.util.Date
import java.text.*

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 */
class RecordsController {

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

    def demographicService
    def authorizationService
    def hceService
    
    // FIXME: tambien esta implementadas en GuiGenController
    
    /**
     * Devuelve un Map con los templates configurados para el dominio actual.
     *
     * this.getDomainTemplates()
     *
     * @return Map
     */
    private Map getDomainTemplates()
    {
        //def routes = grailsApplication.config.domain.split('/') // [hce, trauma]
        //def domainTemplates = grailsApplication.config.templates
        //routes.each{
        //    domainTemplates = domainTemplates[it]
        //}
        //println domainTemplates
        
        // =============================================================
        // Nuevo: para devolver los templates del dominio seleccionado
        def domain = session.traumaContext.domainPath
        def domainTemplates = grailsApplication.config.templates2."$domain"
        // =============================================================

        return domainTemplates
    }
    
    /**
     * Devuelve todos los prefijos de identificadores de templates del domino actual.
     * @return
     */
    private List getSections()
    {
        def sections = []
        this.getDomainTemplates().keySet().each {
            
            sections << it
        }
        
        return sections
    }
	
    /**
     * Obtiene las subsecciones de una seccion dada.
     *
     * this.getSubsections('EVALUACION_PRIMARIA')
     *
     * @param section es el prefijo del id de un template
     * @return List
     */
    private List getSubsections( String section )
    {
        // Lista de ids de templates
        def subsections = []

        this.getDomainTemplates()."$section".each { subsection ->
            subsections << section + "-" + subsection
        }
        
        return subsections
    }
    
    
    def index = {

        redirect(action:'list')
    }
    
    // TODO: vista
    // Pantalla 2.1- Escritorio Medico-Administrativo
    def list = {
        
        def compos = []
       
        // FIXME: esto deberia hacerse con filters?
        if (!session.traumaContext || !session.traumaContext.domainPath) // puede pasar si caduca la session
        {
            // TODO: flash.message
            redirect(controller:'domain', action:'list')
            return
        }
         
        // ==========================================================================
        // TODO: filtrar registros por dominio (session.traumaContext.domainPath)
        println "dominio: " + session.traumaContext.domainPath
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )
         
        println "domain items objectId: " + domain.items.objectId.value
         
        // FIXME: si no coincide ningun criterio, devuelve todas las compos.
        // esto se resuelve teniendo la referencia inversa desde las compos
        // al parent Folder.
        compos = Composition.withCriteria {
            
            // La lista de items podria ser larguisima,
            // una solucion mas performante es que cada
            // composition tenga como parent al folder
            // domain. 'parent' es un atributo de Locatable (creo)
            
            // Inlist implementado mas o menos
            /*
            or {
            domain.items.each{ objref ->
            // Supongo que objref.type == 'COMPOSITION', y que objref.namespace=='local'
            // podria agregar un chequeo por las dudas.
            eq('id', Long.parseLong(objref.objectId.value))
                  
            println "ref compo id: " + objref.objectId.value
            }
            }
             */
            
            // Uso la referencia desde los hijos al padre, asi me ahorro el loop
            eq('rmParentId', domain.id)
            
            // TODO: paginacion
            // TODO: orden por fecha descendente
            // TODO: poner cantidad en config
           
            maxResults(10)
            firstResult(0)
            
            order("id", "desc") 
            //order("context.startTime.value", "desc") // no funca
        }

        def c = Composition.createCriteria()
        def total = c.count {
            eq('rmParentId', domain.id)
            order("id", "desc")
        }
         
        // ==========================================================================
         
        // TODO: filtrar registros por paciente, si hay un paciente en session.traumaContext.patientId
       
        // deselecciona el episodio que este seleccionado
        session.traumaContext.episodioId = null
         
         
        // Antes se devolvian todas las compositions, ahora se filtra por dominio.
        def format = new SimpleDateFormat("dd-MM-yyyy")
         def desde = format.format(new Date(0))
        def hasta = format.format(new Date())
        

        return [compositions: compos,
            userId: session.traumaContext.userId,
            domain: domain,
            total: total,
            estado: "todos",
            desde: desde,
            hasta:hasta]
    }

    def listFiltro= {
        println params
        def estado ="todos"
        def formatter = new SimpleDateFormat("yyyy-MM-dd")
        def desde = formatter.format(new Date(0))
        def hasta = formatter.format(new Date() + 1)

        def format = new SimpleDateFormat("dd-MM-yyyy")
        if(params.desde){
         
         def date = (Date)format.parse(params.desde)
         desde = formatter.format(date)


        }

        if(params.hasta){

         def date = (Date)format.parse(params.hasta)
         hasta = formatter.format(date + 1)


            
        }

      


        
        if(params.estado){
            estado = params.estado
        }


        def compos = []
        def vers =[]
        def total = 0
        def max = 10
        def offset = 0

        if (params.maxResults){ max =Integer.parseInt(params.maxResults)}
        if (params.offset){ offset =Integer.parseInt(params.offset)}


        // FIXME: esto deberia hacerse con filters?
        if (!session.traumaContext || !session.traumaContext.domainPath) // puede pasar si caduca la session
        {
            // TODO: flash.message
            redirect(controller:'domain', action:'list')
            return
        }
        // ==========================================================================
        // TODO: filtrar registros por dominio (session.traumaContext.domainPath)
        println "dominio: " + session.traumaContext.domainPath
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )

        println "domain items objectId: " + domain.items.objectId.value

        
            def contexts = EventContext.withCriteria {
                startTime{
                   // between("value", desde.format("yyyy-MM-dd"),hasta.format("yyyy-MM-dd") )
                between("value", desde,hasta )

                }
            }
            if (contexts.size>0)
            {

                compos = Composition.withCriteria {


                    eq('rmParentId', domain.id)
                    
                    
                    or {
                        contexts.each{ contx ->
                            eq('context', contx)

                        }
                    }
                    

                }
                if(compos.size>0){

                  def v = Version.createCriteria()
                  vers = v.list(max: max, offset: offset){

                        or {
                            compos.each{ data ->
                                eq('data', data)

                            }
                        }

                        if(estado != "todos"){
                        eq('lifecycleState',estado)}
                        order("data.id", "desc")

                  }
                  compos =[]
                   if(vers.size()>0){
                       
                        vers.each{
                            compos.add(it.data)

                        }

                   }
                 
                  total = vers.totalCount


                }


            

                
            

        }
		//session.traumaContext.episodioId = null


		render(template: 'listado', model:[compositions: compos,
            userId: session.traumaContext.userId,
            domain: domain,
            total: total,
            estado: estado,
            desde:params.desde,
            hasta:params.hasta])


	}
def preCreate = {

	def ids=[]

	def id = UIDBasedID.create(params.root,params.extension)
	
	ids[0] = id
	println "ids"+ ids
	redirect( action: 'create', params: [ids: ids])


}
    
// Pantalla 3.2- Crear Episodio
// Puede venir un patientId si creo el episodio para un paciente desde admision.
def create = {
   
    println "Create: " + params
    
    def identificadores = TipoIdentificador.getTipos()
    def sizeIden = identificadores.size()
    def i
    if(params.root && params.extension){
        for (i=0 ; i < sizeIden ; i++){
            if(identificadores[i].codigo == params.root){
                params.nombreCorto = identificadores[i].nombreCorto
            }
        }
    }    
        
        
    if (params.doit)
    {
        if(!params.startDate){

            flash.message = 'records.create.error.fecha'
            return
                
        }


        //  def startDate = DateConverter.iso8601ExtendedDateTimeFromParams( params, 'startDate_' )
        def startDate = DateConverter.iso8601ExtendedDateTimeFromParamsSOS( params.startDate )

        println "Parametro: " + params.startDate

        println "Startdate: " + startDate

        def composition = hceService.createComposition( startDate, params.otherContext )
        // TODO: verificar si se crea para un paciente:
        // - buscarlo por id en el servicio demografico
        // - asociarlo como subject de la composition
        // - guardar todo
            
        // FIXME: si hay un paciente seleccionado no deberia venir en params,
        //        deberia estar en HCESession.
        if (params.root && params.extension) // si viene el id del paciente
        {
            //def id = UIDBasedID.create(params.root,params.extension)
            println "Se crea un episodio para el paciente seleccionado"
            def partySelf = hceService.createPatientPartysSelf(params.root, params.extension)
            def participation = hceService.createParticipationToPerformer( partySelf )
            composition.context.addToParticipations( participation )
        }
            
            
        // Set parent
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )
        composition.padre = domain
            
            
        //XStream xstream = new XStream()
        //render( text: xstream.toXML(composition), contentType:'text/xml' )
        if (!composition.save())
        {
            // FIXME: haldlear el error si ocurre!, darle un mensaje lindo al usuario, etc.
			
			//log.error(composition.errors)
			logged(composition.errors,"error",session.traumaContext.userId)
			println "Error: " + composition.errors
        }
            
        // ------------------------------------------------------------------
        //
        // TODO: poner la composition dentro del folder del dominio actual
        //
        /*
        ObjectRef ref = new ObjectRef(
        namespace: 'local', // porque se usa el id local en la base para la composition
        type: 'COMPOSITION',
        objectId: new ObjectID( // FIXME: ObjectID en el RM es abstracta, ver si otra subclase encaja mejor o pedir que se relaje el modelo para usar directamente ObjectID.
        value: composition.id.toString() // El value es de tipo string
        )
        )
            
        domain.addToItems( ref )
        if (!domain.save())
        {
        // TODO: handlear el error
        println "Error al guardar domain folder: " + domain.errors
        }
         */
        // ------------------------------------------------------------------
            
        // Crea la version inicial
        def version = new Version(
            data: composition,
            timeCommited: new DvDateTime(
                value: DateConverter.toIso8601ExtendedDateTimeFormat( new Date() )
            )
        )
            
        if (!version.save())
        {
            println "ERROR: " + version.errors
			logged(version.errors,"error",session.traumaContext.userId)
			//log.error(version.errors)
        }
            
        // Pablo: antes volvia al listado.
        // Queda mas agil que vaya derecho al show luego de crear, asi empieza a registrar.
        
		
		logged("Episodio registrado correctamente, compositionId: "+composition.id+" ","info",session.traumaContext.userId)
		redirect(action:'show', id:composition.id)
        return
    }
}
    
def show = {
       
    // TODO: poner en sesion el episodio que se esta viendo
    println "Show: " + params
       
       
    // Si expira la sesion tengo que volver al listado para crearla de nuevo
    // FIXME: esto deberia estar en un pre-filter
    if (!session.traumaContext)
    {
        redirect(action:'list')
        return
    }
       
       
       def composition = Composition.get( params.id )
       
	   if(!composition){
			
			//regar flash.message error.label.episodio.invalido="El episodio seleccionado es invalido"
			
			flash.message="Episodio invalido!!"
			
			logged("Episodio incorrecto {compositionId: " + params.id+" ","error", session.traumaContext.userId)
//			log.error("Episodio incorrecto {compositionId: " + params.id +", userId: "+session.traumaContext.userId+", user: "+LoginAuth.get(session.traumaContext.userId).user+", person: "+
//					LoginAuth.get(session.traumaContext.userId).person+ ", roles: "+LoginAuth.get(session.traumaContext.userId).person.roles.type+"}")
			
			redirect(action:'list')
			return
	   }
		
		session.traumaContext.episodioId = Integer.parseInt(params.id) 
       
    // FIXME:
    // La primera vez que se muestra luego de seleccionar un paciente, esto da null.
    // Ver si es un tema de la carga lazy de las participations y si se resuelve con carga eager.
    // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
    def patient = hceService.getPatientFromComposition( composition )
    // NECESARIO PARA EL MENU
    def sections = this.getSections()
    //def subsections = this.getSubsections(templateId.split("-")[0]) // this.getSubsections('EVALUACION_PRIMARIA')
	
	
	// patient puede ser null si todavia no se selecciono un paciente para el episodio,
    // p.e. si la atencion es de urgencia, se atiente primero y luego se identifica al paciente.
   /* return [idComposition: params.id,
        composition: composition,
        patient: patient,
        episodeId: session.traumaContext?.episodioId,
        userId: session.traumaContext.userId,
        sections: sections, // necesario para el menu
        allSubsections: this.getDomainTemplates()
    ]*/
	
	
	if(patient)
		println "Patient from composition: " + patient
		
		logged("Episodio seleccionado correctamente episodioId: "+session.traumaContext.episodioId+" ", "info", session.traumaContext.userId)
		
	

    
		redirect(controller:'guiGen',action:'showRecord')
}
    
    
// TODO: vista listando links a templates segun config.
// Pantalla 5.1- Registro Clinico
def registroClinico = {
        
    // FIXME: desde que esta el filter del login esto no es necesario.
    // DEBE haber un episodio seleccionado para poder asociar el registro clinico.
    if (!session.traumaContext?.episodioId)
    {
        flash.message = 'trauma.list.error.noEpisodeSelected'
        redirect(action:'list')
        return
    }
       
    def domainTemplates = this.getDomainTemplates()
       
    def sections = [:]
    domainTemplates.keySet().each { sectionPrefix ->
        domainTemplates."$sectionPrefix".each { section ->
            
            if (!sections[sectionPrefix]) sections[sectionPrefix] = []
             
            // Tiro la lista de esto para cada "section prefix" que son los templates
            // de las subsecciones de la seccion principal.
            //println sectionPrefix + "-" + section
            sections[sectionPrefix] << sectionPrefix + "-" + section
        }
    }
       
    def composition = Composition.get( session.traumaContext?.episodioId )
       
    // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
    def patient = hceService.getPatientFromComposition( composition )
         
    return [sections: sections,
        composition: composition,
        episodeId: session.traumaContext?.episodioId,
        patient: patient,
        userId: session.traumaContext.userId]
}
    
    
def registroClinico2 = {
    
    if (!session.traumaContext?.episodioId)
    {
        flash.message = 'trauma.list.error.noEpisodeSelected'
        redirect(action:'list')
        return
    }
       
    def section = params.section
    def subsections = this.getSubsections(section) // this.getSubsections('EVALUACION_PRIMARIA')
    def firstSubSection = subsections[0]
        
    //println "section: " + section
    //println "firstSubSection: " + firstSubSection
        
    def composition = Composition.get( session.traumaContext?.episodioId )

    // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
    //def patient = hceService.getPatientFromComposition( composition )

    // FIXME: mismo codigo que en GuiGen generarTemplate
    if ( hceService.isIncompleteComposition( composition ) )
    {
        //g.hasContentItemForTemplate( episodeId: session.traumaContext?.episodioId, templateId: section+'-'+firstSubSection)
        def item = hceService.getCompositionContentItemForTemplate(composition, section+'-'+firstSubSection)
            
        // FIXME:
        // Esto ya se chequea en la vista, es mas simple chequearlo aca y que
        // la vista si tiene que generar how o generar template siempre llame
        // a registoClinico2.
        // Se fija si el episodio tiene o no registro para el template dado.
        //Si item es NULL no tiene registro para el template dado, entonces se genera el template
        //if (it.hasItem)
        if (item)
        {
            redirect(controller: 'guiGen',
                action: 'generarShow',
                params: [templateId: firstSubSection, //section+'-'+firstSubSection,
                    //episodeId: session.traumaContext?.episodioId,
                    //patient:patient,
                    //userId: session.traumaContext.userId,
                    id: item.id])
            return
        }
        else
        {
            redirect(controller: 'guiGen',
                action: 'generarTemplate',
                params: [templateId: firstSubSection, //section+'-'+firstSubSection,
                    //episodeId: session.traumaContext?.episodioId,
                    //patient:patient,
                    //userId: session.traumaContext.userId
                ])
            return
        }
    }
    else
    {
        flash.message = "registroClinico.warning.noHayRegistroParaLaSeccion"
        redirect( action: 'show', id: session.traumaContext?.episodioId)
        return
    }
}
    
/**
 * Acion auxiliar para mostrar imagenes guardas en DvMultimedia en la web.
 */
def fetch_mm = {
        
    response.setHeader("Cache-Control", "no-store")
    response.setHeader("Pragma", "no-cache")
    response.setDateHeader("Expires", 0)
          
    def image = DvMultimedia.get( params.id )
            
    if (image)
    {
        response.setContentType(image.mediaType.codeString)
        response.getOutputStream().write(image.data) // con byte[]
            
        response.outputStream.flush()
        response.outputStream.close()
    }

    return null
}
    
/**
 * Firma y cierra el registro (antes firmar y cerrar eran procesos separados: http://code.google.com/p/open-ehr-gen-framework/issues/detail?id=9).
 * in: id episode id
 */
def signRecord = {
        
    // FIXME: se tiene el id en session.traumaContext?.episodioId
    def composition = Composition.get( params.id )

    if (!composition)
    {
        flash.message = 'trauma.list.error.noEpisodeSelected'
        redirect(action:'list')
        return
    }
        
    // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
    def patient = hceService.getPatientFromComposition( composition )


    // Es necesario para mostrar el menu
    def sections = this.getSections()
    def subsections = [] // No hay porque estoy firmando el registro


    flash.message = null
    flash.error = null
        
    // Para retornarle a la vista
    def model = [episodeId: session.traumaContext?.episodioId,
        userId: session.traumaContext.userId,
        composition: composition,
        patient: patient,
        sections: sections,
        subsections: subsections,
        allSubsections: this.getDomainTemplates()
    ]
        
        
    // FIXME: cerrar y firmar deberian estar dentro de la misma transaccion y asegurar de que si fallo algo, el registro
    //        NO quede cerrado y no firmado, o abierto y firmado.
    if (params.doit)
    {
        if (!patient)
        {
            flash.error = "trauma.sign.noPatientSelected"
            return model
        }
           
        if (composition.composer)
        {
            flash.error = "trauma.sign.registryAlreadySigned"
            return model
        }

        if (!composition.content){
            flash.error = "trauma.sign.empty"
            return model


        }

        //en esta linea se verifica el usuario y password  para firmar una HME
        def auth = authorizationService.getLogin(params.user, params.pass)
        if (!auth)
        {
			logged("Firma de registro invalida user: "+params.user+" ","info", session.traumaContext.userId )
			//log.info("Firma de registro invalida {user: "+params.user+"}")
            flash.error = "trauma.sign.wrongSignature"
            return model
        }
            
        // Verificacion del rol, debe ser medico
        // Este problema puede pasar si estoy logueado como medico pero firmo con datos de un adminsitrativo.
        // TODO: un posible tema a ver es que pasa si la persona firmante no es la persona
        //       que esta logueada, puede pasar y no necesariamente es un problema.
        def roles = Role.withCriteria {
            eq('performer', auth.person)
        }
            
        def roleKeys = roles.type
        if ( !roleKeys.contains(Role.MEDICO) )
        {
			logged("Firma de registro no autorizada user: "+params.user+" ","info", session.traumaContext.userId)
			//log.info("Firma de registro no autorizada  {user: "+params.user+"}")
            flash.error = "trauma.sign.wrongSigningRole"
            return model
        }
                
            
        def person = auth.person
        def id = person.ids[0] // FIXME: ver si tiene ID, DEBERIA TENER UN ID SIEMPRE, es un medico!

            
        // Cierra el registro
        if ( !hceService.closeComposition(composition, DateConverter.toIso8601ExtendedDateTimeFormat(new Date())) )
        {
            flash.error = "trauma.sign.closeInternalError"
            logged("Error interno al tratar de cerrar episodio compositionId: "+composition.id+" ","error",session.traumaContext.userId)
			//log.error("Error interno al tratar de cerrar episodio {compositionId: "+composition.id+"}")
			return model
        }
           
        // TODO:
        // Guardar digesto del registro para detectar alteraciones posteriores
        // Usar clave privada del medico para encriptar el digesto, y asi firmar el registro.
        //   Luego con su clave publica se podra decifrar el digesto y compararlo con el digesto original.
        //   Con esto se garantiza autoria, pero se necesita algun tipo de gestor de claves para mantener la publica y permitir que el medico ingrese la privada (que no se puede mantener en el sistema).
            
        // Firma el registro
        if (!hceService.setCompositionComposer(composition, id.root, id.extension))
        {
			logged("Error interno al tratar de firmar episodio, user: "+params.user+" ","error",session.traumaContext.userId)
			//log.error("Error interno al tratar de firmar episodio {user: "+params.user+"}")
            flash.error = "trauma.sign.signInternalError"
            return model
        }

        // Cambia el estado del regsitro en su VERSION
        def version = Version.findByData( composition )
        version.lifecycleState = Version.STATE_SIGNED
        version.save()

        flash.message = "trauma.sign.recordCorrectlySigned"
		logged("Firma de episodio valida, user: "+params.user+" ", "info", session.traumaContext.userId )
		//log.info("Firma de episodio valida {userId:"+session.traumaContext.userId+", user: "+
		//LoginAuth.get(session.traumaContext.userId).user+", person: "+
		//LoginAuth.get(session.traumaContext.userId).person+ ", roles: "+
		//LoginAuth.get(session.traumaContext.userId).person.roles.type+"}")

        //AÑADIENDO CREACION AUTOMATICA DE CDA

        if(CdaRecords.registrarCda(params.id, this.getDomainTemplates())){

            //CDA CREADO AUTOMATICAMENTE
            println "CDA CREADO AUTOMATICAMENTE"
        }


            
        redirect(action:'registroClinico')
    }
        
    return model
}

//-------------------------------------------------------------------------------------------------------------
// Pantalla - Reapertura de registro
def reopenRecord = {
	println "reopenRecord!!!--"
    def composition = Composition.get( params.id )

    if (!composition)
    {
        redirect(action:'list')
        return
    }

    def version = Version.findByData( composition ) // Ojo. findByData retorna una coleccion. Como hay una sola version con esa composition retorna una instancia (porque al crear una nueva version, pongo null en el atributo data de la version)
    if (version.lifecycleState == Version.STATE_SIGNED  || version.lifecycleState==Version.STATE_COMPLETE)
    {
        // -----------------------------------------------------------------
        // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
        def patient = hceService.getPatientFromComposition( composition )
            
        /*
        def sections = [] // NECESARIO PARA EL MENU
        def subsections = [] // No hay porque estoy firmando el registro
        grailsApplication.config.hce.emergencia.sections.trauma.keySet().each { sectionPrefix ->
        sections << sectionPrefix
        }
         */
        def sections = this.getSections()
        def subsections = [] // No hay porque estoy firmando el registro

        //------------------------------------------------------------------
        //------------------------------------------------------------------

        flash.message = null
        flash.error = null

        if (params.doit)
        {
            //en esta linea se verifica el usuario y password  para reabrir la HME.
            def auth = authorizationService.getLogin(params.user, params.pass)
            if (!auth)
            {
				
                
				logged("Reapertura de episodio invalida, user: "+params.user+" ", "info", session.traumaContext.userId)
				//log.info("Reapertura de episodio invalida {user: "+params.user+"}")
				
				// TODO: i18n
                flash.error = "Firma erronea, verifique sus datos"
                return [episodeId: session.traumaContext?.episodioId,
                    userId: session.traumaContext.userId,
                    composition: composition,
                    patient: patient,
                    sections: sections,
                    subsections: subsections,
                    allSubsections: this.getDomainTemplates()
                ]
            }

            // Verificacion del rol, debe ser medico
            // Este problema puede pasar si estoy logueado como medico pero firmo con datos de un adminsitrativo.
            // TODO: un posible tema a ver es que pasa si la persona firmante no es la persona
            //       que esta logueada, puede pasar y no necesariamente es un problema.
            def roles = Role.withCriteria {
                eq('performer', auth.person)
            }

            def roleKeys = roles.type
            if ( !roleKeys.contains(Role.MEDICO) )
            {
				logged("Reapertura de episodio no autorizada, user: "+params.user+" ", "info", session.traumaContext.userId)
				//log.info("Reapertura de episodio no autorizada {user: "+params.user+"}")
                
				// TODO: i18n
				flash.error = "Firma erronea, la persona firmante no es medico"
                return [episodeId: session.traumaContext?.episodioId,
                    userId: session.traumaContext.userId,
                    composition: composition,
                    patient: patient,
                    sections: sections,
                    subsections: subsections,
                    allSubsections: this.getDomainTemplates()
                ]
            }


            def person = auth.person
            def id = person.ids[0] // FIXME: ver si tiene ID, DEBERIA TENER UN ID SIEMPRE, es un medico!

            if (!hceService.setVersionCommitter(version, id.root, id.extension))
            {
				//log.error
                // TODO: i18n
                flash.error = "Ocurrio un error al intentar firmar el registro clinico, intente de nuevo"
                
				logged("Error al tratar de crear object referencia para la nueva version en hce.hceService.setVersionCommitter ", "error", -1)

				
				return [episodeId: session.traumaContext?.episodioId,
                    userId: session.traumaContext.userId,
                    composition: composition,
                    patient: patient,
                    sections: sections,
                    subsections: subsections,
                    allSubsections: this.getDomainTemplates()
                ]
            }


            if(!hceService.setVersionPatient(version, composition)){
				
				//log.error
                // TODO: i18n
                flash.error = "Ocurrio un error al intentar firmar el registro clinico, intente de nuevo"
				
				logged( "Error intentando referenciar a paciente en la nueva version en hce.hceService.setVersionPatient", "error", -1)
                return [episodeId: session.traumaContext?.episodioId,
                    userId: session.traumaContext.userId,
                    composition: composition,
                    patient: patient,
                    sections: sections,
                    subsections: subsections,
                    allSubsections: this.getDomainTemplates()
                ]


            }

            // Cambia el estado del regsitro en su VERSION
            //def version = Version.findByData( composition )
            //version.lifecycleState = Version.STATE_SIGNED
            //version.save()


            // Creo CDA si no existe
            def archivoCDA = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '//' + version.nombreArchCDA)
            if (!archivoCDA.exists())
            {
                def cdaMan = new ManagerCDA()
                int idEpisodio = Integer.parseInt(params.id)
                cdaMan.createFileCDA(idEpisodio, this.getDomainTemplates())
            }

            // Creo una copia de la composition
            ////def new_composition = new Composition(archetypeNodeId: composition.archetypeNodeId,
            ////                                      name: composition.name,
            ////                                      archetypeDetails: composition.archetypeDetails,
            ////                                      path: composition.path,
            ////                                      composer: null,
            ////                                      context: composition.context,
            ////                                      category: composition.category,
            ////                                      territory: composition.territory,
            ////                                      language: composition.language)
            ////composition.content.each{e ->
            ////    new_composition.addToContent(e)
            ////}
            //RMLoader.recorrerComposition(composition, new_composition)

            // Elimino movimiento y firma de la composition (de la copia)
            def composerAux = composition.composer
            def contentAux = composition.content
            composition.composer = null
                
            // Esto no es mas necesario en la reapertura, porque cerrar el registro ya
            // no implica que se movio al paciente.
            //hceService.eliminarMovimientoComposition(composition)

                
            //composition.save()

            // Creo nueva versión (con motivo, firma, nombre Arch CDA, composition)
            def new_version = new Version(
                //data: composition,
                //timeCommited: new DvDateTime(
                //  value: DateConverter.toIso8601ExtendedDateTimeFormat( new Date() )
                //),
                //lifecycleState: Version.STATE_INCOMPLETE,
                //numeroVers: version.getNumVersion() + 1
            )

            new_version.data = composition
            new_version.timeCommited = new DvDateTime(value: DateConverter.toIso8601ExtendedDateTimeFormat(new Date()))
            new_version.lifecycleState = Version.STATE_INCOMPLETE
            new_version.numeroVers = version.getNumVersion() + 1
            println "XXXXXXXXXXXXXX------>>>> V0:" + version.getNumVersion()
            println "XXXXXXXXXXXXXX------>>>> V1:" + new_version.getNumVersion()

            if (new_version.save())
            {
                version.data = null
                if (version.save())
                {
					logged("Reapertura firmada correctamente, user: "+params.user+" ", "info", session.traumaContext.userId)
					//log.info("Reapertura firmada correctamente { user: "+params.user+"}")
                    // TODO: i18n
					flash.message = "Reapertura firmada correctamente"
                }
                else
                {
                    composition.composer = composerAux
                    composition.content = contentAux
                    version.data = composition;
					
					logged("Error creando nueva version en version.save()", "error", -1)
					logged(version.error, "error", -1)
					//log.error("Error creando nueva version en version.save()")
					//log.error(version.error)
                    // TODO: i18n
					flash.error = "Ocurrio un error al intentar firmar el registro clinico, intente de nuevo"
                }
            }
            else
            {
				logged("problema de creacion de nueva version en new_version.save()", "error", -1)
				logged(version.error, "error", -1)
				//log.error("problema de creacion de nueva version en new_version.save()")
				//log.error(version.error)
                // TODO: i18n
				flash.error = "Ocurrio un error al intentar firmar el registro clinico, intente de nuevo"
            }
                
            /*return [episodeId: session.traumaContext?.episodioId,
                userId: session.traumaContext.userId,
                composition: composition,
                patient: patient,
                sections: sections,
                subsections: subsections,
                allSubsections: this.getDomainTemplates()
            ]*/
             redirect(action:'registroClinico')
        }
		
        return [composition: composition,
            patient: patient,
            episodeId: session.traumaContext?.episodioId,
            userId: session.traumaContext.userId,
            sections: sections, // necesario para el menu
            subsections: subsections, // necesario para el menu
            allSubsections: this.getDomainTemplates()
        ]
         
    }
    else
    {
        // Vuelvo a la Pagina de Selección Episodio
        redirect(action:'list')
        return
    }
}
}
