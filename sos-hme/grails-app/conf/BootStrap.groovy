
import demographic.*
import demographic.contact.*
import demographic.party.*
import demographic.identity.*
import demographic.role.*
import hce.core.support.identification.*
import authorization.*
import hce.core.common.change_control.Version
import hce.HceService
import tablasMaestras.*
import hce.core.data_types.quantity.date_time.*

// TEST Folder
import hce.core.common.directory.Folder
import hce.core.data_types.text.*
import hce.core.common.archetyped.Archetyped
import org.springframework.web.context.support.WebApplicationContextUtils
//import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib // Para usar g.message

//carga de data inicial
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.commons.ApplicationHolder


//--Interceptores
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.UnsupportedCallbackException
/*import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
import org.apache.ws.security.WSConstants
import org.apache.ws.security.WSPasswordCallback
import org.apache.ws.security.handler.WSHandlerConstants
*/

class BootStrap {

    def hceService
    
    // Reference to Grails application. Lo inyecta.
    def grailsApplication
   
/*def customSecureServiceClientCdaFactory
def customSecureServiceClientImpFactory
  */
    def init = { servletContext ->

        println ""
        println "======= +++++++++++++++++++ ======="
        println "======= Bootstrap - SOS HME ======="
        println "======= +++++++++++++++++++ ======="
        println ""
        servletContext.conexionImp  = false //Se setea en falso el semaforo de conexion al IMP
        
        environments {
            loadData{
                
        println "======= +++++LOAD DATA+++++ ======="
            
             
        // TEST Folder
        //def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
        def appContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
        def messageSource = appContext.getBean( 'messageSource' )
        
        def folder
        def domains = grailsApplication.config.domains
        domains.each { domain ->
           
           folder = new Folder(
              //name: new DvText(value: g.message(code: domain)),
              name: new DvText(value: messageSource.getMessage(domain, new Object[2], new Locale('es'))),
              path: domain,
              archetypeNodeId: "at0001",         // Inventado
              archetypeDetails: new Archetyped(  // Inventado
                archetypeId: 'ehr.domain',
                templateId: 'ehr.domain',
                rmVersion: '1.0.2'
              )
           )
           
           // FIXME: no esta salvando...
           // TODO: setear atributos de Locatable
           
           if (!folder.save())
           {
              println folder.errors
              println folder.name.errors
              println folder.archetypeDetails.errors
           }
        }
        // /TEST Folder
        
        TimeZone.'default'= TimeZone.getTimeZone('GMT-04:30') //set the default time zone
 
 
        println " - START: Carga tablas maestras"
        
        // saco para acelerar la carga
        
      /*  println "   - CIE 10..."
        def codigos = Cie10Trauma.getCodigos()
        codigos.each { codigo ->
           if (!codigo.save()) println codigo.errors
        }
        */
        
        println "   - OpenEHR Concepts..."
        def oehr_concepts = OpenEHRConcept.getConcepts()
        oehr_concepts.each { concept ->
           if (!concept.save()) println concept.errors
        }
        
        println "   - Tipos de identificadores..."
        def identificadores = TipoIdentificador.getTipos()
        identificadores.each { id ->
           if (!id.save()) println id.errors
        }
        
        println "   - Motivos de consulta (idem tipos de evento)..."
        def eventos = MotivoConsulta.getTipos()
        eventos.each { evento ->
           if (!evento.save()) println evento.errors
        }
        
        println "   - Empresas emergencia movil..."
        def emergencias = EmergenciaMovil.getEmergencias()
        emergencias.each { emergencia ->
           if (!emergencia.save()) println emergencia.errors
        }
        
        println "   - Departamentos UY..."
        def departamentos = DepartamentoUY.getDepartamentos()
        departamentos.each { dpto ->
           if (!dpto.save()) println dpto.errors
        }
        
        println " - END: Carga tablas maestras"
        
        // TODO: no crear si ya existen
        
        // ----------------------------------------------------------------------------
        
       
        println " - Creacion de pacientes de prueba"
        
        
        
        def persona3 = new Person()
        persona3.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.2.1.1::44444444') )
        persona3.addToIdentities( new PersonNameUser(primerNombre:'Mata', primerApellido:'Lozano') )
        persona3.fechaNacimiento = new Date(83, 11, 26)
        persona3.type = "Person"
        persona3.sexo = "Femenino"
        if (!persona3.save()) println persona3.errors
        
        // Paciente con estudios imagenologicos en el CCServer local
        def persona6 = new Person()
        persona6.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.2.1.1::2178309') ) // id en el CCServer
        persona6.addToIdentities( new PersonNameUser(primerNombre:'CT', primerApellido:'Mister') )
        persona6.type = "Persona"
        persona6.sexo = "Masculino"
        if (!persona6.save()) println persona6.errors
        
        def persona_administrativo = new Person()
        persona_administrativo.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.2.1.1::3334442') )
        persona_administrativo.addToIdentities( new PersonNameUser(primerNombre:'John', primerApellido:'Doe') )
        persona_administrativo.type = "Persona"     
        persona_administrativo.sexo = "Maculino"
        if (!persona_administrativo.save()) println persona_administrativo.errors
        
        
        // Medico
        def role6 = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.MEDICO, performer:persona3, status: '1')
        if (!role6.save()) println role6.errors

        // Administrativo
        def role_adm = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.ADMINISTRATIVO, performer:persona_administrativo, status: '1')
        if (!role_adm.save()) println role_adm.errors
		
		// Admin
        def role_sudo = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.ADMIN, performer:persona6, status: '1')
        if (!role_sudo.save()) println role_sudo.errors
        
        
        // LOGINS
        // los password son encriptados antes de ser creados los login.
        String pass1 = "pass"
        String pass2 = "1234"
        String pass3 = "1234"
		
		
        // Login para el medico   
        def login = new LoginAuth(user:'user', pass:pass1,pass2:pass1, person:persona3)
        if (!login.save())  println login.errors

