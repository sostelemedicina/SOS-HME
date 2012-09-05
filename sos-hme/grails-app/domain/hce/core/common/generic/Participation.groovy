package hce.core.common.generic

import hce.core.data_types.text.*
import hce.core.data_types.quantity.DvInterval

class Participation {

    PartyProxy performer
    
    /* This attribute
    should be coded, but cannot be limited to the
    HL7v3:ParticipationFunction vocabulary, since it
    is too limited and hospital-oriented.
    */
    DvText function
    
    DvCodedText mode // coded by "openehr::parcitipation mode"
    DvInterval time

    static mapping = {
        performer cascade: "save-update"
        function cascade: "save-update"
        mode cascade: "save-update"
        time cascade: "save-update"
    }

    static constraints = {
        performer (nullable: false)
        function (nullable: false)
        mode (nullable: false)
        time (nullable: true)
    }
}
