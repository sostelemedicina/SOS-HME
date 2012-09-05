package hce.core.common.archetyped

//import hce.core.*

/**
 * Padre abstracto de todas las clases cuyas instancias son accesibles por caminos (path), y
 * que saben cómo localizar objetos secundarios por caminos.
 *
 * @author Leandro Carrasco
 */

class Pathable { //extends RMObject { // Abstracta

   /**
   * Separator used to delimit segments in the path
   *
   */
   static final String PATH_SEPARATOR = "/";
   static final String ROOT = PATH_SEPARATOR;

   
    //Pathable parent
    // Si pongo Pathable, genera mal el esquema de la BD, confunde esta relacion
    // con la columna 'parent' para manejar relaciones one to many.
    Long rmParentId // Cumple el rol de Pathable parent
    String path

    // El atributo parent y su persistencia se maneja a mano, no se deja que GRAILS guarde o actualiza el parent.
   // rmParentId definido en Pathable
   // Si no se pone tira except property [rmParent] not found on entity
   static transients = ['padre']
   // Si pongo Object en lugar de Pathable, funciona, si no tira mil errores de que no
   // encuentra la propiedad 'padre' en las subclases
   Object getPadre()
   {
      if (!this.rmParentId) return null
      return Pathable.get(this.rmParentId)
   }
   void setPadre(Object parent) // Object debe ser Pathable!
   {
      if (!parent) throw new Exception("parent no puede ser nulo")
      if (!parent.id) throw new Exception("parent debe tener id (debe guardarse previamente en la base)")
      this.rmParentId = parent.id
   }
   
    
    static mapping = {
       //parent cascade: "save-update" // yo no deberia salvar a mi parent.
       //parent column:'parent_id'
       path column:'pathable_path'
    }

    static constraints = {
       rmParentId(nullable: true)
    }

    

    /**
     * El item como una ruta (relativa a este item) solo valido para path unicos,
     * es decir, paths que resuelven items simples.
     *
     * @param path no nulo y unico
     * @return el item
     * @throws IllegalArgumentException si el path es invalido
     */
    //Object itemAtPath(String path){} // Abstracta

     /**
     * Lista de items que corresponden a un path no unico.
     *
     * @param path no nulo y no unico
     * @return Los items
     */
    //List<Object> itemsAtPath(String path){} // Abstracta

    /**
     * El path de un item relativo a la raiz de la estructura arquetipada.
     *
     * @param item no nulo
     */
    //String pathOfItem(Pathable item){} // Abstracta

    /**
     * True si el path existe en los datos con respecto al item actual
     *
     * @param path no null o vacio
     * @return true si existe
     */
    //boolean pathExists(String path){} // Abstracta

    /**
     * True si el path corresponde a un item simple en los datos.
     * @param path no nulo y existe
     * @return true si es unico
     */
    //boolean pathUnique(String path){} // Abstracta

    /*
    boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (!( o instanceof Pathable )) return false;
        return (this.parent == ((Pathable)o).parent)
    }
    */
}
