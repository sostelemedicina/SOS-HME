package demographic.identity

class Direccion {
    Lugar padre
    String localidad
    String urbsector
    String avcarreteracalle
    String casaedif
    Integer piso
    Integer tiemporesidencia
    
    static belongsTo = PersonName
    static hasMany = [lugar : Lugar]
    
    static constraints = {
        urbsector(size:1..100)
        avcarreteracalle(size:1..30)
        casaedif(size:1..10)
        piso(size:1..3,nullable:true)
        tiemporesidencia(size:1..2,nullable:true)
    }
}
