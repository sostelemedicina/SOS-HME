package hce.core.support.identification

import hce.core.*;

class ObjectID { //extends RMObject{

    /**
     * Clase ancestra para identificadores de objetos de informacion. Ids may be
     * completely meaningless, in which case their only job is to refer
     * to something, or may carry some information to do with the
     * identified object.
     *
     * @author Leandro Carrasco
     * @version 1.0
     */

    // Lea lo tiene como oidvalue
    String value // El valor del ide en la forma  “::” .

    static constraints = {
        value (nullable: false, blank: false)
    }
    static mapping = {
        value column: "objectid_value"     
    }
}
