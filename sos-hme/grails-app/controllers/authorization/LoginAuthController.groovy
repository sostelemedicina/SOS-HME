package authorization
import java.util.regex.Matcher /*para uso de expresiones regulares*/
import java.util.regex.Pattern
import demographic.DemographicService
import demographic.party.Person

import util.FormatLog
/*
 *@author Angel Rodriguez Leon
 */
 
class LoginAuthController {

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
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
		def personUsers = Person.withCriteria{
				identities{
					eq("purpose", "PersonNameUser")
				}
			}
			
        return [loginAuthInstance: loginAuthInstance, personUsers: personUsers]
    }

    def save = {
		def loginAuthInstance = new LoginAuth(params)
		def personUsers = Person.withCriteria{
				identities{
					eq("purpose", "PersonNameUser")
				}
			}
		if(params.pass && params.user && params.pass2){
		
			if(params.pass.equals(params.pass2)){
			
			
				params.pass =  params.pass.encodeAsPassword()
				loginAuthInstance = new LoginAuth(params)
				
				
				 
				if (loginAuthInstance.save(flush: true)) {
					flash.message = "${message(code: 'default.created.message')}"
					
					logged("loginAuth creado correctamente, loginAuthId: "+loginAuthInstance.id+" ", "info", session.traumaContext.userId)
					redirect(action: "show", id: loginAuthInstance.id)
				}
				else {
					logged("loginAuth error al crear, loginAuthId: "+loginAuthInstance.error+" ", "error", session.traumaContext.userId)
					render(view: "create", model: [loginAuthInstance: loginAuthInstance, personUsers: personUsers])
				}
			}else{
				//la clave debe coincidir 
				flash.message = "${message(code: 'default.failur.key.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
				//flash.message = "${message(code: 'default.repeated.key.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
				render(view: "create", model: [loginAuthInstance: loginAuthInstance, personUsers: personUsers])
			
			}
		}else{
			
			flash.message = "${message(code: 'default.null.message', args: [message(code: 'loginAuth.user.label'), message(code: 'default.loginAuth.label')])}"
			render(view: "create", model: [loginAuthInstance: loginAuthInstance, personUsers: personUsers])
		}

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
            return [loginAuthInstance: loginAuthInstance]
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
                    render(view: "edit", model: [loginAuthInstance: loginAuthInstance])
                    return
                }
            }


            
            /*se verifica que las claves coincidan y que no sean vacias*/
            if(params.pass.equals(params.pass2) && !params.pass.isEmpty()){
                /*se codifica la clave*/
                params.pass =  params.pass.encodeAsPassword()


                /*se verifica que la clave no sea igual a la anterior*/
                if(!loginAuthInstance.pass.equals(params.pass)){
                
                
                loginAuthInstance.properties = params
                    if (!loginAuthInstance.hasErrors() && loginAuthInstance.save(flush: true)) {
                        
						flash.message = "${message(code: 'default.updated.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), loginAuthInstance.id])}"
                        logged("loginAuth clave actualizada correctamente, LoginAuth: "+loginAuthInstance.id+" ", "info", session.traumaContext.userId)
						redirect(action: "show", id: loginAuthInstance.id)
                    }else {

                        render(view: "edit", model: [loginAuthInstance: loginAuthInstance])
                    }
                }else{
                        flash.message = "${message(code: 'default.repeated.key.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
                        render(view: "edit", model: [loginAuthInstance: loginAuthInstance])
                        println "la nueva clave debe ser distinta a la anterior\n\n"
                }
            }else{
                /*en caso que el usuario no confirme la clave correctamente*/
                flash.message = "${message(code: 'default.failur.key.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
                println "ingrese y confirme su clave correctamente\n\n"
                render(view: "edit", model: [loginAuthInstance: loginAuthInstance])
            }
        }
        else {

            /*login no encontrado*/
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def loginAuthInstance = LoginAuth.get(params.id)
        if (loginAuthInstance) {
            try {
                loginAuthInstance.delete(flush: true)
                logged("loginAuth Eliminado correctamente, loginAuthId: "+loginAuthInstance.id+" ", "info", session.traumaContext.userId)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                
				logged("loginAuth eliminado de forma inesperada tambi: "+lifing, "error", session.traumaContext.userId)
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
            redirect(action: "list")
        }
    }
}
