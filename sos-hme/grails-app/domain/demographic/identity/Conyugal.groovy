package demographic.identity

class Conyugal {

    String nombre
    String siglas
    
    static belongsTo = PersonName
    static constraints = {
        nombre(size:2..30)
        siglas(size:1..6,nullable:true)
    }
}
