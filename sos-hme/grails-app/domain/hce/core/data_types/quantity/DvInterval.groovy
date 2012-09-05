package hce.core.data_types.quantity

import hce.core.support.basic.Interv
import hce.core.data_types.basic.DataValue
//import hce.core.data_types.quantity.DvOrdered

/**
 * Generic class defining an interval (ie range) of a comparable type.
 * An interval is a contiguous subrange of a comparable base type.
 * Instancese of this class are immutable.
 */
class DvInterval extends DataValue { // <T extends DvOrdered> extends DataValue {

    //Interval<T> interval
    Interv interv

    static mapping = {
        interv cascade: "save-update"
    }

    // TODO: Custom validator>
    /*
     *  if (lower != null && upper != null && upper.compareTo(lower) < 0) {
            throw new IllegalArgumentException("lower > upper");
        }
        interval = new Interval<T>(lower, upper);
     */


    /**
     * Returns lower boundary
     *
     * @return null if not specified
     */
//    public T getLower() {
//        return interval.getLower();
//    }

    /**
     * Returns upper boundary
     *
     * @return null if not specified
     */
//    public T getUpper() {
//        return interval.getUpper();
//    }

    static transients = ['lowerUnbounded', 'upperUnbounded', 'lowerIncluded', 'upperIncluded']

    /**
     * Returns true if lower boundary open
     * @return true is unbounded
     */
    //public boolean isLowerUnbounded() {
    //    return interval.getLower() == null;
    //}

    /**
     * Returns true if upper boundary open
     * @return true is unbounded
     */
    //public boolean isUpperUnbounded() {
    //    return interval.getUpper() == null;
    //}

    /**
     * Checks if lower boundary valude included in range
     * @return true if included
     */
    //public boolean isLowerIncluded() {
    //	return interval.isLowerIncluded();
    //}

    /**
     * Checks if upper boundary valude included in range
     * @return true if included
     */
    //public boolean isUpperIncluded() {
    //	return interval.isUpperIncluded();
    //}

    /**
     * Returns true if lower >= value and value <= upper
     *
     * @param value not null
     * @return true if given value is within this interval
     * @throws IllegalArgumentException if value is null
     */
//    public boolean has(DvOrdered<T> value)
//    {
//        if (value == null) {
//            throw new IllegalArgumentException("null value");
//        }
//
//        return ( interval.isLowerUnbounded() || value.compareTo(interval.getLower()) >= 0 ) &&
//               ( interval.isUpperUnbounded() || value.compareTo(interval.getUpper()) <= 0 )
//    }

    /**
     * Equals if both has same value for lower and upper boundaries
     *
     * @param o
     * @return true if equals
     */
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!( o instanceof DvInterval )) return false;
//
//        final DvInterval interval1 = (DvInterval) o;
//
//        if (!interval.equals(interval1.interval)) return false;
//
//        return true;
//    }

    /**
     * Return a hash code of this interval
     *
     * @return hash code
     */
//    public int hashCode() {
//        return interval.hashCode();
//    }

}
