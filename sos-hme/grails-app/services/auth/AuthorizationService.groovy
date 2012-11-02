package auth;
import demographic.role.Role
import authorization.LoginAuth

class AuthorizationService {
    
    def loginExists( String user, String pass )
    {
        // TODO: pass 2 md5 params.pass.encodeAsPassword()
        def login = LoginAuth.findByUserAndPass(user, pass.encodeAsPassword())
        
        return login != null
    }
    
    def getLogin( String user, String pass )
    {
        // TODO: pass 2 md5
        
        println "SE BUSCA "+pass.encodeAsPassword()
        def login = LoginAuth.findByUserAndPass(user, pass.encodeAsPassword())
        
        return login
    }
	
	def getRolesByPerformer(personId){
	
		def fechaActual = new Date()
		
		// se busca el rol asociado con la clase persona asignada al login
		def roles = Role.withCriteria {
			eq('performer', personId)
			eq('status', true)
			le('timeValidityFrom', fechaActual)
			ge('timeValidityTo', fechaActual)
		}
		
		return roles
	
	}
}
