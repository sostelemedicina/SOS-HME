package hce.core.data_types.quantity

/**
 * Class of enumeration constants defining types of proportion for the
 * DV_PROPORTION class.
 */
public enum ProportionKind {

     /**
      * Ratio type. Numerator and denominator may be any value
      */
     RATIO(0),

     /**
      * Denominator must be 1
      */
     UNITARY(1),

     /**
      * Denominator is 100, numerator is understood as a percentage value
      */
     PERCENT(2),

     /**
      * Numerator and denominator are integral, and the presentation
      * method uses a slash, e.g. "1/2".
      */
     FRACTION(3),

     /**
      * Numerator and denominator are integral, and the presentation
      * method uses a slash, e.g. "1/2"; if the numerator is greater than the
      * denominator, e.g. n=3, d=2, the presentation is "1 1/2"
      */
     INTEGER_FRACTION(4);


     private final int pkvalue;



	/*
	 * Constructor
	 *
	 * @param value
	 */
	private ProportionKind(int val) {
		this.pkvalue = val;
	}

	/**
	 * Gets the integer value of this proportion kind
	 *
	 * @return int value
	 */
	public int getValue() {
		return pkvalue;
	}

	/**
	 * Gets string presentation of this proportion kind
	 */
	public String toString()
    {
		//return Integer.toString(value);
	    switch (this.pkvalue) {
	      case 0:
            return "ProportionKind-> RATIO"
          case 1:
            return "ProportionKind-> UNITARY"
          case 2:
            return "ProportionKind-> PERCENT"
          case 3:
            return "ProportionKind-> FRACTION"
          case 4:
            return "ProportionKind-> INTEGER_FRACTION"
          default:
            throw new IllegalArgumentException("unknown value");
        }
	}

	/**
	 * Gets proportion kind from integer value
	 *
	 * @param value
	 * @return proportionKind of given value
	 * @throws IllegalArgument if value is unknown
	 */
	public static ProportionKind fromValue(int val) {
		switch (val) {
		case 0:
			return RATIO;
		case 1:
			return UNITARY;
		case 2:
			return PERCENT;
		case 3:
			return FRACTION;
		case 4:
			return INTEGER_FRACTION;
		default:
			throw new IllegalArgumentException("unknown value");
		}
	}
}
