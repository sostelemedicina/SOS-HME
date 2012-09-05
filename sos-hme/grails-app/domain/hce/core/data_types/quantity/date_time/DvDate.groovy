package hce.core.data_types.quantity.date_time

import java.util.Date
import java.text.*

class DvDate extends DvTemporal {

    // value definido en temporal (en la especificacion esta en cada una de las subclases de temporal)

    static constraints = {
        // TODO
        value (nullable: false,
               blank: false)
    }
    
    Date toDate()
    {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        def date
        try{
        date = (Date)formatter.parse( this.value );
        }catch(Exception e){
        date = null
            
        }
        date

    }

  /*  String toDate12(){

            Date date = this.toDate()

            SimpleDateFormat sdf

            sdf = new SimpleDateFormat("dd-MM-yyyy");

            sdf.format(date);

    }*/
}
