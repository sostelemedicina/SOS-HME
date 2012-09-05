/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Armando Prieto
 * Tarea que se encarga de registrar todos los CDAs en el IMP periodicamente
 * siempre que exista conexion con el imp
 *
 */
import hce.core.common.change_control.Version
import hce.core.common.generic.*
import demographic.*
import cda.CdaArr
import org.codehaus.groovy.grails.commons.ApplicationHolder
import converters.DateConverter
//import org.springframework.web.context.request.RequestContextHolder
class SubirCdaToImpJob {

    def hceService
    def demographicService
    def customSecureServiceClientCda
    def customSecureServiceClientImp
    static triggers = {
        simple name: 'mySimpleTrigger', startDelay: 1000, repeatInterval: 60000
    }
    def group = "MyGroup"

    def execute(){
    


        //Preguntar si hay conexion con el IMP

        // try{
        if(true){ //conexion con el IMP



            //Listar las versiones que no están en el IMP
            def versionesCdas = Version.findAllByInIMPAndLifecycleState(false,'ehr.lifecycle.signed')
            def person
            def partySelf
            def personId
            versionesCdas.each{

                println "Este CDA está fuera: "+ it.nombreArchCDA
                   
                    
                if(it.data){
                    //Es un CDA actual!!!
                    //'data' tiene el valor de la composition

                    person= hceService.getPatientFromComposition(it.data)

                    if(person){

                        // println "Persona "+person
                        personId= person.id
                    }

                }else if(it.partySelf){

                    //Es una version anterior (vieja) de un CDA
                    //data esta seteado en null

                       
                    //registrar CDA en IMP
                    def persons = demographicService.findPersonById( it.partySelf.externalRef.objectId )

                    if(persons.size()==1){
                        //Siempre deberia ser 1
                        person = persons.get(0)

                    }

                    if(person){
                        //println "Persona "+person
                        personId= person.id
                    }
                }
                    
                def cdaArr = this.formarCda(it.nombreArchCDA)
                if(cdaArr){
                    println "CDA ARR FORMADO"
                    if(this.registrarCda(cdaArr,personId.toString())){
                        println "REGISTRO CDA REALIZADO"
                        it.inIMP = true
                        it.save()


                    }


                }


        
            }
        }
        //        }catch(Exception e){
        //            //Ocurrio un error no se pudo conectar con el IMP
        //            //seguir esperando conexion con IMP....
        //
        //            //FIXME: Escribir en log de conexiones la imposibilidad de conectar
        //
        //        }

    }

    CdaArr formarCda(String nombreArchCDA){
      //  println "A FORMAR CDA"

        def cda = new CdaArr()

        cda.id = nombreArchCDA //ESTE ES EL ID QUE TIENE ASIGNADO EN ESTE SISTEMA
        
        def cda_xml = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '//' + nombreArchCDA)

        if(!cda_xml.exists()){
         
            return null
        }else{

        
        def ClinicalDocument = new XmlParser().parseText(cda_xml.getText())
        cda.titulo = ClinicalDocument.title.text() //Titulo del CDA
        cda.fechaCreacion= DateConverter.fromHL7DateFormat(ClinicalDocument.effectiveTime.@value[0]).format("yyyy-MM-dd")
        cda.documento = cda_xml.getText() //xml
        return cda

        }
    }
    boolean registrarCda(CdaArr cda, String idPaciente){

        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id


        //FIXME: PREGUNTAR PRIMERO SI EXISTE EL PACIENTE EN EL IMP
        def result = false
        try{
        
        if(customSecureServiceClientImp.existePaciente(idPaciente,idOrganizacion)){
        

        result = customSecureServiceClientCda.registrarCDA(cda,idPaciente,idOrganizacion)
        
        }else{
            //El paciente no existe en el Imp
            result= false

        }
        }catch(Exception e ){
        println "No existe coneccion con IMP"
        //Ocurrio un error al conectarse con el IMP
        result= false

        }
        return result

        
    }
	
}