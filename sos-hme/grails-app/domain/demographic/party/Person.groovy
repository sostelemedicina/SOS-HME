/**
 * 
 */
package demographic.party



/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Person extends Actor {

    Date fechaNacimiento // FIXME: DvDateTime
    String sexo
    String email
    static String SEXO_MASCULINO = "Masculino"
    static String SEXO_FEMENINO = "Femenino"

    static List getSexCodes()
    {
    	return [SEXO_MASCULINO, SEXO_FEMENINO]
    }
  
     String toString(){
        
        identities.asList().first()
        
    }

    /*
    String toString()
    {
        return " " + this.identities + "\n"
    }*/
    
    static constraints = {
        fechaNacimiento(nullable:true)
        sexo(blank: false)
        email(nullable: true, unique: true )
       
    }
     
}
