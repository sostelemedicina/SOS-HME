package hce.core.datastructure.history

import hce.core.data_types.quantity.date_time.DvDateTime
import hce.core.datastructure.itemstructure.ItemStructure
import hce.core.datastructure.DataStructure

class Event extends DataStructure{

    DvDateTime time
    ItemStructure data
    //ItemStructure state


    static mapping = {
        time column: "event_time"
        data column: "event_data"
        time cascade: "save-update"
        data cascade: "save-update"
        //state column: "event_state"
        //state cascade: "save-update"
    }

    static constraints = {
        time (nullable: false)
        data (nullable: false)
    }
}
