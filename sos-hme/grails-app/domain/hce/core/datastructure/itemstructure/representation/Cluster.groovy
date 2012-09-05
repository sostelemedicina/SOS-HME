package hce.core.datastructure.itemstructure.representation

import hce.core.common.archetyped.Pathable
import java.util.List

class Cluster extends Item {
	
	 List items // Para que guarden en orden
    static hasMany = [items:Item]

    static mapping = {
        items column: "cluster_items"
        items cascade: "save-update"
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
