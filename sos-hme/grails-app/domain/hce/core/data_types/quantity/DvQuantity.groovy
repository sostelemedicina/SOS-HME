package hce.core.data_types.quantity

//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.builder.EqualsBuilder;
//import org.apache.commons.lang.builder.HashCodeBuilder;

//import hce.core.data_types.text.CodePhrase

//import org.openehr.rm.support.measurement.MeasurementService;

import java.text.NumberFormat


/**
 * Quantitified type representing  scientific  quantities,
 * expressed as a single value and optional units. Instances of this class
 * are immutable.
 */
class DvQuantity extends DvAmount { //<DvQuantity> {
    
    // Cambie para poder meter null y que valide el GORM
    Double magnitude // = 0.0

    /**
     * Precision to which the value of the quantity is expressed,
     * in terms of number of decimal places. The value 0 implies
     * an integral quantity. The value -1 implies no limit, i.e.
     * any number of decimal places.
     */
    Integer precision = -1
    String units

    //MeasurementService measurementService; // add final

    static mapping = {
        magnitude column: "dvquantity_magnitude"
        precision column: "dvquantity_precision"
    }

    static constraints = {
        precision(min:-1) //
        magnitude(nullable:false)
    }

    /**
     * Custom constraint con el measurement service
     *  if (StringUtils.isNotEmpty(units) && measurementService == null) {
            throw new IllegalArgumentException("null measurementService");
        }
     */

     static transients = ['integral']

     /**
     * True if precision = 0; quantity represents an integral number
     *
     * @return ture if integral
     */
    public boolean isIntegral() {
        return precision == 0;
    }

    /**
     * Sum of this quantity and another whose formal type must be the
     * difference type of this quantity.
     *
     * @param q
     * @return result of addition
     * @throws ClassCastException if the specified object's type
     *                            prevents it from being added to this Object.
     */
//    public DvQuantified<DvQuantity> add(DvQuantified<DvQuantity> q) {
//        DvQuantity qt = (DvQuantity) q;
//        return new DvQuantity(getOtherReferenceRanges(), getNormalRange(),
//        		getNormalStatus(), 	getAccuracy(), isAccuracyPercent(),
//        		getMagnitudeStatus(), getUnits(), magnitude + qt.magnitude,
//        		precision, measurementService);
//    }

    /**
     * Difference of this quantity and another whose formal type must
     * be the difference type of this quantity type.
     *
     * @param q
     * @return result of subtraction
     * @throws ClassCastException if the specified object's type
     *                            prevents it from being subtracted to this Object.
     */
//    public DvQuantified<DvQuantity> subtract(DvQuantified<DvQuantity> q) {
//        DvQuantity qt = (DvQuantity) q;
//        return new DvQuantity(getOtherReferenceRanges(), getNormalRange(),
//        		getNormalStatus(), getAccuracy(), isAccuracyPercent(),
//        		getMagnitudeStatus(), getUnits(), magnitude - qt.magnitude,
//        		precision, measurementService);
//    }

    /**
     * Type of quantity which can be added or subtracted to this
     * quantity. Usually the same type, but may be different as in
     * the case of dates and times.
     *
     * @return diff type
     */
//    public Class getDiffType() {
//        return DvQuantity.class;
//    }

    /**
     * Negated version of current object, such as used for
     * representing a difference, like a weight loss.
     *
     * @return negated version
     */
//    public DvQuantity negate() {
//        return new DvQuantity(getOtherReferenceRanges(), getNormalRange(),
//        		getNormalStatus(), getAccuracy(), isAccuracyPercent(),
//        		getMagnitudeStatus(), getUnits(), -magnitude,
//                precision, measurementService);
//    }

    /**
     * string form displayable for humans
     *
     * @return string presentation
     */
//    public String toString() {
//        NumberFormat format = NumberFormat.getInstance();
//        format.setMinimumFractionDigits(precision);
//        format.setMaximumFractionDigits(precision);
//        return format.format(magnitude) + ( StringUtils.isEmpty(getUnits()) ? "" : " " + getUnits() );
//    }

    /**
     * Compares this object with the specified object for order.
     *
     * @return a negative integer, zero, or a positive integer as
     *         this object is less than, equal to, or greater than
     *         the specified object
     * @throws ClassCastException if the specified object's type
     *                            prevents it from being compared to this Object.
     */
//    public int compareTo(DvOrdered o) {
//        DvQuantity q = (DvQuantity) o;
//        if (magnitude > q.magnitude) {
//            return 1;
//        } else if (magnitude < q.magnitude) {
//            return -1;
//        }
//        return 0;
//    }

    /**
     * Tests if two instances are strictly comparable.
     *
     * @param other
     * @return true if two instances are strictly comparable
     * @throws IllegalArgumentException if other null or wrong type
     */
//    public boolean isStrictlyComparableTo(DvOrdered other) {
//        if (!( other instanceof DvQuantity )) {
//            throw new IllegalArgumentException("other not Quantity");
//        }
//        final DvQuantity dvQuantity = (DvQuantity) other;
//        return measurementService.unitsEquivalent(getUnits(),
//                dvQuantity.getUnits());
//    }

    /**
     * Equals if getMagnitude, units and precision equals
     *
     * @param o
     * @return true if equals
     */
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!( o instanceof DvQuantity )) return false;
//
//        final DvQuantity dvQuantity = (DvQuantity) o;
//
//        return new EqualsBuilder()
//                .appendSuper(super.equals(o))
//                .append(precision, dvQuantity.precision)
//                .append(units, dvQuantity.units)
//                .isEquals();
//    }

    /**
     * Return hash code of this Quantity
     *
     * @return hash code
     */
//    public int hashCode() {
//        return new HashCodeBuilder()
//                .appendSuper(super.hashCode())
//                .append(precision)
//                .toHashCode();
//    }

}
