/**
 * 
 */
package demographic

import hce.core.support.identification.UIDBasedID

import demographic.identity.PersonName
import demographic.party.Person
import demographic.role.Role

// Para armar el XML
import groovy.xml.MarkupBuilder

// Para convertir fechas a formato HL7
import converters.DateConverter

// Para hacer requests SOAP
//import groovyx.net.soap.SoapClient
//import groovyx.net.ws.WSClient

// ws proxies generado con cxf
import org.opensih.webservices.*

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 */
class PixPdqDemographicAccess extends DemographicAccess {

    static int pixQueryCount = 0
    static int pdqQueryCount = 0
    
    // TODO: ver como se llaman todos los metodos del WS del maciel
    static String PIX_OPERATION_QUERY = "pixQuery"
    static String PDQ_OPERATION_QUERY = "demographicQuery"
    
    // TODO: guardar las ultimas XX consultas para hacer la Continuation.
    // TODO: como guardar los diferentes parametros de la busqueda? (podria unir todo guardando instancias de Person o simplemente un array)
    //       tendria que guardar tambien el tipo de busqeuda que se hace.
    static List pixLastQueries // FIXME: debe ser sincronizada por acceso simultaneo
    static List pdqLastQueries
    
    /**
     * Encuentra otros identificadores de la persona, a partir de un identificador conocido.
     * Se corresponde a PIX ids query con un solo identificador como criterio de busqueda.
     */
    public List<UIDBasedID> findIdsById( UIDBasedID id )
    {
        // Crear mensaje
        def xml = this.pixQuery( id )
        println xml
        
        // TODO: enviar mensaje
        // TODO: procesar respuesta
        
        this.pixQueryCount ++
        
        // TODO
        return []
    }
    
    /**
     * Encuentra otros identificadores de la persona, a partir de los identificadores conocidos.
     * Se corresponde a PIX ids query con muchos identificadores como criterio de busqueda.
     * El resultado debe ser el mismo que si se invoca findIdsById para cada id de ids y luego se hace merge de los resultados.
     */
    public List<UIDBasedID> findIdsByIds( List<UIDBasedID> ids )
    {
        // TODO
        println "findIdsByIds NO IMPLEMENTADA"
        return []
    }
    
    /**
     * Busca por id (root y ext)
     */
    public List<Person> findPersonById( UIDBasedID id )
    {
        // Al ser IMP remoto, busca primero en cache local.
        // Si esta lo devuelve, si no, hace un pedido PDQ
        def candidatos = Person.withCriteria {
            ids {
                //like('value', '%'+id.extension+'%')
                //like('value', '%::%'+id.extension+'%')
                eq('value', id.value) //id.root+'::'+id.extension) // si no hago busqueda exacta con ids que uno es substring del otro me tira a las 2 personas, el id, si existe deberia tirar una sola.
            }
        }
        
        // TODO: ver que es mas rapido si as Set as List o Collection.unique
        //return (candidatos as Set) as List // Saco duplicados
        return candidatos.unique{it.id}
        
        /* TODO: como no se elimina en cache, no hago el pedido remoto.
        // Crear mensaje
        def xml = this.pdqQueryById( id )
        println xml
        
        // TODO: enviar mensaje
        // TODO: procesar respuesta
        
        this.pdqQueryCount ++
        
        println "findPersonById NO IMPLEMENTADA"
        
        // TODO
        return []
        */
    }
    
