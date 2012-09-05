package hce.core.data_types.uri;

class DvEHRURI extends DvURI {

    // A DV_EHR_URI is a DV_URI which has the scheme name “ehr”, and which can only reference elements in EHRs

    static final String EHR_SCHEM = "ehr";

    static constraints = {
        value(validator: {
            it.getScheme() == EHR_SCHEM
        })
    }

    String scheme() {
         return EHR_SCHEM;
    }
}