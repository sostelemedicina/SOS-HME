package demographic.identity

class PersonNamePatient extends PersonName{

    byte[] foto
    String tipofoto
    Etnia etnia
    Lugar nacionalidad
    Lugar lugar //hace referencia al id del municipio
    String ciudadnacimiento
    Integer situacionconyugal
    Integer analfabeta
    Integer niveleducativo
    String anosaprobados
    Ocupacion ocupacion

    Lugar direccion
    String ciudad
    String urbasector
    String avenidacalle
    String casaedif
    String pisoplanta
    String ptoreferenica
    String telfhabitacion
    String telfcelular
    String tiemporesidencia
    String email
    String nombrepadre
    String nombremadre
    String otradireccion
    String contactoemergencia

      static constraints = {
        /*
        foto(nullable:true, maxSize: 204800 
        tipofoto(nullable:true)  
        primerNombre (blank:false, matches: "[a-zA-Z]+")
        segundoNombre (blank:false, matches: "[a-zA-Z]+")
        primerApellido (blank:false, matches: "[a-zA-Z]+")
        segundoApellido (blank:false, matches: "[a-zA-Z]+")

        etnia (nullable:true)
        nacionalidad(blank:false)
        lugar(blank:false)
        ciudadnacimiento (blank:false)
        situacionconyugal (blank:false , range:1..6)
        analfabeta (blank:false, range:0..1)
        niveleducativo (blank:false, range: 1..7)
        anosaprobados (nullable:true)
        ocupacion (blank:false)
        direccion (blank:false)
        ciudad (blank:false, size:1..60)
        urbasector (blank:false, size:1..200)
        avenidacalle (blank:false, size:1..200)
        casaedif (blank:false, size:1..50)
        pisoplanta (nullable:true)
        ptoreferenica (nullable:true)

        telfhabitacion (nullable:true,matches:"[0-9]+")
        telfcelular (nullable:true,matches:"[0-9]+")


        tiemporesidencia (nullable:true)
        email (nullable:true, email:true)

        nombrepadre (nullable:true, matches: "[a-zA-Z]+")
        nombremadre (nullable:true, matches: "[a-zA-Z]+")
        otradireccion (nullable:true)
        contactoemergencia (blank:false)
        */
    }
    
    static mapping = {
        foto type: "binary" // or "blob"?
        foto column: "foto", sqlType: "blob"
    }


}
