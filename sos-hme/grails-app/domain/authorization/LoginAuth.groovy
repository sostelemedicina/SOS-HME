package authorization

class LoginAuth extends PersonAuth {
    
    String user
    String pass
    String pass2
    String idReset
    PreguntaSecreta preguntaSecreta
    String respuesta
    static transients = ['pass2']


    static constraints = {
        user(size:3..20, unique: true, nullable:false)
        idReset(nullable:true)
        preguntaSecreta(nullable:true)
        respuesta(nullable:true,blank: true)
        pass(size:3..120, nullable:false, blank:false,validator: {val, obj ->
            if(!obj.codificarPassword(obj)){
                return false
            }
            if(obj.pass2 == val.encodeAsPassword()){
                return true
            }else{
                return false
            }
        })
       
    }
    private boolean codificarPassword(Authorization obj){
        if(obj.pass != ""){
        obj.pass= obj.pass.encodeAsPassword()
        obj.pass2= obj.pass2.encodeAsPassword()
        return true
        }else{
        return false    
        }
        
        
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