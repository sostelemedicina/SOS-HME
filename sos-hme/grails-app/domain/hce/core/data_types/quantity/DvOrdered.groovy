package hce.core.data_types.quantity

import hce.core.data_types.basic.DataValue
import hce.core.data_types.quantity.ReferenceRange
//import hce.core.data_types.text.CodePhrase

//import org.openehr.rm.support.terminology.OpenEHRCodeSetIdentifiers;
//import org.openehr.rm.support.terminology.TerminologyService;


/**
 * Abstract class defining the concept of ordered values, which
 * includes ordinals as well as true quantities.
 */
//abstract
class DvOrdered extends DataValue {
         //<T extends DvOrdered> extends DataValue {
         //implements Comparable<DvOrdered> {


    DvInterval normalRange
    static hasMany = [otherReferenceRanges:ReferenceRange]
//    CodePhrase normalStatus

    static mapping = {
        normalRange cascade: "save-update"
        otherReferenceRanges cascade: "save-update"
    }

   static constraints = {
      normalRange(nullable:true)
   }

    /**
     * TODO: Constraints
     * if (otherReferenceRanges != null) {
            if (otherReferenceRanges.isEmpty()) {
                throw new IllegalArgumentException("empty otherReferenceRanges");
            }
            this.otherReferenceRanges =
                    new ArrayList<ReferenceRange<T>>(otherReferenceRanges);
        } else {
            this.otherReferenceRanges = null;
        }
     */



    /**
     * Value is in the normal range if there is one, otherwise True
     *
     * @return true if normal
     * @throws IllegalStateException if both normalRange and normalStatus null
     */
//    boolean isNormal() throws IllegalStateException
//    {
//    	if(this.normalRange == null && this.normalStatus == null) {
//    		throw new IllegalStateException(
//    				"both normalRange and normalStatus null");
//    	}
//    	if(normalRange != null) {
//    		return this.normalRange.has(this);
//    	} else {
//    		return this.normalStatus.getCodeString().equals("N");
//    	}
//    }

    /**
     * True if this quantity has no reference ranges
     *
     * @return true if has no reference range
     */
//    public boolean isSimple() {
//        return otherReferenceRanges == null;
//    }

    /**
     * Tests if two instances are strictly comparable.
     *
     * @param ordered
     * @return true if two instances are strictly comparable
     */
    //public abstract boolean isStrictlyComparableTo(DvOrdered ordered);

}