        // Login para el adminsitrativo
        def login_adm = new LoginAuth(user:'adm', pass:pass2,pass2:pass2, person:persona_administrativo)
        if (!login_adm.save())  println login_adm.errors
		
		// Login para el administrador o super usuario
        def login_sudo = new LoginAuth(user:'suuu', pass:pass3,pass2:pass3, person:persona6)
        if (!login_sudo.save())  println login_sudo.errors
        

        
        
        
       // Data inicial
       
     /*
        println " - Datos Iniciales Tablas Demograficas"
        
       
        Sql sql = Sql.newInstance(CH.config.dataSource.url, CH.config.dataSource.username,
        CH.config.dataSource.password, CH.config.dataSource.driverClassName)
        
        String sqlFilePath = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/lugarOptimo.sql")
        String sqlString = new File(sqlFilePath).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathConyugal = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/situacionConyugal.sql")
        String sqlStringConyugal = new File(sqlFilePathConyugal).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathNivelEducativo = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/nivelEducativo.sql")
        String sqlStringNivelEducativo = new File(sqlFilePathNivelEducativo).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathProfesion = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/profesion.sql")
        String sqlStringProfesion = new File(sqlFilePathProfesion).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathOcupacion = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/ocupacion.sql")
        String sqlStringOcupacion = new File(sqlFilePathOcupacion).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathEtnia = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/etnia.sql")
        String sqlStringEtnia = new File(sqlFilePathEtnia).eachLine {
            sql.execute(it)
        }
    */
                
      /*                                                              
        Map<String, Object> inProps = [:]
        inProps.put(WSHandlerConstants.ACTION, org.apache.ws.security.handler.WSHandlerConstants.USERNAME_TOKEN+" "+org.apache.ws.security.handler.WSHandlerConstants.TIMESTAMP +" "+org.apache.ws.security.handler.WSHandlerConstants.SIGNATURE+ " "+org.apache.ws.security.handler.WSHandlerConstants.ENCRYPT )
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, org.apache.ws.security.WSConstants.PW_DIGEST);
        inProps.put(WSHandlerConstants.PW_CALLBACK_REF, new CallbackHandler() {

              //SE CREA UNA INSTANCIA DE LA CLASE CallbackHandler
              //
              //Se sobreescribe el método handle()

            void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

                    WSPasswordCallback pc = (WSPasswordCallback) callbacks[0]
              
                    pc.setPassword("keystore")


            }
        })




        inProps.put(WSHandlerConstants.SIG_PROP_FILE, "server.properties")
        inProps.put(WSHandlerConstants.DEC_PROP_FILE, "server_key.properties")

        customSecureServiceClientCdaFactory.getInInterceptors().add(new WSS4JInInterceptor(inProps))
        */
        
            
        println "======= ++++END LOAD DATA+++ ======="
        }
       
            development{
        
    
                  println "======= ++++DEVELOPMENT+++ ======="
            }
        }
        
       




        println ""
        println "======= +++++++++++++++++++ ======="
        println "======= Bootstrap - SOS HME ======="
        println "======= +++++++++++++++++++ ======="
        println ""
        
     }
     def destroy = {
     }
} 