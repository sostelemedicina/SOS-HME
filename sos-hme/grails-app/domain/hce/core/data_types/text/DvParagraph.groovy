package hce.core.data_types.text

import hce.core.data_types.basic.DataValue;

class DvParagraph extends DataValue{

    static hasMany = [items:DvText]

    static mapping = {
        items cascade: "save-update"
    }

    static constraints = {
        items (nullable: false, minSize: 1)
    }
}
