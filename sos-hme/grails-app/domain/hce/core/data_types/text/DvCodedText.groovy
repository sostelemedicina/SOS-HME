package hce.core.data_types.text

import hce.core.datastructure.itemstructure.representation.Element

class DvCodedText extends DvText{

    // A text item whose value must be the rubric from a controlled terminology, the key (i.e. the
    // ‘code’) of which is the defining_code attribute. In other words: a DV_CODED_TEXT is a
    // combination of a CODE_PHRASE (effectively a code) and the rubric of that term, from
    // a terminology service, in the language in which the data was authored.

    CodePhrase definingCode

    static mapping = {
        definingCode cascade: "save-update"
    }

    static constraints = {
        definingCode (nullable: false)
    }
}
