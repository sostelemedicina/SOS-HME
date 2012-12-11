// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// idiomas disponibles
langs = ['es','en','pt'] // ISO 639-1 Code

// donde se buscan con templates en disco, corresponde al domino de la HCE.
// TODO: el framework podria soportar multiples dominios ofreciendo una pantalla
//       con todos los dominios disponibles al usuario (segun su perfil) luedo de
//       que se loguea.

// Nuevo para organizar los registros por domain
// ver http://code.google.com/p/open-ehr-gen-framework/issues/detail?id=12
domains = [
           '/domain.integral',
           '/domain.materno_infantil',
           '/domain.pediatria',
           '/domain.dermatologia'
]

// FIXME: no deberia ir a buscar los templates a distintos directorios,
//        distintos dominios pueden compartir templates.
//domain = 'hce/trauma'
//domain = 'hce/emergencia'

// Configuracion nueva, para usar con dominios
templates2 {
    path = 'hce' // Path en disco de los templates, no debe empezar ni terminar en / porque TemplateManager poner las /
   
    // Configuracion de templates por dominio,
    // cada dominio tiene un registro distinto
    // formado por multiples templates
    /*   '/domain.prehospitalario_same_uy' {
    PRUEBA = ['armando']
    PREHOSPITALARIO = ['same_uy', 'same_uy_ubicacion']
    }
    '/domain.trauma' {
    // en la composition se listan las sections y subsections, si tiene una sola es que no hay subsecciones.
    // con estos nombres se arman los nombres de los templates a pedir para cada registro.
    INGRESO = ['triage'] //,'test_body_weight'] //, 'test_a1_a2', 'test_cluster', 'test_dates']
    ADMISION = ['prehospitalario', 'contexto_del_evento']
    ANAMNESIS = ['resumen_clinico']
    EVALUACION_PRIMARIA = [
    'via_aerea',
    'columna_vertebral',
    'ventilacion',
    'estado_circulatorio',
    'disfuncion_neurologica'
    ]
    PARACLINICA = ['pedido_imagenes', 'pedido_laboratorio']
    EVALUACION_SECUNDARIA = ['exposicion_corporal_total']
    DIAGNOSTICO = ['diagnosticos']
    // decisiones terapeuticas evolutivas, ISS
    COMUNES = ['movimiento_paciente']
    }
     */
   
   
    '/domain.integral'  {
        INTERROGATORIO=['interrogatorio']
        MOTIVO = ['motivo_de_consulta']
        EXPLORACION=['exploracion_funcional','antecedentes_o_anamnesis']
        OBSERVATION=['enfermedad_actual']
        EXAMEN_FISICO=['examen_fisico']
        DIAGNOSTICO=['diagnosticos'] //PRESENTA PROBLEMAS
        TRATAMIENTO=['accion_y_tratamiento']
        MOVIMIENTO=['movimiento_de_paciente']
      
    }
    /*  '/domain.materno_infantil' {
        
    MOTIVO=['motivo_de_consulta']
               
    }*/
    /*   '/domain.pediatria'{
    INTERROGATORIO=['interrogatorio']
    MOTIVO{ repet = true
    cardinalidad = 'inf'
    subsections = ['motivo_de_consulta']
    }

    }*/


}

/* templates2 es el nuevo
templates {
hce {
trauma {
// en la composition se listan las sections y subsections, si tiene una sola es que no hay subsecciones.
// con estos nombres se arman los nombres de los templates a pedir para cada registro.
INGRESO = ['triage'] //,'test_body_weight'] //, 'test_a1_a2', 'test_cluster', 'test_dates']
ADMISION = ['prehospitalario', 'contexto_del_evento']
ANAMNESIS = ['resumen_clinico']
EVALUACION_PRIMARIA = [
'via_aerea',
'columna_vertebral',
'ventilacion',
'estado_circulatorio',
'disfuncion_neurologica'
]
PARACLINICA = ['pedido_imagenes', 'pedido_laboratorio']
EVALUACION_SECUNDARIA = ['exposicion_corporal_total']
DIAGNOSTICO = ['diagnosticos']
// decisiones terapeuticas evolutivas, ISS
COMUNES = ['movimiento_paciente']
}
emergencia {
ACCIONES = ['adm_sust']
DIAGNOSTICO = ['diagnosticos']
}
ambulatorio {
            
}
quirurgica {
            
}
}
}
 */

hce {
    patient_administration {
        serviceType {
            local = true // busqueda de pacientes es local a no ser que diga lo contrario
        }
    }
    close_record_job_on = false
}

openEHR.RMVersion = '1.0.2'

// Ruta a directorio en donde se almacenan los CDAs generados

hce.rutaDirCDAs = ".//CDAs"

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
    xml: ['text/xml', 'application/xml'],
    text: 'text/plain',
    js: 'text/javascript',
    rss: 'application/rss+xml',
    atom: 'application/atom+xml',
    css: 'text/css',
    csv: 'text/csv',
    all: '*/*',
    json: ['application/json','text/json'],
    form: 'application/x-www-form-urlencoded',
    multipartForm: 'multipart/form-data'
]
// The default codec used to encode data with ${}
grails.views.default.codec="html" // none, html, base64
grails.views.gsp.encoding="UTF-8" //"UTF-8" ISO-8859-1
grails.converters.encoding="UTF-8" //"UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// IMPORTANT - these must be set externally to env if you want to refer to them later for use
// via cxf.  You can also simply hardcode the url in the cxf section and NOT refer to a variable
// as well
service.soshme.url.cda = ""
service.soshme.url.imp = ""
service.sostriaje.url.imp = ""

