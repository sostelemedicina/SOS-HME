package sos
import authorization.*
import demographic.role.*
class AdminSecurityFilters {

    def filters = {
        admin(controller:'person|role|personNameUser|loginAuth|authorization', action:'*', invert:true) {
            before = {
			
				if(session.traumaContext){
					def login = LoginAuth.get( session.traumaContext.userId )

					// Roles de la persona
					def roles = Role.withCriteria {
						eq('performer', login.person)
					}

					def roleKeys = roles.type
				
					if ( roleKeys.intersect([Role.ADMIN]).size() > 0 ){
					
						println "acceso invalido para usuario admin....!!..."
						redirect(controller:'loginAuth', action:'list')
					}
				}
            }
            after = {
                
            }
            afterView = {
                
            }
        }
    }
    
}
