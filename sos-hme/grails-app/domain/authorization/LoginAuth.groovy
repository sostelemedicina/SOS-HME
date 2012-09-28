package authorization

class LoginAuth extends PersonAuth {
    
    String user
    String pass
    String idReset
    PreguntaSecreta pregunta
    String respuesta


    static constraints = {
        user(size:3..20, unique: true, nullable:false)
        pass(size:3..120, nullable:false)
        idReset(nullable:true)
        pregunta(nullable:true)
        respuesta(nullable:true,blank: true)
    }
    
    public boolean resetPassword(String newPassword){
        
        
        this.pass = newPassword.encodeAsPassword()
        if(this.validate()){
            this.idReset = ""
            if(this.save()){
                return true
            }
            
        }
        return false
    }
    
    public static LoginAuth existIdReset(String idReset){
        
        if(idReset){
            
            def loginAuth = LoginAuth.withCriteria{
                eq("idReset", idReset)
            }
            if(loginAuth){
                return loginAuth[0]
            }else{
                return null
            }
            
        }else{
            
            return null
        }
        
    }
    public createIdReset(){
        //CREAR UN UUID
        this.idReset = java.util.UUID.randomUUID().toString()
       
        
    }
    
}