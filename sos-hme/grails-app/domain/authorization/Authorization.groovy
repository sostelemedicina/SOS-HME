/**
 * 
 */
package authorization

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 * Clase no OpenEHR para modelar la autorizacion de distintas entidades.
 */
class Authorization {

    // Para que se usa esta autorizacion, que es> login, 
    // pin, validacion de direccion IP, etc.
    String purpose
    
    static constraints = {
        purpose(nullable:true)
    }
}
