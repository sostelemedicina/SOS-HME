package sos
import authorization.*
import demographic.role.*

class AdminSecurityFilters {

    def filters = {/*
        admin(controller:'admin|person|role|personNameUser|authorization|loginAuth|preguntaSecreta|reportes|domain', action:'*', invert:true) {
            before = {
			
				if(session.traumaContext){
					def login = LoginAuth.get( session.traumaContext.userId )

					// Roles de la persona
					def roles = Role.withCriteria {
						eq('performer', login.person)
					}

					def roleKeys = roles.type
				
					if ( roleKeys.intersect([Role.ADMIN]).size() > 0 ){
					
						flash.message = "Acceso invalido para usuario admin....!"
                                                redirect(controller:'admin')
					}
				}
            }
            after = {
                
            }
            afterView = {
                
            }
        }
    */}
    }
