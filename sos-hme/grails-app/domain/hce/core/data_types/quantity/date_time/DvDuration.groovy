package hce.core.data_types.quantity.date_time

import hce.core.data_types.quantity.DvAmount

class DvDuration  extends DvAmount{

    String value

    static mapping = {
        value column: "dvduration_value"
    }

    static constraints = {
    }
}
