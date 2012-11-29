/**
 * 
 */
package demographic.party


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Organization extends Actor {

    /*En un futuro se pudiese extender de esta clase segun el tipo de organizacion
     *Ej. Ambulatorio, hospital, Organizacion externa o sistema, etc...
     *
     *En Party el atributo 'type' define el tipo de organizacion
     **/
    String subType
    
     static constraints = {
        subType(nullable:true, blank: true)
     }

}
