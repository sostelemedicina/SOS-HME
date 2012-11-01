package authorization

class LoginAuth extends PersonAuth {
    
    String user
    String pass
    String pass2
    String idReset
    PreguntaSecreta preguntaSecreta
    String respuesta
    boolean bandera = false
    static transients = ['pass2', 'bandera']

    static constraints = {
        user(size:3..20, unique: true, blank:false)
        idReset(nullable:true)
        preguntaSecreta(nullable:true)
        respuesta(nullable:true,blank: true)
        pass(size:3..120,validator: {val, obj ->
                println "CONSTRAIN"
            if(obj.bandera != true){
                if(!obj.codificarPassword(obj)){
                    return ['custom.blank']
                }
                if(obj.pass2 == obj.pass){
                    return true
                }else{
                    return ['custom.passwordsNotequeals']
                }
            }else{
                return true
                    
            }
        })
       
    }
    private boolean codificarPassword(Authorization obj){
        if(obj.pass != ""){
            
        println "CODIFICANDO " +obj.pass + " "+ obj.pass2
        obj.pass= obj.pass.encodeAsPassword()
        obj.pass2= obj.pass2.encodeAsPassword()
        return true
        }else{
        return false    
        }
        
        
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