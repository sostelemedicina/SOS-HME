package hce.core.data_types.quantity

import hce.core.data_types.quantity.DvOrdered

/**
 * Abstract class defining the concept of true quantified values,
 * like values which are not only ordered, but which have
 * a getMagnitude, and for which the addition and difference
 * operations can be defined.
 */
//abstract
class DvQuantified extends DvOrdered {
     //<T extends DvQuantified> extends DvOrdered<T> {

     /**
      * Optional status of magnitude with values:
      * <ul>
      * <li> "=" : magnitude is a point value</li>
      * <li> "<" : value is < magnitude</li>
      * <li> ">" : value is > magnitude</li>
      * <li> "<=" : value is <= magnitude</li>
      * <li> ">=" : value is >= magnitude</li>
      * <li> "~" : value is approximately magnitude</li>
      * <li> If not present, meaning is "="</li>
      * </ul>
      * @return null if unspecified
      */
    static String EQUAL = '='
    static String MAJOR = '>'
    static String MAJOR_OR_EQUAL = '>='
    static String MINOR = '<'
    static String MINOR_OR_EQUAL = '<='
    static String DISTINCT = '~'

    /* FIXME: por ahora no uso magnitudeStatus, comento para que no tire errores de validacion.
    String magnitudeStatus

    static constraints = {
        magnitudeStatus(nullable:false, validator: { // 	inv: value <> void and not value.is_empty and not (value.has(CR) or value.has(LF))
                ((s == EQUAL) || (s == MAJOR) || (s == MAJOR_OR_EQUAL) || (s == MINOR) || (s == MINOR_OR_EQUAL) || (s == DISTINCT))
        })
    }
    
    int magnitude(){ // retorna Ordered_Numeric
        return magnitudeStatus.count()
    }

    boolean valiid_magnitude_status(String s){
        return ((s == EQUAL) || (s == MAJOR) || (s == MAJOR_OR_EQUAL)
                || (s == MINOR) || (s == MINOR_OR_EQUAL) || (s == DISTINCT))
    }*/

    /**
     * Numeric value of the quantity in canonical (single value) form
     *
     * @return getMagnitude
     */
//    public abstract Number getMagnitude();


    /**
     * Equals if getMagnitude equals
     *
     * @param o
     * @return true if equals
     */
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!( o instanceof DvQuantified )) return false;
//
//        final DvQuantified dvQuantified = (DvQuantified) o;
//
//        return getMagnitude().equals(dvQuantified.getMagnitude());
//    }

    /**
     * Return hash code of this Quantified
     *
     * @return hash code
     */
//    public int hashCode() {
//        return getMagnitude().hashCode();
//    }

}
