package authorization

class LoginAuthController {

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
        return [loginAuthInstance: loginAuthInstance]
    }

    def save = {
        def loginAuthInstance = new LoginAuth(params)
        if (loginAuthInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), loginAuthInstance.id])}"
            redirect(action: "show", id: loginAuthInstance.id)
        }
        else {
            render(view: "create", model: [loginAuthInstance: loginAuthInstance])
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
            loginAuthInstance.properties = params
            if (!loginAuthInstance.hasErrors() && loginAuthInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), loginAuthInstance.id])}"
                redirect(action: "show", id: loginAuthInstance.id)
            }
            else {
                render(view: "edit", model: [loginAuthInstance: loginAuthInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def loginAuthInstance = LoginAuth.get(params.id)
        if (loginAuthInstance) {
            try {
                loginAuthInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'loginAuth.label', default: 'LoginAuth'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
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
