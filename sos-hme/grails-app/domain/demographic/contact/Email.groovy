/**
 * 
 */
package demographic.contact



/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Email extends Address {

    static transients = ['email']
    
    // Por ahora el numero y la linea de Address son lo mismo.
    def setEmail( String email )
    {
        this.asString = email
    }
    def getEmail()
    {
        return this.asString
    }
}
