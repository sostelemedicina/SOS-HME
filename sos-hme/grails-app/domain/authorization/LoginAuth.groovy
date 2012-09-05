package authorization

class LoginAuth extends PersonAuth {
    
    String user
    String pass


    static constraints = {
        user(size:3..20, unique: true, nullable:false)
        pass(size:3..120, nullable:false)
    }
}