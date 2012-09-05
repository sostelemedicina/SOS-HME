package hce.core.composition.content.entry

import hce.core.datastructure.itemstructure.ItemStructure
import hce.core.data_types.quantity.date_time.DvDateTime

class Action extends CareEntry {

    DvDateTime time
    ItemStructure description
    //ISMTransition ismTransition
    //InstructionDetails instructionDetails

    static mapping = {
        time cascade: "save-update"
        description cascade: "save-update"
        description column: "action_description_id"
    }

    static constraints = {
        //time (nullable: false)
        description (nullable: false)
        //ismTransition (nullable: false)
    }
}
