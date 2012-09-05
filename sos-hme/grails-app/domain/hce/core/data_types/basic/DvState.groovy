package hce.core.data_types.basic

import hce.core.data_types.text.DvCodedText;

class DvState extends DataValue{

    boolean terminal // Indicates whether this state is a terminal state, such as “aborted”, “completed” etc from which no further transitions are possible.
    DvCodedText value // The state name. State names are determined by a state/event table defined in archetypes, and coded using openEHR Terminology or local archetype terms, as specified by the archetype

    static mapping = {
        value column: "dvstate_value"
        value cascade: "save-update"
    }

    static constraints = {
        value (nullable: false)
        terminal (nullable: false)
    }
}
