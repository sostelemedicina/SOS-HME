package sos
import authorization.*
import demographic.role.*

class AdminFilters {
    
    def filters = {
/*
        all(controller:'admin|role|personNameUser', action:'*') {
            before = {
                def login = LoginAuth.get( session.traumaContext.userId )
                // Roles de la persona
                def roles = Role.withCriteria {
                    eq('performer', login.person)
                }
                def roleKeys = roles.type
                String user = "user"
                if ( roleKeys.intersect([Role.ADMIN]).size() > 0 ){
                    return true
                }else{
                    flash.message = "filter.default.not.acces"
                    redirect(controller:'domain', action:'list')
                    return false
              }
           }
        }
        
        personShow(controller:'person', action:'*'){
            before = {
                
                return true
            }
            
        }
        
        usuario(controller:'loginAuth', action:'index|list|create|save|delete'){
            
            before = {
                if(session?.traumaContext?.userId){
                    def login = LoginAuth.get( session.traumaContext.userId )
                    // Roles de la persona
                    def roles = Role.withCriteria {
                        eq('performer', login.person)
                    }
                    def roleKeys = roles.type
                    String user = "user"
                    if ( roleKeys.intersect([Role.ADMIN]).size() > 0 ){
                         return true
                    }else{
                        flash.message = "filter.default.not.acces"
                        redirect(controller:'domain', action:'list')
                        return false
                    }
                }
            }
        }
        
        loginAuthShow(controller:'loginAuth', action:'show'){
            before= { 
                Long userId = new Long(params.id)
                def login = LoginAuth.get(session.traumaContext.userId)
                
                if(login.user != "suuu"){
                    if(session.traumaContext.userId != userId){
                        flash.message = "filter.default.not.acces"
                        redirect(controller:'domain', action:'list')
                        return false
                    }
                }
            }
        }
    */
    }

}
