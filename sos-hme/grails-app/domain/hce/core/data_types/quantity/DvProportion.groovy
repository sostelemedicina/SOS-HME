package hce.core.data_types.quantity

//import hce.core.data_types.text.CodePhrase

/**
 * Models a ratio of values, i.e. where the numerator and denominator
 * are both pure numbers
 */
class DvProportion extends DvAmount { //<DvProportion> {

    Double numerator
    Double denominator
    //ProportionKind type
    Integer precision

    static mapping = {
        precision column: "dvproportion_precision"
    }

    static constraints = {
        //type(nullable:false) // null type
    }

/*
        if(type == ProportionKind.UNITARY) {
        	if(denominator != 1) {
        		throw new IllegalArgumentException(
        				"denominator for unitary proportion must be 1");
        	}
        } else if(type == ProportionKind.PERCENT) {
        	if(denominator != 100) {
        		throw new IllegalArgumentException(
        				"denominator for unitary proportion must be 100");
        	}
        } else if(type == ProportionKind.FRACTION ||
        		type == ProportionKind.INTEGER_FRACTION) {

        	if(! bothIntegral(numerator, denominator)) {
        		throw new IllegalArgumentException(
        			"both numberator and denominator must be integral for " +
        			"fraction or integer fraction proportion");
        	}
        }

        if(bothIntegral(numerator, denominator) && precision != 0) {
        	throw new IllegalArgumentException("precision must be 0 if both " +
        			"numerator and denominator are integral");
        }
        if( !bothIntegral(numerator, denominator) && precision == 0) {
        	throw new IllegalArgumentException("zero precision for " +
        			"non-integral numerator or denominator");
        }

        this.numerator = numerator;
        this.denominator = denominator;
        this.type = type;
        this.precision = precision;
    }

    */

	/**
	 * True if the numerator and denominator values are integers
	 *
	 * @return true if integral
	 */
	/*
	public boolean isIntegral() {
		return bothIntegral(numerator, denominator);
	}

	private boolean bothIntegral(double num1, double num2) {
		return (Math.floor(num1) == num1) && (Math.floor(num2) == num2);
	}

	@Override
	public DvQuantified<DvProportion> add(DvQuantified<DvProportion> q) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DvQuantified<DvProportion> subtract(DvQuantified<DvProportion> q) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Class getDiffType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number getMagnitude() {
		return new Double(numerator / denominator);
	}
	@Override
	public boolean isStrictlyComparableTo(DvOrdered ordered) {
		// TODO Auto-generated method stub
		return false;
	}
	public int compareTo(DvOrdered arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	*/
}

