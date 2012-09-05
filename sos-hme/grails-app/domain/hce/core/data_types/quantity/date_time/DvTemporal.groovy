package hce.core.data_types.quantity.date_time

import hce.core.data_types.basic.DataValue;

class DvTemporal extends DataValue{

    String value
    //Date value
    static mapping = {
        value column: "dvtemporal_value"
    }

    static constraints = {
    }
}
