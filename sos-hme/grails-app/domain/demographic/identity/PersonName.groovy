/**
 * 
 */
package demographic.identity

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class PersonName extends PartyIdentity {

    String primerNombre
    String segundoNombre
    String primerApellido
    String segundoApellido
    
  static constraints = {
  
        primerNombre (blank:false, matches: "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")
        segundoNombre (nullable:true, matches: "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")
        primerApellido (blank:false, matches: "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")
        segundoApellido (nullable:true, matches: "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")
    
 }
  
    String toString()
    {
        def nombreCompleto

        if(primerNombre != null)
            nombreCompleto = primerNombre+' '

        if(segundoNombre != null)
            nombreCompleto += segundoNombre+' '

        if(primerApellido != null)
            nombreCompleto += primerApellido+' '

        if(segundoApellido != null)
            nombreCompleto += segundoApellido

        return nombreCompleto
        /*return "PersonName: " +
               primerNombre +' '+ 
               segundoNombre +' '+ 
               primerApellido +' '+ 
               segundoApellido*/
    }




}
