package demographic.identity

class Contactoemergencia {
    
    String nombre
    String apellido
    String telf1
    String telf2
    String parentesco
    Direccion direccion
    
    static belongsTo = PersonName
    static hasMany = [direccion : Direccion]
    static constraints = {
        nombre(size:1..60)
        apellido(size:1..60)
        telf1(size:11..11)
        telf(size:11, nullable:true)
        parentesco(inlist:["PADRE","MADRE","HERMANO","HERMANA","CONYUGE","OTRO"],nullable:true)
    }
}