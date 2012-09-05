package hce.core.data_types.basic

class DvIdentifier extends DataValue {

    String assigner; // Organisation that assigned the id to the item being identified.
    String code; // NOMBRE ORIGINAL: id // Organisation that assigned the id to the item being identified
    String issuer; // Authority which issues the kind of id used in the id field of this object.
    //String type; // The identifier type, such as “prescription”, or “SSN”. One day a controlled vocabulary might be possible for this.

    static constraints = {
        issuer (nullable: false, blank: false)
        assigner (nullable: false, blank: false)
        code (nullable: false, blank: false)
        //type (nullable: false, blank: false)
    }
}
