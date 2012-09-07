/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */

import demographic.party.*
import demographic.identity.PersonName
import demographic.role.*
import authorization.LoginAuth

import hce.core.support.identification.UIDBasedID

import hce.HceService
import auth.AuthorizationService

import tablasMaestras.TipoIdentificador

import hce.core.composition.*

import com.thoughtworks.xstream.XStream

import converters.DateConverter

// TEST
import demographic.PixPdqDemographicAccess
import org.springframework.context.MessageSource
import org.codehaus.groovy.grails.commons.ApplicationHolder

// Para manejar eventos
import events.*

import util.RandomGenerator
import hce.core.common.archetyped.Locatable

/*Demograficos paciente*/
import demographic.identity.*
import java.text.SimpleDateFormat
import java.util.*
import java.text.*
/**/

import cda.*
import converters.*

import hce.core.common.directory.Folder
import hce.core.common.generic.*

import java.io.*
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH


/*reportes*/
import templates.TemplateManager
import tablasMaestras.Cie10Trauma

/*imagenes*/
import javax.activation.MimetypesFileTypeMap
import java.io.File
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.apache.commons.io.FileUtils
import util.*
import imp.PacienteArr

class DemographicController{

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


    def hceService
    def demographicService
    
    def customSecureServiceClientCda //Servicios del IMP - CDA
    def customSecureServiceClientImp //Servicios del IMP - IMP
    def index = {
        // Por defecto es la busqueda de pacientes
        redirect(action:'admisionPaciente')
    }
    
    /**
     * Comienzo de la admision del paciente (proceso de identificacion
     * y seleccion del paciente). Es la pantalla de ingreso de criterio de busqueda.
     */
    def admisionPaciente = {
        // TODO: me deberia venir un id de episodio para el cual
        // quiero seleccionar un paciente.
        def tiposIds = TipoIdentificador.list()
        return [tiposIds: tiposIds]
    }

    /**
     * Busqueda de candidatos.
     */
    def findPatient = {	// Por alguna razon no devuelve un objeto person con todos sus ids completos.

		//println "PARAMS: " + params + "\n"

		
        
        // TODO: aca va la consulta PIX al maciel.
        // Deberia hacerse como un strategy, definiendo una interfaz comun,
        // tanto para la entrada como para la salida.
        // Cual estrategia se elige, deberia sacarse de la config.
        // En la config dice que IMP se usa.
    
        
        def id = null
        if (params.identificador)
			id = new UIDBasedID(value:params.root+'::'+params.identificador)

        // TODO: usar rango de fechas... si viene solo una se usa esa como bd.
        
        // Para la fecha no funciona el bindData, lo hago a mano
        def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' ) // Si no vienen todos los datos, que sea null
        

        // TODO: si no hay datos para los nombres, no crear pn.
        def pn = null
        if (params.'personName.primerNombre'  || params.'personName.segundoNombre'||
            params.'personName.primerApellido' || params.'personName.segundoApellido')
        {
            pn = new PersonName()
            bindData(pn, params, 'personName')
            //println "Person Name: " + pn
        }
        
        
        println "======================================="
        println "buscando por: "
        println "   PN: "+ pn
        println "   BD: "+ bd
		println "   ID: "+ id
        println "======================================="

        def candidatos = []
		//(id) del candidato obtenido mediante un identificador uid
		def candidatosid = []
		//uids de el candidato obtenido mediante su id (candidatosid)
		def candidatosUidbid = []

		def i
		
		def idss = []

        try // La comunicacion puede tirar timeout
        {	
			
			candidatos = demographicService.findByPersonDataAndIdAndRole(
			pn,
			bd,
			null,
			id,
			Role.PACIENTE )
			
			
			//debido a que en la consulta de candidatos cuando se suministra un id esta no retorna
			// los otros identificadores UID de la Person seleccionada, se realizan estas consultas
			// para obtener todos los identificadores.
			if(id){
				candidatosid = Person.executeQuery("select p.id from Person as p left join p.ids as i left join p.identities as idt where i.value =? and idt.purpose =?",id.value, "PersonNamePatient")
				if(candidatosid){
					candidatosUidbid = Person.executeQuery("select uid.value from Person as p left join p.ids as uid left join p.identities as idt where p.id =? and idt.purpose =?", candidatosid[0], "PersonNamePatient")
					println "candidatos uidbid "+ candidatosUidbid
					for (i=0; i<candidatosUidbid.size(); i++) {
						idss[i] = new UIDBasedID(value:candidatosUidbid[i])
					}
					
				}
			}
			
			
			
			
        }
        catch (Exception e)
        {
            flash.message = "Ocurrio un error en la comunicacion, intente de nuevo"
            println "Ocurrio un error en la comunicacion " + e.getMessage()
        }
/*		
		println "candidatos: "+candidatos
		println "candidatosIds" + candidatosid
		
		def numIds = candidatosid.size()
		
		println "num candidatos: "+numIds

		
		println "idss " + idss

*/		
		


		
        def pacienteSeleccionado
		
        if(session.traumaContext.episodioId){
            pacienteSeleccionado = true

        }else{
            pacienteSeleccionado = false
        }
		
		
		
        render(view:'listaCandidatos', model:[candidatos:candidatos,pacienteSeleccionado:pacienteSeleccionado, idss:idss])
        
    } // findPatient
    
    /**
     * Selecciona a un paciente en el sistema para ser atendido (identificacion positiva).
     * PRE: el paciente debe tener por lo menos 1 id.
     */
    def abc = {

        render "<h1><p>SI FunCA</p></h1>"
        //render(template: 'digo')//[arm: "digo yo"]
    }

    def busquedaExterna = {

        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset
        println "params.desde::::"+params.desde
        println "params.hasta::::"+params.hasta
        //params.max
        // params.offset


        def desde = params.desde
        def hasta = params.hasta

        def d
        def h
        def offset

        if(params.marca=='fil'){


            if(desde==null) desde = new Date()
            if(hasta==null) hasta = new Date()

            d = desde.format("yyyy-MM-dd").toString()
            h = hasta.format("yyyy-MM-dd").toString()
            offset = 0

        }else if(params.marca=='pag'){

          
            offset= (Integer) params.offset.toInteger()
            d= desde
            h = hasta
        }

        //BUSCAR EN EL SERVICIO IMP ------------------------
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        cda.ConjuntoCda result = customSecureServiceClientCda.buscarCDAByPacienteAndRango(
            params.id,
            XMLGregorianCalendarConverter.getXMLCalendar(d,"yyyy-MM-dd"),
            XMLGregorianCalendarConverter.getXMLCalendar(h,"yyyy-MM-dd"),
            offset,
            idOrganizacion)

        //-------------------------------
        if(result){

            render(template: 'registroExterno', model: [idPaciente: params.id, externo: result.listCdaArr, total: result.total, llamar: 'busquedaExterna', desde:d,hasta:h] )
        }else{

            render(template: 'registroExterno', model: [externo: null])


        }


    }
    def busquedaAllExterna = {

        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset
        
        def offset

        if(params.marca=='fil'){
  
            offset = 0

        }else if(params.marca=='pag'){

        
            offset= (Integer) params.offset.toInteger()
        }

        //BUSCAR EN EL SERVICIO IMP ------------------------
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        cda.ConjuntoCda result = customSecureServiceClientCda.buscarCDAByPaciente(
            params.id,
            offset,
            idOrganizacion)

        //-------------------------------
        if(result){

            render(template: 'registroExterno', model: [idPaciente: params.id,externo: result.listCdaArr, total: result.total, llamar: 'busquedaAllExterna' ])
        }else{

            render(template: 'registroExterno', model: [externo: null])


        }


    }

