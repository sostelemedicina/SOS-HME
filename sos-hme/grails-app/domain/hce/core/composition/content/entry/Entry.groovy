package hce.core.composition.content.entry

import hce.core.composition.content.ContentItem
import hce.core.data_types.text.*

class Entry extends ContentItem {

    CodePhrase language;
    CodePhrase encoding;
    //PartyProxy subject;
    //PartyProxy provider;
    //ObjectRef workflowId;
    //static hasMany = [otherParticipations:Participation]

    static mapping = {
        language cascade: "save-update"
        encoding cascade: "save-update"
        // Intento por excepcion que tira al hacer run-app
        // Repeated column in mapping for entity: hce.core.composition.content.entry.Entry 
        // column: parent_id (should be mapped with insert="false" update="false")
        //parent column:"parent_id", insert:"false", update:"false"
    }

    static constraints = {
        language (nullable: false)
        encoding (nullable: false)
        //subject (nullable: false)
    }
}
