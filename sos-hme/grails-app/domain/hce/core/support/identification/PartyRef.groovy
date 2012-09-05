package hce.core.support.identification

class PartyRef extends ObjectRef {

    public String toString()
    {
        return this.getClass().getSimpleName()+'-> '+this.namespace+' '+this.type+' '+this.objectId.toString()
    }
}
