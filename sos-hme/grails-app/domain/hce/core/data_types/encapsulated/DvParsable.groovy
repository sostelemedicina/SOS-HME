package hce.core.data_types.encapsulated

//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.builder.EqualsBuilder;
//import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Encapsulated data expressed as a parsable string.
 * The internal model of the data item is not described in the
 * openEHR model in common with other encapsulated types, but in
 * this case, the form of the data is assumed to be plaintext,
 * rather than compressed or other types of large binary data.
 * <p/>
 * Instances of Parsable are immutable.
 */
class DvParsable extends DvEncapsulated {

    String value
    String formalism

    static mapping = {
        value column: "dvparseable_value"
    }

    static constraints = {
        value(nullable:false) // null value
        formalism(nullable:false) // null or empty formalism
    }

    int size(){
        return value.count()
    }

    /**
     * Two Parsable equal if both has same value and
     * formalism
     *
     * @param o
     * @return true if equals
     */
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!( o instanceof DvParsable )) return false;
//
//        final DvParsable parsable = (DvParsable) o;
//
//        return new EqualsBuilder()
//                .append(value, parsable.value)
//                .append(formalism, parsable.formalism)
//                .isEquals();
//    }

    /**
     * Return a hash code of this parsable
     *
     * @return hash code
     */
//    public int hashCode() {
//        return new HashCodeBuilder(17, 47)
//                .append(value)
//                .append(formalism)
//                .toHashCode();
//    }
}

