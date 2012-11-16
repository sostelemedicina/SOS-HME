package sos

import demographic.party.*
import demographic.identity.PersonName
import demographic.role.*
import hce.core.support.identification.UIDBasedID

import hce.HceService
import tablasMaestras.TipoIdentificador
import hce.core.composition.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import hce.core.common.archetyped.Locatable

/*Demograficos paciente*/
import demographic.identity.*
import java.text.SimpleDateFormat
import java.util.*
import java.text.*
/**/

import cda.*
import converters.*

import hce.core.common.directory.Folder
import hce.core.common.generic.*

import java.io.*
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import org.codehaus.groovy.grails.commons.ApplicationHolder
import javax.servlet.*
import java.net.*


/*reportes*/
import templates.TemplateManager
import tablasMaestras.Cie10Trauma

import java.util.HashMap
import net.sf.jasperreports.engine.data.JRXmlDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.*

//creacion de archivos xml
import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.*
import java.lang.Object.*
import groovy.xml.XmlUtil
import groovy.util.XmlSlurper
import javax.xml.parsers.*
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


    /*
     *@author Juan Carlos Escalante
     *
     *suite de reportes estadísticos sobre episodios clínicos*/
class ReportesController {
    
    def hceService
    def demographicService
    
    def index = { }
    
