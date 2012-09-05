package hce.core.datastructure.itemstructure.representation

import hce.core.common.archetyped.*

class Item extends Locatable{ // Abstracta

    //UIDBasedID uid;
    //String archetypeNodeId; // Id del nodo (ej. at0001) -->  arquetipo.node(path).nodeID
    //DvText name; // Termino asociado al archetypeNodeId --> termino pedido a la ontologia en base a archetypeNodeId
    //Archetyped archetypeDetails; // Tiene ArqchetypeId, TemplateId y VersionID
    //Set<Link> links;

    static mapping = {
        //name cascade: "save-update"
        //archetypeDetails cascade: "save-update"
    }

    static constraints = {
    }
    
    
    

}
