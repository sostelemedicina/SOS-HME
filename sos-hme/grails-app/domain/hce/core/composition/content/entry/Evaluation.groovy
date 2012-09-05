package hce.core.composition.content.entry

import hce.core.datastructure.itemstructure.ItemStructure

class Evaluation extends CareEntry{

    ItemStructure data;

    static mapping = {
        data column: "evaluation_data"
        data cascade: "save-update"
    }

    static constraints = {
        data (nullable:false)
    }
}
