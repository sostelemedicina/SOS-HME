package hce.core.common.archetyped

import hce.core.data_types.text.*;
import hce.core.data_types.uri.*;
//import hce.core.RMObject;

class Link { //extends RMObject {

    private DvText meaning;
    private DvText type;
    private DvEHRURI target;

    static mapping = {
        meaning cascade: "save-update"
        type cascade: "save-update"
        target cascade: "save-update"
    }
    
    static constraints = {
        //meaning (nullable: false)
        //type (nullable: false)
        //target (nullable: false)
    }

    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (!( o instanceof Link )) return false;
        Link l = (Link)o;
        return ((this.meaning == (l.meaning)) && (this.type == (l.type)) && (this.target  == (l.target)))
    }
}
