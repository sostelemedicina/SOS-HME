package imp


import imp.Paciente
import com.thoughtworks.xstream.*
import java.io.OutputStream.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import cda.BuscarCDAByRangoResponse
import cda.BuscarCDAByIdResponse
import cda.BuscarCDAByPacienteResponse
import cda.BuscarCDAByPacienteAndOrganizacionResponse
import cda.BuscarCDAByPacienteAndRangoResponse

import cda.CdaArr
import imp.PacienteArr
import converters.*
import java.util.ArrayList

import demographic.party.*
import tablasMaestras.TipoIdentificador

import util.FormatLog


class ServiceController {

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


    /*
    WebService webService
    def wsdl = ApplicationHolder.application.config.wsdl
    def proxy
     */
    def customSecureServiceClientCda
    def customSecureServiceClientImp
    // def complexServiceClient

    def demographicService
    def hceService

    def index = {

        customSecureServiceClientCda.serviceMethod()

        render("<h1>Bien</h1>")
    }

    def subirImagen = {
        
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        def f= new File('web-app/images/close.png')
       // render f.name + " "+f.getBytes()

        def result= customSecureServiceClientImp.agregarImagenPaciente(f.getBytes(),f.name,'17',"eb8e7ad6-6957-4884-81e6-eb78e5005f92")

        if(result){

        render "<p>Imagen subida</p>"
        }else{

        render "<p>ERROR</p>"
        }
        

    }


    def registrarCda = {
//
//        //ID 'token' asignado a la organizacion en el IMP
//        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
//
//        def cda = new CdaArr()
//
//        cda.id = params.id //ESTE ES EL ID QUE TIENE ASIGNADO EN ESTE SISTEMA
//        cda.titulo = "Algun titulo"
//        cda.fechaCreacion = "1988-05-20" //formato dd-MM-yyyy
//        def cda_xml = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '//' + params.id + '.xml')
//
//        if(!cda_xml.exists()){
//
//            render "<p> Archivo: NO Existe</p>"
//            return false
//        }else{
//
//
//            cda.documento = cda_xml.getText() //xml
//
//            def result1 = customSecureServiceClientCda.registrarCDA(cda,"15",idOrganizacion)
//            render "<p>Listo revisar </p> <p>${result1}</p>"
//            return true
//
//        }

        



    }
    def eliminarCda = {
        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        long a = 19
        def result = customSecureServiceClientCda.eliminarCDA(a,"15",idOrganizacion)

        render "<p>"+result+"</p>"

    }

    def buscarCdaByRango = {
        
       //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        
        
        cda.ConjuntoCda result = customSecureServiceClientCda.buscarCDAByRango(
            XMLGregorianCalendarConverter.getXMLCalendar("1987-08-30","yyyy-MM-dd"),
            XMLGregorianCalendarConverter.getXMLCalendar("2010-10-30","yyyy-MM-dd"),
            0,
            idOrganizacion)
        
       // println "Resultado CDAs: "+ result
        if(result){
            
            render(view: "listaCdas", model: [result: result.listCdaArr, total: result.total])
        
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }

    }

  
    def buscarCdaByPacienteAndOrganizacion = {
        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        
        long a= params.org.toLong()
        
        def conexionImp = true
        cda.ConjuntoCda result
        try{
        result= customSecureServiceClientCda.buscarCDAByPacienteAndOrganizacion(
            params.pac,
            a,
            0,
            idOrganizacion)
        }catch(Exception e){

                //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
                result = null
            
        }
      //  println "Resultado CDAs: "+ result.id
        if(result){
            render(view: "listaCdas", model: [conexionImp:conexionImp,idPaciente: params.pac,idOrg:params.org,result: result.listCdaArr, total: result.total, llamar:'busquedaAllExternaByOrg'])
        }else{
            render(view: "listaCdas", model: [conexionImp:conexionImp,idPaciente: params.pac,result: null])
            //  render "<p>No se encontró CDA </p>"
        }


    }

