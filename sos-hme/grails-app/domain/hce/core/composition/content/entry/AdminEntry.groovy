package hce.core.composition.content.entry

import hce.core.datastructure.itemstructure.ItemStructure

class AdminEntry extends Entry {

    ItemStructure data

    static mapping = {
        data cascade: "save-update"
        data column: "admin_entry_data_id"
    }

    static constraints = {
        data(nullable:false)
    }
}
