/**
 * 
 */
package tablasMaestras

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class EmergenciaMovil {

    String nombre
    String telefono
    
    //static transients = ['emergencias']
    
    static def getEmergencias()
    {
        def ret = []
        
        ret << new EmergenciaMovil(nombre:"UCM", telefono:"4873333")
        ret << new EmergenciaMovil(nombre:"SEMM", telefono:"7112121")
        ret << new EmergenciaMovil(nombre:"SUAT", telefono:"7110711")
        ret << new EmergenciaMovil(nombre:"MSP", telefono:"")
        ret << new EmergenciaMovil(nombre:"UCAR", telefono:"")
        
        return ret
    }
}
