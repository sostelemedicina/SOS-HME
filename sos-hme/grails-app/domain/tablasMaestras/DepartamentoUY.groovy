/**
 * 
 */
package tablasMaestras


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class DepartamentoUY {

    // Nombre en texto libre, es es nombre de uso comun
    String nombre
    
    // Lista de codigos con los que mapea el departamento
    String iso3166_2UY
    String prefijoPlacasVehiculares
    
    //static transients = ['departamentos']
    
    static List getDepartamentos()
    {
        def ret = []
        
        ret << new DepartamentoUY(nombre:"Artigas",        iso3166_2UY:"UY-AR", prefijoPlacasVehiculares:"G")
        ret << new DepartamentoUY(nombre:"Canelones",      iso3166_2UY:"UY-CA", prefijoPlacasVehiculares:"A")
        ret << new DepartamentoUY(nombre:"Cerro Largo",    iso3166_2UY:"UY-CL", prefijoPlacasVehiculares:"E")
        ret << new DepartamentoUY(nombre:"Colonia",        iso3166_2UY:"UY-CO", prefijoPlacasVehiculares:"L")
        ret << new DepartamentoUY(nombre:"Durazno",        iso3166_2UY:"UY-DU", prefijoPlacasVehiculares:"Q")
        ret << new DepartamentoUY(nombre:"Flores",         iso3166_2UY:"UY-FS", prefijoPlacasVehiculares:"N")
        ret << new DepartamentoUY(nombre:"Florida",        iso3166_2UY:"UY-FD", prefijoPlacasVehiculares:"O")
        ret << new DepartamentoUY(nombre:"Lavalleja",      iso3166_2UY:"UY-LA", prefijoPlacasVehiculares:"P")
        ret << new DepartamentoUY(nombre:"Maldonado",      iso3166_2UY:"UY-MA", prefijoPlacasVehiculares:"B")
        ret << new DepartamentoUY(nombre:"Montevideo",     iso3166_2UY:"UY-MO", prefijoPlacasVehiculares:"S")
        ret << new DepartamentoUY(nombre:"Paysandú",       iso3166_2UY:"UY-PA", prefijoPlacasVehiculares:"I")
        ret << new DepartamentoUY(nombre:"Río Negro",      iso3166_2UY:"UY-RN", prefijoPlacasVehiculares:"J")
        ret << new DepartamentoUY(nombre:"Rivera",         iso3166_2UY:"UY-RV", prefijoPlacasVehiculares:"F")
        ret << new DepartamentoUY(nombre:"Rocha",          iso3166_2UY:"UY-RO", prefijoPlacasVehiculares:"C")
        ret << new DepartamentoUY(nombre:"Salto",          iso3166_2UY:"UY-SA", prefijoPlacasVehiculares:"H")
        ret << new DepartamentoUY(nombre:"San José",       iso3166_2UY:"UY-SJ", prefijoPlacasVehiculares:"M")
        ret << new DepartamentoUY(nombre:"Soriano ",       iso3166_2UY:"UY-SO", prefijoPlacasVehiculares:"K")
        ret << new DepartamentoUY(nombre:"Tacuarembó",     iso3166_2UY:"UY-TA", prefijoPlacasVehiculares:"R")
        ret << new DepartamentoUY(nombre:"Treinta y Tres", iso3166_2UY:"UY-TT", prefijoPlacasVehiculares:"D")
        
        return ret

    }
}
