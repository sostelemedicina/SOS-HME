package hce.core.common.generic

class PartySelf extends PartyProxy {

    public String toString()
    {
        return this.getClass().getSimpleName()+'-> '+this.externalRef.toString()
    }
}
