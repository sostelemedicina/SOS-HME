package hce.core.common.generic

import hce.core.data_types.basic.DvIdentifier

class PartyIdentified extends PartyProxy {
    
    String name
    List identifiers
    static hasMany = [identifiers: DvIdentifier]
    
    static constraints = {
      name(nullable:true)
      identifiers(nullable:true)
    }
}
