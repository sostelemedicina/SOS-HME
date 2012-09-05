package hce.core.datastructure.itemstructure

import hce.core.datastructure.itemstructure.representation.Element

class ItemSingle extends ItemStructure{

    Element item;

    static mapping = {
        item column: "itemsingle_items"
        item cascade: "save-update"
    }

    static constraints = {
        item (nullable: false)
    }
}