    public List<Person> findByPersonData( PersonName pn, Date bd, String sx )
    {
        // Crear mensaje
        def xml = this.pdqQueryByPersonData( pn, bd, sx )
        println "Request: " + xml
        
        // Enviar mensaje
        def response = this.sendMessageToPDQServiceSOAP( 'consultaPaciente', xml )
        //println "Response findByPersonData: " + response
        
        def result = this.parsePDQResponse( response )
        
//        <subject1>
//            <patient classCode="PAT">
//                <id root="2.16.840.1.113883.2.14.2.1" extension="26194007"/>
//                <statusCode code="NI"/>
//                <patientPerson>
//                    <name>
//                        <given>MARCELO GUSTAVO</given>
//                        <family>DUFOUR COHELO</family>
//                    </name>
//                    <telecom value="tel://SE DESCONOCE" use="HP"/>
//                    <administrativeGenderCode code="M"/>
//                    <birthTime value="19710331"/>
//                    <addr>
//                        <streetAddressLine>OBLIGADO 1131</streetAddressLine>
//                        <city>MONTEVIDEO</city>
//                        <state>MONTEVIDEO</state>
//                        <country>Uruguay</country>
//                    </addr>
//                    <asOtherIDs classCode="PAT">
//                        <id root="1.2.840.114350.1.13.99997.2.3412" extension="38273D433"/>
//                        <scopingOrganization>
//                           <id root="1.2.840.114350.1.13.99997.2.3412"/>
//                        </scopingOrganization>
//                    </asOtherIDs>
//                    <asOtherIDs classCode="CIT">
//                        <id root="2.16.840.1.113883.4.1" extension="999-88-6345"/>
//                        <scopingOrganization>
//                           <id root="2.16.840.1.113883.4.1"/>
//                        </scopingOrganization>
//                    </asOtherIDs>
//                </patientPerson>
//            </patient>
//        </subject1>

        /*
        def parsedResponse = new XmlSlurper().parseText( response )
        def result = []

        def _subjects = parsedResponse.controlActProcess.subject
        _subjects.each { _subject ->
            
            def _patient = _subject.registrationEvent.subject1.patient
            
            // ============================================================
            def person = new Person()
            
            // ============================================================ IDS
            _patient.id.each{ _id ->
               person.addToIds(
                 new UIDBasedID(
                   //root: _id.'@root'.text(),
                   //value: _id.'@extension'.text()
                   value: _id.'@root'.text()+'::'+_id.'@extension'.text()
                 )
               ) // FIXME: corregir al formato de UIDBasedID todo en el value separado con ::
            }
            
            // ============================================================ PERSON NAME
            person.sexo = _patient.patientPerson.administrativeGenderCode.'@code'.text()
            // TODO: convertir fecha de nacimiento de aaaaMMdd de HL7 a aaaa-MM-dd de openEHR
            def name = new PersonName(
                             primerNombre: _patient.patientPerson.name.given.text(),
                             primerApellido: _patient.patientPerson.name.family.text()
                           )
            
            person.addToIdentities( name )
            
            // TODO: fecha de nacimiento
            //DateConverter.iso8601BasicToExtendedDate( String basic )
            
            // ============================================================
                
            result << person
        }
        */
        
        this.pdqQueryCount ++

        return result
    }
    
    public List<Person> findByPersonDataAndId( PersonName pn, Date bd, String sx, UIDBasedID id )
    {
        def xml = this.pdqQueryByPersonDataAndId( pn, bd, sx, id )
        println "Request: " + xml
        
        // Enviar mensaje
        def response = this.sendMessageToPDQServiceSOAP( 'consultaPaciente', xml )
        println "Response findByPersonDataAndId: " + response
        
        def result = this.parsePDQResponse( response )
        
        /*
        def parsedResponse = new XmlSlurper().parseText( response )

        def result = []
        
        def _subjects = parsedResponse.controlActProcess.subject
        _subjects.each { _subject ->
            
            def _patient = _subject.registrationEvent.subject1.patient
            
            // ============================================================
            def person = new Person()
            
            // ============================================================ IDS
            _patient.id.each{ _id ->
                person.addToIds(
                    new UIDBasedID(
                        //root: _id.'@root'.text(),
                        //value: _id.'@extension'.text()
                        value: _id.'@root'.text()+'::'+_id.'@extension'.text()
                    )
                ) // FIXME: corregir al formato de UIDBasedID todo en el value separado con ::
            }
            
            // ============================================================ PERSON NAME
            person.sexo = _patient.patientPerson.administrativeGenderCode.'@code'.text()

            // TODO: convertir fecha de nacimiento de aaaaMMdd de HL7 a aaaa-MM-dd de openEHR
            def name = new PersonName(
                primerNombre: _patient.patientPerson.name.given.text(),
                primerApellido: _patient.patientPerson.name.family.text()
            )
            
            person.addToIdentities( name )
            
            // TODO: fecha de nacimiento
            //DateConverter.iso8601BasicToExtendedDate( String basic )
            
            // ============================================================
            
            result << person
        }
        */
        
        this.pdqQueryCount ++
        
        return result
        
    } // findByPersonDataAndId
    
