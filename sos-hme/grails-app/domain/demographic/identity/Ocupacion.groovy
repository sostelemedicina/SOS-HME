package demographic.identity

class Ocupacion {

    String nombre
    
    static belongsTo = PersonName
    static constraints = {
        nombre(size:1..80)
    }
}
