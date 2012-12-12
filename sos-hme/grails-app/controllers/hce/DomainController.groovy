package hce;

import hce.core.common.directory.Folder
import hce.HceService
import authorization.LoginAuth
import demographic.role.*
import util.FormatLog

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 */
class DomainController {

    def hceService
    def authorizationService
 
    /*
     *@author Angel Rodriguez Leon
     *
     *Funcion que genera entradas en log correspondiente al nivel que se le pase por parametro.
	 *error o info
     * */ 
	private void logged(String message, String level, userId){

		def bla = new FormatLog()
		
		if(level.equals("info"))
			log.info(bla.createFormat(message, "long",userId))
		if(level == "error")
			log.error(bla.createFormat(message, "long",userId))
	}

   def index = {
      redirect(action: 'list')
   }
   
   /**
    * Lista dominios existentes.
    * TODO: filtrar dominios por permisos del usuario.
    */
   def list = {
      
      if (session.traumaContext)
      {
          // Si hay un dominio seleccionado, se deselecciona
          session.traumaContext.domainPath = null
      }
      
      // Lista de codigos de dominios de la config
      //def domains = grailsApplication.config.domains
      
      // Lista de folders creados a partir de los codigos de dominios de la configuracion
      def folders = Folder.findAllByPathLike("/domain%") // Las paths de los folders de dominios empiezan con /domain
      
      //println domains
      //println Folder.list()
      
      //return [domains: domains]
      return [folders: folders]
   }
   
   def selectDomain = {
      
      if (!hceService.domainHasTemplates(params.path))
      {		
			//informacion de transaccion para el log.info
			logged("Dominio incorrecto: "+ params.path+" ", "info", session.traumaContext.userId)
			flash.message = 'domain.selectDomain.flash'
			
			redirect(action: "list")
			return
      }
      
      if (session.traumaContext)
      {
			session.traumaContext.domainPath = params.path
      }
      
	  //informacion de transaccion para el log.info
	  logged("Dominio seleccionado correctamente: "+ session.traumaContext.domainPath+" ","info",session.traumaContext.userId)
	  
        if(authorizationService.isAdminUser(session.traumaContext.userId)){
                
            redirect(controller: 'reportes')
            
            }else{
            
            redirect(controller: 'demographic', action: 'admisionPaciente')
        }
   }
}
