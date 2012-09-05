package hce.core.datastructure.history

import java.util.List;

import hce.core.data_types.quantity.date_time.DvDateTime
import hce.core.datastructure.DataStructure

class History extends DataStructure{

    DvDateTime origin
    DvDateTime period
    DvDateTime duration
	
	List events = [] // Para que guarden en orden
    static hasMany = [events:Event]

    static mapping = {
        origin cascade: "save-update"
        period cascade: "save-update"
        duration cascade: "save-update"
        events cascade: "save-update"
    }

    static constraints = {
        origin (nullable: false)
    }
}