    def busquedaInterna = {
        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset
        println "params.desde::::"+params.desde
        println "params.hasta::::"+params.hasta


        def persona = Person.get(params.id)
        def compos = [] //lista de composiciones (registros internos)

        def desde
        def hasta
        def offset
        def max

            
        if(params.marca=='fil'){
            desde = params.desde
            hasta = params.hasta
            offset =0
            max = 4

            if(desde==null) desde = new Date()
            if(hasta==null) hasta = new Date()
        
            
            
        }else if(params.marca=='pag'){

            offset = params.offset.toInteger()
            max = params.max.toInteger()

            desde = Date.parse("yyyy-MM-dd", params.desde)
            hasta = Date.parse("yyyy-MM-dd", params.hasta)



        }


        compos = hceService.getAllCompositionForPatient(persona, desde, hasta)
        if(compos){

            //if(max >= compos.size()){
            //    max = compos.size()
            //}
            def set = offset + max - 1
            if(set>=compos.size()){
                set = compos.size()-1
            }

            def rango = new IntRange(offset, set)
            def rangoCompos = compos.getAt(rango)
            def d = desde.format("yyyy-MM-dd").toString()
            def h = hasta.format("yyyy-MM-dd").toString()
            
            render(template: 'registroInterno', model: [idPaciente: params.id , compositions: rangoCompos, compositionsSize: compos.size(), compositionsMax: max,desde:d,hasta:h,llamar: 'busquedaInterna'])
                
        }else{
            render(template: 'registroInterno', model: [idPaciente: params.id , compositions: compos, compositionsSize: 0,compositionsMax: max ])
                
        }


        

       
        
    }
    
    def busquedaAllInterna = {
        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset
           


        def persona = Person.get(params.id)
        def compos = [] //lista de composiciones (registros internos)

          
        def offset
        def max


        if(params.marca=='fil'){
           
            offset =0
            max = 4

                
        }else if(params.marca=='pag'){

            offset = params.offset.toInteger()
            max = params.max.toInteger()
        }


        compos = hceService.getAllCompositionForPatient(persona, new Date(0), new Date())
        if(compos){

            //if(max >= compos.size()){
            //    max = compos.size()
            //}
            def set = offset + max - 1
            if(set>compos.size()){
                set = compos.size()-1
            }

            def rango = new IntRange(offset, set)
            def rangoCompos = compos.getAt(rango)
            render(template: 'registroInterno', model: [idPaciente: params.id, compositions: rangoCompos, compositionsSize: compos.size(), compositionsMax: max, llamar: 'busquedaAllInterna'])

        }else{
            render(template: 'registroInterno', model: [idPaciente: params.id, compositions: compos, compositionsSize: 0,compositionsMax: max ])

        }






    }


/*	private void agregarPacienteImp(String id){

		def person=   Person.get(id)
		
        def personNamePatient = person.identities.find{
            it.purpose == 'PersonNamePatient'
        }

        
        if(personNamePatient){
            //   def paciente = Paciente.get(params.id)
            
            //RECIBIR PARAMS DEL PACIENTE

            def p = new PacienteArr()
            p.setIdPaciente(id) //ESTE ES EL ID QUE TIENE ASIGNADO EN ESTE SISTEMA

            person.ids.each{
                def codigo = TipoIdentificador.findByCodigo(it.root)
                if((codigo.nombreCorto == "CI V")||(codigo.nombreCorto == "CI E")){
                        p.setCedula(it.extension)
                }
                if(codigo.nombreCorto == "Pasaporte"){
                        p.setPasaporte(it.extension)
                }
            }
            
            
            p.setPrimerNombre(personNamePatient.getPrimerNombre())
            p.setSegundoNombre(personNamePatient.getSegundoNombre())
            p.setPrimerApellido(personNamePatient.getPrimerApellido())
            p.setSegundoApellido(personNamePatient.getSegundoApellido())
            p.setSexo(person.getSexo())
            p.setFechaNacimiento(person.getFechaNacimiento().format("yyyy-MM-dd").toString())


            


            //ID 'token' asignado a la organizacion en el IMP
            String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

            def result
            def conexionImp = true
            try{
            result= customSecureServiceClientImp.agregarPaciente(p, idOrganizacion)
            }catch(Exception e){
                
                //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
                result = null
                println "catch!!"
                
            }
            if(result){
			//println "catch!!"
				//logged("Paciente agregado correctamente al IMP, patientId: "+params.id+" ", "info", session.traumaContext.userId)
                //println personNamePatient.foto
                //println personNamePatient.tipofoto

              if(personNamePatient.foto && personNamePatient.tipofoto){
                  def resultImagen= customSecureServiceClientImp.agregarImagenPaciente(personNamePatient.foto,
                                                      personNamePatient.tipofoto,
                                                      id,idOrganizacion)

                   if(resultImagen){
                   //Se agrega imagen
				   //logged("Foto del paciente agregada correctamente al IMP , patientId: "+params.id+" ", "info", session.traumaContext.userId)
                   //return 1
				   }else{
                   //No se pudo agregar imagen
					//return -1
                   }
                   

              }
              //flash.message = "service.imp.agregarPaciente.true"
              //flash.clase = "ok"
            }else{
				//return -2
            }
            
            //redirect(controller:'demographic', action: 'show', params: [id: params.id, pestana: 'pestanaOpcionesImp'] )

        }else{
			//return -3
            //render("<p>El paciente no existe</p>")

        }

	}*/
	
/*    def impValidate2 = {
		//render ""
		//println " parametros: " +params
		//println "submit validate!! id: "+params.idd
		def a = agregarPacienteImp(params.idd.toString())
		//println "a: "
		flash.clase = "error"
		flash.message = "service.imp.sinConexion"
		redirect(controller:'demographic', action: 'show', params: [id: params.idd, pestana: 'pestanaOpcionesImp'] )
		

	}
*/

def authorizationService

    def impValidate = {
		//render ""
		//println " parametros: " +params
		//println "submit validate!! id: "+params.idd
		//def a = agregarPacienteImp(params.idd.toString())
		//println "a: "
		
		def login = authorizationService.getLogin(params.user, params.pass)
		if (login){
                //http://localhost:9090/sos/service/agregarPaciente/30
				//http://localhost:9090/sos/service/eliminarPaciente/30
				//http://localhost:9090/sos/service/eliminarRelacionPaciente/30
				//http://localhost:9090/sos/service/agregarRelacionPaciente?idCentroImp=4&idPacienteImp=32&idPacienteOrg=30
				def roles = authorizationService.getRolesByPerformer(login.person)
				def roleKeys = roles.type
				
				if ( roleKeys.intersect([Role.MEDICO]).size() > 0 ){
					if(params.actionType == "addImpPatient"){
						session.traumaContext.authTemp = "start"
						redirect( controller:'service', action:'agregarPaciente', params: [id: params.idaddp.toString()] )
					}
					if(params.actionType == "delImpPatient"){
						session.traumaContext.authTemp = "start"
						redirect( controller:'service', action:'eliminarPaciente', params: [id: params.iddelp.toString()] )
					}
					if(params.actionType == "addImpRelation"){
						session.traumaContext.authTemp = "start"
						println "params demographic "+ params
						redirect(controller:'service', action: 'agregarRelacionPaciente',
						params: [idCentroImp: params.idaddr1,idPacienteImp: params.idaddr2 ,idPacienteOrg: params.idaddr3] )
						
						
					}
					if(params.actionType=="delImpRelation"){
						session.traumaContext.authTemp = "start"
						redirect(controller:'service', action:'eliminarRelacionPaciente', params:[id: params.iddelr])
					}

				}else{
					flash.clase = "error"
					flash.message = "service.imp.accesoNoAutorizado"
					//redirect(controller:'demographic', action: 'show', params: [id: params.idd, pestana: 'pestanaOpcionesImp'] )
						
					if(params.actionType.equals("addImpPatient"))
						redirect(controller:'demographic', action: 'show', params: [id: params.idaddp, pestana: 'pestanaOpcionesImp'] )		
					if(params.actionType.equals("delImpPatient"))
						redirect(controller:'demographic', action: 'show', params: [id: params.iddelp, pestana: 'pestanaOpcionesImp'] )
					if(params.actionType.equals("addImpRelation"))
						redirect(controller:'demographic', action: 'show', params: [id: params.idaddr3, pestana: 'pestanaOpcionesImp'] )
					if(params.actionType.equals("delImpRelation"))
						redirect(controller:'demographic', action: 'show', params: [id: params.iddelr, pestana: 'pestanaOpcionesImp'] )	
						
				
				}
		
		}else{
			flash.clase = "error"
			flash.message = "service.imp.accesoInvalido"
			
			if(params.actionType.equals("addImpPatient"))
				redirect(controller:'demographic', action: 'show', params: [id: params.idaddp, pestana: 'pestanaOpcionesImp'] )		
			if(params.actionType.equals("delImpPatient"))
				redirect(controller:'demographic', action: 'show', params: [id: params.iddelp, pestana: 'pestanaOpcionesImp'] )
			if(params.actionType.equals("addImpRelation"))
				redirect(controller:'demographic', action: 'show', params: [id: params.idaddr3, pestana: 'pestanaOpcionesImp'] )
			if(params.actionType.equals("delImpRelation"))
				redirect(controller:'demographic', action: 'show', params: [id: params.iddelr, pestana: 'pestanaOpcionesImp'] )		
		}
	}
	
