package hce.core.data_types.text

import hce.core.data_types.basic.DataValue;
import hce.core.data_types.uri.DvURI;
//import hce.core.support.definition.BasicDefinitions;
import hce.core.datastructure.itemstructure.representation.Item

class DvText extends DataValue{

    String formatting // A format string of the form “name:value; name:value...”, e.g. "font-weight : bold; font-family : Arial; font-size : 12pt;". Values taken from W3C CSS2 properties lists “background” and “font”.
    DvURI hyperlink // Optional link sitting behind a section of plain text or coded term item.
    String value // Displayable rendition of the item, regardless of its underlying structure. For DV_CODED_TEXT, this is the rubric of the complete term as provided by the terminology service. No carriage returns, line feeds, or other non-printing characters permitted.

    static hasMany = [mappings:TermMapping] // terms from other terminologies most closely matching this term, typically used where the originator (e.g. pathology lab) of information uses a local terminology but also supplies one or more equivalents from wellknown terminologies (e.g. LOINC). 
    CodePhrase language
    CodePhrase encoding

    static mapping = {
        value column: "dvtext_value"
        hyperlink cascade: "save-update"
        mappings cascade: "save-update"
        language cascade: "save-update"
        encoding cascade: "save-update"
    }

    static constraints = {
        value (nullable: false,
               blank: false,
               maxSize :1024*1024*10
               /*,
               validator: { // 	inv: value <> void and not value.is_empty and not (value.has(CR) or value.has(LF))
                 (!it.contains(BasicDefinitions.CR) && !it.contains(BasicDefinitions.LF))
               }
               */
        )
        formatting (nullable: true) // en gral no vamos a usar formatting
        hyperlink (nullable:true)
    }
 
}