//service.soshme.serverURL = "http://190.169.161.50:9090"
service.soshme.serverURL = "http://127.0.0.1:9090"
service.sostriaje.serverURL = "http://127.0.0.1:8080"

// set per-environment service url
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// port is set to 9090 for test use -Dserver.port=9090 during test
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
environments {
    production {
        service.soshme.url.cda = "${service.soshme.serverURL}/imp-cda/services/cda"
        service.soshme.url.imp = "${service.soshme.serverURL}/imp-cda/services/imp"
        
        service.sostriaje.url.webService = "${service.sostriaje.serverURL}/SOS-TRIAJE/services/servicioWebTriaje"
    }
    development {
        service.soshme.url.cda = "${service.soshme.serverURL}/imp-cda/services/cda"
        service.soshme.url.imp = "${service.soshme.serverURL}/imp-cda/services/imp"
        
        service.sostriaje.url.webService = "${service.sostriaje.serverURL}/SOS-TRIAJE/services/servicioWebTriaje"
    }
    test {
        service.soshme.url.cda = "${service.soshme.serverURL}/imp-cda/services/cda"
        service.soshme.url.imp = "${service.soshme.serverURL}/imp-cda/services/imp"
        
        service.sostriaje.url.webService = "${service.sostriaje.serverURL}/SOS-TRIAJE/services/servicioWebTriaje"
    }
}

//Indice Maestro de Pacientes
//UCV CAIBCO 
imp.organizacion.id = "26be4e75-8c36-4fc9-8c52-e8c74cbd4f97"
cxf {
    installDir = "C:/sos/apps/apache-cxf-2.4.4" //only used for wsdl2java script target
    client {
        customSecureServiceClientCda {
            wsdl = "docs/cda.wsdl" //only used for wsdl2java script target
            namespace = "cda"
            clientInterface = cda.CdaServicePortType
            serviceEndpointAddress = "${service.soshme.url.cda}"
        }
        customSecureServiceClientImp {
            wsdl = "docs/imp.wsdl" //only used for wsdl2java script target
            namespace = "imp"
            clientInterface = imp.ImpServicePortType
            serviceEndpointAddress = "${service.soshme.url.imp}"
        }
        customSecureServiceClientTriaje {
            wsdl = "docs/servicioWebTriaje.wsdl" //only used for wsdl2java script target
            //wsdlArgs = ['-autoNameResolution', '-validate']
            namespace = "webService"
            //client = false //defaults to false
            //bindingFile = "grails-app/conf/bindings.xml"
            //outputDir = "src/java"
            
            clientInterface = webService.ServicioWebTriajeServicePortType
            serviceEndpointAddress = "${service.sostriaje.url.webService}"
        }
         
    }
}
// log4j configuration
log4j = {

    production {
        appenders {
            file name:'errorAppender', threshold: org.apache.log4j.Level.ERROR, file:'/sos2/dev/logs/errorLog.log'
            file name:'infoAppender', threshold: org.apache.log4j.Level.INFO, file:'/sos2/dev/logs/infoLog.log'
        }
    }
    test{
        appenders {
            file name:'errorAppender', threshold: org.apache.log4j.Level.ERROR, file:'/sos2/dev/logs/errorLog.log'
            file name:'infoAppender', threshold: org.apache.log4j.Level.INFO, file:'/sos2/dev/logs/infoLog.log'
        }
	
    }

    development {
        appenders {
            file name:'errorAppender', threshold: org.apache.log4j.Level.ERROR, file:'/sos2/dev/logs/errorLog.log'
            file name:'infoAppender', threshold: org.apache.log4j.Level.INFO, file:'/sos2/dev/logs/infoLog.log'
        }
    }
    //PARCHE: hay un problema con los entornos de desarrollo no tradicionales, es necesario preguntar para que no se de un error
    if (GrailsUtil.environment=='loadData') {
        loadData {
            appenders {
                file name:'errorAppender', threshold: org.apache.log4j.Level.ERROR, file:'/sos2/dev/logs/errorLog.log'
                file name:'infoAppender', threshold: org.apache.log4j.Level.INFO, file:'/sos2/dev/logs/infoLog.log'
            }
        }
    }
        
    root {
        error 'errorAppender'
    }
	
    info infoAppender: 'grails.app.controller'
	
    /*	
    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
    'org.codehaus.groovy.grails.web.pages', //  GSP
    'org.codehaus.groovy.grails.web.sitemesh', //  layouts
    'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
    'org.codehaus.groovy.grails.web.mapping', // URL mapping
    'org.codehaus.groovy.grails.commons', // core / classloading
    'org.codehaus.groovy.grails.plugins', // plugins
    'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
    'org.springframework',
    'org.hibernate'

    warn   'org.mortbay.log'
    
    //info   'org.hibernate'
    //debug   file:'org.hibernate'*/
}





grails.views.javascript.library="jquery"

graphviz {
    dot.executable = "C:/Archivos de programa/Graphviz 2.28/bin/dot" // include full file path if not on path
}
//graphviz.dot.executable =

grails.json.legacy.builder = false

images.location = "web-app/images/previas"


grails {
    mail {
        host = "mmailmed.med.ucv.ve"
        port = 25
        username = "soporte.caibco@med.ucv.ve"
        password = "soca012"
        props = ["mail.smtp.auth":"true"]
        from="server@yourhost.com"
        //jndiName = "myMailSession"
    }
}
grails.mail.default.from="sos-hme.soporte@telemedicina.com"
