/**
 * 
 */
package converters



/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class DateConverter {
    
    /**
     * @param hl7date aaaaMMdd
     */
    static Date fromHL7DateFormat( String hl7date )
    {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd")
        Date d = sdf.parse(hl7date)
        return d
    }
    
    /**
     * Devuelve aaaaMMddhhmmss
     * @param d
     * @return
     */
    static String toHL7DateTimeFormat( Date d )
    {
        def hl7dt = ""
        hl7dt += (d.year + 1900)
        hl7dt += (((d.month+1)<10) ? ('0'+(d.month+1)) : (d.month+1))
        hl7dt += ((d.date<10) ? ('0'+d.date) : d.date)
        hl7dt += ((d.hours<10) ? ('0'+d.hours) : d.hours)
        hl7dt += ((d.minutes<10) ? ('0'+d.minutes) : d.minutes)
        hl7dt += ((d.seconds<10) ? ('0'+d.seconds) : d.seconds)
        
        return hl7dt
    }
    
    /**
     * Devuelve aaaaMMdd
     * @param d
     * @return
     */
    static String toHL7DateFormat( Date d )
    {
        def hl7dt = ""
        hl7dt += (d.year + 1900)
        hl7dt += (((d.month+1)<10) ? ('0'+(d.month+1)) : (d.month+1))
        hl7dt += ((d.date<10) ? ('0'+d.date) : d.date)

        return hl7dt
    }
    
    /**
     * Devuelve aaaa-MM-dd hh:mm:ss
     * @param d
     * @return
     */
    static String toIso8601ExtendedDateTimeFormat( Date d )
    {
        def iso8601 = ""
        iso8601 += (d.year + 1900) + "-"
        iso8601 += (((d.month+1)<10) ? ('0'+(d.month+1)) : (d.month+1)) + "-"
        iso8601 += ((d.date<10) ? ('0'+d.date) : d.date) + " "
        iso8601 += ((d.hours<10) ? ('0'+d.hours) : d.hours) + ":"
        iso8601 += ((d.minutes<10) ? ('0'+d.minutes) : d.minutes) + ":"
        iso8601 += ((d.seconds<10) ? ('0'+d.seconds) : d.seconds)
        
        return iso8601
    }
    
    /**
     * basic: aaaaMMdd (HL7)
     * extended: aaaa-MM-dd (OpenEHR)
     */
    static String iso8601BasicToExtendedDate( String basic )
    {
        return basic[0..3]+"-"+basic[4..5]+"-"+basic[5..6]
    }
    
    static String iso8601BasicToExtendedDateTime( String basic )
    {
        return basic[0..3]+"-"+basic[4..5]+"-"+basic[5..6]+" "+basic[7..8]+":"+basic[9..10]
    }
    
    static String iso8601ExtendedDateTimeFromParams( Map params, String prefix )
    {

        //Es utilizado cuando se pasan parametros provenientes de un <g:datePicker>
        def year = ''
        def month = ''
        def day = ''
        def hour = ''
        def minute = ''
        
        params.keySet().each { key ->
        
            if (key.startsWith(prefix))
            {
                def field = key.split('_')[1] // year, month, etc
                switch(field)
                {
                    case 'year':   year = params[key]
                    break
                    case 'month':  month = params[key]
                    break
                    case 'day':    day = params[key]
                    break
                    case 'hour':   hour = params[key]
                    break
                    case 'minute': minute = params[key]
                    break
                }
            }
        }
        
        return year+'-'+
        ((month.size()==2)?month:'0'+month)+'-'+
        ((day.size()==2)?day:'0'+day)+' '+
        ((hour.size()==2)?hour:'0'+hour)+':'+
        ((minute.size()==2)?minute:'0'+minute)+":"+
              '00'
    }
    static String iso8601ExtendedDateTimeFromParamsSOS( String fecha )
    {
        //Se espera que fecha tenga formato dd-mm-yyy

        def year = ''
        def month = ''
        def day = ''
        def hour = ''
        def minute = ''

        StringTokenizer tok = new StringTokenizer(fecha," ");

        if(tok.countTokens()>2){
        fecha = tok.nextToken()


           StringTokenizer hor = new StringTokenizer(tok.nextToken(),":")
           hour = hor.nextToken()
           minute = hor.nextToken()

           def aux = tok.nextToken()
           if(aux == "pm" || aux=='PM'){

                hour = (((hour.toInteger()+12) <= 23)?(hour.toInteger()+12):12).toString()

           }else if((aux == "am" || aux=='AM') && hour == "12") {

            hour = "00"

           }

        }else{
        hour = "00"
        minute = "00"

        }
        StringTokenizer tokens = new StringTokenizer(fecha,"-");

        day = tokens.nextToken();
        month = tokens.nextToken();
        year = tokens.nextToken();


        



        return year+'-'+
        ((month.size()==2)?month:'0'+month)+'-'+
        ((day.size()==2)?day:'0'+day)+' '+
        ((hour.size()==2)?hour:'0'+hour)+':'+
        ((minute.size()==2)?minute:'0'+minute)+":"+
              '00'
    }

   
    
    static Date dateFromParams( Map params, String prefix )
    {
        def year = ''
        def month = ''
        def day = ''
        
        params.keySet().each { key ->
        
            if (key.startsWith(prefix))
            {
                def field = key.split('_')[1] // year, month, etc
                switch(field)
                {
                    case 'year':   year = params[key]
                    break
                    case 'month':  month = params[key]
                    break
                    case 'day':    day = params[key]
                    break
                }
            }
        }
        
        try {
            String fecha = year+'-'+month+'-'+day
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd")
            Date d = sdf.parse(fecha)
            return d
        }
        catch (Exception e)
        {
            return null // es porque no vino alguno de dia mes o anio para armar la fecha y no pudo parsear
        }
    }
    
}
