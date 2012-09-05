/**
 * 
 */
package demographic.role

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 * Capacidad de un rol, tales como "modificador de HME",
 * "proveedor de cuidados de salud". Capacidad debería ser
 * respaldado por las credenciales.
 */
class Capability {

    // Intervalo de validez de la capacidad: en el modelo de OpenEHR es un Interval<DvDate>
    Date timeValidityFrom
    Date timeValidityTo
    
    // Las credenciales del rol en la función de esta capacidad.
    // Esto podría incluir las cualificaciones profesionales y las
    // identificaciones oficiales, tales como números de su proveedor, etc.
    String credentials
}
