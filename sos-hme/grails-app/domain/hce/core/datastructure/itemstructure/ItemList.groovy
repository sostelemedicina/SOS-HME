package hce.core.datastructure.itemstructure

import java.util.List

import hce.core.datastructure.itemstructure.representation.Element

class ItemList extends ItemStructure{
	
	List items = [] // Para que guarden en orden
    static hasMany = [items:Element]

    static mapping = {
        items column: "itemlist_items"
        items cascade: "save-update"
    }

    static constraints = {
    }
}