    /*
    def epi10emergencia = {
        def compos = []
        def paciente = []
        //def sexo = []
        def sexo
        //def fullDireccion = [] //se cambia por variable unica a reescribir en casa pasada del ciclo
        def fullDireccion
        //def ocupacion = [] // situacion similar al fullDireccion
        def ocupacion
        //def edad = []
        def edad
        def composition = null
        
        def inicio = params.desde
        def fin = params.hasta
        def j = 0 // variable unica para loop sobre las compositions
        def nombreDoc = "epi10emergencia"
        
       Folder domain = Folder.findByPath( session.traumaContext.domainPath )
       compos = hceService.getAllCompositionForDate(inicio, fin)
       
       def archivo = demographicService.createXML(nombreDoc) // 
        
       if(compos !=null){
        
            while(compos[j]!=null){
                composition = compos[j]
                session.traumaContext.episodioId = composition.id
                def composi = Composition.get(composition.id)
                
                // paciente de cada composition
                def patient = hceService.getPatientFromComposition(composi)
                if(patient){
                    def datos = patient.identities.find{it.purpose == 'PersonNamePatient'}
                    if(datos!=null){
                        def direccion = demographicService.findFullAddress((int)datos.direccion.id)
                        //fullDireccion << "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif + ", "+direccion
                        fullDireccion = "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif + ", "+direccion
                        //sexo << patient.sexo
                        sexo = patient.sexo
                        paciente << patient
                        //ocupacion << demographicService.getOcupacion((int)datos.ocupacion.id)
                        ocupacion = demographicService.getOcupacion((int)datos.ocupacion.id)

                        if(patient.fechaNacimiento){
                            def myFormatter = new SimpleDateFormat("yyyy")
                            //println(myFormatter.format(patient.fechaNacimiento))
                            def hoy = new Date()
                            //edad << Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                            edad = Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                        }
                        // diagnóstico asosiciado al patient en la composition
                        def elemento = hceService.getCompositionContentItemForTemplate(composi, "DIAGNOSTICO-diagnosticos")
                        if(elemento!=null){
                            def rmNode =  Locatable.findByName(elemento.name) //enlace al nodo de la composition en el modelo de referencia   
                            def rmNodeData =  rmNode.data
                            def rmNodeDataEvents = rmNodeData.events
                            def rmNodeDataEventsData = rmNodeDataEvents.data
                            //def rmNodeDataEventsDataItems = rmNodeDataEventsData.items

                            def element = rmNodeDataEvents[0].data.items
                            println("------------------paciente------------------")
                                println("fecha: ->"+composi.context.startTime.value)
                                println("cedula: ->"+patient.ids.value[0].extension)
                                println("Nombre y Apellido: ->"+patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0])
                                println("Direccion de Residencia: ->"+fullDireccion)
                                println("Ocupacion: ->"+ocupacion)
                                println("Edad: ->"+edad)
                                println("Sexo: ->"+sexo)
                                println("Diagnósticos: ->")
                                    def k=0 // variable de ciclo, usada en caso de que la composition tenga varios diagnósticos
                                    def codigos = []
                                    while(element[k]!=null){
                                        def codigo = Cie10Trauma.findByCodigo(element[k].value.definingCode.codeString)
                                        //println("Nombre Diagnostico: ->"+codigo.nombre)
                                        println("Dianostico "+(k+1)+" "+codigo.nombre)
                                        codigos << codigo.nombre
                                    k++
                                    }
                            println("--------------------------------------------")
                            def formatofecha = composi.context.startTime.value
                            def agregarNodo = demographicService.crearXmlEpi10(
                                                                                nombreDoc, 
                                                                                composi.context.startTime.toDate().format("dd/MM/yyyy"),
                                                                                //composi.context.startTime.value,
                                                                                patient.ids.value[0].extension,
                                                                                patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0],
                                                                                fullDireccion,
                                                                                ocupacion,
                                                                                Integer.toString(edad),
                                                                                sexo,
                                                                                codigos as String[]
                                                                            )
                        }
                    }
                }
            j++
            }
        
        }else{
            redirect(controller:'reportes', action:'index')
        }
    }
    */
    def epi10general = {
        def compos = []
        def paciente = []
        def sexo 
        def fullDireccion
        def edad
        def composition = null
        def fechaNace
        def generarReporte = false
        def inicio
        def fin
        
        if(params.desde && params.hasta){
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy")
            java.util.Date d =  sdf.parse(params.desde.toString())
            inicio =  d
            java.util.Date h =  sdf.parse(params.hasta.toString())
            java.util.Date dayAfter = new java.util.Date(h.getTime()+(24*60*60*1000));
            fin =  dayAfter
        }
        else{
            flash.message = "Los rangos de fecha deben seleccionarse"
            redirect(controller:'reportes', action:'index')
        }
        println("inicio:->"+inicio)
        println("fin:->"+fin)
        
        
        def j = 0 //loop
        def nombreDoc = "epi10general"
        def etnia = 0
        def niveleducativo = 0
        def generado = false
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )
        compos = hceService.getAllCompositionForDate(inicio, fin)
        def archivo = demographicService.createXML(nombreDoc)
        println("compos:->"+compos)
        if(compos != null){
            
            while(compos[j]!=null){
                composition = compos[j]
                def aux =composition.composer?.externalRef
                if(aux){
                println "COMPOSER: "+composition.composer?.externalRef.objectId.value
                
               def p = Person.createCriteria()
               
               def result = p.list{
                    ids{
                        eq("value",composition.composer.externalRef.objectId.value)
                    }
               } 
               
               if(result)
                    result.each{
                        def medicoResponsable = it.identities.find{ 
                            ident ->ident.purpose == "PersonNameUser"
                            }
                        println "USER LA identidad: "+medicoResponsable.primerNombre +" "+medicoResponsable.primerApellido    
                            
                        }
                    
                }
                
               // session.traumaContext.episodioId = composition.id
                
                //def composi = Composition.get(composition.id)
                def composi = composition
                def patient = hceService.getPatientFromComposition(composi)
                
                if(patient){
                    def datos = patient.identities.find{it.purpose == 'PersonNamePatient'}
                    if(datos != null){
                        def direccion = demographicService.findFullAddress((int)datos.direccion.id)
                        fullDireccion = "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif + ", "+direccion
                        sexo = patient.sexo
                        if(datos.etnia){
                            etnia = datos.etnia.id
                        }
                        if(datos.niveleducativo){
                            niveleducativo = datos.niveleducativo
                        }
                        
                        paciente << patient
                        
                        
                        if(patient.fechaNacimiento){
                            fechaNace = patient.fechaNacimiento
                            def myFormatter = new SimpleDateFormat("yyyy")
                            //println(myFormatter.format(patient.fechaNacimiento))
                            def hoy = new Date()
                            //edad << Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                            edad = Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                        }
                        
                        def elemento = hceService.getCompositionContentItemForTemplate(composi, "DIAGNOSTICO-diagnosticos")
                        
                        if(elemento != null){
                            generarReporte = true
                            def rmNode =  Locatable.findByName(elemento.name) //enlace al nodo de la composition en el modelo de referencia   
                            def rmNodeData =  rmNode.data
                            
                            
                            def rmNodeDataEvents = rmNodeData.events
                            
                            def element = rmNodeDataEvents[0].data.items
                            def k=0 // variable de ciclo, usada en caso de que la composition tenga varios diagnósticos
                            def codigos = []
                            def codigo
                            while(element[k]!=null){
                               // println("element[k]"+element[k].name.value)
                                
                                if(element[k].name.value!="Descripción"){ // identifico si el nodo el arquetipo de diagnostico hace referencia al diagnostico codificado o a la impresion diagnostica (Ver Arquetipo EHR-OBSERVATION.diagnosticos)
                                    codigo = Cie10Trauma.findByCodigo(element[k].value.definingCode.codeString)
                                    if(codigo!=null){
                                            codigos << codigo.nombre
                                        }else{
                                            codigo = Cie10Trauma.findBySubgrupo(element[k].value.definingCode.codeString)
                                            codigos << codigo.nombre
                                        }
                                    
                                }else{
                                    codigo = element[k].value.value
                                    codigos << codigo
                                }
                            k++
                            }
                            
                            
                           def tipoDeIdentificador = demographicService.tipoIdentificador(patient.ids.value.root)
                           def cedulaRegistro
                           if("[CI V]" == tipoDeIdentificador || "[CI E]" == tipoDeIdentificador || "[Pasaporte]" == tipoDeIdentificador){
                               cedulaRegistro = patient.ids.value[0].extension
                           }else{
                               cedulaRegistro = "Sin Cédula"
                           }
                            
                            
                            def agregarNodoXml =  demographicService.crearXmlEPI10Gen(nombreDoc,
                                                                                      cedulaRegistro,
                                                                                      patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0],
                                                                                      patient.fechaNacimiento.format("dd/MM/yyyy"), 
                                                                                      fullDireccion,
                                                                                      sexo,
                                                                                      Long.toString(etnia),
                                                                                      Integer.toString(niveleducativo),
                                                                                      Integer.toString(edad), 
                                                                                      codigos as String[]
                                                                                      ) 
                        }else{
                            generarReporte = true
                            def k=0 // variable de ciclo, usada en caso de que la composition tenga varios diagnósticos
                            def codigos = []
                            codigos << "Sin Diagnóstico"
                            def agregarNodoXml =  demographicService.crearXmlEPI10Gen(nombreDoc,
                                                                                      patient.ids.value[0].extension,
                                                                                      patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0],
                                                                                      patient.fechaNacimiento.format("dd/MM/yyyy"), 
                                                                                      fullDireccion,
                                                                                      sexo,
                                                                                      Long.toString(etnia),
                                                                                      Integer.toString(niveleducativo),
                                                                                      Integer.toString(edad), 
                                                                                      codigos as String[]
                                                                                      )
                        }
                    }
                }
            j++    
            }
                         
