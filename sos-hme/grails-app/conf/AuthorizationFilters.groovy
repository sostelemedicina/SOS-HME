import org.codehaus.groovy.grails.commons.ApplicationHolder
class AuthorizationFilters {
    
    def openActions = ['authorization-login',
                       'authorization-logout',
                       'loginAuth-lostPassword',
                       'loginAuth-sendEmailLink',
                       'loginAuth-resetPassword',
                       'loginAuth-newPassword',
                       'loginAuth-answerSecretQuestion',
                       'loginAuth-sendSecretAnswer',
                       'services-index']
    def webService
    def filters = {
        
        loginCheck(controller:'*', action:'*')
        {
            before = {
                
                if( !session?.traumaContext?.userId &&
                    !openActions.contains(controllerName+"-"+actionName) )
                {
                    redirect(controller:'authorization', action:'login')
                    return false
                }
            }
        }


     
        
    }
    
    
}