    public List<Person> findByPersonDataAndIds( PersonName pn, Date bd, String sx, List<UIDBasedID> ids )
    {
        // TODO
        println "findByPersonDataAndIds NO IMPLEMENTADA"
        return []
    }
    
    public List<Person> findByPersonDataAndRole( PersonName pn, Date bd, String sx, Role role )
    {
        // TODO
        println "findByPersonDataAndRole NO IMPLEMENTADA"
        return []
    }
    
    public List<Person> findByPersonDataAndIdAndRole( PersonName pn, Date bd, String sx, UIDBasedID id, String roleType )
    {
        // Como en el PDQ se busca solo en base de pacientes, el rol en realidad no importa asi que es lo mismo que findByPersonDataAndId
        return findByPersonDataAndId( pn, bd, sx, id )
    }
    
    public List<Person> findByPersonDataAndIdsAndRole( PersonName pn, Date bd, String sx, List<UIDBasedID> ids, Role role )
    {
        // TODO
        println "findByPersonDataAndIdsAndRole NO IMPLEMENTADA"
        return []
    }
    
    def pixQuery( UIDBasedID _id )
    {
       def writer = new StringWriter()
       def xml = new MarkupBuilder(writer)
       
       def creationTimeString = DateConverter.toHL7DateTimeFormat( new Date() )
       
       xml.PRPA_IN201309UV {
         id(root:"2220c1c4-87ef-11dc-b865-3603d6866807") // TODO: ver si este root es el correcto o debe cambiar
         creationTime( value: creationTimeString ) // yyyyMMddhhmmss
         interactionId( root: "2.16.840.1.113883.1.6", extension: "PRPA_IN201309UV")
         processingCode( code: "P")
         processingModeCode( code: "T")
         acceptAckCode( code: "AL")
         receiver( typeCode: "RCV" ) {
           device( determinerCode: "INSTANCE" ) {
             id( root: "1.2.840.114350.1.13.99999.4567" ) // TODO: este deberia ser el id del Maciel
             telecom( value: "https://example.org/PIXQuery" ) // FIXME: que pongo aca?
           }
         }
         sender( typeCode: "SND" ) {
           device( determinerCode: "INSTANCE" ) {
             id( root: "1.2.840.114350.1.13.99997.2.7788" ) // TODO: este deberia mi id
           }
         }
         controlActProcess(moodCode:'RQO') {
           code(code:'PRPA_TE201309UV', codeSystem:'2.16.840.1.113883.1.6')
           // TODO: authorOrPerformer (no tengo informacion suficiente para crearlo ahora)
           /*
            * <authorOrPerformer typeCode="AUT">
            *    <assignedPerson>
            *        <id root="1.2.840.114350.1.13.99997.2.7766" extension="USR5568"/>
            *    </assignedPerson>
            * </authorOrPerformer>
            */
           queryByParameter {
             queryId(root:'1.2.840.114350.1.13.99999.4567.34', extension: this.pixQueryCount) // FIXME: el root es correcto?
             statusCode(code:'new')
             responsePriorityCode(code:'I')
             parameterList {
               patientIdentifier {
                 value(root: _id.root, extension: _id.extension)
                 semanticText('Patient.Id') // FIXME: ¿cuales son los valores posibles para esto?
               }
             }
           }
         }
       }
       
       return writer.toString()
    }
    
