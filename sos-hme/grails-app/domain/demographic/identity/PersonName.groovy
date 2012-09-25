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
  
        primerNombre (blank:false, matches: "[a-zA-ZáéíóúÁÉÍÓÚ]+")
        segundoNombre (nullable:true, matches: "[a-zA-ZáéíóúÁÉÍÓÚ]+")
        primerApellido (blank:false, matches: "[a-zA-ZáéíóúÁÉÍÓÚ]+")
        segundoApellido (nullable:true, matches: "[a-zA-ZáéíóúÁÉÍÓÚ]+")
    
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