	//validate para ventana modal con ajax
    
	def impValidatee = {
		params.id
		
		println "ajax validate!!"+params.id
		def array = params.id.split("-")
		if(array[0] == "" || array[1] == ""){
			println "usuario vacio"
			render 	"<div class='close'><a href='#' class='simplemodal-close'>x</a></div>"+
					"<div id='osx-modal-data'>"+
					"<h2>clave o usuario no puede ser vacio</h2>"+
					"<form action='/sos/demographic/impValidate' method='post'>"+
						"<div id='userlogin' class='userlogin'>"+
							"<input type='text' id='user' class='userlogin' value='Usuario' onfocus='focused(this)'/>"+
						"</div>"+
						"<div id='passlogin' class='userlogin'>"+
							"<input id='pass' name='pass' type='text' value='Contrase&ntilde;a' class='userlogin' onfocus='replaceT(this)'/>"+
						"</div>"+
						"<div id='ingresarboton' class='ingresarboton'>"+
							"<input type='submit' name='doit' id='doit' value='Ingresar' class='buttonlogin'/>"+
							"<input type='button' value='ajax' class='buttonlogin' onclick='validImp(user.value, pass.value, person_id2.value)'/>"+
					"</div></form></div>"+
					"<script>"+
						"var bas = function(){ jQuery.modal.close(); };"+
						"jQuery(document).ready(function() {"+
							"jQuery('.close a').click(bas)"+
					"});</script>"
					return 0
		
		}else{
			if(array[0].equals("user") && array[1].equals("pass")){
			def ident = array[2]
            //redirect( controller:'service', action:'agregarPacienteAjax', params: [id: ident] )
					
					//def a = agregarPacienteImp(ident)	
					//println "usuario valido"+a
					/*render 	"<div class='close'><a href='#' class='simplemodal-close'>x</a></div>"+
						"<div id='osx-modal-data'>"+
						"<h2>Accesos valido</h2>"+
						"<form action='/sos/demographic/impValidate' method='post'>"+
							"<div id='userlogin' class='userlogin'>"+
								"<input type='text' id='user' class='userlogin' value='Usuario' onfocus='focused(this)'/>"+
							"</div>"+
							"<div id='passlogin' class='userlogin'>"+
								"<input id='pass' name='pass' type='text' value='Contrase&ntilde;a' class='userlogin' onfocus='replaceT(this)'/>"+
							"</div>"+
							"<div id='ingresarboton' class='ingresarboton'>"+
								"<input type='submit' name='doit' id='doit' value='Ingresar' class='buttonlogin'/>"+
								"<input type='button' value='ajax' class='buttonlogin' onclick='validImp(user.value, pass.value, person_id2.value)'/>"+
						"</div></form></div>"+
						"<script>"+
							"var bas = function(){ jQuery.modal.close(); };"+
							"jQuery(document).ready(function() {"+
								"jQuery.modal.close();"+
						"});</script>"*/

						//println "usuario valido2"+a
						redirect( controller:'service', action:'agregarPacienteAjax', params: [id: ident] )
						return 0
						
						
			}else{
				println "usuario invalido"
				render 	"<div class='close'><a href='#' class='simplemodal-close'>x</a></div>"+
						"<div id='osx-modal-data'>"+
						"<h2>Accesso invalido</h2>"+
						"<form action='/sos/demographic/impValidate' method='post'>"+
							"<div id='userlogin' class='userlogin'>"+
								"<input type='text' id='user' class='userlogin' value='Usuario' onfocus='focused(this)'/>"+
							"</div>"+
							"<div id='passlogin' class='userlogin'>"+
								"<input id='pass' name='pass' type='text' value='Contrase&ntilde;a' class='userlogin' onfocus='replaceT(this)'/>"+
							"</div>"+
							"<div id='ingresarboton' class='ingresarboton'>"+
								"<input type='submit' name='doit' id='doit' value='Ingresar' class='buttonlogin'/>"+
								"<input type='button' value='ajax' class='buttonlogin' onclick='validImp(user.value, pass.value, person_id2.value)'/>"+
						"</div></form></div>"+
						"<script>"+
							"var bas = function(){ jQuery.modal.close(); };"+
							"jQuery(document).ready(function() {"+
								"jQuery('.close a').click(bas)"+
						"});</script>"
						return 0
			
			
			}
		

		
		}
		println "de otra forma.."
		
		//validacion de parametros not null
		
		render 	"<div class='close'><a href='#' class='simplemodal-close'>x</a></div>"+
				"<div id='osx-modal-data'>"+
				"<h2>Ingrese a SOS</h2>"+
				"<form action='/sos/demographic/impValidate' method='post'>"+
					"<div id='userlogin' class='userlogin'>"+
						"<input type='text' id='user' class='userlogin' value='Usuario' onfocus='focused(this)'/>"+
					"</div>"+
					"<div id='passlogin' class='userlogin'>"+
						"<input id='pass' name='pass' type='text' value='Contrase&ntilde;a' class='userlogin' onfocus='replaceT(this)'/>"+
					"</div>"+
					"<div id='ingresarboton' class='ingresarboton'>"+
						"<input type='submit' name='doit' id='doit' value='Ingresar' class='buttonlogin'/>"+
						"<input type='button' value='ajax' class='buttonlogin' onclick='validImp(user.value, pass.value)'/>"+
					"</div></form></div>"+
				"<script>"+
					"var bas = function(){ jQuery.modal.close(); };"+
					"jQuery(document).ready(function() {"+
						"jQuery('.close a').click(bas)"+
				"});</script>"
				return 0

	}

	
	
	
    def show ={


	
	
        //Mostrar detalle de un paciente
         def persona = Person.get(params.id)
		 
		if(!persona){
		//redirect(controller:'record', action:'list')
			//redirect( action : 'findPatient', params
			return 
		}
        
		if (persona.ids.size() == 0) // Debe tener un id!
            {
			
                redirect( action : 'findPatient',
                    params : ['flash.message': 'El paciente seleccionado no tiene identificadores, debe tener por lo menos uno.'] )
                return
            }
        //Folder domain = Folder.findByPath( session.traumaContext.domainPath )
            String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
            def agreImp
            def relaImp
            def conexionImp = SCH.servletContext.conexionImp


            //MANEJANDO EXCEPCION DE CONEXION AL IMP
            if(conexionImp){
            try{
            agreImp = customSecureServiceClientImp.existePaciente(params.id, idOrganizacion)

            if(agreImp){

                relaImp = customSecureServiceClientImp.existeRelacionPaciente(params.id, idOrganizacion)
            }else{
                relaImp = false

            }

            }catch(Exception e){

                //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
            }
            }

            def ids = persona.ids.toArray()
			def accesoValido = params.accesoValido
            println "ObjectID ": ids[0].root +"::"+ids[0].extension
            render( view:'show', model: [ userId: session.traumaContext.userId,persona: persona, root: ids[0].root, extension: ids[0].extension, conexionImp: conexionImp, agregadoImp: agreImp, relacionadoImp:relaImp, accesoValido: accesoValido])



    }
    def seleccionarPaciente = {
        
		
		
        // FIXME: esta hecho en base al id en la base, que pasa cuando la
        // seleccion se hace sobre un paciente en un IMP remoto y no esta en la base?
        
        // Guardo los resultados de consultar el IMP remoto en la base como cache.
        def persona = Person.get(params.id)
        
		if(!persona){
			
			//i18n
			flash.message = 'Episodio seleccionado Invalido'
			
			logged("Episodio seleccionado invalido","info",session.traumaContext.userId)
			
			redirect(controller:'records', action:'list')
			return
		}
		
        
        // =====================================================================
        // 1) Si no hay un episodio seleccionado, muestro la patalla de show del
        // paciente que tiene un boton "crear episodio" para abrir un episodio
        // para ese paciente, es la apertura desde admision.
        
        // =====================================================================
        // 2) Si hay un episodio seleccionado, entonces admision o el medico esta
        // seleccionando un paciente para ese episodio. Es un paciente existente
        // o uno ingresado en el momento. Previo a asignar a esta persona al
        // episodio se debe verificar que no se tenga ya una persona seleccionada
        // (o simplemente pongo la que me digan y que ellos corrijan).
        // Cada correccion debe tener un log de quien lo hizo.
        // Vuelve a la pantalla principal del episodio seleccionado (show).
		
		

        if (!session.traumaContext?.episodioId) // caso 1)
        {
            println "No hay epidosio seleccionado"
			
			logged("Paciente seleccionado correctamente sin episodio asaociado ", "info", session.traumaContext.userId)

            redirect(action:'show', params: params)




        }
        else // caso 2)
        {
            println "Hay un episodio seleccionado"
            
            // Pide el episodio a la base para agregarle la participation del paciente
            def composition = Composition.get( session.traumaContext.episodioId )
            
            // PRE: el episodio no deberia tener un paciente asignado.
            // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
            if ( hceService.getPatientFromComposition( composition ) )
            {
                flash.message = 'trauma.show.feedback.patientAlreadySelectedForThisEpisode'
                redirect( controller:'records', action:'show',
                    params: [id: session.traumaContext.episodioId, 'flash.message': 'trauma.show.feedback.patientAlreadySelectedForThisEpisode'] )
                return
            }
            
            //println "IDS: " + persona.ids
            
            // Crea la participacion del paciente para la compostion del episodio
            if (persona.ids.size() == 0) // Debe tener un id!
            {
                redirect( controller:'records', action:'show',
                    params: [id: session.traumaContext.episodioId, 'flash.message': 'El paciente seleccionado no tiene identificadores, debe tener por lo menos uno.'] )
                return
            }
            
            // Crea un PartyRef desde la composition hacia la persona usando una copia del id de la persona,
            // esto crea otra instancia de ObjectID con el valor igual al id de la persona.
            def ids = persona.ids.toArray()
            def partySelf = hceService.createPatientPartysSelf( ids[0].root, ids[0].extension )
            def participation = hceService.createParticipationToPerformer( partySelf )

            // TODO: agregar un participation a la composition deberia hacerse tambien en HceService.
            composition.context.addToParticipations( participation )
            
            // Si no le pongo flush:true parece que demora un poco mas en guardar el partyself y
            // vuelve a la pagina rapido y muestra que el episodio no tiene paciente.
            
			if (!composition.save(flush:true))
            {
                println "ERROR compo: " + composition.errors
				
				
				logged("Problemas en el save() de composition","error",-1)
				logged(composition.errors, "error", -1)
            }
            
            
            // Ejecuta eventos cuando el paciente seleccionado con exito.
            EventManager.getInstance().handle("post_seleccionar_paciente_ok", [composition:composition, persona:persona])
            
            
            //println "ERROR participation: " + participation.errors
            //println "ERROR partySelf: " + partySelf.errors
            
            // FIXME: cuando selecciona un paciente y vuelve al show del episodio,
            //        no se ve el paciente, si se hace reload de la pagina, se ve el paciente...
            //        puede ser un tema de aca (hay que hacer flush de la session o algo),
            //        o es un tema de carga lazy en el records.show para las participations del
            //        episodio.
            
			
			
			logged("Paciente seleccionado correctamente", "info", session.traumaContext.userId)
            
			redirect( controller:'records', action:'show',
                params: [id: session.traumaContext.episodioId] )
        }
        
        
        
        // Mientras rederea aprovecho para lanzar el evento
        EventManager.getInstance().handle("paciente_seleccionado",
            [
                patient: persona,
                episodeId: session.traumaContext?.episodioId // puede ser null
            ]
        )
    }
    
