package hce.core.data_types.quantity

//import org.apache.lang.builder.HashCodeBuilder;
//import hce.core.data_types.text.CodePhrase
import hce.core.data_types.quantity.DvAmount


/**
 * This class defines countable quantities.
 */
class DvCount extends DvAmount { //<DvCount> {

    Integer magnitude // cambie el int para poder meter null y que valide el GORM

    static mapping = {
        magnitude column: "dvcount_magnitude"
    }
    
    static constraints = {
        magnitude(nullable: false)   
    }

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * @param o the Object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 *         is less than, equal to, or greater than the specified object.
	 * @throws ClassCastException if the specified object's type prevents it
	 *                            from being compared to this Object.
	 */
//	public int compareTo(DvOrdered o) {
//		final DvCount c = (DvCount) o;
//		if (magnitude < c.magnitude)
//			return -1;
//		if (magnitude > c.magnitude)
//			return 1;
//		return 0;
//	}

	/**
	 * Tests if two instances are strictly comparable.
	 *
	 * @param ordered
	 * @return true if two instances are strictly comparable
	 */
//	public boolean isStrictlyComparableTo(DvOrdered ordered) {
//		return ordered instanceof DvCount;
//	}

	/**
	 * Two Counts equal if has same magnitude
	 *
	 * @param o
	 * @return true if equals
	 */
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (!(o instanceof DvCount))
//			return false;
//
//		final DvCount dvCount = (DvCount) o;
//
//		if (magnitude != dvCount.magnitude)
//			return false;
//
//		return true;
//	}


}
