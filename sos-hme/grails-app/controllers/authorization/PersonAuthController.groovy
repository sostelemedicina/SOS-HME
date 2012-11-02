package authorization

class PersonAuthController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personAuthInstanceList: PersonAuth.list(params), personAuthInstanceTotal: PersonAuth.count()]
    }

    def create = {
        def personAuthInstance = new PersonAuth()
        personAuthInstance.properties = params
        return [personAuthInstance: personAuthInstance]
    }

    def save = {
        def personAuthInstance = new PersonAuth(params)
        if (personAuthInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'personAuth.label', default: 'PersonAuth'), personAuthInstance.id])}"
            redirect(action: "show", id: personAuthInstance.id)
        }
        else {
            render(view: "create", model: [personAuthInstance: personAuthInstance])
        }
    }

    def show = {
        def personAuthInstance = PersonAuth.get(params.id)
        if (!personAuthInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personAuth.label', default: 'PersonAuth'), params.id])}"
            redirect(action: "list")
        }
        else {
            [personAuthInstance: personAuthInstance]
        }
    }

    def edit = {
        def personAuthInstance = PersonAuth.get(params.id)
        if (!personAuthInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personAuth.label', default: 'PersonAuth'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personAuthInstance: personAuthInstance]
        }
    }

    def update = {
        def personAuthInstance = PersonAuth.get(params.id)
        if (personAuthInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (personAuthInstance.version > version) {
                    
                    personAuthInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'personAuth.label', default: 'PersonAuth')] as Object[], "Another user has updated this PersonAuth while you were editing")
                    render(view: "edit", model: [personAuthInstance: personAuthInstance])
                    return
                }
            }
            personAuthInstance.properties = params
            if (!personAuthInstance.hasErrors() && personAuthInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'personAuth.label', default: 'PersonAuth'), personAuthInstance.id])}"
                redirect(action: "show", id: personAuthInstance.id)
            }
            else {
                render(view: "edit", model: [personAuthInstance: personAuthInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personAuth.label', default: 'PersonAuth'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def personAuthInstance = PersonAuth.get(params.id)
        if (personAuthInstance) {
            try {
                personAuthInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'personAuth.label', default: 'PersonAuth'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'personAuth.label', default: 'PersonAuth'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personAuth.label', default: 'PersonAuth'), params.id])}"
            redirect(action: "list")
        }
    }
}
