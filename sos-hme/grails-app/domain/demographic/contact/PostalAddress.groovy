/**
 * 
 */
package demographic.contact

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 * Clase que modela un domicilio.
 */
class PostalAddress extends Address {

    String calle1
    String calle2
    String numero
    
    String piso
    String apartamento
    
    String barrio
    String localidad
    String departamento
    
    // Metodo para actualizar la linea cuando se tienen todos los datos de la direccion
    def writeLine()
    {
        this.asString = calle1 +" "+ numero + ((apartamento)?"/"+apartamento:"") +" "+ calle2 +" "+
                        barrio +" "+ localidad +" "+ departamento
    }
}
