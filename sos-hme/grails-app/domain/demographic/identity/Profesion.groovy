package demographic.identity

class Profesion {

    String nombre
    
    static belongsTo = PersonName
    static constraints = {
        nombre(size:1..70)
    }
}
