package hce.core.datastructure.itemstructure

import java.util.List;

import hce.core.datastructure.itemstructure.representation.Cluster

class ItemTable extends ItemStructure{
	
	List rows = [] // Para que guarden en orden
    static hasMany = [rows:Cluster]

    static mapping = {
        rows cascade: "save-update"
    }

    static constraints = {
    }
}
