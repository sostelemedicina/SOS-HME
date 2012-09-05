/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */

import java.text.SimpleDateFormat;

class DateFormatTagLib {

    /*
     * Para formatear fechas de tipo Date. Saca el formato del archivo de messages.
     * Date date
     */
    def format = { attrs, body ->

        def date = attrs.date
        
        if (!date) return
        
        SimpleDateFormat formatter = new SimpleDateFormat ( g.message(code:'default.date.format') );
        
        out << formatter.format( date )
    }
}
