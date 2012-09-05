package hce.core.composition.content.entry

import java.util.List;

import hce.core.data_types.text.DvText
import hce.core.data_types.quantity.date_time.DvDateTime
import hce.core.data_types.encapsulated.DvParsable

class Instruction extends CareEntry {

    DvText narrative
    DvDateTime expiryTime
    DvParsable wfDefinition
	
	List activities // Para que guarden en orden
    static hasMany = [activities:Activity]

    static mapping = {
        narrative cascade: "save-update"
        expiryTime cascade: "save-update"
        wfDefinition cascade: "save-update"
        activities cascade: "save-update"
    }

    static constraints = {
        narrative (nullable: false)
        expiryTime (nullable: true)
        wfDefinition (nullable: true)
    }
}
