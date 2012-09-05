package hce.core.composition.content.entry

import hce.core.datastructure.itemstructure.ItemStructure

class CareEntry extends Entry{

    ItemStructure protocol

    static mapping = {
        protocol cascade: "save-update"
    }

    static constraints = {
        protocol(nullable:true)
    }
}
