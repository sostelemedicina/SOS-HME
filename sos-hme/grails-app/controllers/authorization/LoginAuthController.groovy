package authorization
import java.util.regex.Matcher /*para uso de expresiones regulares*/
import java.util.regex.Pattern
import demographic.DemographicService
import demographic.party.Person
import org.apache.commons.validator.EmailValidator
import java.net.InetAddress

import util.FormatLog
/*
 *@author Angel Rodriguez Leon
 */
 
class LoginAuthController {

    def demographicService
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    /*
     *@author Angel Rodriguez Leon
     *
     *
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

    def lostPassword = {
        flash.message = ""
       
        
         
    }
    def sendEmailLink = {
       
        def emailValidator = EmailValidator.getInstance()
        if (!emailValidator.isValid(params.userEmail)) {
            flash.message = "loginAuth.sendEmailLink.noValidEmail"
            render(view:'lostPassword')
            return 
        }
           
        def person = Person.withCriteria{
           
            eq("email", params.userEmail)
           
        }
        
        if(!person){
            
            //El usuario no existe
            flash.message = "loginAuth.sendEmailLink.noExisteEmail"
           render(view:'lostPassword')
            return 
            //Eniviar mensaje sobre la no existencia del email   
        }else{
           
            //El usuario existe
            flash.message = "loginAuth.sendEmailLink.mensaje"
            //Asignar idReset
           
            
            def loginAuth = LoginAuth.findByPerson(person[0])
           
            loginAuth.createIdReset()
            loginAuth.bandera = true
            loginAuth.save()
            ////Enviar Email
           
            //FIXME: SI TIENE MÁS DE UNA INTERFAZ DE RED DEVOLVERÁ ALEATORIAMENTE UN SOLO VALOR
            //REALIZAR PRUEBAS EN UN COMPUTADOR CON MÁS DE UNA INTERFAZ DE RED
            def link = "http://"+InetAddress.getLocalHost().getHostAddress() + ":"+ request.getLocalPort() + createLink(controller: 'loginAuth',action:'resetPassword',id:loginAuth.idReset)
            try{
                sendMail {
                    to loginAuth.person.email //Email del usuario
                    subject "Restablecer contraseña en SOS-HME" //Nombre del usuario
                    html "Haz click en el link para restablecer tu contraseña en SOS-HME <a href='"+link +"'>RESTABLECER CONTRASEÑA</a>"
                }
            }catch(Exception e){
                
                
                flash.message = "loginAuth.sendEmailLink.error"
                render(view:'lostPassword')
                return 
            }
            
            return
        }
     
    }
    def restablecerPassword = {
        if(params.id){
            def result =1 
            def loginAuth = LoginAuth.get(params.id)
            bindData(loginAuth, params)
            loginAuth.idReset = ""
            if(loginAuth.save(flush: true)){
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), loginAuth.id])}"
                loginAuth.discard()
                redirect(controller: "authorization",action: "login")
                return 
            }else{
                render(view: 'resetPassword',model:[loginAuth: loginAuth,result:result])
                return 
            }
        
        }  
        
    }
    def resetPassword = {
        
        if(params.id){
            def loginAuth = LoginAuth.existIdReset(params.id)
            if(loginAuth){
                //Link correcto
                //    flash.message = "loginAuth.resetPassword.mensaje"
                return [result:1, idReset: params.id, loginAuth: loginAuth]
            }else{
                //Link Errado
                flash.message = "loginAuth.resetPassword.linkErrado"
                 
                return [result:2]
            }
        }else{
            flash.message = "loginAuth.resetPassword.linkErrado"
            return [result:2]
            
        }
        
    }
    
    def newPassword = {
        
        if(params.userPassword == params.userPasswordRepeat){
            
            def loginAuth = LoginAuth.findByIdReset(params.idReset)

            if(loginAuth){
                    
                if(loginAuth.resetPassword(params.userPassword)){
                        
                    flash.message = "loginAuth.newPassword.mensaje"
                    return [result:1]
                }else{
                    flash.message = "loginAuth.newPassword.passwordInvalid"
                    return [result:-1]
                }
                        
            }else{
                flash.message = "loginAuth.resetPassword.linkErrado"
                return [result:-1]
            }
            
        }else{
            flash.message = "loginAuth.newPassword.passwordNotEqual"
            return [result:-1]
            
        }
        
        
        
    }
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [loginAuthInstanceList: LoginAuth.list(params), loginAuthInstanceTotal: LoginAuth.count()]
    }

    def create = {
        def loginAuthInstance = new LoginAuth()
        loginAuthInstance.properties = params
        
        return [loginAuthInstance: loginAuthInstance, personUsers: personasDisponibles(), listaPreguntas: PreguntaSecreta.list()]
    }

    def save = {
       
        
               
                
        def loginAuthInstance = new LoginAuth(params)
        loginAuthInstance.preguntaSecreta = PreguntaSecreta.get(params.pregunta)
                
        if (loginAuthInstance.save()) {
            flash.message = "${message(code: 'default.created.message')}"
            logged("loginAuth creado correctamente, loginAuthId: "+loginAuthInstance.id+" ", "info", session.traumaContext.userId)
            redirect(action: "show", id: loginAuthInstance.id)
        }
        else {
       
            logged("loginAuth error al crear, loginAuthId: "+loginAuthInstance.errors+" ", "error", session.traumaContext.userId)
            render(view: "create", model: [loginAuthInstance: loginAuthInstance, personUsers: personasDisponibles(), listaPreguntas: PreguntaSecreta.list()])
        }
    }
    public List personasDisponibles(){
         def p = Person.withCriteria{
            identities{
                eq("purpose", "PersonNameUser")
            }
            projections {
                distinct("id")
            }
        }
        def c = LoginAuth.createCriteria()
        def loginAuthInstaceList = c.list{
              person{
                projections {
                distinct("id")
                }
            }
        }
        List a =[]
        List b = []
        p.each{
            a.add(Person.get(it))
        }
        loginAuthInstaceList.each{
            b.add(Person.get(it))
        }
        
        def difference = (a-b)
        return difference
    }
    def show = {
        def loginAuthInstance = LoginAuth.get(params.id)
        if (!loginAuthInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
            redirect(action: "list")
        }
        else {
            [loginAuthInstance: loginAuthInstance]
        }
    }
   

    def edit = {
        def loginAuthInstance = LoginAuth.get(params.id)
        if (!loginAuthInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [loginAuthInstance: loginAuthInstance, listaPreguntas: PreguntaSecreta.list()]
        }
    }
	
	
    // este update es usado unicamente para cambiar de clave en caso de perdida de la anterior.
    def update = {
        def loginAuthInstance = LoginAuth.get(params.id)
        if (loginAuthInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (loginAuthInstance.version > version) {
                    
                    loginAuthInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'loginAuth.label', default: 'LoginAuth')] as Object[], "Another user has updated this LoginAuth while you were editing")
                    render(view: "edit", model: [loginAuthInstance: loginAuthInstance,listaPreguntas: PreguntaSecreta.list()])
                    return
                }
            } 
            
            bindData(loginAuthInstance, params)
            
           loginAuthInstance.preguntaSecreta = PreguntaSecreta.get(params.pregunta)
                   
            if (loginAuthInstance.save()) {
                    
                flash.message = "${message(code: 'loginAuth.update.update')}"
                logged("loginAuth clave actualizada correctamente, LoginAuth: "+loginAuthInstance.id+" ", "info", session.traumaContext.userId)
                redirect(action: "show", id: loginAuthInstance.id)
                //NO HACER GUARDADO AUTOMATICO PARA NO GENERAR UNA ENCRIPTACION DOBLE
                loginAuthInstance.discard() 
                return
                       
            }else {
                render(view: "edit", model: [loginAuthInstance: loginAuthInstance,listaPreguntas: PreguntaSecreta.list()])
                return
            }
              
           
        }else{
            /*en caso que el usuario no confirme la clave correctamente*/
            flash.message = "${message(code: 'default.failur.key.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
            //println "ingrese y confirme su clave correctamente\n\n"
            render(view: "edit", model: [loginAuthInstance: loginAuthInstance, listaPreguntas: PreguntaSecreta.list()])
            return
        }
     
    }

    def delete = {
        def loginAuthInstance = LoginAuth.get(params.id)
        if (loginAuthInstance) {
            try {
                loginAuthInstance.delete(flush: true)
                logged("loginAuth Eliminado correctamente, loginAuthId: "+loginAuthInstance.id+" ", "info", session.traumaContext.userId)
                flash.message = "${message(code: 'loginAuth.deleted.message')}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                
                logged("loginAuth eliminado de forma inesperada tambi: "+lifing, "error", session.traumaContext.userId)
                flash.message = "${message(code: 'loginAuth.not.deleted.message')}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'loginAuth.not.found.message')}"
            redirect(action: "list")
        }
    }
    
    def answerSecretQuestion = {
        
        if(params.userLogin){
            
        def loginAuthInstance = LoginAuth.findByUser(params.userLogin)
            if(loginAuthInstance){
                
                if(loginAuthInstance.preguntaSecreta){
                [pregunta:loginAuthInstance.preguntaSecreta.pregunta, userId: loginAuthInstance.id]
                }else{
                flash.message = "loginAuth.answerSecretQuestion.noQuestion"
                render(view:'lostPassword')
                return
                }

            }else{
                flash.message = "loginAuth.answerSecretQuestion.noUser"
                render(view:'lostPassword')
                return
            }

        }
        
        
    }
    def sendSecretAnswer ={
        if(params.userId){
           
            
           def loginAuthInstance = LoginAuth.get(params.userId)
           if(loginAuthInstance.respuesta == params.respuesta){
               flash.message = "Respuesta correcta, cambie su contraseña"
               
               redirect(controller:'authorization',action:'login', params:[doit:true,userId:loginAuthInstance.id,editPassword:true]) 
                
           }else{
               flash.message = "Respuesta incorrecta"
               redirect(action:'answerSecretQuestion',params: [userLogin: loginAuthInstance.user]) 
           }
           
            
        
        
        
        }else{
            flash.message = "No existe el usuario"
            render(view:'lostPassword')
            
        }
        
        
        
    }
}
