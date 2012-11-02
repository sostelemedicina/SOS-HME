package authorization

class PreguntaSecretaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [preguntaSecretaInstanceList: PreguntaSecreta.list(params), preguntaSecretaInstanceTotal: PreguntaSecreta.count()]
    }

    def create = {
        def preguntaSecretaInstance = new PreguntaSecreta()
        preguntaSecretaInstance.properties = params
        return [preguntaSecretaInstance: preguntaSecretaInstance]
    }

    def save = {
        def preguntaSecretaInstance = new PreguntaSecreta(params)
        if (preguntaSecretaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta'), preguntaSecretaInstance.id])}"
            redirect(action: "show", id: preguntaSecretaInstance.id)
        }
        else {
            render(view: "create", model: [preguntaSecretaInstance: preguntaSecretaInstance])
        }
    }

    def show = {
        def preguntaSecretaInstance = PreguntaSecreta.get(params.id)
        if (!preguntaSecretaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta'), params.id])}"
            redirect(action: "list")
        }
        else {
            [preguntaSecretaInstance: preguntaSecretaInstance]
        }
    }

    def edit = {
        def preguntaSecretaInstance = PreguntaSecreta.get(params.id)
        if (!preguntaSecretaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [preguntaSecretaInstance: preguntaSecretaInstance]
        }
    }

    def update = {
        def preguntaSecretaInstance = PreguntaSecreta.get(params.id)
        if (preguntaSecretaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (preguntaSecretaInstance.version > version) {
                    
                    preguntaSecretaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta')] as Object[], "Another user has updated this PreguntaSecreta while you were editing")
                    render(view: "edit", model: [preguntaSecretaInstance: preguntaSecretaInstance])
                    return
                }
            }
            preguntaSecretaInstance.properties = params
            if (!preguntaSecretaInstance.hasErrors() && preguntaSecretaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta'), preguntaSecretaInstance.id])}"
                redirect(action: "show", id: preguntaSecretaInstance.id)
            }
            else {
                render(view: "edit", model: [preguntaSecretaInstance: preguntaSecretaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def preguntaSecretaInstance = PreguntaSecreta.get(params.id)
        if (preguntaSecretaInstance) {
            try {
                preguntaSecretaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'preguntaSecreta.label', default: 'PreguntaSecreta'), params.id])}"
            redirect(action: "list")
        }
    }
}
