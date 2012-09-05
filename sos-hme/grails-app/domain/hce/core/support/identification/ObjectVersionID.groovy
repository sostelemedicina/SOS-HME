package hce.core.support.identification;

class ObjectVersionID extends UIDBasedID {
    
    // Value codificado:
    // object_id ‘::’ creating_system_id ‘::’ version_tree_id
    
    static transients = ['objectId','versionTreeId','creatingSystemId','branch']

    /**
     * Si se implementa el 'change control', seria el uid del VersionedObject
     * que contiene todas las versiones del mismo objeto. Ahora no se usa (*).
     * 
     * Unique identifier for logical object of which this identifier identifies
     * one version; normally the object_id will be the unique identifier of the
     * version container containing the version referred to by this
     * OBJECT_VERSION_ID instance.
     *  
     * @return UID (por ahora string)
     */
    def getObjectId()
    {
        // TODO
        // (*) ahora no se usa, se va a usar cuando se implemente el 'change control'
        return null
    }

    /**
     * Tree identifier of this version with respect to other versions in the
     * same version tree, as either 1 or 3 part dot-separated numbers, e.g. “1”, “2.1.4”.
     * 
     * @return VersionTreeID
     */
    def getVersionTreeId()
    {
        // TODO: implementar para el 'change control'
        return null
    }
    
    /**
     * Identifier of the system that created the Version corresponding to this Object
     * version id.
     * 
     * @return UID (String por ahora)
     */
    def getCreatingSystemId()
    {
        // TODO: implementar para el 'change control'
        return null
    }
    
    /**
     * True if this version identifier represents a branch.
     * 
     * @return boolean
     */
    def isBranch()
    {
        // TODO: implementar para el 'change control'
        return false
        
    }
}
