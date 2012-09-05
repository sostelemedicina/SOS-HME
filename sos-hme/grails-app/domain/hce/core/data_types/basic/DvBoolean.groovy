package hce.core.data_types.basic

class DvBoolean extends DataValue {

    //boolean value // Boolean value of this item.
    Boolean value // Para poder meterle null y que valide el GORM

    //static final DvBoolean TRUE = new DvBoolean(value: true)
    //static final DvBoolean FALSE = new DvBoolean(value: false)

    static mapping = {
        value column: "dvboolean_value"
    }

    static constraints = {
        value (nullable: false)
    }
}

