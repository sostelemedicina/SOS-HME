package hce.core.composition

import hce.core.common.archetyped.Locatable
import hce.core.data_types.text.DvCodedText
import hce.core.data_types.quantity.date_time.DvDateTime
import hce.core.datastructure.itemstructure.ItemStructure
import hce.core.common.generic.Participation

class EventContext extends Locatable {

    DvDateTime startTime
    DvDateTime endTime
    String location
    DvCodedText setting
    //PartyIdentified healthCareFacility;
    
    // TODO: participation, todavia no lo usamos para poner a los medicos! necesitamos el login antes!
    List participations // Para que guarden en orden
    static hasMany = [participations: Participation]
    
    ItemStructure otherContext

    static mapping = {
        startTime cascade: "save-update"
        endTime cascade: "save-update"
        setting cascade: "save-update"
        otherContext cascade: "save-update"
        location column:'event_context_location'
    }

    static constraints = {
        startTime (nullable: false)
        setting (nullable: false)
        location (nullable: true)
    }
}