package hce.core.common.generic

import hce.core.support.identification.PartyRef

class PartyProxy { // Abstracta

    PartyRef externalRef

    static mapping = {
        externalRef cascade: "save-update"
    }

    static constraints = {
    }
    
    // Solucion a http://old.nabble.com/Getting-Item_%24%24_javassist_165-from-ins.getClass%28%29.getSimpleName%28%29-td27317238.html
    // Con item.getClass().getSimpleName() obtengo Item_$$_javassist_165 en lugar de Cluster o Element
    static transients = ["className"]
    String getClassName()
    {
        return this.getClass().getSimpleName()
    }
}
