package hce.core.datastructure.itemstructure

import java.util.List;

import hce.core.datastructure.itemstructure.representation.Item

class ItemTree extends ItemStructure{
	
	 List items = [] // Para que guarden en orden
    static hasMany = [items:Item]

    static mapping = {
        items column: "itemtree_items"
        items cascade: "save-update"
    }

    static constraints = {
    }
}
