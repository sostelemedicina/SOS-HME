package hce.core.data_types.quantity

//import hce.core.data_types.text.CodePhrase

import hce.core.data_types.quantity.DvQuantified

/**
 * Abstract class defining the concept of relative quantified 'amounts'.
 * For relative quantities, the '+' and '-' operators are defined
 * (unlike descendants of DV_ABSOLUTE_QUANTITY, such as the date/time types).
 */
//abstract
class DvAmount extends DvQuantified {
     // <T extends DvAmount> extends DvQuantified<T> {

    double accuracy = 0.0
    boolean accuracyIsPercent = false

    static constraints = {
        //accuracy()
        //constraints()
    }

    /**
     * Sum of this quantity and another whose formal type must be the
     * difference type of this quantity.
     *
     * @param q
     * @return product of addition
     */
//    public abstract DvQuantified<T> add(DvQuantified<T> q);

    /**
     * Difference of this quantity and another whose formal type must
     * be the difference type of this quantity type.
     *
     * @param q
     * @return product of substration
     */
//    public abstract DvQuantified<T> subtract(DvQuantified<T> q);

    /**
     * Type of quantity which can be added or subtracted to this
     * quantity. Usually the same type, but may be different as in
     * the case of dates and times.
     *
     * @return diff type
     */
//    public abstract Class getDiffType();

}
