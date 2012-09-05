/**
 * 
 */
package tablasMaestras



/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class TipoIdentificador {

    String codigo // OID si existe
    String nombre
    String nombreCorto
    
    static String AUTOGENERADO = "2.16.840.1.113883.6.6.6"
    
    // FIXME: tanto nombre como nombreCorto deberian ser codigos i18n.
    static def getTipos()
    {
        def ret = []
        
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.2.14.2.1.1", nombre:"Cédula de Identidad V.-", nombreCorto:"CI V")
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.2.14.2.1.2", nombre:"Cédula de Identidad E.-", nombreCorto:"CI E")

        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.858", nombre:"Pasaporte", nombreCorto:"Pasaporte")
      //  ret << new TipoIdentificador(codigo:"2.16.840.1.113883.2.14.2.3", nombre:"Libreta de Conducción Uruguaya", nombreCorto:"Libreta de Conducción")
      //  ret << new TipoIdentificador(codigo:"2.16.840.1.113883.2.14.2.4", nombre:"Documento Fronterizo Uruguay-Brasil", nombreCorto:"Documento Fronterizo Uruguay-Brasil")
      //  ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.666", nombre:"Id en CC local", nombreCorto:"Id en CC local")
        // oids buscados en  http://www.hl7.org/oid/index.cfm
        /*
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.192", nombre:"Passport Numbers Namespace for CUBA", nombreCorto:"Pasaporte cubano")
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.170", nombre:"Passport Numbers Namespace for COLOMBIA", nombreCorto:"Pasaporte colombiano")
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.32", nombre:"Passport Numbers Namespace for ARGENTINA", nombreCorto:"Pasaporte argentino")
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.76", nombre:"Passport Numbers Namespace for BRAZIL", nombreCorto:"Pasaporte brasilero")
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.152", nombre:"Passport Numbers Namespace for CHILE", nombreCorto:"Pasaporte chileno")
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.604", nombre:"Passport Numbers Namespace for PERU", nombreCorto:"Pasaporte peruano")
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.68", nombre:"Passport Numbers Namespace for BOLIVIA", nombreCorto:"Pasaporte boliviano")
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.4.330.600", nombre:"Passport Numbers Namespace for PARAGUAY", nombreCorto:"Pasaporte paraguayo")
        */
        
        ret << new TipoIdentificador(codigo:"2.16.840.1.113883.2.14.1.1.1.3.1.5.1", nombre:"Número de paciente SOS Telemedicina", nombreCorto:"Número de paciente SOS")
        
        // Para ingresar pacientes que no se sabe su id
        ret << new TipoIdentificador(codigo:AUTOGENERADO, nombre:"Autogenerado por el sistema", nombreCorto:"Autogenerado")
        
        return ret
    }
}
