package demographic.identity
import util.FormatLog

/*
 *@author Angel Rodriguez Leon
 */
 
class PersonNameUserController {
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
	

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personNameUserInstanceList: PersonNameUser.list(params), personNameUserInstanceTotal: PersonNameUser.count()]
    }

    def create = {
        def personNameUserInstance = new PersonNameUser()
        personNameUserInstance.properties = params
        return [personNameUserInstance: personNameUserInstance]
    }

    def save = {
        def personNameUserInstance = new PersonNameUser(params)
        if (personNameUserInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), personNameUserInstance.id])}"
			logged("PersonNameUser creado correctamente, personNameUserId: "+personNameUserInstance.id+" ", "info", session.traumaContext.userId)
			redirect(action: "show", id: personNameUserInstance.id)

        }
        else {
            render(view: "create", model: [personNameUserInstance: personNameUserInstance])
        }
    }

    def show = {
        def personNameUserInstance = PersonNameUser.get(params.id)
        if (!personNameUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            [personNameUserInstance: personNameUserInstance]
        }
    }

    def edit = {
        def personNameUserInstance = PersonNameUser.get(params.id)
        if (!personNameUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personNameUserInstance: personNameUserInstance]
        }
    }

    def update = {
        def personNameUserInstance = PersonNameUser.get(params.id)
        if (personNameUserInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (personNameUserInstance.version > version) {
                    
                    personNameUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'personNameUser.label', default: 'PersonNameUser')] as Object[], "Another user has updated this PersonNameUser while you were editing")
                    render(view: "edit", model: [personNameUserInstance: personNameUserInstance])
                    return
                }
            }
            personNameUserInstance.properties = params
            if (!personNameUserInstance.hasErrors() && personNameUserInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), personNameUserInstance.id])}"
				logged("PersonNameUser actualizado correctamente, personNameUserId: "+personNameUserInstance.id+" ", "info", session.traumaContext.userId)
				redirect(action: "show", id: personNameUserInstance.id)
            }
            else {
                render(view: "edit", model: [personNameUserInstance: personNameUserInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def personNameUserInstance = PersonNameUser.get(params.id)
        if (personNameUserInstance) {
            try {
                personNameUserInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
                logged("PersonNameUser eliminado correctamente, personNameUserId: "+personNameUserInstance.id+" ", "info", session.traumaContext.userId)
				redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
                logged("PersonNameUser error al persistir en la BD", "error", session.traumaContext.userId)
				redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
            logged("Identificador invalido para PersonNameUser", "error", session.traumaContext.userId)
			redirect(action: "list")
        }
    }
}
