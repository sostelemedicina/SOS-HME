/**
 * 
 */
package demographic.contact


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 * Clase que modela un telefono, tanto fijo como un celular.
 */
class Phone extends Address {

    static transients = ['number']
    
    // Por ahora el numero y la linea de Address son lo mismo.
    def setNumber( String number )
    {
        this.asString = number
    }
    def getNumber()
    {
        return this.asString
    }
}
