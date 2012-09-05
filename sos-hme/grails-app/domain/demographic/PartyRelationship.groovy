/**
 * 
 */
package demographic

import demographic.party.Party

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class PartyRelationship {

    // Cualquier detalle de la relacion
    String details // En el modelo de OpenEHR es un ITEM_STRUCTURE.
    
    // Tipo de la relacion: como "empleo", "autoridad", "prestación de salud"
    String type
    
    // Intervalo de duracion de la relacion: en el modelo de OpenEHR es un Interval<DvDate>
    Date timeValidityFrom
    Date timeValidityTo
    
    // Entidades relacionadas
    Party source
    Party target
}
