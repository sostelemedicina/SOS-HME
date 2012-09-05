/**
 * 
 */
package demographic.contact


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Contact {

    // Intervalo de validez del medio de contacto: en el modelo de OpenEHR es un Interval<DvDate>
    Date timeValidityFrom
    Date timeValidityTo
    
    //Finalidad para la que este contacto se utiliza, por ejemplo,
    // "mail", "teléfono durante el día", etc. Tomado de valor del
    // atributo de nombre heredado.
    String purpose
    
    static hasMany = [addresses : Address]
    
}