    def buscarCdaById = {
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        def conexionImp = true
        cda.CdaArr result
        try{
        result= customSecureServiceClientCda.buscarCDAById(params.id.toInteger(), idOrganizacion)
        }catch(Exception e)
        {
             //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
                result = null
            
        }
        //    println "Resultado CDAs: "+ result
        if(result){

            if(params.render == 'xml'){

           
               response.setHeader("Content-disposition", "attachment; filename=cda-${result.getId()}-${result.getTitulo()}-${result.getFechaCreacion()}.xml");
               render(text: result.getDocumento(),contentType:"text/xml")
            }else if(params.render == 'cda'){


                
                def cda_xml = new StringReader(result.getDocumento())
                def cda_xsl = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '//' + 'CDA.xsl')
                //def cda_xsl = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '//' + 'CDA_CDATA.xsl')

                def factory = TransformerFactory.newInstance()
                def transformer = factory.newTransformer(new StreamSource(cda_xsl))

                StringWriter salida = new StringWriter()
                transformer.transform(new StreamSource(cda_xml), new StreamResult(salida))
                 //AÑADIENDO BOTON ATRAS
                        String cadena=salida.toString()
                        int indice = cadena.lastIndexOf("</body>")
                        String cadena_punta = cadena.substring(0,indice)
                        String cadena_cola = cadena.substring(indice, cadena.length())
                        //boton
                        String agregado = "<a href='../../demographic/seleccionarPaciente/"+params.idPaciente+"' class='atras'>Regresar</a>"
                        cadena = cadena_punta + agregado +cadena_cola

                        render(text:cadena.replace("&lt;","<").replace("&gt;",">"),contentType:"text/html",encoding:"UTF-8")
            }
        }else{
            render(view: "listaCdas", model: [idPaciente:params.idPaciente,conexionImp:conexionImp,result: null])
            //  render "<p>No se encontró CDA </p>"
        }

    }
    def listarOrganizaciones = {

        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        def conexionImp = true
        List<cda.OrgArr> result
        try{
        result = customSecureServiceClientCda.listarOrganizacionesByPaciente(params.id, idOrganizacion)
        }catch(Exception e){
             //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
                result = null

        }
        
        render(template: "listadoOrganizaciones", model: [pacienteId:params.id, result: result, conexionImp:conexionImp])
        


    }
