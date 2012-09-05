package hce.core.support.identification

class UIDBasedID extends ObjectID { // Abstracta

    static transients = ['extension','root']
    
    def getExtension()
    {
        return this.value?.split('::')[1]
    }

    def getRoot()
    {
        // TODO: transformarlo a UID
        return this.value?.split('::')[0]
    }

    boolean hasExtension()
    {
        if (this.getExtension()) return true
        return false
    }
    
    // Fabrica a partir de valores simples
    static UIDBasedID create(String root, String extension)
    {
        // FIXME: ambos son obligatorios!
        return new UIDBasedID(
                     value: ((root)?root:'')+'::'+((extension)?extension:'') 
                   )
    }
    
    public String toString()
    {
        return this.getClass().getSimpleName()+'-> '+this.value
    }
}