    def pdqQueryById( UIDBasedID _id )
    {
       def writer = new StringWriter()
       def xml = new MarkupBuilder(writer)
       
       def creationTimeString = DateConverter.toHL7DateTimeFormat( new Date() )
       
       xml.PRPA_IN201305UV ('xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance") {
         id(root:"1.2.840.114350.1.13.0.1.7.1.1", extension: "35423") // TODO: ver si este root es el correcto o debe cambiar
         creationTime( value: creationTimeString ) // yyyyMMddhhmmss
         interactionId( root: "2.16.840.1.113883.1.6", extension: "PRPA_IN201305UV")
         processingCode( code: "T")
         processingModeCode( code: "I")
         acceptAckCode( code: "AL")
         receiver( typeCode: "RCV" ) {
           device( determinerCode: "INSTANCE" ) {
             id( root: "1.2.840.114350.1.13.99999.4567" ) // TODO: este deberia ser el id del Maciel
             telecom( value: "http://servicelocation/PDQuery" ) // FIXME: que pongo aca?
           }
         }
         sender( typeCode: "SND" ) {
           device( determinerCode: "INSTANCE" ) {
             id( root: "1.2.840.114350.1.13.99997.2.7788" ) // TODO: este deberia mi id
           }
         }
         controlActProcess(moodCode:'RQO') {
           code(code:'PRPA_TE201305UV', codeSystem:'2.16.840.1.113883.1.6')
           // TODO: PDQ no tiene authorOrPerformer o sera que es opcional
           
           queryByParameter {
             queryId(root:'11.2.840.114350.1.13.28.1.18.5.999', extension:this.pdqQueryCount) // FIXME: el root es correcto?
             statusCode(code:'new')
             initialQuantity( value: "50" ) // FIXME: ver que es esto, sirve para paginar, hay que sacar de configuracion.
             
             matchCriterionList {
                 minimumDegreeMatch {
                     value( 'xsi:type':'INT', value:'75' ) // FIXME: sacar el % de coincidencia de la configuracion
                     semanticsText( 'indice de coincidencia pedido' )
                 }
             }
             
             //responsePriorityCode(code:'I')
             
             parameterList {
               livingSubjectId {
                 value(root: _id.root, extension: _id.extension)
                 semanticText('LivingSubject.Id')
               }
             }
           }
         }
       }
       
       return writer.toString()
    }
    
