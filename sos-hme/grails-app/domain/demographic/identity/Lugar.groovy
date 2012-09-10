package demographic.identity

class Lugar {
    String nombre
    String tipolugar
    Lugar padre
    //static hasMany = [padre : Lugar]
    //static belongsTo = [Direccion , PersonName]
    static belongsTo = PersonName
    
    static constraints = {
        nombre(size:1..80)
        tipoLugar(size:1..30)
    }
    static mapping = {
	       padre cascade:'all-delete-orphan'
	}
    
}
