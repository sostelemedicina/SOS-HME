package hce.core.data_types.text

class TermMapping {

    // Represents a coded term mapped to a DV_TEXT, and the relative match of the target term with
    // respect to the mapped item. Plain or coded text items may appear in the EHR for which one or
    // mappings in alternative terminologies are required. Mappings are only used to enable computer
    // processing, so they can only be instances of DV_CODED_TEXT.

    static char NARROWER = '<'
    static char EQUIVALENT = '='
    static char BROADER = '>'
    static char UNKNOWN = '?'

    char match = '?' //Match  // The relative match of the target term with respect to the mapped text item. Result meanings: • ‘>’: the mapping is to a broader term e.g. orginal text = “arbovirus infection”, target = “viral infection” • ‘=’: the mapping is to a (supposedly) equivalent to the original item • ‘<’: the mapping is to a narrower term. e.g. original text = “diabetes”, mapping = “diabetes mellitus”. • ‘?’: the kind of mapping is unknown. The first three values are taken from the ISO standards 2788 (“Guide to Establishment and development of monolingual thesauri”) and 5964 (“Guide to Establishment and development of multilingual thesauri”).
    DvCodedText purpose // Purpose of the mapping e.g. “automated data mining”, “billing”, “interoperability”
    CodePhrase target

    static mapping = {
        match column: "termmapping_match"
        purpose cascade: "save-update"
        target cascade: "save-update"
    }

    static constraints = {
        match (inList:[NARROWER, EQUIVALENT, BROADER, UNKNOWN]) // inv: is_valid_match_code(match)
        // purpose () // FALTA: inv: purpose <> Void implies terminology(Terminology_id_openehr). has_code_for_group_id(Group_id_term_mapping_purpose, purpose. defining_code) 
        target (nullable: false)
    }

    // post:match = ‘>’ implies Result
    // The mapping is to a broader term.
    boolean broader(){
        return match == (BROADER)
    }

    // post:match = ‘=’ implies Result
    // The mapping is to an equivalent term.
    boolean equivalent(){
        return match == (EQUIVALENT)
    }

    // post: Result := c = ‘>’ or c = ‘=’ or c = ‘<’ or c = ‘?’
    // True if match valid.
    boolean isValidMatchCode(char c){
        return ((c == (BROADER)) || (c == (EQUIVALENT)) || (c == (NARROWER)) || (c == (UNKNOWN)))
    }

    // post: match = ‘<’ implies Result
    // The mapping is to a narrower term.
    boolean narrower(){
        return match == (NARROWER)
    }

    // post: match = ‘?’ implies Result
    // The kind of mapping is unknown.
    boolean unknown(){
        return match == (UNKNOWN)
    }
}
