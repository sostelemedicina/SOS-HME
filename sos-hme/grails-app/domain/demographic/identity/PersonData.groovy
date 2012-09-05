package demographic.identity


//class PersonData extends PartyIdentity {
class PersonData {
    
    Lugar lugarnacimiento
    String localidadnacimiento
    Etnia etnia
    String analfabeta
    Niveleducativo niveleducativo
    String anosaprobados
    Direccion direccion
    Conyugal situaconyugal
    Profesion profesion
    Ocupacion ocupacion
    String segurosocial
    Contactoemergencia  contactoemergencia
    String telfhabitacion
    String telfcelular
    String email
    String nombremadre
    String nombrepadre
    String tiemporesidencia
    String otradireccion
    
    /*
    static hasMany = [
                      etnia : Etnia, 
                      niveleduca : Niveleducativo,
                      lugarnace : Lugar,
                      direccion : Direccion,
                      conyugal : Conyugal,
                      ocupacion : Ocupacion,
                      profesion : Profesion,
                      contemergencia : Contactoemergencia]
                      */
                  
    static constraints = {
        etnia(nullable:true)
        localidadnacimiento(size:1..50,nullable:true)
        analfabeta(inList:["Si","No"])
        segurosocial(inList:["Si","No"],nullable:true)
        anosaprobados(size: 1..3,nullable:true)
        situacionconyugal(nullable:true)
        email(nullable:true,email:true)
        nombremadre(size:1..80,nullable:true)
        nombrepadre(size:1..80,nullable:true)
        telfhabitacion(size:10..11,nullable:true)
        telfcelular(size:10..11,nullable:true)
        tiemporesidencia(size:1..3,nullable:true)
        otradireccion(size:1..1000, nullable:true)
    }
}

