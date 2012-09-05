package hce.core.composition

import hce.core.common.archetyped.Locatable
import hce.core.data_types.text.*
import hce.core.composition.content.*
import hce.core.common.generic.*
import java.util.List

class Composition extends Locatable {

    /**
     * The composer is the person who was primarily responsible for the content
     * of the Composition. This is the identifier that should appear on the screen.
     * It could be a junior doctor who did all the work, even if not legally
     * responsible, or it could be a nurse,
     */
    PartyProxy composer
    EventContext context
    DvCodedText category
    CodePhrase territory
    CodePhrase language
	
    List content // Para que guarden en orden
    static hasMany = [content:ContentItem]

    static mapping = {
        composer cascade: "save-update"
        context cascade: "save-update"
        category cascade: "save-update"
        territory cascade: "save-update"
        language cascade: "save-update"
        content cascade: "save-update"
        content column:'composition_content'
        //table 'composition'
    }

    static constraints = {
        category (nullable: false)
        territory (nullable: false)
        language (nullable: false)
        //composer (nullable: false)
        composer (nullable: true) // permito que sea null para poder guardar la composition sin composer, pero al final deberia tener uno!
        context (nullable:true)
    }
}