                 if(generarReporte==true){
                     def FileName = []
                     
                     //PAGINA 1
                     FileName << ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/reportes/epi10consultageneralNuevoFormato.jrxml")
                     
                     //PAGINA 2
                     FileName << ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/reportes/epi10consultaGeneralP2.jrxml")
                     
                     //def outFile = "C:/Users/juan/Desktop/sosDeve/sos-hme/web-app/data/reports/reportes/epi10consultageneral.pdf"
                     def outFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/documentos/epi10consultageneral.pdf")
                     //def xmlFile = "C:/Users/juan/Desktop/sosDeve/sos-hme/web-app/data/reports/source/epi10general.xml"
                     def xmlFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/epi10general.xml")
                     def record = "/pacientes/paciente"
                     generado = reportsOutput(FileName as String[], outFile, xmlFile, record)
                     if(generado){
                        redirect(controller:'reportes', action:'index', params:[creado10general:true,tipo:outFile])
                        }else{
                            redirect(controller:'reportes', action:'index', params:[creado10general:false])
                        }
                     }else{
                         redirect(controller:'reportes', action:'index', params:[creado10general:false])
                     }  
            }else{
                redirect(controller:'reportes', action:'index', params:[creado10general:false])
            }
    }
    
    def epi13morbilidad ={
        def compos = []
        def paciente = []
        def sexo 
        def fullDireccion
        def edad
        def composition = null
        def fechaNace
        def desde
        def hasta
        def generarReporte = false
        
        
        if(params.desde && params.hasta){
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy")
            java.util.Date d =  sdf.parse(params.desde.toString())
            desde =  d
            java.util.Date h =  sdf.parse(params.hasta.toString())
            java.util.Date dayAfter = new java.util.Date(h.getTime()+(24*60*60*1000));
            hasta =  dayAfter
        }else{
            flash.message = "Los rangos de fecha deben seleccionarse"
            redirect(controller:'reportes', action:'index')
        }
        
        def j = 0 //loop
        def nombreDoc = "epi13morbilidad"
        def generado = false
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )
        compos = hceService.getAllCompositionForDate(desde, hasta)
        def archivo = demographicService.createXML(nombreDoc)
        
        if(compos != null){
            while(compos[j]!=null){
                composition = compos[j]
                session.traumaContext.episodioId = composition.id
                def composi = Composition.get(composition.id)
                def patient = hceService.getPatientFromComposition(composi)
                if(patient){
                    def datos = patient.identities.find{it.purpose == 'PersonNamePatient'}
                    if(datos != null){
                        def direccion = demographicService.tokenDireccion((int)datos.direccion.id)
                        StringTokenizer st = new StringTokenizer(direccion,"#",false)
                        def parroquia = st.nextToken()
                        def municipio = st.nextToken()
                        def estado = st.nextToken()    
                        fullDireccion = "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif
                        sexo = patient.sexo
                        //paciente << patient
                        if(patient.fechaNacimiento){
                            fechaNace = patient.fechaNacimiento
                            def myFormatter = new SimpleDateFormat("yyyy")
                            //println(myFormatter.format(patient.fechaNacimiento))
                            def hoy = new Date()
                            edad = Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                            def elemento = hceService.getCompositionContentItemForTemplate(composi, "DIAGNOSTICO-diagnosticos")
                            
                            println("fechaInicioComposi:->"+composi.context.startTime.value)
                            
                            if(elemento != null){
                                def rmNode =  Locatable.findByName(elemento.name) //enlace al nodo de la composition en el modelo de referencia   
                                def rmNodeData =  rmNode.data
                                def rmNodeDataEvents = rmNodeData.events

                                def element = rmNodeDataEvents[0].data.items
                                def k=0 // variable de ciclo, usada en caso de que la composition tenga varios diagnósticos
                                def codigos = []
                                while(element[k]!=null){
                                    
                                    
                                    if(element[k].name.value!="Descripción"){ // identifico si el nodo el arquetipo de diagnostico hace referencia al diagnostico codificado o a la impresion diagnostica (Ver Arquetipo EHR-OBSERVATION.diagnosticos)
                                        def codigo = Cie10Trauma.findByCodigo(element[k].value.definingCode.codeString)
                                    //println("Dianostico "+(k+1)+" "+codigo.nombre)
                                        def notificable
                                        if(codigo!=null){
                                            notificable = demographicService.verificaEnfermedadNotificable(codigo.subgrupo,codigo.codigo)
                                        }else{
                                            codigo = Cie10Trauma.findBySubgrupo(element[k].value.definingCode.codeString)
                                            notificable = demographicService.verificaEnfermedadNotificable(codigo.subgrupo,codigo.codigo)
                                        }
                                        if(notificable==true){
                                            generarReporte = true
                                            codigos << codigo.nombre
                                            paciente << patient
                                            
                                           def tipoDeIdentificador = demographicService.tipoIdentificador(patient.ids.value.root)
                                           def cedulaRegistro
                                           if("[CI V]" == tipoDeIdentificador || "[CI E]" == tipoDeIdentificador || "[Pasaporte]" == tipoDeIdentificador){
                                               cedulaRegistro = patient.ids.value[0].extension
                                           }else{
                                               cedulaRegistro = "Sin Cédula"
                                           }
                                            
                                            def agregarNodoXml =  demographicService.crearXmlEPI13Morbilidad(nombreDoc,
                                                                                      cedulaRegistro,
                                                                                      patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0],
                                                                                      patient.fechaNacimiento.format("dd/MM/yyyy"), 
                                                                                      fullDireccion,
                                                                                      parroquia,
                                                                                      municipio,
                                                                                      estado,
                                                                                      composi.context.startTime.value,
                                                                                      sexo,
                                                                                      codigos as String[],
                                                                                      params.desde,
                                                                                      params.hasta
                                                                                      )
                                        }
                                    }
                                k++
                                }
                            }
                        }
                }
            
                
            }
          j++      
        }
        
        }
        
         if(generarReporte == true){
            def FileName = []
             FileName << ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/reportes/epi13morbilidad.jrxml")
             def outFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/documentos/epi13morbilidad.pdf")
             def xmlFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/epi13morbilidad.xml")
             def record = "/pacientes/paciente"
             generado = reportsOutput(FileName as String[], outFile, xmlFile, record)
             if(generado){
                redirect(controller:'reportes', action:'index', params:[creado13morbilidad:true,tipo:outFile])
                }
         }else{
                redirect(controller:'reportes', action:'index', params:[creado13morbilidad:false])
            }   
        
    }
    
    def epi12morbilidad ={
        def nombreDoc = "epi12morbilidad"
        def archivo = demographicService.createXML(nombreDoc)
        
        def gruposNotificablesReporte = ["A00","A08-A09","A06","B15","A15-A19","J10-J11",
                                  "A50","Z21","B20-B24","A37","B26","A33","A34","A35","A36",
                                  "B05","B06","A90","A91","A95","B50-B54","B55","B57","A82",
                                  "A27","A87","G00","B01","B16","B17","B19","J12-J18",
                                  "T60","A82","R50","J00-J06yJ20-J22","Y40-Y57","Y58-Y59"]
        
        def codigosNotificables = ["A01.0","A92.2","A39.0","A39.9","B17.1-B18.2","G82.0","A96.8"]
        
        def cicloGrupo = 0
        def cicloCodigo = 0
        while (cicloGrupo <= gruposNotificablesReporte.size()-1){
            demographicService.agregaNodoNotificable(gruposNotificablesReporte[cicloGrupo],nombreDoc)
            cicloGrupo++
        }
        while (cicloCodigo <= codigosNotificables.size()-1){
            demographicService.agregaNodoNotificable(codigosNotificables[cicloCodigo],nombreDoc)
            cicloCodigo++
        }
        
        def compos = []
        def paciente = []
        def sexo
        def edad
        def composition = null
        def fechaNace
        def desde
        def hasta
        def generarReporte = false
        
        if(params.desde && params.hasta){
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy")
            java.util.Date d =  sdf.parse(params.desde.toString())
            desde =  d
            java.util.Date h =  sdf.parse(params.hasta.toString())
            java.util.Date dayAfter = new java.util.Date(h.getTime()+(24*60*60*1000));
            hasta =  dayAfter
        }else{
            flash.message = "Los rangos de fecha deben seleccionarse"
            redirect(controller:'reportes', action:'index')
        }
        
        def j = 0 //loop
        def generado = false
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )
        compos = hceService.getAllCompositionForDate(desde, hasta)
        
        
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+nombreDoc+".xml")
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance()
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder()
        Document doc = docBuilder.parse(ruta)
        Node pacientes = doc.getFirstChild()

        StringTokenizer stsi = new StringTokenizer(params.desde,"-",false)
        def semanaInicioDia = stsi.nextToken()
        def semanaInicioMes = stsi.nextToken()

        StringTokenizer stsf = new StringTokenizer(params.hasta,"-",false)
        def semanaFinDia = stsf.nextToken()
        def semanaFinMes = stsf.nextToken()
        String semanaRegistro = "Del "+semanaInicioDia+"/"+semanaInicioMes+" al "+semanaFinDia+"/"+semanaFinMes
        println("semana de registro:->"+semanaRegistro)
        Node semanaxml = doc.createElement("semana");
        semanaxml.setTextContent(semanaRegistro)
        pacientes.appendChild(semanaxml)

        Node anioxml = doc.createElement("anioregistro");
        anioxml.setTextContent(stsi.nextToken())
        pacientes.appendChild(anioxml)
        
        def output = XmlUtil.serialize(pacientes)
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+nombreDoc+".xml"))
        writer.write(output)
        
        
        
        if(compos != null){
            while(compos[j]!=null){
                composition = compos[j]
                session.traumaContext.episodioId = composition.id
                def composi = Composition.get(composition.id)
                def patient = hceService.getPatientFromComposition(composi)
                if(patient){
                    def datos = patient.identities.find{it.purpose == 'PersonNamePatient'}
                    //println("datos->"+datos)
                    if(datos != null){
                        sexo = patient.sexo
                        if(patient.fechaNacimiento){
                            fechaNace = patient.fechaNacimiento
                            def myFormatter = new SimpleDateFormat("yyyy")
                            def hoy = new Date()
                            edad = Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                            def elemento = hceService.getCompositionContentItemForTemplate(composi, "DIAGNOSTICO-diagnosticos")
                            
                            if(elemento != null){
                               def rmNode =  Locatable.findByName(elemento.name) //enlace al nodo de la composition en el modelo de referencia   
                                def rmNodeData =  rmNode.data
                                def rmNodeDataEvents = rmNodeData.events

                                def element = rmNodeDataEvents[0].data.items
                                def k=0 // variable de ciclo, usada en caso de que la composition tenga varios diagnósticos
                                while(element[k]!=null){
                                    def notificable
                                    
                                        if(element[k].name.value!="Descripción"){ // identifico si el nodo el arquetipo de diagnostico hace referencia al diagnostico codificado o a la impresion diagnostica (Ver Arquetipo EHR-OBSERVATION.diagnosticos)
                                            def codigo = Cie10Trauma.findByCodigo(element[k].value.definingCode.codeString)
                                            if(codigo!=null){
                                                notificable = demographicService.verificaEnfermedadNotificable(codigo.subgrupo,codigo.codigo)
                                            }else{
                                                codigo = Cie10Trauma.findBySubgrupo(element[k].value.definingCode.codeString)
                                                notificable = demographicService.verificaEnfermedadNotificable(codigo.subgrupo,codigo.codigo)
                                            }
                                            if(notificable==true){

                                                paciente << patient
                                                def agregarNodoXml =  demographicService.crearXmlEPI12Morbilidad(nombreDoc, codigo.codigo, codigo.subgrupo, Integer.toString(edad), sexo)
                                                if(agregarNodoXml==true){
                                                    generarReporte = true
                                                }
                                            }
                                        }
                                k++
                                }
                            }
                            
                        }
                }
            
                
            }
          j++      
        }
        
        }
        
        if(generarReporte == true){
            def FileName = []
             FileName << ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/reportes/epi12Morbilidad.jrxml")
             FileName << ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/reportes/epi12MorbilidadP2.jrxml")
             def outFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/documentos/epi12morbilidad.pdf")
             def xmlFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/epi12morbilidad.xml")
             def record = "/pacientes"
             generado = reportsOutput(FileName as String[], outFile, xmlFile, record)
             if(generado){
                redirect(controller:'reportes', action:'index', params:[creado12morbilidad:true,tipo:outFile])
                }
         }else{
                redirect(controller:'reportes', action:'index', params:[creado12morbilidad:false])
            }
            
    }
    
    
    
    
    
    public static boolean reportsOutput(String[] reportFileName, String outFileName, String xmlFileName, String recordPath){
       JRXmlDataSource jrxmlds = new JRXmlDataSource(xmlFileName,recordPath)
       HashMap hm = new HashMap()
       //List jpList = new ArrayList()
       JasperReport jasperReport
       List<JasperPrint> jpList = new ArrayList<JasperPrint>();
       String filexml = outFileName;
       try
          {
              def i =0
              def j = reportFileName.size()
              for(i=0;i<=j-1;i++){
                  //println("ciclo:->"+i)
                  //println("path:->"+reportFileName[i])
                  jasperReport = JasperCompileManager.compileReport(reportFileName[i])
                  //JasperPrint reporte = JasperFillManager.fillReport(reportFileName[i],new HashMap(),new JRXmlDataSource(xmlFileName,recordPath))
                  
                  //JasperPrint reporte = JasperFillManager.fillReport(reportFileName[i],hm,jrxmlds)
                  JasperPrint reporte = JasperFillManager.fillReport(jasperReport,hm,new JRXmlDataSource(xmlFileName,recordPath))
                  
                  jpList.add(reporte)
              }
             
            JRPdfExporter exporter = new JRPdfExporter();
            //exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,outFileName);
            exporter.setParameter(net.sf.jasperreports.engine.export.JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
            
            OutputStream output = new FileOutputStream(new File(outFileName));
            exporter.setParameter(net.sf.jasperreports.engine.export.JRPdfExporterParameter.OUTPUT_STREAM, output);
            //response.setContentLength(output.toByteArray().length)
            
            
            exporter.exportReport()
            
            System.out.println("Created file: " + outFileName)
            return true
          }
          catch (JRException e){
              e.printStackTrace();
              System.exit(1);
              return false
          }
          catch (Exception e){
              e.printStackTrace();
              System.exit(1);
              return false
          } 
    }
    
    def descargar={
        String filename = params.archivo
        File file = new File(filename);
        response.setContentType(new javax.activation.MimetypesFileTypeMap().getContentType(file));
        response.setContentLength((int)file.length());
        response.setHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        InputStream is = new FileInputStream(file);
        org.springframework.util.FileCopyUtils.copy(is, response.getOutputStream());
        return null
    }
    
}