    /**
     * Agrega un nuevo paciente cuando el paciente a atender no esta en el sistema.
     */
    def agregarPaciente = {
        def tiposIds = TipoIdentificador.list()
        def etniasIds = Etnia.list()
        def profesionIds = Profesion.list()
        def conyugalIds = Conyugal.list()
        def nivelEducIds = Niveleducativo.list()
        def ocupacionIds = Ocupacion.list()
        def paisesIds = Lugar.findAllByTipolugarLike("Pais")
        def entidadesIds = Lugar.findAllByTipolugarLike("Estado")
        //println params
        
        // FIXME: si viene el id, verificar que no hay otro paciente con ese id, si lo hay, no dejar dar de alta, decirle que ya existe.
        
        // FIXME:  <%-- Solo se puede agregar un nuevo paciente si el repositorio es local --%>
        //  <g:if test="${ApplicationHolder.application.config.hce.patient_administration.serviceType.local}">
        
        if (params.doit)
        {
            // Veo si viene extension y root o si root es autogenerado
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
            }
            else
            {
                if (params.extension && params.root)
                {
                    id = UIDBasedID.create(params.root, params.extension) // TODO: if !hasExtension => error
                    
                    // FIXME: verificar que no hay otro paciente con el mismo id
                    println "===================================================="
                    println "Busco por id para ver si existe: " + id.value
                    
					def existPatient = Person.withCriteria{
						ids{
							eq("value", id.value)
						}
						
						identities{
							eq("purpose", "PersonNamePatient")
						}
					
					}
					
					//def existId = UIDBasedID.findByValue(id.value)
                    if (existPatient)
                    {
                        println "Ya existe!"
                        flash.message = "Ya existe la persona con id: " + id.extension + ", verifique el id ingresado o vuelva a buscar la persona"
                        //def tiposIds = TipoIdentificador.list()
                        return [tiposIds: tiposIds,
                                etniasIds : etniasIds,
                                profesionIds : profesionIds,
                                paisesIds : paisesIds,
                                conyugalIds : conyugalIds,
                                nivelEducIds : nivelEducIds,
                                ocupacionIds : ocupacionIds,
                                entidadesIds : entidadesIds]
                    }
                    else
                    println "No existe!"
                }
                
                
                else
                {
                    // Vuelve a la pagina
                    
					//i18n
					flash.message = "identificador obligatorio, si no lo tiene seleccione 'Autogenerado' en el tipo de identificador"
                    //def tiposIds = TipoIdentificador.list()
                    return [tiposIds: tiposIds,
                                etniasIds : etniasIds,
                                profesionIds : profesionIds,
                                paisesIds : paisesIds,
                                conyugalIds : conyugalIds,
                                nivelEducIds : nivelEducIds,
                                ocupacionIds : ocupacionIds,
                                entidadesIds : entidadesIds]
                }
                
            }

			
            def person = new Person() // sexo, fechaNac (no mas)
            
            //println("fechaNacimiento:->"+params.fechaNacimiento)
            def dia = params.fechaNacimiento.split('-')[0]
            def mes = params.fechaNacimiento.split('-')[1]
            def anio = params.fechaNacimiento.split('-')[2]
            String fecha = anio+'-'+mes+'-'+dia
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd")
            java.util.Date d =  sdf.parse(fecha.toString())
            person.fechaNacimiento =  d
            
            person.sexo = params.sexo
			
			def personId = Person.executeQuery("select p.id from Person as p left join p.ids as i where i.value =?",id.value)
			if(personId){
				def Personn = Person.get(personId[0])
				
				println "ids:   ->"+Personn
				
				def idTemp
				for(i in Personn.ids){
					idTemp = UIDBasedID.findByValue(i.value)
					println("identificador: "+idTemp+"\n")
					person.addToIds( idTemp )
				}
            
				println "ids:   ->"+person.ids
			}else
				person.addToIds( id )
            
            //def name = new PersonName(params)
            //person.addToIdentities( name )
            
            def datos = new PersonNamePatient(params)
            
            //foto del paciente

            if(params.foto){

                def x1 = params.x1 as Integer
                def y1 = params.y1 as Integer
                def x2 = params.x2 as Integer
                def y2 = params.y2 as Integer
                File tempPicture = new File(grailsApplication.config.images.location.toString() + File.separatorChar + params.foto)
                BufferedImage image = ImageIO.read(tempPicture)
                BufferedImage croppedImage = image.getSubimage(x1, y1, x2 - x1, y2 - y1)
                File profilePicture = new File(grailsApplication.config.images.location.toString() + File.separatorChar +"prueba.jpg")
                ImageIO.write(croppedImage, "jpg", profilePicture);
                FileUtils.deleteQuietly(tempPicture)


                //def f = request.getFile('foto')
                def okcontents = ['image/png' , 'image/jpeg' , 'image/gif']
                if(okcontents.contains(new MimetypesFileTypeMap().getContentType(profilePicture).toString())){
                    datos.foto = profilePicture.getBytes()
                    datos.tipofoto = new MimetypesFileTypeMap().getContentType(profilePicture).toString()
                }


            }
            /*---------------------------------*/
            person.addToIdentities(datos)
            
            
            if(person.save()){
                def role = new Role(timeValidityFrom: new Date(), type: "paciente", performer: person, timeValidityTo: new Date())
                if(role.save()){
                    
					logged("Paciente creado correctamente patientId: "+person.id+" ","info",session.traumaContext.userId)
					redirect(action:'seleccionarPaciente', id:person.id)
                }
                else{
                    logged("Error creando role al paciente ","error",session.traumaContext.userId)
					logged(" "+role.errors,"error",session.traumaContext.userId)
					println role.errors 
                }
            }else{
                logged("Error creando Person para el paciente ","error",session.traumaContext.userId)
				logged(person.errors,"error",session.traumaContext.userId)
				println person.errors
            }
			
            redirect(action:'admisionPaciente')
        
       }
        
