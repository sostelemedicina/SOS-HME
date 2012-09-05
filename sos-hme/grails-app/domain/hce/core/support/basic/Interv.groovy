package hce.core.support.basic

// En lugar de dejar la clase tipada, restringe el uso a DvOrdered.
import hce.core.data_types.quantity.DvOrdered

//import org.apache.commons.lang.builder.EqualsBuilder;
//import org.apache.commons.lang.builder.HashCodeBuilder;
//import org.apache.commons.lang.builder.ToStringBuilder;
//import org.openehr.rm.RMObject;

/**
 * Interval of comparable items. Instances of this class
 * are immutable.
 */
class Interv { //<T extends Comparable> {

//    T lower
//    T upper
    DvOrdered lower
    DvOrdered upper
    boolean lowerIncluded = false
    boolean upperIncluded = false

    static mapping = {
        lower cascade: "save-update"
        upper cascade: "save-update"
    }

    // TODO: constraint
        /*
         * if (lower != null && upper != null
                && upper.compareTo(lower) < 0) {
            throw new IllegalArgumentException("lower > upper");
        }
         */

    static transients = ['lowerUnbounded', 'upperUnbounded']

    /**
     * Returns true if lower boundary open
     *
     * @return true if has lower boundary
     */
    public boolean isLowerUnbounded()
    {
        return lower == null
    }

    /**
     * Returns true if upper boundary open
     *
     * @return true if has upper boundary
     */
    public boolean isUpperUnbounded()
    {
        return upper == null
    }

    /**
     * Returns true if (lower >= value and value <= upper)
     *
     * @param value to compare to
     * @return ture if given value is included in this Interval
     * @throws IllegalArgumentException if value is null
     */
//    public boolean has(T value) {
//        if (value == null) {
//            throw new IllegalArgumentException("null value");
//        }
//        return ( lower == null
//                || value.compareTo(lower) > 0
//                || ( lowerIncluded && value.compareTo(lower) == 0 ) )
//                && ( upper == null
//                || value.compareTo(upper) < 0
//                || ( upperIncluded && value.compareTo(upper) == 0 ) );
//    }

    /**
     * Equals if two Intervals have same values for lower and
     * upper boundaries
     *
     * @param o the object to compare with
     * @return true if equals
     */
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!( o instanceof Interval )) return false;
//
//        final Interval interval = (Interval) o;
//
//        return new EqualsBuilder()
//                .append(lower, interval.lower)
//                .append(upper, interval.upper)
//                .append(lowerIncluded, interval.lowerIncluded)
//                .append(upperIncluded, interval.upperIncluded)
//                .isEquals();
//    }

    /**
     * Return a hash code of this Interval
     *
     * @return hash code
     */
//    public int hashCode() {
//        return new HashCodeBuilder()
//                .append(lower)
//                .append(upper)
//                .append(lowerIncluded)
//                .append(upperIncluded)
//                .toHashCode();
//    }

    /**
     * Return string presentation of this Interval. The string
     * consists of both lower and upper boundary, if any of them
     * is not specified, "unbounded" is provided.
     *
     * @return string presentation
     */
//    public String toString() {
//        return new ToStringBuilder(this)
//                .append("lower", lower)
//                .append("lowerIncluded", lowerIncluded)
//                .append("upper", upper)
//                .append("upperIncluded", upperIncluded)
//                .toString();
//    }

}

