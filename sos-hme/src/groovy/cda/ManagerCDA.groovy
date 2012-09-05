
package cda

import groovy.xml.MarkupBuilder
import hce.core.composition.Composition
import hce.core.common.directory.Folder
import demographic.DemographicService
import demographic.role.Role
import demographic.party.Person
import demographic.identity.PersonName
import converters.DateConverter
import hce.core.support.identification.UIDBasedID
import com.thoughtworks.xstream.XStream
import org.codehaus.groovy.grails.commons.ApplicationHolder;
import org.springframework.context.ApplicationContext
import serviceinterfaces.DemographicServiceInterface
import templates.TemplateManager
import hce.core.common.change_control.Version
import java.text.SimpleDateFormat
import org.springframework.web.context.request.RequestContextHolder



/**
 *
 * @author leacar21
 */
class ManagerCDA {
    
    def ctx = ApplicationHolder.getApplication().getMainContext();
    def hceService = ctx.getBean("hceService");
    def serviceDemographic = ctx.getBean("demographicService") // (DemographicServiceInterface)

    //--------------------------------------------------------------------------

    def createFileCDA(int idEpisodio, def domainTemplates){
        def composition = Composition.get(idEpisodio)
        //def cdaMan = new ManagerCDA()
        //def xmlCDA = cdaMan.createCDA(composition) //(new_composition) //, session)
        def xmlCDA = createCDA(composition, domainTemplates) //(new_composition) //, session)
        //println xmlCDA
        // Creo el directorio si no existe
        File directorio = new File(ApplicationHolder.application.config.hce.rutaDirCDAs)
        if (!directorio.exists()){
            directorio.mkdirs() // Crea el directorio (toda las estructura si es necesario)
        }

        def version = Version.findByData(composition)

        String nombreArch = getNombreArchCDA(idEpisodio,version.getNumVersion(),new Date())
        def archivo = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + "//" + nombreArch)
        archivo.write(xmlCDA);
        version.nombreArchCDA = nombreArch
        version.save()
    }

    //--------------------------------------------------------------------------

    String getNombreArchCDA(int idEpisodio, int numVersion, Date fecha){
        return "CDA-" + idEpisodio + "-" + "V" + numVersion + "-" + getStringFecha(fecha) + ".xml"
    }

    //--------------------------------------------------------------------------

    String getStringFecha(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss")
        return sdf.format(fecha)
    }

    //--------------------------------------------------------------------------

    //public String createCDA( Composition comp ) //, Object session )
    def createCDA( Composition comp, def domainTemplates )
    {
        //XStream xstream = new XStream();

        //DemographicService demAcc = new DemographicService()// Para buscar Personas

        //ApplicationContext ctx = (ApplicationContext)ApplicationHolder.getApplication().getMainContext()
        //def serviceDemographic = ctx.getBean("demographicService") // (DemographicServiceInterface)
        ///String str = service.sayHello(request.getParameter.("name"));
        
        //CLINICAL DOCUMENT
        def clidoc_typeId_extension = "POCD_HD000040" // tipo del mensaje HL7, 000040 es el código del CDA
        def clidoc_typeId_root = "2.16.840.1.113883.1.3" // Debe ser el valor fijo 2.16.840.1.113883.1.3 
        
        // FIXME: este nombre deberia depender del "domain" configurado en Config: emergencia, trauma, internacion, etc.
        def clidoc_templateId_extension = "EHRv1.0" // En extension iría el nombre del template que se usó para generar la Composition, como usamos en realidad templates para generar las Sections, acá ponele duro extension="TRAUMAv1.0"
        
        def clidoc_templateId_root = "2.16.858.1.1.1.1.1.2" // 2.16.858 es el OID de uruguay, el resto es invento
        def clidoc_id_extension = comp.id //session.traumaContext?.episodioId// TODO, deberia ser: comp.uid.getExtension(), pero uid esta comentado en composition
        def clidoc_id_root = "2.16.858.1.1.1.1.1.3"// TODO, deberia ser: comp.uid.getRoot(), pero uid esta comentado en composition
        def clidoc_code_displayName = "Episodio de Trauma"
        def clidoc_code_codeSystemName = "HCET"
        def clidoc_code_codeSystem = "2.16.858.1.1.1.1.1.4"
        def clidoc_code_code = "11111-1" // Elegido por nositros, todos los docs de trauma tienen el mismo code

        //--------------
        def domain_path = RequestContextHolder.currentRequestAttributes().getSession().traumaContext.domainPath
        def path = Folder.findByPath(domain_path)
        def clidoc_title = "SOS Telemedicina - "+path.name.value
        //--------------------

        def clidoc_effectiveTime_value = DateConverter.toHL7DateFormat(comp.context.startTime.toDate())
        def clidoc_confidentialityCode_code = "N"
        def clidoc_confidentialityCode_codeSystem = "2.16.858.1.1.1.1.1.5"
        def clidoc_languageCode_code = "es-UY" // Sacar del config cuando se guarde alli.
        def version = Version.findByData(comp) // Ojo. findByData retorna una coleccion. Como hay una sola version con esa composition retorna una instancia (porque al crear una nueva version, pongo null en el atributo data de la version)
        def clidoc_versionNumber_value = version.getNumVersion()
        // AUTOR
        def autor_time_value = DateConverter.toHL7DateFormat(comp.context.startTime.toDate()) //.value.replace("T", "") // Indica desde cuando el autor ha participado en la creación del documento (usamos esto, pero tambien podria ser el instante en el que se firma el documento)
        def autor_assignedAuthor_id_extension = comp.composer.externalRef.objectId.value?.split('::')[1]
        def autor_assignedAuthor_id_root = comp.composer.externalRef.objectId.value?.split('::')[0]
        //----------------------------------------------------------------------
        def autor_assignedAuthor_assignedPerson_name_prefix = ""
        def autor_assignedAuthor_assignedPerson_name_given = ""
        def autor_assignedAuthor_assignedPerson_name_family = ""

        // Encuentro a la Persona (autor) segun su id (root::extension)
        // A pesar de que cuando se crea el objeto se guarda un UIDBaseID (subclase de ObjectID), al
        // traer comp.composer.externalRef.objectId metrae un ObjectID, por eso creo un UIDBasedID
        UIDBasedID uidAutor = new UIDBasedID(value: comp.composer.externalRef.objectId.value)
        def autoresPersonas = serviceDemographic.findPersonById(uidAutor)
        //def autoresPersonas = demAcc.findPersonById(uidAutor)

        autoresPersonas.identities.each{personAutorName ->
            autor_assignedAuthor_assignedPerson_name_prefix = ""
            autor_assignedAuthor_assignedPerson_name_given = personAutorName.primerNombre[0]// Nombres del Paciente
            if (personAutorName.segundoNombre[0]) autor_assignedAuthor_assignedPerson_name_given += personAutorName.segundoNombre[0]
            autor_assignedAuthor_assignedPerson_name_family = personAutorName.primerApellido[0] // Apellidos del Paciente
            if (personAutorName.segundoApellido[0]) autor_assignedAuthor_assignedPerson_name_family += personAutorName.segundoApellido[0]
        }
        //----------------------------------------------------------------------
        def autor_assignedAuthor_assignedPerson_representedOrganization_id_root = "2.16.858.1.1.1.1.1.6"
        def autor_assignedAuthor_assignedPerson_representedOrganization_name = "SOS Telemedicina"
        // CUSTODIAN
        def custodian_assignedCustodian_representedCustodianOrganization_id_root = "2.16.858.1.1.1.1.1.7"
        def custodian_assignedCustodian_representedCustodianOrganization_name = "SOS Telemedicina"
        // RECORD TARGET
        def recordTarget_patientRole_id_extension = ""
        def recordTarget_patientRole_id_root = ""
        def recordTarget_patientRole_patient_name_given = ""
        def recordTarget_patientRole_patient_name_family = ""
        def recordTarget_patientRole_patient_administrativeGenderCode_code = ""
        def recordTarget_patientRole_patient_administrativeGenderCode_codeSystem = ""
        def recordTarget_patientRole_patient_birthTime_value = ""

        comp.context.participations.each{elem ->
            def idPaciente = new UIDBasedID(value: elem.performer.externalRef.objectId.value)
            //def listaPersonasPacientes = demAcc.findByPersonDataAndIdAndRole(null, null, null, idPaciente, Role.PACIENTE)
            def listaPersonasPacientes = serviceDemographic.findByPersonDataAndIdAndRole(null, null, null, idPaciente, Role.PACIENTE)
            if (listaPersonasPacientes != null){ // Si se encontro algún paciente
                listaPersonasPacientes.each{elemPers -> // Deberia haber un unico Paciente
                    recordTarget_patientRole_id_extension = idPaciente.getExtension()
                    recordTarget_patientRole_id_root = idPaciente.getRoot()
                    if (elemPers.identities != null){ // Si tiene un nombre (PersonName)
                        elemPers.identities.each{elemPersName ->  // Supongo que tendra un solo elemento identificaro y que sera de la clase PersonName
                            //PersonName persName = elemPers.identities.get(0) // Supongo que tendra un solo elemento identificaro y que sera de la clase PersonName
                            recordTarget_patientRole_patient_name_given = elemPersName.primerNombre// Nombres del Paciente
                            if (elemPersName.segundoNombre) recordTarget_patientRole_patient_name_given += elemPersName.segundoNombre
                            recordTarget_patientRole_patient_name_family = elemPersName.primerApellido // Apellidos del Paciente
                            if (elemPersName.segundoApellido) recordTarget_patientRole_patient_name_family += elemPersName.segundoApellido
                        }
                    }
                    recordTarget_patientRole_patient_administrativeGenderCode_code = elemPers.sexo // Sexo del Paciente
                    recordTarget_patientRole_patient_administrativeGenderCode_codeSystem = "2.16.858.1.1.1.1.1.8"
                    if (elemPers.fechaNacimiento){
                        recordTarget_patientRole_patient_birthTime_value = DateConverter.toHL7DateFormat(elemPers.fechaNacimiento)
                    }
                }
            }
        }
        //----------------------------------------------------------------------
        // LEGAL AUTENTICATOR
        def legalAuthenticator_time_value = DateConverter.toHL7DateTimeFormat(new Date())
        def legalAuthenticator_signatureCode_code = "S"
        def legalAuthenticator_assignedEntity_id_root = autor_assignedAuthor_id_root
        def legalAuthenticator_assignedEntity_id_extension = autor_assignedAuthor_id_extension
        def legalAuthenticator_assignedEntity_assignedPerson_mane_given = autor_assignedAuthor_assignedPerson_name_given
        def legalAuthenticator_assignedEntity_assignedPerson_name_family = autor_assignedAuthor_assignedPerson_name_family
        def legalAuthenticator_assignedEntity_representedOrganization_id_root = "2.16.858.1.1.1.1.1.9"
        def legalAuthenticator_assignedEntity_representedOrganization_name = "SOS Telemedicina"
        
        // FIXME: setear coding a ISO-8859-1
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        
        // new
        //def template = TemplateManager.getInstance().getTemplate( comp.archetypeDetails.templateId )
        def component_nonxmlbody = convertirObjetoXML(comp, domainTemplates) //, template)

        // FIXME: donde dice xmlns_DOS_PUNTOS_xsi hay que poner 'xsi:' como string entre comillas.
        /*
         * Este es un ejemplo de como se hace: http://groovy.codehaus.org/Creating+XML+using+Groovy's+MarkupBuilder
         * 
			xml.'rec:records'('xmlns:rec': 'http://groovy.codehaus.org') {
			    car(name:'HSV Maloo', make:'Holden', year:2006) {
			        country('Australia')
			        record(type:'speed', ' Truck with speed of 271kph')
			    }
			}
         */



        xml.ClinicalDocument ( xmlns_DOS_PUNTOS_xsi: "http://www.w3.org/2001/XMLSchema-instance", xsi_DOS_PUNTOS_schemaLocation: "urn:hl7-org:v3 CDA/CDA/CDA.xsd", xmlns:"urn:hl7-org:v3", xmlns_DOS_PUNTOS_voc:"urn:hl7-org:v3/voc" ) {
            typeId (extension: clidoc_typeId_extension, root: clidoc_typeId_root)
            templateId (extension: clidoc_templateId_extension, root: clidoc_templateId_root)
            //<!--  Identificador del documento -->
            id (extension: clidoc_id_extension, root: clidoc_id_root)
            //<!--  Tipo del documento clínico -->
            code (code: clidoc_code_code, codeSystem: clidoc_code_codeSystem, codeSystemName: clidoc_code_codeSystemName, displayName: clidoc_code_displayName)
            title ( clidoc_title )
            //<!-- Fecha del acto médico -->
            effectiveTime (value: clidoc_effectiveTime_value)
            confidentialityCode (code: clidoc_confidentialityCode_code, codeSystem: clidoc_confidentialityCode_codeSystem)
            languageCode (code: clidoc_languageCode_code)
            versionNumber (value: clidoc_versionNumber_value)

            
            //<!--  Información  del paciente -->
            recordTarget(){
                patientRole (){
                    //<!--  Identificadores  del paciente -->
                    id (extension: recordTarget_patientRole_id_extension, root: recordTarget_patientRole_id_root)
                    patient(){
                        //<!--  Nombres y apellidos del paciente -->
                        name(){
                            given( recordTarget_patientRole_patient_name_given )
                            family( recordTarget_patientRole_patient_name_family )
                        }
                        //<!--  Sexo del paciente -->
                        if (recordTarget_patientRole_patient_administrativeGenderCode_code)
                        {
                          administrativeGenderCode ( code: recordTarget_patientRole_patient_administrativeGenderCode_code,
                                                     codeSystem: recordTarget_patientRole_patient_administrativeGenderCode_codeSystem )
                        }
                        if (recordTarget_patientRole_patient_birthTime_value)
                        {
                          birthTime ( value: recordTarget_patientRole_patient_birthTime_value )
                        }
                    }
                }
            }

            author (){
                time (value: autor_time_value)
                assignedAuthor(){
                    id (extension: autor_assignedAuthor_id_extension, root: autor_assignedAuthor_id_root)
                    assignedPerson(){
                        name(){
                            prefix( autor_assignedAuthor_assignedPerson_name_prefix )
                            given( autor_assignedAuthor_assignedPerson_name_given )
                            family ( autor_assignedAuthor_assignedPerson_name_family )
                        }
                    }
                    representedOrganization(){
                        id (root: autor_assignedAuthor_assignedPerson_representedOrganization_id_root)
                        name ( autor_assignedAuthor_assignedPerson_representedOrganization_name )
                    }
                }
            }

            custodian(){
                assignedCustodian(){
                    representedCustodianOrganization{
                        id (root: custodian_assignedCustodian_representedCustodianOrganization_id_root)
                        name (custodian_assignedCustodian_representedCustodianOrganization_name)
                    }
                }
            }


            //<!--Responsable legal del documento-->
            //<!-- Si alguien es Autor y Responsable legal, ambos hechos deben ser expresados -->
            // Representa a la persona que se hace responsable legalmente de haber firmado el documento
            // En este caso debe incluirse la identificación de la persona, la fecha en la que fue
            // firmado el documento, y el área jerárquica a la que pertenece la persona. (fuente: GUIA IMPLEMENTACIOn CDA HI)
            legalAuthenticator(){
                time ( value: legalAuthenticator_time_value )
                //<!--Codigo de firma S=Está firmado, puede referirse al documento original, no a la firma digital de éste-->
                signatureCode ( code: legalAuthenticator_signatureCode_code )
                assignedEntity(){
                    //<!--Identificacion del responsable legal del documento -->
                    id ( root: legalAuthenticator_assignedEntity_id_root, extension: legalAuthenticator_assignedEntity_id_extension )
                    //<!--Identificacion del responsable legal del documento -->
                    assignedPerson(){
                        name(){
                            given ( legalAuthenticator_assignedEntity_assignedPerson_mane_given )
                            family ( legalAuthenticator_assignedEntity_assignedPerson_name_family )
                        }
                    }
                    representedOrganization (){
                        id ( root: legalAuthenticator_assignedEntity_representedOrganization_id_root )
                        name ( legalAuthenticator_assignedEntity_representedOrganization_name )
                    }
                }
            }

            component {
                nonXMLBody {
                  text(mediaType: 'text/html', "<![CDATA[" + component_nonxmlbody + "]]>") // Con esto valida contra el XSD del CDA...
                
                }
            }
        }

        return convertir(writer.toString())
    }

   def convertir(String conver)
  {

  return conver.replace("&amp;","&")
           .replace("&nbsp;"," ")
           .replace("&lt;","<")
           .replace("&gt;",">")
           .replace("&ntilde;","ñ")
           .replace("&Ntilde;","Ñ")
           .replace("&aacute;","á")
           .replace("&eacute;","é")
           .replace("&iacute;","í")
           .replace("&oacute;","ó")
           .replace("&uacute;","ú")
           .replace("&iquest;","¿")
           .replace("&iexcl;","¡")
           .replace("&quot;","\"")
           .replace("&#039;","'")
           .replace("&deg;","°")
           .replace("_DOS_PUNTOS_", ":") // component_nonxmlbody viene con los caracteres '<'' y '>' cambiados.
                                                                                                      // Como no puedo poner ":" en los nombres de las etiquetas, le
                                                                                                      // pongo _DOS_PUNTOS_ a las etiquetas cuando la defino y luego hago el replace

 }



    //--------------------------------------------------------------------------
    //Convierte la composition en texto HTML mediante uso de los templates
    public String convertirObjetoXML(Composition comp, def domainTemplates) //, Object template)
    {
        //XStream xstream = new XStream();
        //String xml = xstream.toXML(o);
        
        // TODO
        //def tagLib = applicationContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
        def tagLib = ctx.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')

      
        def content = hceService.getCompositionInOrder(comp,domainTemplates)

        def xml = tagLib.render(template:'/guiGen/showRecord',encoding:"UTF-8", model:
        [
        content:    content,
        composition: comp,
        episodeId: comp.id, // necesario para el layout
        ]
        )
        return xml
    }

    //--------------------------------------------------------------------------

}