    def pdqQueryByPersonData( PersonName pn, Date bd, String sx )
    {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        
        def creationTimeString = DateConverter.toHL7DateTimeFormat( new Date() )
        
        xml.PRPA_IN201305UV ('xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance") {
          id(root:"1.2.840.114350.1.13.0.1.7.1.1", extension: "35423") // TODO: ver si este root es el correcto o debe cambiar
          creationTime( value: creationTimeString ) // yyyyMMddhhmmss
          interactionId( root: "2.16.840.1.113883.1.6", extension: "PRPA_IN201305UV")
          processingCode( code: "T")
          processingModeCode( code: "I")
          acceptAckCode( code: "AL")
          receiver( typeCode: "RCV" ) {
            device( determinerCode: "INSTANCE" ) {
              id( root: "1.2.840.114350.1.13.99999.4567" ) // TODO: este deberia ser el id del Maciel
              telecom( value: "http://servicelocation/PDQuery" ) // FIXME: que pongo aca?
            }
          }
          sender( typeCode: "SND" ) {
            device( determinerCode: "INSTANCE" ) {
              id( root: "1.2.840.114350.1.13.99997.2.7788" ) // TODO: este deberia mi id
            }
          }
          controlActProcess(moodCode:'RQO') {
            code(code:'PRPA_TE201305UV', codeSystem:'2.16.840.1.113883.1.6')
            // TODO: PDQ no tiene authorOrPerformer o sera que es opcional
            
            queryByParameter {
              queryId(root:'11.2.840.114350.1.13.28.1.18.5.999', extension:this.pdqQueryCount) // FIXME: el root es correcto?
              statusCode(code:'new')
              initialQuantity(  value: "50" ) // FIXME: ver que es esto, sirve para paginar, hay que sacar de configuracion.
              
              matchCriterionList {
                  minimumDegreeMatch {
                      value( 'xsi:type':'INT', value:'75' ) // FIXME: sacar el % de coincidencia de la configuracion
                      semanticsText( 'indice de coincidencia pedido' )
                  }
              }
              
              //responsePriorityCode(code:'I')
              
              parameterList {
                // nombre
                if (pn)
                {
                  livingSubjectName {
                    value {
                      if (pn.primerNombre)
                        given( pn.primerNombre )
                      if (pn.segundoNombre)
                        given(pn.segundoNombre)
                      if (pn.primerApellido)
                        family(pn.primerApellido)
                      if (pn.segundoApellido)
                        family(pn.segundoApellido)
                    }
                    semanticsText( 'LivingSubject.name' )
                  }
                }
                // sexo
                if (sx)
                {
                  livingSubjectAdministrativeGender {
                     value( code: sx )
                     semanticsText( 'LivingSubject.administrativeGender' )
                  }
                }
                // fecha nacimiento
                if (bd)
                {
                  livingSubjectBirthTime {
                    value( value: DateConverter.toHL7DateFormat( bd ) )
                    semanticsText( 'LivingSubject.birthTime' )
                  }
                }

// este no busca por ids 
//                livingSubjectId {
//                  value(root: _id.root, extension: _id.extension)
//                  semanticText('LivingSubject.Id')
//                }
              }
            }
          }
        }
        
        return writer.toString()
    }
    
    
    def pdqQueryByPersonDataAndId( PersonName pn, Date bd, String sx, UIDBasedID _id )
    {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        
        def creationTimeString = DateConverter.toHL7DateTimeFormat( new Date() )
        
        xml.PRPA_IN201305UV ('xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance") {
            id(root:"1.2.840.114350.1.13.0.1.7.1.1", extension: "35423") // TODO: ver si este root es el correcto o debe cambiar
            creationTime( value: creationTimeString ) // yyyyMMddhhmmss
            interactionId( root: "2.16.840.1.113883.1.6", extension: "PRPA_IN201305UV")
            processingCode( code: "T")
            processingModeCode( code: "I")
            acceptAckCode( code: "AL")
            receiver( typeCode: "RCV" ) {
                device( determinerCode: "INSTANCE" ) {
                    id( root: "1.2.840.114350.1.13.99999.4567" ) // TODO: este deberia ser el id del Maciel
                    telecom( value: "http://servicelocation/PDQuery" ) // FIXME: que pongo aca?
                }
            }
            sender( typeCode: "SND" ) {
                device( determinerCode: "INSTANCE" ) {
                    id( root: "1.2.840.114350.1.13.99997.2.7788" ) // TODO: este deberia mi id
                }
            }
            controlActProcess(moodCode:'RQO') {
                code(code:'PRPA_TE201305UV', codeSystem:'2.16.840.1.113883.1.6')
                // TODO: PDQ no tiene authorOrPerformer o sera que es opcional
                
                queryByParameter {
                    queryId(root:'11.2.840.114350.1.13.28.1.18.5.999', extension:this.pdqQueryCount) // FIXME: el root es correcto?
                    statusCode(code:'new')
                    initialQuantity( value: "50" ) // FIXME: ver que es esto, sirve para paginar, hay que sacar de configuracion.
                    
                    matchCriterionList {
                        minimumDegreeMatch {
                            value( 'xsi:type':'INT', value:'75' ) // FIXME: sacar el % de coincidencia de la configuracion
                            semanticsText( 'indice de coincidencia pedido' )
                        }
                    }
                    
                    //responsePriorityCode(code:'I')
                    
                    parameterList {
                        // nombre
                        if (pn)
                        {
                            livingSubjectName {
                                value {
                                    if (pn.primerNombre)
                                        given( pn.primerNombre )
                                    if (pn.segundoNombre)
                                        given(pn.segundoNombre)
                                    if (pn.primerApellido)
                                        family(pn.primerApellido)
                                    if (pn.segundoApellido)
                                        family(pn.segundoApellido)
                                }
                                semanticsText( 'LivingSubject.name' )
                            }
                        }
                        // sexo
                        if (sx)
                        {
                            livingSubjectAdministrativeGender {
                                value( code: sx )
                                semanticsText( 'LivingSubject.administrativeGender' )
                            }
                        }
                        // fecha nacimiento
                        if (bd)
                        {
                            livingSubjectBirthTime {
                                value( value: DateConverter.toHL7DateFormat( bd ) )
                                semanticsText( 'LivingSubject.birthTime' )
                            }
                        }
                        
                        // busca por id 
                        if (_id)
                        {
                            parameterList {
                                livingSubjectId {
                                    value(root: _id.root, extension: _id.extension)
                                    semanticText('LivingSubject.Id')
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return writer.toString()
    }
    
    
    // Hace POST a una URL, es mas bien para REST WS
    def sendMessageToPIXServiceHTTP( String request )
    {
        // FIXME: va en configuracion
        def urlString = "http://192.168.1.108:8080/Rphm-ear-1.0-Rphm-ejb-1.0/ServicioRepositorio"

        // Abro url
        def url = new URL(urlString)
        def connection = url.openConnection()
        connection.setRequestMethod("POST")
        connection.doOutput = true

        // Posteo params
        def writer = new OutputStreamWriter(connection.outputStream)
        writer.write( request ) // Posteo el mensaje
        writer.flush()
        writer.close()
        connection.connect()

        // Devuelvo respuesta
        def pixResponse = connection.content.text
        
        log.debug(pixResponse)

        return pixResponse
    }
    
    // Manda request via SOAP WS al servicio PIX
    // Ver http://groovy.codehaus.org/Groovy+SOAP
    def sendMessageToPIXServiceSOAP( String operation, String request )
    {
        // FIXME: pix wsld va en configuracion
//        def wsdl = "http://192.168.1.108:8080/Rphm-ear-1.0-Rphm-ejb-1.0/ServicioRepositorio?wsdl"
//        def proxy = new SoapClient( wsdl )
//                
//        def pixResponse = proxy.(operation)( request )
//        
//        log.debug(pixResponse)
//
//        return pixResponse

          println "sendMessageToPIXServiceSOAP NO IMPLEMENTADA"
          return ""
    }
    
    // Con Grab intenta resolver las dependencias por Ivy y me da un error de compilacion.
//    @Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.0')
//    def getProxy(wsdl, classLoader) {
//      new WSClient(wsdl, classLoader)
//    }
    
    //  Manda request via SOAP WS al servicio PDQ
    //  Ver http://groovy.codehaus.org/Groovy+SOAP
    def sendMessageToPDQServiceSOAP( String operation, String request )
    {
    /* Con GroovyWS no funciona...
     * 
        // FIXME: pdq wsdl va en configuracion
        def wsdl = "http://192.168.118.28:8080/Rphm-ear-1.0-Rphm-ejb-1.0/ServicioRepositorio?wsdl"
        //def wsdl = "http://192.168.118.102:8080/Rphm-ear-1.0-Rphm-ejb-1.0/ServicioRepositorio?wsdl"
        //def wsdl = "http://www.hmaciel.gub.uy:8080/Rphm-ear-1.0-Rphm-ejb-1.0/ServicioRepositorio?wsdl"
        //def proxy = new SoapClient( wsdl )
    
        def proxy = new WSClient(wsdl, this.class.classLoader)
        proxy.initialize()
                
        def pdqResponse = proxy."$operation"( request )
        //println "1. sendMessageToPDQServiceSOAP RESPONSE: " + pdqResponse
        
        //pdqResponse = proxy.PDQSupplier_PRPA_IN201305UV( request )
        //println "2. sendMessageToPDQServiceSOAP RESPONSE: " + pdqResponse
     */
        
        
     // IMP MACIEL
        def svc = new ConsultaRepo()
        def pdqResponse = svc.getOpenSIH_0020_0020Prototipo_0020WebService_0020PublisherPort().consultaPaciente(request)
        //println "2. sendMessageToPDQServiceSOAP RESPONSE: " + pdqResponse
        
        //pdqResponse = proxy.consultaPaciente( request )
        //println "2. sendMessageToPDQServiceSOAP RESPONSE: " + pdqResponse
     
        
       // prueba local
       /*
       def pdqResponse = '''<PRPA_IN201306UV xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="urn:hl7-org:v3 ../../schema/HL7V3/multicacheschemas/PRPA_IN201306UV.xsd"
    xmlns="urn:hl7-org:v3" ITSVersion="XML_1.0">
    <id root="1.2.840.114350.1.13.999.238" extension="55789"/>
    <creationTime value="20070428150302"/>
    <interactionId root="2.16.840.1.113883.1.6" extension="PRPA_IN201306UV"/>
    <processingCode code="T"/>
    <processingModeCode code="I"/>
    <acceptAckCode code="NE"/>
    <receiver typeCode="RCV">
        <device determinerCode="INSTANCE">
            <id root="1.2.840.114350.1.13.999.567"/>
        </device>
    </receiver>
    <sender typeCode="SND">
        <device determinerCode="INSTANCE">
            <id root="1.2.840.114350.1.13.999.234"/>
            <telecom value="http://servicelocation/PDQuery"/>
        </device>
    </sender>
    <acknowledgement>
        <typeCode code="AA"/>
        <targetMessage>
            <id root="1.2.840.114350.1.13.0.1.7.1.1" extension="35423"/>
        </targetMessage>
    </acknowledgement>
    <controlActProcess moodCode="EVN">
        <code code="PRPA_TE201306UV" codeSystem="2.16.840.1.113883.1.6"/>
        <subject typeCode="SUBJ">
            <registrationEvent>
                <id nullFlavor="NA"/>
                <statusCode code="active"/>
                <subject1>
                    <patient classCode="PAT">
                        <id root="1.2.840.114350.1.13.99998.8734" extension="34827K410"/>
                        <statusCode code="active"/>
                        <patientPerson>
                            <name>
                                <given>James</given>
                                <family>Jones</family>
                            </name>
                            <telecom value="tel:+1-481-555-7684;ext=2342" use="WP"/>
                            <telecom value="tel:+1-765-555-4352" use="HP"/>
                            <administrativeGenderCode code="M"/>
                            <birthTime value="19630804"/>
                            <addr>
                                <streetAddressLine>3443 North Arctic Avenue</streetAddressLine>
                                <city>Some City</city>
                                <state>IL</state>
                            </addr>
                            <asOtherIDs classCode="PAT">
                                <id root="1.2.840.114350.1.13.99997.2.3412" extension="38273D433"/>
                                <scopingOrganization>
                                    <id root="1.2.840.114350.1.13.99997.2.3412"/>
                                </scopingOrganization>
                            </asOtherIDs>
                            <asOtherIDs classCode="CIT">
                                <id root="2.16.840.1.113883.4.1" extension="999-88-6345"/>
                                <scopingOrganization>
                                    <id root="2.16.840.1.113883.4.1"/>
                                </scopingOrganization>
                            </asOtherIDs>
                        </patientPerson>
                        <providerOrganization>
                            <id root="1.2.840.114350.1.13.99998.8734"/>
                            <name>Good Health Clinic</name>
                            <contactParty>
                                <telecom value="tel:+1-342-555-8394"/>
                            </contactParty>
                        </providerOrganization>
                        <subjectOf1>
                            <queryMatchObservation>
                                <code code="IHE_PDQ"/>
                                <value xsi:type="INT" value="92"/>
                            </queryMatchObservation>
                        </subjectOf1>
                    </patient>
                    <queryByParameter>
                        <queryId root="1.2.840.114350.1.13.28.1.18.5.999" extension="18204"/>
                        <statusCode code="new"/>
                        <initialQuantity value="2"/>
                        <parameterList>
                            <livingSubjectAdministrativeGender>
                                <value code="M"/>
                                <semanticsText>LivingSubject.administrativeGender</semanticsText>
                            </livingSubjectAdministrativeGender>
                            <livingSubjectBirthTime>
                                <value value="19630804"/>
                                <semanticsText>LivingSubject..birthTime</semanticsText>
                            </livingSubjectBirthTime>
                            <livingSubjectName>
                                <value>
                                    <given>Jimmy</given>
                                    <family>Jones</family>
                                </value>
                                <semanticsText>LivingSubject.name</semanticsText>
                            </livingSubjectName>
                            <otherIDsScopingOrganization>
                                <value root="1.2.840.114350.1.13.99997.2.3412"/>
                                <semanticsText>OtherIDs.scopingOrganization.id</semanticsText>
                            </otherIDsScopingOrganization>
                            <otherIDsScopingOrganization>
                                <value root="2.16.840.1.113883.4.1"/>
                                <semanticsText>OtherIDs.scopingOrganization.id</semanticsText>
                            </otherIDsScopingOrganization>
                            <otherIDsScopingOrganization>
                                <value root="1.2.840.114350.1.13.99998.8734"/>
                                <semanticsText>OtherIDs.scopingOrganization.id</semanticsText>
                            </otherIDsScopingOrganization>
                        </parameterList>
                    </queryByParameter>
                </subject1>
                <custodian>
                    <assignedEntity>
                        <id root="1.2.840.114350.1.13.99998.8734"/>
                    </assignedEntity>
                </custodian>
            </registrationEvent>
        </subject>
        <subject>
            <registrationEvent>
                <id nullFlavor="NA"/>
                <statusCode code="active"/>
                <subject1>
                    <patient classCode="PAT">
                        <id root="1.2.840.114350.1.13.99998.8734" extension="34827R534"/>
                        <statusCode code="active"/>
                        <patientPerson>
                            <name>
                                <given>Jim</given>
                                <family>Jones</family>
                            </name>
                            <telecom value="tel:+1-795-555-4745" use="HP"/>
                            <administrativeGenderCode code="M"/>
                            <birthTime value="19630713"/>
                            <addr>
                                <streetAddressLine>8734 Blue Ocean Street</streetAddressLine>
                                <city>Other City</city>
                                <state>IL</state>
                            </addr>
                            <asOtherIDs classCode="CIT">
                                <id root="2.16.840.1.113883.4.1" extension="999-89-3300"/>
                                <scopingOrganization>
                                    <id root="2.16.840.1.113883.4.1"/>
                                </scopingOrganization>
                            </asOtherIDs>
                        </patientPerson>
                        <providerOrganization>
                            <id root="1.2.840.114350.1.13.99998.8734"/>
                            <name>Good Health Clinic</name>
                            <contactParty>
                                <telecom value="tel:+1-342-555-8394"/>
                            </contactParty>
                        </providerOrganization>
                        <subjectOf1>
                            <queryMatchObservation>
                                <code code="IHE_PDQ"/>
                                <value xsi:type="INT" value="85"/>
                            </queryMatchObservation>
                        </subjectOf1>
                    </patient>
                    <queryByParameter>
                        <queryId root="1.2.840.114350.1.13.28.1.18.5.999" extension="18204"/>
                        <statusCode code="new"/>
                        <initialQuantity value="2"/>
                        <parameterList nullFlavor="NA"/>
                    </queryByParameter>
                </subject1>
                <custodian>
                    <assignedEntity>
                        <id root="1.2.840.114350.1.13.99998.8734"/>
                    </assignedEntity>
                </custodian>
            </registrationEvent>
        </subject>
        <queryAck>
            <queryId root="1.2.840.114350.1.13.28.1.18.5.999" extension="18204"/>
            <queryResponseCode code="OK"/>
            <resultTotalQuantity value="5"/>
            <resultCurrentQuantity value="2"/>
            <resultRemainingQuantity value="3"/>
        </queryAck>
    </controlActProcess>
</PRPA_IN201306UV>'''
    	*/
         
        return pdqResponse
    }
    
    
    List<Person> parsePDQResponse( String response )
    {
        def parsedResponse = new XmlSlurper().parseText( response )
        
        def result = []
        
        def _subjects = parsedResponse.controlActProcess.subject
        _subjects.each { _subject ->
            
            def _patient = _subject.registrationEvent.subject1.patient
            
            // ============================================================
            def person = new Person()
            
            // ============================================================ IDS
            _patient.id.each{ _id ->
                person.addToIds(
                   new UIDBasedID(
                      value: _id.'@root'.text()+'::'+_id.'@extension'.text()
                   )
                )
            }
            
            // ============================================================ PERSON NAME
            person.sexo = _patient.patientPerson.administrativeGenderCode.'@code'.text()
            
            // TODO: convertir fecha de nacimiento de aaaaMMdd de HL7 a aaaa-MM-dd de openEHR
            def name = new PersonName(
               primerNombre: _patient.patientPerson.name.given.text(),
               primerApellido: _patient.patientPerson.name.family.text()
            )
            
            person.addToIdentities( name )
            
            // fecha de nacimiento
            //println "fecha nac: " + _patient.patientPerson.birthTime.'@value'.text()
            if (_patient.patientPerson.birthTime.'@value'.text())
            {
               person.fechaNacimiento = DateConverter.fromHL7DateFormat( _patient.patientPerson.birthTime.'@value'.text() )
            }
            // ============================================================
            
            result << person
        }
        
        return result
    }
    
}
