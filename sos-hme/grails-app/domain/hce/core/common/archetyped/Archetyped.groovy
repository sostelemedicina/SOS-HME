package hce.core.common.archetyped

import hce.core.*;
import hce.core.support.identification.*;
import hce.core.datastructure.itemstructure.representation.Item

/**
 * Archetypes act as the configuration basis for the particular
 * structures of instances defined by the reference model. To enable
 * archetypes to be used to create valid data, key classes in the
 * reference model act as "root" points for archetyping; accordingly,
 * these classes have the archetype_details attribute set.
 * An instance of the class <code>Archetyped</code> contains the
 * relevant archetype identification information, allowing generating
 * archetypes to be matched up with data instances.
 * <p/>
 * Instancias de esta clase son inmutables.
 *
 * @author Leandro Carrasco
 * @version 1.0
 */

class Archetyped { //extends RMObject{

    String archetypeId;
    String templateId;
    String rmVersion;

    static constraints = {
        archetypeId(nullable: false)
        rmVersion(nullable: false, blank: false)
    }

    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (!( o instanceof Archetyped )) return false;
        return (this.archetypeId == ((Archetyped)o).archetypeId) && (this.templateId  == ((Archetyped)o).templateId) && (this.rmVersion  == ((Archetyped)o).rmVersion)
    }
}
