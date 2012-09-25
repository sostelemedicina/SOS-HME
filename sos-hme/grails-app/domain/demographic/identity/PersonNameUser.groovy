package demographic.identity

class PersonNameUser extends PersonName{


    String telfhabitacion
    String telfcelular
    //String email


    static constraints = {
      


        telfhabitacion (nullable:true,matches:"[0-9]+")
        telfcelular (nullable:true,matches:"[0-9]+")
      //  email (nullable:true, email:true)


    }
}
