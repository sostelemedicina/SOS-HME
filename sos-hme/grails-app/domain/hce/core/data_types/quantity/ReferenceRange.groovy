package hce.core.data_types.quantity

//import hce.core.data_types.text.DvText

import hce.core.data_types.basic.DataValue
import hce.core.data_types.quantity.DvOrdered
import hce.core.data_types.quantity.DvInterval

//import org.apache.commons.lang.builder.EqualsBuilder;
//import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * This class defines a named range to be associated with any Ordered
 * datum. Each such range is particular to the patient and context,
 * like sex, age, and any other factor which affects ranges.
 */
class ReferenceRange extends DataValue { //<T extends DvOrdered> extends DataValue {

    static final String NORMAL = "normal"

//    DvText meaning
//    DvInterval<T> range
    DvInterval range

    static mapping = {
        range cascade: "save-update"
    }

    static constraints = {
//        meaning(nullable:false)
        range(nullable:false)
    }

    // TODO: Custom validator para range>
    /*
     *  if (!isSimple(range)) {
            throw new IllegalArgumentException("range not simple");
        }

    private static boolean isSimple(DvInterval range) {
        return ( range.isLowerUnbounded() ||
                range.getLower().isSimple() )
                &&
                ( range.isUpperUnbounded() ||
                range.getUpper().isSimple() );
    }
     */

    /**
     * Indicates if the value is inside the range
     *
     * @param value
     * @return true if has the value
     */
//    boolean isInRange(DvOrdered value)
//    {
//        return this.range.has(value);
//    }

    /**
     * Two ReferenceRanges equal if both has same value for meaning
     * and range
     *
     * @param o
     * @return true if equals
     */
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!( o instanceof ReferenceRange )) return false;
//
//        final ReferenceRange rg = (ReferenceRange) o;
//
//        return new EqualsBuilder()
//                .append(meaning, rg.meaning)
//                .append(range, rg.range)
//                .isEquals();
//    }

    /**
     * Return a hash code of this object
     *
     * @return hash code
     */
//    public int hashCode() {
//        return new HashCodeBuilder(13, 29)
//                .append(meaning)
//                .append(range)
//                .toHashCode();
//    }

    /**
     * String presentation of this range
     *
     * @return string presentation
     */
//    public String toString() {
//        return meaning + ", " + range;
//    }

}
