import org.codehaus.groovy.grails.commons.ApplicationHolder
class AuthorizationFilters {
    
    def openActions = ['authorization-login',
                       'authorization-logout']
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


        //FILTRO PARA NO CONECTAR EN CASO DE NO HABER CONEXION CON EL WEB SERVICE
      /*  web(controller:'service', action:'*'){


            before= {

                try{
                def proxy = webService.getClient(ApplicationHolder.application.config.wsdl)
                println "Si hay servicio"
                return true
                
                }
                catch(org.apache.cxf.service.factory.ServiceConstructionException e){

                  //  e.printStackTrace()
                println "No hay servicio"
                    return false
                }

            }



        }*/
        
        /*
        noCache(controller:'*', action:'*')
		{
            response.setHeader("Cache-Control",
                               "no-store")
        }
        */
        
    } 
}
