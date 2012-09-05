package hce.core.composition.content.entry

import hce.core.datastructure.itemstructure.ItemStructure
import hce.core.data_types.encapsulated.DvParsable
import hce.core.common.archetyped.Locatable
import hce.core.common.archetyped.Pathable

class Activity extends Locatable {

    ItemStructure description
    DvParsable timing
    String action_archetype_id

    static mapping = {
        description cascade: "save-update"
        description column: "activity_description_id"
        timing cascade: "save-update"
    }

    static constraints = {
        description (nullable: false)
        timing (nullable: false)
        action_archetype_id (nullable: false)
    }
    
   
   /*
   // rmParentId definido en Pathable
   // Si no se pone tira except property [rmParent] not found on entity
   static transients = ['padre']
   Pathable getPadre()
   {
      if (!this.rmParentId) return null
      return Pathable.get(this.rmParentId)
   }
   */
   /*
   void setRmParent(Pathable parent)
   {
      if (!parent) throw new Exception("parent no puede ser nulo")
      if (!parent.id) throw new Exception("parent debe tener id (debe guardarse previamente en la base)")
      this.rmParentId = parent.id
   }
   */
}
