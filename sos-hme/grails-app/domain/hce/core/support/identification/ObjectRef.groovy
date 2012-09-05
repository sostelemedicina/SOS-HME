package hce.core.support.identification

class ObjectRef {

    String namespace // local, unknown, demographic, ...
    
    /* 
     * Name of the class (concrete or abstract) of object to which this
     * identifier type refers, e.g. “PARTY”, “PERSON”, “GUIDELINE”, “ANY”.
     */
    String type
    
    /*
     * Globally unique id of an object, regardless of where it is stored.
     */
    ObjectID objectId // no puedo ponerle id! por grails.
    
    static constraints = {
        namespace(nullable:false, blank:false)
        type(nullable:false, blank:false)
    }
    
    static mapping = {
        objectId cascade: "save-update"
        //table 'object_ref'
    }
    
    public String toString()
    {
        return this.getClass().getSimpleName()+'-> '+this.namespace+' '+this.type+' '+this.objectId.toString()
    }
}
