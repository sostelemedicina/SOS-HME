package hce.core.data_types.text

import hce.core.data_types.basic.DataValue;
import hce.core.support.identification.TerminologyID

class CodePhrase extends DataValue{

    String codeString; // The key used by the terminology service to identify a concept or coordination of concepts. This string is most likely parsable inside the terminology service, but nothing can be assumed about its syntax outside that context. 
    TerminologyID terminologyId; // Identifier of the distinct terminology from which the code_string (or its elements) was extracted. 

    static mapping = {
        terminologyId cascade: "save-update"
    }

    static constraints = {
        codeString (nullable: false, blank:false)
    	terminologyId (blank:false)
    }
}