/*    
	def agregarPacienteAjax = {

        def person=   Person.get(params.id)

        def personNamePatient = person.identities.find{
            it.purpose == 'PersonNamePatient'
        }

        
        if(personNamePatient){
            //   def paciente = Paciente.get(params.id)
            
            //RECIBIR PARAMS DEL PACIENTE

            def p = new PacienteArr()
            p.setIdPaciente(params.id) //ESTE ES EL ID QUE TIENE ASIGNADO EN ESTE SISTEMA

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
                
                
            }
            if(result){
			
				logged("Paciente agregado correctamente al IMP, patientId: "+params.id+" ", "info", session.traumaContext.userId)
                //println personNamePatient.foto
                //println personNamePatient.tipofoto

              if(personNamePatient.foto && personNamePatient.tipofoto){
                  def resultImagen= customSecureServiceClientImp.agregarImagenPaciente(personNamePatient.foto,
                                                      personNamePatient.tipofoto,
                                                      params.id,idOrganizacion)

                   if(resultImagen){
                   //Se agrega imagen
				   logged("Foto del paciente agregada correctamente al IMP , patientId: "+params.id+" ", "info", session.traumaContext.userId)
                   }else{
                   //No se pudo agregar imagen

                   }
                   

              }
              flash.message = "service.imp.agregarPaciente.true"
              flash.clase = "ok"
            }else{
                    flash.clase = "error"
                    if(!conexionImp){
                    flash.message = "service.imp.sinConexion"
                    }else{
                    flash.message = "service.imp.agregarPaciente.false"
                    }
            }
            
						render 	"<div class='close'><a href='#' class='simplemodal-close'>x</a></div>"+
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
								"<input type='button' value='ajax' class='buttonlogin' onclick='validImp(user.value, pass.value)'/>"+
						"</div></form></div>"+
						"<script>"+
							"var bas = function(){ jQuery.modal.close(); };"+
							"jQuery(document).ready(function() {"+
								"jQuery.modal.close();"+
						"});</script>"
			
			
			//sleep(300)
			return 0
			
            //redirect(controller:'demographic', action: 'show', params: [id: params.id, pestana: 'pestanaOpcionesImp'] )

        }else{
			
            render("<p>El paciente no existe</p>")

        }
    }
*/

    def agregarPaciente = {

        def person = Person.get(params.id)
		println "person: " + person
		println "person: " + session.traumaContext.authTemp
	
	if(session.traumaContext.authTemp.equals("start")){

	
		session.traumaContext.authTemp = "stop"
        def personNamePatient = person.identities.find{
            it.purpose == 'PersonNamePatient'
        }

        
        if(personNamePatient){
            //   def paciente = Paciente.get(params.id)
            
            //RECIBIR PARAMS DEL PACIENTE

            def p = new PacienteArr()
            p.setIdPaciente(params.id) //ESTE ES EL ID QUE TIENE ASIGNADO EN ESTE SISTEMA

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
                
                
            }
            if(result){
			
				logged("Paciente agregado correctamente al IMP, patientId: "+params.id+" ", "info", session.traumaContext.userId)
                //println personNamePatient.foto
                //println personNamePatient.tipofoto

              if(personNamePatient.foto && personNamePatient.tipofoto){
                  def resultImagen= customSecureServiceClientImp.agregarImagenPaciente(personNamePatient.foto,
                                                      personNamePatient.tipofoto,
                                                      params.id,idOrganizacion)

                   if(resultImagen){
                   //Se agrega imagen
				   logged("Foto del paciente agregada correctamente al IMP , patientId: "+params.id+" ", "info", session.traumaContext.userId)
                   }else{
                   //No se pudo agregar imagen

                   }
                   

              }
              flash.message = "service.imp.agregarPaciente.true"
              flash.clase = "ok"
            }else{
                    flash.clase = "error"
                    if(!conexionImp){
                    flash.message = "service.imp.sinConexion"
                    }else{
                    flash.message = "service.imp.agregarPaciente.false"
                    }
            }
            
            redirect(controller:'demographic', action: 'show', params: [id: params.id, pestana: 'pestanaOpcionesImp'] )

        }else{

            render("<p>El paciente no existe</p>")

        }
	}
    }
    def eliminarPaciente = {
	
	
        def person = Person.get(params.id)
		println "person: "+ person
		println "person: " + session.traumaContext.authTemp
		
	if(session.traumaContext.authTemp.equals("start")){
		
		session.traumaContext.authTemp = "stop"
        if(person){
            def personNamePatient = person.identities.find{
                it.purpose == 'PersonNamePatient'
            }

            if(personNamePatient){
                String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
                def conexionImp = true
                def result

                try{
                result= customSecureServiceClientImp.eliminarPaciente(params.id, idOrganizacion)
                }catch(Exception e){
                     //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
                result = null
                    
                }

                if(result){
                    
					logged("Paciente eliminado correctamente del IMP , patientId: "+params.id+" ", "info", session.traumaContext.userId)
					
					
                    //Cambiar el atributo inIMP de todos los CDA´s de el paciente eeliminado del IMP
                    hceService.changeVersionInIMP(params.id)

                    flash.message = "service.imp.eliminarPaciente.true"
                    flash.clase = "ok"
                  

                }else{
                    flash.clase = "error"
                    if(!conexionImp){
                    flash.message = "service.imp.sinConexion"
                    }else{
                    flash.message = "service.imp.eliminarPaciente.false"
                    }
                }
				
				
                redirect(controller:'demographic', action: 'show', params: [id: params.id, pestana: 'pestanaOpcionesImp'] )
            }else{

                render("<p>El paciente no existe</p>")

            }
        }else{

            render("<p>El Person no existe</p>")
        }
		
	}

    }
    def buscarPaciente = {

        def persona = Person.get(params.id)
        def name = persona.identities.find{ it.purpose == 'PersonNamePatient'}
        
        def objPaciente = new PacienteArr()
        objPaciente.setIdPaciente(params.id)
        
        objPaciente.setPrimerNombre(name.primerNombre)
        objPaciente.setSegundoNombre(name.segundoNombre)
        objPaciente.setPrimerApellido(name.primerApellido)
        objPaciente.setSegundoApellido(name.segundoApellido)
        

        def offset= params.offset.toInteger()
        
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        
        imp.ConjuntoPaciente result
        def conexionImp = true
        //try{
        result= customSecureServiceClientImp.buscarCandidatos(objPaciente,offset,idOrganizacion )
        //}catch(Exception e){
//            e.printStackTrace()
//             //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
//                conexionImp = false
//                result = null
//
//        }
        if(result){

            render(template: "candidatos", model: [conexionImp:conexionImp,idPacienteOrg: params.id, result: result.listPacienteArr, total: result.total])
        }else{


            render(template: "candidatos", model: [conexionImp:conexionImp,idPacienteOrg: params.id, result: null])
        }



    }
    def agregarRelacionPaciente = {
		println "params service: "+ params
        //ID 'token' asignado a la organizacion en el IMP
        
		if(session.traumaContext.authTemp.equals("start")){
			
			session.traumaContext.authTemp = "stop"
			String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
			
			long idCentroImp= params.idCentroImp.toLong()
			String idPacienteImp= params.idPacienteImp
			String idPacienteOrg = params.idPacienteOrg
			def result
			def conexionImp = true
			try{
			result= customSecureServiceClientImp.agregarRelacionPaciente(idCentroImp,idPacienteImp,idPacienteOrg,idOrganizacion)
			println "result: "+ result
			}catch(Exception e){
				

				 //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
					
					result = null

			}
			 if(result){
						logged("Relacion agregada correctamente para paciente, patientId: "+idPacienteOrg+" ", "info", session.traumaContext.userId)
						flash.message = "service.imp.agregarRelacionPaciente.true"
						flash.clase = "ok"
					   

				   }else{
						flash.clase = "error"
						if(!conexionImp){
						flash.message = "service.imp.sinConexion"
						}else{
						flash.message = "service.imp.agregarRelacionPaciente.false"
						}
					}

					redirect(controller:'demographic', action: 'show', params: [id: params.idPacienteOrg, pestana: 'pestanaOpcionesImp'] )

		}
    }
    def eliminarRelacionPaciente = {
		
		if(session.traumaContext.authTemp.equals("start")){
			session.traumaContext.authTemp="stop"
			//ID 'token' asignado a la organizacion en el IMP
			String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

			def result
			def conexionImp = true
			try{
			result= customSecureServiceClientImp.eliminarRelacionPaciente(params.id,idOrganizacion)
			}catch(Exception e){
				  //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
					conexionImp = false
					result = null
				
			}
			  if(result){
						flash.message = "service.imp.eliminarRelacionPaciente.true"
						flash.clase = "ok"
						logged("Relacion eliminada correctamente para paciente, patientId: "+params.id+" ", "info", session.traumaContext.userId)
					}else{
						flash.clase = "error"
						if(!conexionImp){
						flash.message = "service.imp.sinConexion"
						}else{
						flash.message = "service.imp.eliminarRelacionPaciente.false"
						}
					}

					redirect(controller:'demographic', action: 'show', params: [id: params.id, pestana: 'pestanaOpcionesImp'] )

		}
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
        def conexionImp = true
        cda.ConjuntoCda result
        try{
        result = customSecureServiceClientCda.buscarCDAByPacienteAndRango(
            params.id,
            d,
            h,
            offset,
            idOrganizacion)
        }catch(Exception e){
            //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
                result = null
        }

        //-------------------------------
        if(result){

            render(template: 'registroExterno', model: [conexionImp:conexionImp,idPaciente: params.id, externo: result.listCdaArr, total: result.total, llamar: 'busquedaExterna', desde:d,hasta:h] )
        }else{

            render(template: 'registroExterno', model: [conexionImp: conexionImp, externo: null])


        }


    }
    def busquedaAllExterna = {

        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset

        int offset

        if(params.marca=='fil'){

            offset = 0

        }else if(params.marca=='pag'){


            offset= (Integer) params.offset.toInteger()
        }

        //BUSCAR EN EL SERVICIO IMP ------------------------
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        def conexionImp = true
        cda.ConjuntoCda result
        try{
        result = customSecureServiceClientCda.buscarCDAByPaciente(
            params.id,
            offset,
            idOrganizacion)
        }catch(Exception e){
             //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
                result = null

        }
        //-------------------------------
        if(result){

            render(template: 'registroExterno', model: [conexionImp:conexionImp,idPaciente: params.id,externo: result.listCdaArr, total: result.total, llamar: 'busquedaAllExterna' ])
        }else{

            render(template: 'registroExterno', model: [conexionImp:conexionImp,externo: null])


        }


    }
    def busquedaAllExternaByOrg = {

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
        long a= params.idOrg.toLong()

        def conexionImp = true
        cda.ConjuntoCda result
        try{
        result= customSecureServiceClientCda.buscarCDAByPacienteAndOrganizacion(
            params.id,
            a,
            offset,
            idOrganizacion)
        }catch(Exception e){

                //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
                result = null

        }

        //-------------------------------
        if(result){

            render(template: 'registroExterno', model: [conexionImp:conexionImp,idPaciente: params.id,idOrg: params.idOrg,externo: result.listCdaArr, total: result.total, llamar: 'busquedaAllExternaByOrg' ])
        }else{

            render(template: 'registroExterno', model: [conexionImp:conexionImp,externo: null])


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
            if(set>=compos.size()){
                set = compos.size()-1
            }

            def rango = new IntRange(offset, set)
            def rangoCompos = compos.getAt(rango)
            render(template: 'registroInterno', model: [idPaciente: params.id, compositions: rangoCompos, compositionsSize: compos.size(), compositionsMax: max, llamar: 'busquedaAllInterna'])

        }else{
            render(template: 'registroInterno', model: [idPaciente: params.id, compositions: compos, compositionsSize: 0,compositionsMax: max ])

        }






    }

   

  
}



