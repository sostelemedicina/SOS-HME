package demographic.identity

class Etnia {
    
    String nombre
    
    //static belongsTo = PersonData
    static belongsTo = PersonName
    
    static constraints = {
        nombre(size:1..70)
    }
}
