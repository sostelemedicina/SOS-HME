package demographic.party

import demographic.identity.OrganizationName
import demographic.contact.Contact
import demographic.contact.Address
import demographic.contact.PostalAddress
class OrganizationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
   

    def list = {
        /*
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [organizationInstanceList: Organization.list(params), organizationInstanceTotal: Organization.count()]
         */
        def organizacionId = Organization.findByType("ambulatorio")
        
        if(organizacionId){
            redirect(action: 'show',id:organizacionId.id)
        }else{
            redirect(action: 'create')
        }
        
        
    }
   

    def create = {
        def organizationInstance = new Organization()
        //organizationInstance.properties = params
        return [organizationInstance: organizationInstance]
    }

    def save = {
       
        def partyType = "ambulatorio"
        def partyIdentityPurpose = "Nombre del centro ambulatorio"
        
        def organizationNameInstance = new OrganizationName(params.name)
        organizationNameInstance.name = params.nombre
        organizationNameInstance.purpose = partyIdentityPurpose
        organizationNameInstance.save()
        
        def organizationAddress = new PostalAddress()
        organizationAddress.asString = params.ubicacion
        organizationAddress.localidad = "La Castellana"
        organizationAddress.entidad = "Edo. Miranda"
        organizationAddress.municipio = "Mun. Chacao"
        organizationAddress.parroquia = "San Jose Chacao"
        organizationAddress.type = "localidad"
        organizationAddress.save()
        
        def organizationContact = new Contact()
        organizationContact.addToAddresses(organizationAddress)
        organizationContact.purpose = "localidad"
        organizationContact.timeValidityFrom = new Date()
        organizationContact.timeValidityTo = new Date() + 100
        organizationContact.save()
        
        def organizationInstance = new Organization()
        organizationInstance.type = partyType
        organizationInstance.addToIdentities(organizationNameInstance)
        organizationInstance.addToContacts(organizationContact)
        
        if (organizationInstance.save()) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'organization.label', default: 'Organization'), organizationInstance.id])}"
            redirect(action: "show", id: organizationInstance.id)
        }
        else {
            render(view: "create", model: [organizationInstance: organizationInstance])
        }
    }

    
    def show = {
        def organizationInstance = Organization.get(params.id)
       
        if (!organizationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
            redirect(action: "list")
        }
        else {
            [organizationInstance: organizationInstance]
        }
    }

    def edit = {
        def organizationInstance = Organization.get(params.id)
        if (!organizationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [organizationInstance: organizationInstance]
        }
    }

    def update = {
        def organizationInstance = Organization.get(params.id)
        if (organizationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (organizationInstance.version > version) {
                    
                    organizationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'organization.label', default: 'Organization')] as Object[], "Another user has updated this Organization while you were editing")
                    render(view: "edit", model: [organizationInstance: organizationInstance])
                    return
                }
            }
            organizationInstance.properties = params
            
           
            def organizationNameInstance = OrganizationName.get(organizationInstance.identities.id[0])
            organizationNameInstance.name = params.name
             
            if (!organizationNameInstance.hasErrors() && organizationNameInstance.save(flush: true)) {
                
                  def addressInstance = Address.get(organizationInstance.contacts.addresses[0].id[0])
                 
                addressInstance.asString = params.ubicacion
                addressInstance.entidad = params.entidad
                addressInstance.municipio = params.municipio
                addressInstance.parroquia = params.parroquia
                addressInstance.localidad = params.localidad
                
                
                  if (!addressInstance.hasErrors() &&  addressInstance.save(flush: true)) {
                       flash.message = "${message(code: 'default.updated.message', args: [message(code: 'organization.label', default: 'Organization'), organizationInstance.id])}"
                       redirect(action: "show", id: organizationInstance.id)
                  }else{
                    render(view: "edit", model: [organizationInstance: organizationInstance])   
                  }
                
               
            }
            else {
            
                render(view: "edit", model: [organizationInstance: organizationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        /*
        def organizationInstance = Organization.get(params.id)
        if (organizationInstance) {
            try {
                organizationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])}"
            redirect(action: "list")
        }*/
         redirect(action: "list")
    }
}
