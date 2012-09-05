package hce.core.data_types.quantity

import hce.core.data_types.quantity.DvOrdered
import hce.core.data_types.text.DvCodedText

//import hce.core.data_types.text.DvCodedText

/**
 * Purpose Models rankings and scores, like pain, Apgar values, etc,
 * where there is a) implied ordering, b) no implication that the
 * distance between each value is constant, and c) the total number
 * of values is finite.
 */
class DvOrdinal extends DvOrdered { //<DvOrdinal> {

    Integer value
    DvCodedText symbol
    Integer limitsIndex = -1

    static mapping = {
        value column: "dvordinal_value"
        symbol cascade: "save-update"
    }

    static constraints = {
        value(nullable:false, min:1) // bad value
        symbol(nullable:false) // null symbol
    }

    /**
     * Inicializacion de limitsIndex >si me setean< DvOrdered.otherReferenceRanges
     *
     *  int index = -1;
        if (otherReferenceRanges != null) {
            for (int i = 0, j = otherReferenceRanges.size(); i < j; i++) {
                ReferenceRange range = (ReferenceRange) otherReferenceRanges.get(i);
                if ("limits".equals(range.getMeaning().getValue())) {
                    index = i;
                    break;
                }
            }
            if (index < 0) {
                throw new IllegalArgumentException( "no limits in otherReferenceRanges");
            }
            this.limitsIndex = index;
        }
     */


//    public String toString() {
//        return symbol.toString();
//    }

    /**
     * @param o the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this Object.
     */
//    public int compareTo(DvOrdered o) {
//        final DvOrdinal dvOrdinal = (DvOrdinal) o;
//        if (value > dvOrdinal.value) return 1;
//        if (value < dvOrdinal.value) return -1;
//        return 0;
//    }



    /**
     * Limits of the ordinal enumeration, to allow comparison of an
     * ordinal value to its limits.
     *
     * @return reference range
     */
//    public ReferenceRange<DvOrdinal> limits() {
//        return getOtherReferenceRanges().get(limitsIndex);
//    }

    /**
     * Tests if two instances are strictly comparable.
     *
     * @param ordered
     * @return true if two instances are strictly comparable
     */
/*
    public boolean isStrictlyComparableTo(DvOrdered ordered) {
        if (!( ordered instanceof DvOrdinal )) {
            return false;
        }
        final DvOrdinal dvOrdinal = (DvOrdinal) ordered;

        if (!symbol.getDefiningCode().getTerminologyId().equals(
                dvOrdinal.symbol.getDefiningCode().getTerminologyId())) {
            return false;
        }
        // todo: chevk if symbols are from same subset or value range in the same vocabulary
        return true;
    }
*/
}
