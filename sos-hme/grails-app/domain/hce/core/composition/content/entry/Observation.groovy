package hce.core.composition.content.entry

import hce.core.datastructure.history.History

class Observation extends CareEntry{

    History data
    //History state

    static mapping = {
        data column: "observation_data"
        data cascade: "save-update"
        //state column: "observation_state"
        //state cascade: "save-update"
    }

    static constraints = {
        data (nullable: false)
    }
}
