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
    String departamento
    
    String localidad
    String entidad
    String municipio
    String parroquia
    
    // Metodo para actualizar la linea cuando se tienen todos los datos de la direccion
    def writeLine()
    {
        this.asString = calle1 +" "+ numero + ((apartamento)?"/"+apartamento:"") +" "+ calle2 +" "+
                        barrio +" "+ localidad +" "+ departamento
    }
    
    static constraints = {
        calle1(nullable:true,blank: true)
        calle2(nullable:true,blank: true)
        numero(nullable:true,blank: true)
        piso(nullable:true,blank: true)
        apartamento(nullable:true,blank: true)
        barrio(nullable:true,blank: true)
        departamento(nullable:true,blank: true)
        localidad(nullable:true,blank: true)
        entidad(nullable:true,blank: true)
        municipio(nullable:true,blank: true)
        parroquia(nullable:true,blank: true)
        
        
    }
}
