/**
 * 
 */
package demographic.party

import hce.core.support.identification.*
import demographic.*
import demographic.contact.Contact
import demographic.identity.*

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Party {

    // Tipo del party como "persona", "organización", etc.
    // Nombre del rol, por ejemplo, "medico general", "enfermera",
    // "ciudadano privado". Tomado del atributo de nombre heredado.
    String type = this.getClass().getSimpleName()


    List ids
    
    // relationships: Relaciones de la cual este party es source
    // ids: identificadores del party (ObjectID es II en HL7) -- este campo iria en el campo details del modelo de OpenEHR.
    // contacts: medios de contacto 
    // identities: informacion para identificar al party
    static hasMany = [
                       relationships : PartyRelationship,
                       ids: UIDBasedID, // FIXME: esto tiene que ser HierObjectId
                       contacts: Contact,
                       identities: PartyIdentity
                     ]
    // Como PartyRel tiene 2 Party, tengo que decirle
    // con cual de esos se mapea la relacion 1-N 'relationships'
    // de Party en PartyRel: es el source.
    static mappedBy = [relationships:'source']
    static mapping = {
        ids cascade: "save-update" //delet all orphan
        ids cascade: "all-delete-orphan"
        identities cascade: "save-update"
		identities cascade: "all-delete-orphan"
        contacts cascade: "save-update"
    }

    
}
