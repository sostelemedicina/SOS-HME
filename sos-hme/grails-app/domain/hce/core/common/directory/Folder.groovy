package hce.core.common.directory

import hce.core.common.archetyped.Locatable
import hce.core.common.archetyped.Pathable
import hce.core.support.identification.ObjectRef

/**
 * Folder puede tener subfolders e items.
 * 
 * Items son los elementos de contenido del Folder, como Compositions
 * (o cualquier otro tipo de elemento, p.e. los folders podrian servir
 * para organizar registros de cierto tipo, p.e. todas las observaciones
 * de signos vitales, o todos los registros de un cierto dominio).
 * 
 * Items es ObjectRef, por lo que las Compositions se tienen asociadas
 * por referencia, no directamente. (deberia haber un buscador de elementos
 * por ObjectRef, tanto para Compositions como otros tipos de elementos).
 * 
 * Debido a que los objetos que contiene el Folder son referenciados, pueden
 * ocurrir multiples referencias al mismo objeto, esto sirve para poder
 * clasificar de distintas formas el mismo objeto.
 * 
 * Los Folders son sados para agrupar cosas, por ejemplo se podrian agrupar
 * registros demograficos para una misma familia, de forma de poder llegar a
 * armar un historial familiar facilmente.
 * 
 * @author Pablo Pazos Gutierrez <pablo.swp@gmail.com>
 *
 */

class Folder extends Locatable {

   // El nombre del directorio es es atributo Locatable.name: DvText (podria ser tambien DvCodedText)
   // ObjectRef tiene:
   // - id: identificador unico global
   // - namespace: espacio de nombres al que pertenece el identificador en el sistema local (p.e. terminology, demographic, local, etc)
   // - type: nombre de la clase al que refiere el id, p.e. PERSON, COMPOSITION, OBSERVATION, etc.
   
   List folders // Para que se guarden en orden
   static hasMany = [folders: Folder, items: ObjectRef]
   
   static mapping = {
      items column: "folder_items"
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
