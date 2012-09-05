package hce.core.data_types.quantity

/**
 * De esta clase heredan las clases de date_time
 */

/**
 * Abstract class defining the concept of quantified entities whose values
 * are absolute with respect to an origin. Dates and Times are the main example.
 */
//abstract
class DvAbsoluteQuantity extends DvQuantified {
    //<T extends DvAbsoluteQuantity> extends DvQuantified<T> {

    DvAmount accuracy

    static mapping = {
        accuracy cascade: "save-update"
    }

	/**
     * Sum of this quantity and another whose formal type must be the
     * difference type of this quantity.
     *
     * @param s
     * @return product of addition
     */
//    public abstract T add(S s);

    /**
     * Difference of this quantity and another whose formal type must
     * be the difference type of this quantity type.
     *
     * @param s
     * @return product of substration
     */
//    public abstract T subtract(S s);

    /**
     * Difference of two quantities.
     *
     * @return diff type
     */
//    public abstract DvAmount diff(T other);

}
