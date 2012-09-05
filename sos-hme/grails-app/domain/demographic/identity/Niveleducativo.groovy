package demographic.identity

class Niveleducativo {
    
    String nombre
    String siglas
    
    static belongsTo = PersonName
    static constraints = {
        nombre(size:2..30)
        siglas(size:1..5,nullable:true)
    }
}