        // creacion de un nuevo paciente
        
        
        return [tiposIds: tiposIds,
            etniasIds : etniasIds,
            profesionIds : profesionIds,
            paisesIds : paisesIds,
            conyugalIds : conyugalIds,
            nivelEducIds : nivelEducIds,
            ocupacionIds : ocupacionIds,
            entidadesIds : entidadesIds]
    }


    def fotoPrevia = {
       def foto
       def tipoFoto
            def okcontents = ['image/png' , 'image/jpeg' , 'image/gif']
            def f = request.getFile('fotoPrevia')
	    if(!f.empty) {
                if(okcontents.contains(f.getContentType())){
	      //flash.message = 'Your file has been uploaded'
		  new File( grailsApplication.config.images.location.toString() ).mkdirs()
		  f.transferTo( new File( grailsApplication.config.images.location.toString() + File.separatorChar + f.getOriginalFilename() ) )
                
                  render f.getOriginalFilename()
                  return

                }
            }
            render ""
    }


    def mostrarFotoPrevia = {

        if(params.id){
            
            def f = new File( grailsApplication.config.images.location.toString() + File.separatorChar + params.id )
            if(f.exists()){
               response.setContentType(new MimetypesFileTypeMap().getContentType(f).toString())
               response.setContentLength(f.getBytes().size())
               OutputStream out = response.getOutputStream()
               out.write(f.getBytes())
               out.close()
            }
        }
    }
    
    /**
     * Editar un paciente con datos incompletos.
     * in: id
     */
    def edit = {
        
        //println params
        // Si no viene el id, vuelvo a un punto seguro.
        if (!params.id)
        {
            if (session.traumaContext.episodioId)
            redirect(controller:'records', action:'show', id:session.traumaContext.episodioId)
            else
            redirect(controller:'records', action:'list')
            return
        }
        
        def patient = Person.get( params.id )
        
        
        def pn = patient.identities.find{ it.purpose == 'PersonNamePatient' }
        
        def entidadNace = null
        def municipioNace = null
        def paisNace = null
        def idPorEliminar = null
        def idDelete = null
        if(pn.lugar!=null){
            municipioNace =  pn.lugar
            entidadNace = Lugar.get(municipioNace.padre.id)
            paisNace = Lugar.get(entidadNace.padre.id)
        }
        
        def municipio =  Lugar.get(pn.direccion.padre.id)
        def estado = Lugar.get(municipio.padre.id)
        
        
        def tiposIds = TipoIdentificador.list()
        def etniasIds = Etnia.list()
        def profesionIds = Profesion.list()
        def conyugalIds = Conyugal.list()
        def nivelEducIds = Niveleducativo.list()
        def ocupacionIds = Ocupacion.list()
        def paisesIds = Lugar.findAllByTipolugarLike("Pais")
        def entidadesIds = Lugar.findAllByTipolugarLike("Estado")
        //def municipios = Lugar.findAllByTipolugarLike("Municipio")
        def municipios = Lugar.findAllByPadreLike(municipio.padre)
        //def parroquias = Lugar.findAllByTipolugarLike("Parroquia")
        def parroquias = Lugar.findAllByPadreLike(municipio)
        
        def fechaNace = patient.fechaNacimiento
        SimpleDateFormat formatter = new SimpleDateFormat ("dd-MM-yyyy")
        
        
        
        if (params.doit)
        {
            def idTemp = null    
            //patient.setProperties( params )
            //pn.setProperties( params )
            patient.sexo = params.sexo
            
            def numeroDeIds = patient.ids.size()
            def cicloIds
            def numeroIdentificador
            def tipoIdentificador
            
            
            
            for (cicloIds = 0; cicloIds < numeroDeIds; cicloIds++){
                numeroIdentificador = 'extension'+cicloIds
                tipoIdentificador = 'root'+cicloIds
                
                if(!(params[numeroIdentificador] in patient.ids.extension) || !(params[tipoIdentificador] in patient.ids.root)){// si los valores del form difieren de los ya registrados
                    if(numeroDeIds== 1 && params[numeroIdentificador]=='' && params[tipoIdentificador] != TipoIdentificador.AUTOGENERADO){
                        flash.message = "El Identificador no Puede quedar vacio"
                        return [patient:patient, pn:pn, tiposIds:tiposIds, 
                                etniasIds : etniasIds,
                                profesionIds : profesionIds,
                                paisesIds : paisesIds,
                                conyugalIds : conyugalIds,
                                nivelEducIds : nivelEducIds,
                                ocupacionIds : ocupacionIds,
                                entidadesIds : entidadesIds,
                                municipios : municipios,
                                parroquias : parroquias,
                                municipio : municipio,
                                estado : estado,
                                municipioNace : municipioNace,
                                entidadNace : entidadNace,
                                paisNace : paisNace,
                                fechaNace : formatter.format(fechaNace)]
                    }
                    else{
                            idPorEliminar = UIDBasedID.create(patient.ids[cicloIds].root,patient.ids[cicloIds].extension)
                            if(params[tipoIdentificador] == TipoIdentificador.AUTOGENERADO){ // en caso de que se decida cambiar el id por uno autogenerado
                                def extensionAutogeneraro = RandomGenerator.generateDigitString(8)
                                idTemp = UIDBasedID.create(params[tipoIdentificador], extensionAutogeneraro)
                                
                                while ( UIDBasedID.findByValue(idTemp.value) ){
                                    extensionAutogeneraro = RandomGenerator.generateDigitString(8)
                                    idTemp = UIDBasedID.create(params[tipoIdentificador], extensionAutogeneraro)
                                }
                            }
                            else{
                                idTemp = UIDBasedID.create(params[tipoIdentificador], params[numeroIdentificador]) // TODO: if !hasExtension => error
                            }

                            def existPatient = Person.withCriteria{
                                ids{
                                        eq("value", idTemp.value)
                                }
                                identities{
                                        eq("purpose", "PersonNamePatient")
                                }
                            }

                            if(existPatient){ // si el id ingresado en form ya pertenece a otro usuario del sistema
                                flash.message = "Ya existe la persona con id: " + idTemp.extension + ", verifique el id ingresado"
                                return [patient:patient, pn:pn, tiposIds:tiposIds, 
                                        etniasIds : etniasIds,
                                        profesionIds : profesionIds,
                                        paisesIds : paisesIds,
                                        conyugalIds : conyugalIds,
                                        nivelEducIds : nivelEducIds,
                                        ocupacionIds : ocupacionIds,
                                        entidadesIds : entidadesIds,
                                        municipios : municipios,
                                        parroquias : parroquias,
                                        municipio : municipio,
                                        estado : estado,
                                        municipioNace : municipioNace,
                                        entidadNace : entidadNace,
                                        paisNace : paisNace,
                                        fechaNace : formatter.format(fechaNace)] 
                            }else{
                                patient.addToIds(idTemp)
                                def eliminado = patient.ids.find{idPorEliminar}
                                def compositionsPatient = hceService.getAllCompositionForPatient(patient, new Date(0), new Date())
                                def numeroCompositions=0;
                                if(compositionsPatient!=null){
                                    numeroCompositions = compositionsPatient.size()
                                }
                                
                                def cicloCompositions
                                def compositionsParticular

                                for(cicloCompositions=0 ; cicloCompositions < numeroCompositions ; cicloCompositions++){

                                    compositionsParticular = compositionsPatient[cicloCompositions]

                                    def partySelf
                                    def pacienteEnCompositions 
                                    def participations = compositionsParticular.context.participations

                                    if(participations){
                                        def numeroParticipations = participations.size()
                                        def cicloParticipations 

                                        for (cicloParticipations = 0; cicloParticipations < numeroParticipations ; cicloParticipations ++){

                                            if(participations[cicloParticipations]!=null){
                                                pacienteEnCompositions = participations[cicloParticipations].find{ it.function.value == 'subject of care' }

                                                if (pacienteEnCompositions.performer.getClassName() == 'PartySelf'){
                                                    partySelf = PartySelf.get(pacienteEnCompositions.performer.id)
                                                    partySelf.externalRef.objectId = idTemp
                                                }
                                            }
                                        }

                                    }
                                    compositionsParticular.save()
                                }
                             // println compositionPatiet.getClass() // solo para que reviente .. codigo errado
                              patient.removeFromIds(eliminado)
                              eliminado.delete(flush:true)
                            }
                    }
                }
                else{  // los valores ingresados ya estaban en los ids del patient, no hay cambios 
                    println "pertenece"
                }
                
            }
           
            
            
            
            def dia = params.fechaNacimiento.split('-')[0]
            def mes = params.fechaNacimiento.split('-')[1]
            def anio = params.fechaNacimiento.split('-')[2]
            String fecha = anio+'-'+mes+'-'+dia
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd")
            java.util.Date d =  sdf.parse(fecha.toString())
            patient.fechaNacimiento =  d
            
            println "PN:: " + pn
            
            //--Guardo la foto anterior
            def auxFoto = pn.foto
            def auxTipoFoto = pn.tipofoto

            // borra el viejo
            patient.removeFromIdentities(pn)
            pn.delete()
            
            
            if(idDelete!=null){
                patient.removeFromIds(idDelete) // elimino el id que fue modificado   
            }
            
            
            
            // crea el nuevo
            pn = new PersonNamePatient(params)

            if(params.foto){

                def x1 = params.x1 as Integer
                def y1 = params.y1 as Integer
                def x2 = params.x2 as Integer
                def y2 = params.y2 as Integer
                File tempPicture = new File(grailsApplication.config.images.location.toString() + File.separatorChar + params.foto)
                BufferedImage image = ImageIO.read(tempPicture)
                BufferedImage croppedImage = image.getSubimage(x1, y1, x2 - x1, y2 - y1)
                File profilePicture = new File(grailsApplication.config.images.location.toString() + File.separatorChar +"prueba.jpg")
                ImageIO.write(croppedImage, "jpg", profilePicture);
                FileUtils.deleteQuietly(tempPicture)


                //def f = request.getFile('foto')
                def okcontents = ['image/png' , 'image/jpeg' , 'image/gif']
                if(okcontents.contains(new MimetypesFileTypeMap().getContentType(profilePicture).toString())){
                    pn.foto = profilePicture.getBytes()
                    pn.tipofoto = new MimetypesFileTypeMap().getContentType(profilePicture).toString()
                }


            }else{
                pn.foto = auxFoto
                pn.tipofoto = auxTipoFoto

            }

            patient.addToIdentities( pn )
            
            
            /*
            def person = new Person( params ) // sexo, fechaNac (no mas)
            
            def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' )
            person.setFechaNacimiento( bd )
            
            person.addToIds( id )
             */
            
            if (!patient.save(flush:true))
            {
                println patient.errors
                return [patient:patient, pn:pn, tiposIds:tiposIds, 
                        etniasIds : etniasIds,
                        profesionIds : profesionIds,
                        paisesIds : paisesIds,
                        conyugalIds : conyugalIds,
                        nivelEducIds : nivelEducIds,
                        ocupacionIds : ocupacionIds,
                        entidadesIds : entidadesIds,
                        municipios : municipios,
                        parroquias : parroquias,
                        municipio : municipio,
                        estado : estado,
                        municipioNace : municipioNace,
                        entidadNace : entidadNace,
                        paisNace : paisNace,
                        fechaNace : formatter.format(fechaNace)]
            }
            
            if (session.traumaContext.episodioId)
            redirect(controller:'records', action:'show', id:session.traumaContext.episodioId)
            else
            redirect(controller:'demographic', action:'show',id:params.id)
            return
        }
        
        // muestra pagina de edit
        return [patient:patient, pn:pn, tiposIds:tiposIds, 
                etniasIds : etniasIds,
                profesionIds : profesionIds,
                paisesIds : paisesIds,
                conyugalIds : conyugalIds,
                nivelEducIds : nivelEducIds,
                ocupacionIds : ocupacionIds,
                entidadesIds : entidadesIds,
                municipios : municipios,
                parroquias : parroquias,
                municipio : municipio,
                estado : estado,
                municipioNace : municipioNace,
                entidadNace : entidadNace,
                paisNace : paisNace,
                fechaNace : formatter.format(fechaNace)]
            
    }






    /*
     *@author Angel Rodriguez Leon
     *
     *Busca en la BD los valores de nombres de usuario identificados por el id
	 *y los trae para la creacion de un paciente a partir de estos datos.
     * */      
	 
	 def messageSource
    
	
		  
	def ajaxGetNombres = {
	
		if(!session.traumaContext){
			redirect(controller:'Authorization', action:'login')
		}
		
		//por alguna razon en el servidor estos mensajes no son desplegados correctamente
		//por lo tanto se debera migrar este codigo a la vista donde los codigos si son
		//desplegados correctamente.
		def m0 = messageSource.getMessage( 'persona.identificador', null, null)		
	
		def m1 = messageSource.getMessage( 'persona.primerApellido', null, null)
		
		def m2 = messageSource.getMessage( 'persona.segundoApellido', null, null)
		
		def m3 = messageSource.getMessage( 'persona.primerNombre', null, null)
		
		def m4 = messageSource.getMessage( 'persona.segundoNombre', null, null)	
	
		def m5 = messageSource.getMessage( 'persona.fechaNacimiento', null, null)
		
		def m6 = messageSource.getMessage( 'persona.sexo', null, null)
		
		def m7 = messageSource.getMessage( 'persona.foto', null, null)
		
		def codmsj = "1"
		
		//se modifica el valor  los mensajes para que sean desplegados correctamente.
		m0 = "Identificador [Tipo / Número]"
		m1 = "Primer Apellido"
		m2 = "Segundo Apellido"
		m3 = "Primer Nombre"
		m4 = "Segundo Nombre"
		m5 = "Fecha de Nacimiento"
		m6 = "Sexo"
		m7 = "Foto del Paciente"
		//println "m2 "+m2
		def array
		def a = true
			array = params.id.split	("!")
			def primerApellido
			def segundoApellido
			def primerNombre
			def segundoNombre
			def fechaNacimiento
			def sexo
			def selectFemenino = ""
			def selectMasculino = ""
			def tiposIds = TipoIdentificador.list()
			def cadenaTiposIds = ""
			//println "root "+array[0]
			for(i in tiposIds){
					if(i.codigo.equals(array[0].toString()))
						cadenaTiposIds = cadenaTiposIds + "<option value='"+i.codigo+"' selected='selected'>"+ i.nombreCorto + "</option>\n"
					else
						cadenaTiposIds = cadenaTiposIds + "<option value='"+i.codigo+"' >"+ i.nombreCorto + "</option>\n"
				}
			//println "opciones: "+cadenaTiposIds
			if(array[2]!=null)
				primerApellido = array[2]
			else
				primerApellido = ""
			if(array[3]!=null)
				segundoApellido = array[3]
			else
				segundoApellido = ""
			if(array[4]!=null)
				primerNombre = array[4]
			else
				primerNombre = ""
			if(array[5]!=null)
				segundoNombre = array[5]
			else
				segundoNombre = ""
	
			if(array[6]==null){
					fechaNacimiento = ""
			}else{
					fechaNacimiento = array[6]
			
			}

			
			if(array[7]!=null)
				sexo = array[7]
			else
				sexo = ""				


			def id = null
			
			
			def candidatosUsuarios
			def existPatient
			try{
				id = new UIDBasedID(value:array[0]+'::'+array[1])
				println "id "+id
				candidatosUsuarios = demographicService.findUserById(id)
				existPatient = demographicService.findPatientById(id)
				//println "candidatosUsuarios: "+ candidatosUsuarios
				
/*
"<div style='clear:both'></div>"+
"<p><label for='identificador'>"+m0+"</label>"+
"<input type='text' name='extension' value='' onchange='updateNombres( root.value, extension.value, primerApellido.value, segundoApellido.value, primerNombre.value, segundoNombre.value, fechaNacimiento.value, sexo.value, 'fin')'/>"+
"<select id='root' name='root' onchange='updateNombres( root.value, extension.value, primerApellido.value, segundoApellido.value, primerNombre.value, segundoNombre.value, fechaNacimiento.value, sexo.value, 'fin')'>"+
cadenaTiposIds+"</select>"+
"</p>"+
								

*/		
				
				
				if(existPatient){ // si el paciente ya existe
						
						codmsj="2"
						render  "<div style='clear:both'></div>"+
								"<p>"+
                                "<label for='primerApellido'>"+m1+"</label>"+
                                                                
								"<input type='text' name='primerApellido' id='primerApellido' value='' style='margin-left:5px;'><span class='obligatorio'>&nbsp;*</span></p>"+

								"<p><label for='segundoApellido'>"+m2+"</label>"+
								"<input type='text' name='segundoApellido' id='segundoApellido' value='' style='margin-left:5px;'></p>"+

								"<p><label for='primerNombre'>"+m3+"</label>"+
								"<input type='text' name='primerNombre' id='primerNombre' value='' style='margin-left:5px;'><span class='obligatorio'>&nbsp;*</span></p>"+

								"<p><label for='segundoNombre'>"+m4+"</label>"+
								"<input type='text' name='segundoNombre' id='segundoNombre' value='' style='margin-left:5px;'></p>"+
								"<p><label for='fechaNacimiento'>"+m5+"</label>"+

								"<input name='fechaNacimiento' type='text' class='Date' id='fechaNacimiento' value='' style='margin-left:5px;'/> <span class='obligatorio'>&nbsp;*</span> <br /><br /></p>"+
								"<p><label for='sexo'>"+m6+"</label>"+
								"<select name='sexo' class='selectci' id='sexo' style='width: 175px; margin-left:5px;' >"+
								"<option value=''>Seleccione</option>"+
								"<option value='Masculino' >Masculino</option>"+
								"<option value='Femenino' >Femenino</option></select><span class='obligatorio'>&nbsp;*</span></p>"+
								"<p><label for='foto'>"+m7+"</label>"+
								"<input type='text' name='foto' id='foto' style='width: 300px; margin-left:5px;'/></p></div>"+
								"<script>jQuery(document).ready(function(){"+
								"jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',changeYear: true, buttonText: 'Calendario', buttonImage: '/sos/images/datepicker.gif', maxDate: new Date(), yearRange: '1900:2100', constrainInput: true, showButtonPanel: true, showOn: 'button' });"+
								"jQuery('#fechaNacimiento').attr('readonly',true);jQuery('#foto').attr('readonly',true);jQuery('#foto').click(function (){jQuery('#inputFotoPrevia').click();});"+	
								"jQuery('#errorms').html('Paciente ya existente');"+
								"jQuery('#extension').css('background-color','#FF9999');"+
								"jQuery('#identificadorUnico').val('novalido');"+
								"jQuery('#extension').focus();"+
								"jQuery('#extension').blur();"+
								"});</script>"
							
							
				}else{ // si no existe
				
					
				
					if(candidatosUsuarios){	//si existen usuarios con ese identificador

						
						def datos = [uno:"",dos:"",tres:"",cuatro:"",cinco:"",seis:""]
						println "candidatos: "+candidatosUsuarios.identities.primerNombre
						if(candidatosUsuarios.identities[0].primerApellido[0]!=null)
							datos.put("uno", candidatosUsuarios.identities[0].primerApellido[0])
						
						if(candidatosUsuarios.identities[0].segundoApellido[0]!= null)
							datos.put("dos", candidatosUsuarios.identities[0].segundoApellido[0])
						
						if(candidatosUsuarios.identities[0].primerNombre[0]!=null)
							datos.put("tres", candidatosUsuarios.identities[0].primerNombre[0])

						if(candidatosUsuarios.identities[0].segundoNombre[0]!=null)
							datos.put("cuatro", candidatosUsuarios.identities[0].segundoNombre[0])
						
						def formateador = new SimpleDateFormat("dd-MM-yyyy")

						if(candidatosUsuarios.fechaNacimiento[0]!=null){
							
							println "fechaa!!!! : " +formateador.format(candidatosUsuarios.fechaNacimiento[0])
							datos.put("cinco", formateador.format(candidatosUsuarios.fechaNacimiento[0]))
						}
						if(candidatosUsuarios.sexo[0]!=null)
							datos.put("seis", candidatosUsuarios.sexo[0])
						

						if(datos.get("seis").equalsIgnoreCase("masculino")){
							selectMasculino = "selected"
						}else if(datos.get("seis").equalsIgnoreCase("femenino")){
							selectFemenino = "selected"
						}
						codmsj="3"
						render  "<div style='clear:both'></div>"+
								"<p>"+
                                "<label for='primerApellido'>"+m1+"</label>"+
                                                                
								"<input type='text' name='primerApellido' id='primerApellido' value='"+datos.get("uno")+"' style='margin-left:5px;'><span class='obligatorio'>&nbsp;*</span>"+

								"<p><label for='segundoApellido'>"+m2+"</label>"+
								"<input type='text' name='segundoApellido' id='segundoApellido' value='"+datos.get("dos")+"' style='margin-left:5px;'>"+

								"<p><label for='primerNombre'>"+m3+"</label>"+
								"<input type='text' name='primerNombre' id='primerNombre' value='"+datos.get("tres")+"' style='margin-left:5px;'><span class='obligatorio'>&nbsp;*</span></p>"+

								"<p><label for='segundoNombre'>"+m4+"</label>"+
								"<input type='text' name='segundoNombre' id='segundoNombre' value='"+datos.get("cuatro")+"' style='margin-left:5px;'></p>"+
								"<p><label for='fechaNacimiento'>"+m5+"</label>"+
								"<input name='fechaNacimiento' type='text' class='Date' id='fechaNacimiento'  value='"+datos.get("cinco")+"' /> <span class='obligatorio'>&nbsp;*</span> <br /><br /></p>"+
								"<p><label for='sexo'>"+m6+"</label>"+
								"<select name='sexo' class='selectci' id='sexo' style='width: 175px; margin-left:5px;'>"+
								"<option value=''>Seleccione</option>"+
								"<option value='Masculino' "+selectMasculino+">Masculino</option>"+
								"<option value='Femenino' "+selectFemenino+">Femenino</option></select><span class='obligatorio'>&nbsp;*</span></p>"+
								"<p><label for='foto'>"+m7+"</label>"+
								"<input type='text' name='foto' id='foto' style='width: 300px; margin-left:5px;'/></p></div>"+
								"<script>jQuery(document).ready(function(){"+
								"jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',changeYear: true, buttonText: 'Calendario', buttonImage: '/sos/images/datepicker.gif', maxDate: new Date(), yearRange: '1900:2100', constrainInput: true, showButtonPanel: true, showOn: 'button' });"+
								"jQuery('#fechaNacimiento').attr('readonly',true);jQuery('#foto').attr('readonly',true);jQuery('#foto').click(function (){jQuery('#inputFotoPrevia').click();});"+	
								"jQuery('#errorms').html('&nbsp;')"+
								"jQuery('#extension').css('background-color','#FFF');"+
								"jQuery('#identificadorUnico').val('valido');"+
								"jQuery('#extension').focus();"+
								"jQuery('#extension').blur();"+
								"});</script>"
							
					}else{ // si no existen usuarios con ese identificador
					
						codmsj="4"

						if(sexo.equalsIgnoreCase("masculino")){
							selectMasculino = "selected"
						}else if(sexo.equalsIgnoreCase("femenino")){
							selectFemenino = "selected"
						}
						
						render  "<div style='clear:both'></div>"+
								"<p>"+
                                "<label for='primerApellido'>"+m1+"</label>"+
                                                                    
								"<input type='text' name='primerApellido' id='primerApellido' value='"+primerApellido+"' style='margin-left:5px;'><span class='obligatorio'>&nbsp;*</span></p>"+
								"<p><label for='segundoApellido'>"+m2+"</label>"+
								"<input type='text' name='segundoApellido' id='segundoApellido' value='"+segundoApellido+"' style='margin-left:5px;'></p>"+

								"<p><label for='primerNombre'>"+m3+"</label>"+
								"<input type='text' name='primerNombre' id='primerNombre' value='"+primerNombre+"' style='margin-left:5px;'><span class='obligatorio'>&nbsp;*</span></p>"+

								"<p><label for='segundoNombre'>"+m4+"</label>"+
								"<input type='text' name='segundoNombre' id='segundoNombre' value='"+segundoNombre+"' style='margin-left:5px;'></p>"+
								"<p><label for='fechaNacimiento'>"+m5+"</label>"+

								"<input name='fechaNacimiento' type='text' class='Date' id='fechaNacimiento' value='"+fechaNacimiento+"' style='margin-left:5px;'/> <span class='obligatorio'>&nbsp;*</span> <br /><br /></p>"+
								"<p><label for='sexo'>"+m6+"</label>"+
								"<select name='sexo' class='selectci' id='sexo' style='width: 175px; margin-left:5px;'>"+
								"<option value=''>Seleccione</option>"+
								"<option value='Masculino' "+selectMasculino+">Masculino</option>"+
								"<option value='Femenino' "+selectFemenino+">Femenino</option></select><span class='obligatorio'>&nbsp;*</span></p>"+
								"<p><label for='foto'>"+m7+"</label>"+
								"<input type='text' name='foto' id='foto' style='width: 300px; margin-left:5px;'/></p></div>"+
								"<script>jQuery(document).ready(function(){"+
								"jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',changeYear: true, buttonText: 'Calendario', buttonImage: '/sos/images/datepicker.gif', maxDate: new Date(), yearRange: '1900:2100', constrainInput: true, showButtonPanel: true, showOn: 'button' });"+
								"jQuery('#fechaNacimiento').attr('readonly',true);jQuery('#foto').attr('readonly',true);jQuery('#foto').click(function (){jQuery('#inputFotoPrevia').click();});"+	
								"jQuery('#errorms').html('&nbsp;');"+
								"jQuery('#extension').css('background-color','#fff');"+
								"jQuery('#identificadorUnico').val('valido');"+
								"jQuery('#extension').focus();"+
								"jQuery('#extension').blur();"+
								"});</script>"
							
					}
				}
				
				//println("funcion ajax get nombres finalizada con exito "+codmsj)
				
			}catch(ArrayIndexOutOfBoundsException e){
				//println "estoy en el catch"
				
				render 	"<div style='clear:both'></div>"+
						"<p>"+
						"<label for='primerApellido'>"+m1+"</label>"+
						"<input type='text' name='primerApellido' value=''><span class='obligatorio'>&nbsp;*</span></p>"+

						"<p><label for='segundoApellido'>"+m2+"</label>"+
						"<input type='text' name='segundoApellido' value=''></p>"+

						"<p><label for='primerNombre'>"+m3+"</label>"+
						"<input type='text' name='primerNombre' value=''><span class='obligatorio'>&nbsp;*</span></p>"+

						"<p><label for='segundoNombre'>"+m4+"</label>"+
						"<input type='text' name='segundoNombre' value=''></p>"+

						"<p><label for='fechaNacimiento'>"+m5+"</label>"+

						"<input name='fechaNacimiento' type='text' class='Date' id='fechaNacimiento' value=''/> <span class='obligatorio'>&nbsp;*</span> <br /><br /></p>"+
						"<p><label for='sexo'>"+m6+"</label>"+
						"<select name='sexo' class='selectci' id='sexo' style='width: 175px;'>"+
						"<option value=''>Seleccione</option>"+
						"<option value='Masculino' >Masculino</option>"+
						"<option value='Femenino' >Femenino</option></select><span class='obligatorio'>&nbsp;*</span></p>"+
						"<p><label for='foto'>"+m7+"</label>"+
						"<input type='text' name='foto' id='foto' style='width: 300px;'/></p></div>"+
						"<script>jQuery(document).ready(function(){"+
						"jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',changeYear: true, buttonText: 'Calendario', buttonImage: '/sos/images/datepicker.gif', maxDate: new Date(), yearRange: '1900:2100', constrainInput: true, showButtonPanel: true, showOn: 'button' });"+
						"jQuery('#fechaNacimiento').attr('readonly',true);jQuery('#foto').attr('readonly',true);jQuery('#foto').click(function (){jQuery('#inputFotoPrevia').click();});"+	
						"jQuery('#mensaje').html('&nbsp;');"+
						"jQuery('#extension').css('background-color','#FF9999');"+
						"jQuery('#identificadorUnico').val('novalido');"+
						"});</script>"
			}
	}
	
	
	
    /*
     *@author Juan Carlos Escalante
     *
     *returna los estados asociados a un país en particulo (inicialmente solo Venezuela)
     *@param id corresponde a la entidad de padre para la busqueda recursiva de sus hijos */
    def ajaxGetEstados = {
        
        if(params.id.toLong() == 1){
            def list = Lugar.createCriteria().list{
                padre{
                    eq('id',params.id.toLong())
                }
            }
            render list.collect{ """<option value="${it.id}">${it.nombre}</option>""" }
        }
        else{
            def list = Lugar.findByNombreLike("Venezuela")
            render {"<input type='hidden' id='generarestados' value=0 />"}
            //render list.collect{ "<option value=-1>- Aplica Sólo a Venezuela</option>"}
        }
    }
    
    /*
     *@author Juan Carlos Escalante
     *
     *returna los municpios y parroquias para entidades seleccionadas (inicialmente solo Venezuela)
     *@param id corresponde a la entidad de padre para la busqueda recursiva de sus hijos */
    def ajaxGetMunicipios = {
        
        if(params.id.toLong() >= 1){
            def list = Lugar.createCriteria().list{
                padre{
                    eq('id',params.id.toLong())
                }
            }
                render list.collect{ """<option value="${it.id}">${it.nombre}</option>""" }
                //render {"""<option value=''>Seleccione</option>"""}
        }
        else{
            def list = Lugar.findByNombreLike("Venezuela")
            //render list.collect{ "<option value=-1>- Aplica Sólo a Venezuela</option>" }
        }
    }
    
    
    
    def fotopaciente = {
       def paciente = Person.get(params.persona)
       def datos = paciente.identities.find{ it.purpose == 'PersonNamePatient'}
       if(!datos || !datos.foto || !datos.tipofoto){
           response.sendError(404)
           return
       }
       response.setContentType(datos.tipofoto)
       response.setContentLength(datos.foto.size())
       OutputStream out = response.getOutputStream()
       out.write(datos.foto)
       out.close()
    }
    
  
}
