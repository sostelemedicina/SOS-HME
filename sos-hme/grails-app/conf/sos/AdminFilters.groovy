package sos
import authorization.*
import demographic.role.*

class AdminFilters {

    def filters = {
/*
        all(controller:'person|role|personNameUser|loginAuth', action:'*') {
            before = {
                def login = LoginAuth.get( session.traumaContext.userId )

                // Roles de la persona
                def roles = Role.withCriteria {
                    eq('performer', login.person)
                }

                def roleKeys = roles.type
                String user = "user"
                if ( roleKeys.intersect([Role.ADMIN]).size() > 0 ){

                    
                    //println "soy admin\n"

                    

                }else{
                    redirect(controller:'domain', action:'list')
                    //println "no soy admin\n"

                    
                }

                
            }
            after = {
                
            }
            afterView = {
                
            }
        }
*/    }
    
}

