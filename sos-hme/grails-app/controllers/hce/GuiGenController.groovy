/**
import hce.core.composition.content.ContentItem;
 * Controller para pruebas de generacion de vistas.
 */
package hce

import se.acode.openehr.parser.*
import org.openehr.am.archetype.Archetype

import hce.HceService

import archetype_repository.ArchetypeManager
import hce.ArchetypeService
import templates.TemplateManager
import templates.tom.* // Template

import events.*

import com.thoughtworks.xstream.XStream

import hce.core.composition.*

import hce.core.common.archetyped.Locatable

//TEST BINDER
import binding.BindingAOMRM
import util.GestionImagen
import org.codehaus.groovy.grails.commons.GrailsApplication;

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 */
class GuiGenController {

    def archetypeService
    def hceService

    /**
     * Devuelve un Map con los templates configurados para el dominio actual.
     * 
     * this.getDomainTemplates()
     * 
     * @return Map
     */
    private Map getDomainTemplates()
    {
        //def routes = grailsApplication.config.domain.split('/') // [hce, trauma]
        //def domainTemplates = grailsApplication.config.templates
        //routes.each{
        //    domainTemplates = domainTemplates[it]
        //}
        
        // =============================================================
        // Nuevo: para devolver los templates del dominio seleccionado
        def domain = session.traumaContext.domainPath
        def domainTemplates = grailsApplication.config.templates2."$domain"
        
        // =============================================================
        
        return domainTemplates
    }


    /**
     * Devuelve todos los prefijos de identificadores de templates del domino actual.
     * @return
     */
    private List getSections()
    {
        def sections = []
        this.getDomainTemplates().keySet().each {
            
            sections << it
        }
        
        return sections
    }
    
    /**
     * Obtiene las subsecciones de una seccion dada.
     * 
     * this.getSubsections('EVALUACION_PRIMARIA')
     * 
     * @param section es el prefijo del id de un template
     * @return List
     */
    private List getSubsections( String section )
    {
        // Lista de ids de templates
        def subsections = []

       this.getDomainTemplates()."$section".each { subsection ->

           subsections << section + "-" + subsection
        }
        
        return subsections
    }
    
    def index = {
        redirect(action:'listarTemplates')
        return
    }
    
    /*
    // Esta accion es de prueba, la lista de templates para completar se hace desde trauma.registroClinico,
    // que carga los templates que se llenan (no las pruebas) y su orden para armar la pantalla 5.1.
    def listarTemplates = {

        // FIXME: Ahora listo los templates asi nomas, puede haber subdirectorios.
        // FIXME: esto a config
        def path = "templates/hce/trauma" // FIXME: sacar dominio actual de la config
        def p = ~/.*\.xml/
        def templatesNames = []
        new File( path ).eachFileMatch(p)
        { f ->
           templatesNames << f.name - ".xml" // Solo el id, no necesito la extension del archivo
        }
         
        render(view:"listarTemplates", model:[templateNames: templatesNames])
    }
    */
    
    // in: templateId
    def generarTemplate = {
       
       EventManager.getInstance().handle("pre_gui_gen", params)

       /*
       // Test para prevenir cacheo
       //NO HACE NADA....
       response.setHeader("Cache-Control", "no-cache, must-revalidate")
       //response.setHeader("Cache-Control", "no-Store")
       response.setHeader("Pragma", "no-cache")
       response.setHeader("Expires", "0")
       */

        // DEBE haber un episodio seleccionado para poder asociar el registro clinico.
        if (!session.traumaContext?.episodioId)
        {
            flash.message = 'trauma.list.error.noEpisodeSelected'
            redirect(controller:'records', action:'list')
            return
        }
    
        // TODO: verificar que el estado del registro es 'incomplete', de lo contrario no puedo editarlo.
    
        def templateId = params.templateId // es el nombre del archivo

        // Model: Paciente del episodio seleccionado
        def composition = Composition.get( session.traumaContext.episodioId )

        // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
        def patient = hceService.getPatientFromComposition( composition )

        // FIXME: verificar si ya se hizo registro para este template, y si se hizo ir a show.
        // def item = hceService.getCompositionContentItemForTemplate(composition, attrs.templateId)
        // def ContentItem getCompositionContentItemForTemplate( Composition composition, String templateId )
        
        def item = hceService.getCompositionContentItemForTemplate(composition, templateId)
        if (item)
        {
            //flash.message = 'trauma.list.error.registryAlreadyDone'
            redirect( controller:'guiGen', action:'generarShow', id: item.id,
                      params: ['flash.message': 'trauma.list.error.registryAlreadyDone'] )
            return
        }

        def sections = this.getSections()
        def subsections = this.getSubsections(templateId.split("-")[0]) // this.getSubsections('EVALUACION_PRIMARIA')
        
        // Genera GUI solo si el registro esta abierto
        if ( hceService.isIncompleteComposition( composition ) )
        {
            //println "subSectionPrefix: " + subSectionPrefix
            //println "subsections: " + subsections
    
            Template template = TemplateManager.getInstance().getTemplate( templateId )
           
            def fullUri = grailsAttributes.getViewUri('../hce/'+templateId, request)
           
            //println fullUri
            //println grailsAttributes.pagesTemplateEngine.getResourceForUri(fullUri)
    
            def resource = grailsAttributes.pagesTemplateEngine.getResourceForUri(fullUri)
            if ( resource && resource.file && resource.exists() )
            {
               println 'vista estatica'
               
               EventManager.getInstance().handle("post_gui_gen", params)
               
               render( view: '../hce/'+templateId,
                       model: [
                               patient: patient,
                               template: template,
                               sections: sections,
                               subsections: subsections,
                               episodeId: session.traumaContext?.episodioId,
                               userId: session.traumaContext.userId,
                               allSubsections: this.getDomainTemplates()
                              ] )
               return
            }
            else
            {
               println 'vista dinamica'
    
               EventManager.getInstance().handle("post_gui_gen", params)
    
               // OK
               //XStream xstream = new XStream()
               //println xstream.toXML(template) + "\n\n"
               //println "templateID: " + template.id
               
               // TODO: buscar si existe la vista para el template, y si no existe ir a la generacion dinamica.
               
               return [archetypeService: archetypeService,
                       template: template,
                       patient: patient,
                       sections: sections,
                       subsections: subsections,
                       episodeId: session.traumaContext?.episodioId,
                       userId: session.traumaContext.userId,
                       allSubsections:  this.getDomainTemplates()
                      ]
            }
        }
        else
        {
            flash.message = "registroClinico.warning.noHayRegistroParaLaSeccion"
            redirect( controller: 'records', action: 'show', id: session.traumaContext?.episodioId)
            return
        }
        
    } // generateTemplate
    
    /**
     * 
     */
    def listarArquetipos = {
   
       println "===" + archetypeService + "==="
            
       // Test archetype manager
       //def manager = ArchetypeManager.getInstance()
       //manager.loadAll()
       //println manager
       // /Test archetype manager
      
       // FIXME: cambie la estructura de arquetipos a la que usa openEHR, asi que habria que listar subdirectorios.
       //def path = "archetypes/ehr/entry/evaluation"
       //def path = "archetypes/ehr/entry/action"
       //def path = "archetypes/ehr/cluster"
       def path = "archetypes/ehr/section"
       def p = ~/.*\.adl/
       def arqsNames = []
       new File( path ).eachFileMatch(p)
       { f ->
          arqsNames << f.name - ".adl" // Solo el id, no necesito la extension del archivo
       }
        
       render(view:"listarArquetipos", model:[arqNames:arqsNames])
    }
    
    /**
     * Prueba para poder ver los arquetipos que estan cargados en cache.
     */
    def loadedArchetypes = {
       
       def manager = ArchetypeManager.getInstance()
       render( manager.toString() )
    }
    
    /**
     * in: archetypeId
     */
    def generar = {
           
       //println "===" + archetypeService + "==="
   
       // FIXME: ahora es con arquetipos para probar, pero
       //        deberia ser con templates!
       def archetypeId = params.archetypeId
       
       log.debug("generar: " + params.archetypeId)
       
       // Test archetype manager
       def manager = ArchetypeManager.getInstance()
       Archetype archetype = manager.getArchetype( archetypeId )
       // /Test archetype manager
       
       
       render(view:"generar", model:[archetype:archetype, archetypeService:archetypeService])
    }
    
    def save = {
        
        // DEBE haber un episodio seleccionado para poder asociar el registro clinico.
        // Se necesita para mostrar el layout con el resumen del episodio arriba.
        if (!session.traumaContext?.episodioId)
        {
            flash.message = 'trauma.list.error.noEpisodeSelected'
            redirect(controller:'records', action:'list')
            return
        }


            // Episodio seleccionado para el cual se está registrando
            Composition comp = Composition.get(session.traumaContext.episodioId)
            def patient = hceService.getPatientFromComposition( comp )
            // TODO: verificar que el estado del registro es 'incomplete', de lo contrario no puedo editarlo.


            EventManager.getInstance().handle("pre_save", [composition:comp])


            // Verifico si el registro para el template de entrada ya se hizo,
            // en ese caso se está haciendo un edit de un registro previo,
            // tengo que buscar el registro previo, sacarlo del episodio y
            // meter el nuevo registro.
            // TODO: Aquí es donde entra en juego el tema del versionado, si
            //       ya pasaron 24 hs desde el inicio del episodio y se quiere
            //       editar, se deberia versionar, guardando la versión actual
            //       del registro y guardando luego la nueva como nueva version.

            def item = hceService.getCompositionContentItemForTemplate( comp, params.templateId )
        if (params.CampoVacio!="0")
        {
            if (item)
            {
                // Si es el save de un edit, borra el registro anterior y sustituye por el nuevo.
                if (params.mode == 'edit')
                {
                    println "Esta seccion o entrada ya estaba registrada, se procede a eliminar el registro actual y sustituirlo por el nuevo ingreso..."

                    //XStream xstream = new XStream()
                    //println "COMPONENT ANTES"
                    //println xstream.toXML(comp)

                    comp.removeFromContent(item)

                    // FIXME: borra la raiz pero no borra los subitems, por ejemplo si es section, la borra pero no sus entries.
                    // Se podria hacer que las entries pertenezcan a las sections, asi se elimina en cascada.
                    // Tambien hay que ver que se eliminen los objetos mas bajo nivel.
                    /**
                     * http://grails.org/doc/latest/ref/Domain%20Classes/delete.html
                     * By default Grails will use transactional write behind to perform the delete,
                     * if you want to perform the delete in place then you can use the 'flush' argument.
                     */
                    item.delete(flush:true) // FIXME: delete no es en cascada si no se pone belongsTo en las clases hijas.

                    //println "COMPONENT DESPUES"
                    //println xstream.toXML(comp)
                }
                else // Si no es save de edit, esta tratando de salvar de nuevo algo que ya habia salvado.
                {
                    println "Registro ya realizado, se va a show para y no se vuelve a guardar"
                    // Muestro el registro ya ingresado previamente
                    //flash.message = 'trauma.list.error.registryAlreadyDone'
                    redirect( controller:'guiGen', action:'generarShow', id: item.id,
                            params: ['flash.message': 'trauma.list.error.registryAlreadyDone'] )
                    return
                }
            }

            // Deberian venir todas las paths definidas en el template que use
            // para crear la vista, y complementarse con las paths que se definieron
            // en el template que no se mostraran en la vista, por ejemplo, para
            // casos de valores asumidos, donde el usuario no puede ingresar otra
            // cosa que lo que dice el template.
            // Los valores asumidos del template pueden derivarse de los arquetipos,
            // pero seran muy pocos arquetipos los que definan estos valores, porque
            // deben ser de uso general.

            // TODO: ver que transformaciones hay definidas para los paths->data
            // submiteados y transformar los datos que tengan transformaciones
            // definidas y darle a Lea todo listo para guardar

            // TODO: las transformaciones de unidades deberian hacerse por el Measurement Service

            // Veo si vienen archivos
            def files = request.getFileMap()

            //**************Modififcacion rafael para cargas de Imagenes****************//
            // Se necesita el id del arquetipo para identificar con cual se esta trabajando
            def Arquetype = params.templateId
            def ArquetypeNodo = ""
            def ArquetypeNode = ""

            /******** Variables para el estandar DICOM*************/
            def PacientName = "Desconocido"
            def pacientId = "0001"
            def Pacientbirth = ""
            if (patient) {
                PacientName = patient.toString().trim()
                pacientId = patient.id.toString()
                Pacientbirth = patient.fechaNacimiento.format("dd-MM-YYYY")
            }

            def StudyId = session.traumaContext?.episodioId
            def StudyDate = comp.context.startTime.toDate().format("dd-MM-YYYY")
            def Series
            def InstanceN
            def folderOriginal = grailsApplication.config.images.domain.location.toString().replace("{domain}",session.traumaContext.domainPath.split("\\.")[1]) + StudyId
            def folderTrans = grailsApplication.config.trans.domain.location.toString().replace("{domain}",session.traumaContext.domainPath.split("\\.")[1]) + StudyId
            def folderDicom = grailsApplication.config.dicom.domain.location.toString().replace("{domain}",session.traumaContext.domainPath.split("\\.")[1]) + StudyId
            /******************************************************/
            // Si se cumple que se esta trabajando con los arquetipos de dermatologia.
            if ((Arquetype.compareTo("EXAMEN-lesiones_elementales")) == 0 || (Arquetype.compareTo("EXAMEN-lesiones_especiales")) == 0) {
                if (Arquetype.compareTo("EXAMEN-lesiones_elementales")==0) {
                    Series="1"
                    ArquetypeNodo = "openEHR-EHR-EVALUATION.lesiones_elementales.v1/data[at0001]/items[at0002]/items[at0134]/items[at0135]/items[at0137]/value"
                    ArquetypeNode = "openEHR-EHR-EVALUATION.lesiones_elementales.v1/data[at0001]/items[at0002]/items[at0134]/items[at0135]/items[at0136]/value"
                }else {
                    Series="2"
                    ArquetypeNodo = "openEHR-EHR-EVALUATION.lesiones_especiales.v1/data[at0001]/items[at0002]/items[at0014]/items[at0015]/items[at0017]/value"
                    ArquetypeNode = "openEHR-EHR-EVALUATION.lesiones_especiales.v1/data[at0001]/items[at0002]/items[at0014]/items[at0015]/items[at0016]/value"
                }


                //def image_count = params.image_count
                def image_count = files.size()
                def i=0
                // para obtener las fechas de cuando fueron creadas las imagenes
                def imagesDates = params.getProperty(ArquetypeNodo)
                def imagesDefaultValues = params.getProperty(ArquetypeNode)
                if (image_count == 1){
                    def f = request.getFile('image-file-0')
                    InstanceN="1"
                    if (f && !f.empty) {
                        // Se crea una carpeta del estudio actual, dentro de la carpeta de images para el dominio Dermatologia
                        new File(folderOriginal).mkdirs()
                        new File(folderTrans).mkdirs()
                        new File(folderDicom).mkdirs()

                        // Se crea el archivo imagen en la carpeta del estudio actual
                        f.transferTo(new File(folderOriginal + File.separator + f.getOriginalFilename()))

                        // Se realiza las transformaciones a la imagen creada recientemente
                        GestionImagen.ChangeSize(folderOriginal + File.separator + f.getOriginalFilename(),folderTrans + File.separator + f.getOriginalFilename(),600,600)
                        GestionImagen.CambiarADicom(folderTrans + File.separator + f.getOriginalFilename(),folderDicom + File.separatorChar + f.getOriginalFilename(),PacientName.toString(),pacientId,String.valueOf(StudyId),Series,InstanceN)
                        GestionImagen.CambiarDicomTagImageDate(folderDicom + File.separator + f.getOriginalFilename(), imagesDates)
                        GestionImagen.CambiarDicomTagStudyDate(folderDicom + File.separator + f.getOriginalFilename(), StudyDate)
                        GestionImagen.CambiarDicomTagPatientBirthDate(folderDicom + File.separator + f.getOriginalFilename(), Pacientbirth)

                        imagesDefaultValues = grailsApplication.config.images.domain.logi.location.toString().replace("{domain}",session.traumaContext.domainPath.split("\\.")[1]) + StudyId + File.separator + f.getOriginalFilename()
                    }
                }else {
                    // Se crea una carpeta del estudio actual, dentro de la carpeta de images para el dominio Dermatologia
                    new File(folderOriginal).mkdirs()
                    new File(folderTrans).mkdirs()
                    new File(folderDicom).mkdirs()

                    for(i=0; i<image_count;i++) {
                        InstanceN = String.valueOf(i+1)
                        //guardar todos los archivitos
                        def f = request.getFile('image-file-'+i)

                        if (f && !f.empty) {
                            //println '*****************************************************************\n********************************************************************'
                            // Se crea el archivo imagen en la carpeta del estudio actual
                            f.transferTo(new File(folderOriginal + File.separator + f.getOriginalFilename()))

                            // Se realiza las transformaciones a la imagen creada recientemente
                            GestionImagen.ChangeSize(folderOriginal + File.separator + f.getOriginalFilename(),folderTrans + File.separator + f.getOriginalFilename(),600,600)
                            GestionImagen.CambiarADicom(folderTrans + File.separator + f.getOriginalFilename(),folderDicom + File.separator + f.getOriginalFilename(),PacientName.toString(),pacientId,String.valueOf(StudyId),Series,InstanceN)
                            GestionImagen.CambiarDicomTagImageDate(folderDicom + File.separator + f.getOriginalFilename(), imagesDates[i])
                            GestionImagen.CambiarDicomTagStudyDate(folderDicom + File.separator + f.getOriginalFilename(), StudyDate)
                            GestionImagen.CambiarDicomTagPatientBirthDate(folderDicom + File.separator + f.getOriginalFilename(), Pacientbirth)

                            imagesDefaultValues[i] = grailsApplication.config.images.domain.logi.location.toString().replace("{domain}",session.traumaContext.domainPath.split("\\.")[1]) + StudyId + File.separator + f.getOriginalFilename()
                            //println params.getProperty("openEHR-EHR-EVALUATION.lesiones.v1/data[at0001]/items[at0002]/items[at0131]/items[at0152]/items[at0151]/value")
                        }
                    }
                }
                params.setProperty(ArquetypeNode,imagesDefaultValues)
            }
            //****************Modififcacion rafael para cargas de Imagenes**************

            //println "VIENEN ARCHIVOS: " + files
            //println "+++++++++++++++++++++++++++++++++++++++++++++++++++"


            def template = TemplateManager.getInstance().getTemplate( params.templateId )
            def transformations = template.getTransformations()

            println "+++++++++++++++++++++++++++++++++++++++++++++++++++"
            println "Hay " + transformations.size() + " transformaciones"

            transformations.each { transform ->

                //println "Transform: " + transform.operation + " " + transform.operand

                // archetypeId+path
                if ( params.containsKey( transform.owner.owner.id + transform.path) )
                {
                    // Solo puede transformar un number
                    // Number no tiene funcion para pasar de un string a number
                    // Uso Float para todo // FIXME: puede dar problemas si el valor no es float en el arquetipo.

                    try // por si el numero no viene tengo un error, deberia meter un error en errors2 del binder si es que el valor era obligatorio: DE ESO SE ENCARGA EL BINDER!
                    {
                        def oldValue = Float.valueOf( params.(transform.owner.owner.id+transform.path) )
                        def newValue = transform.process( oldValue )

                        // FIXME: en la desconversion para el show, si ahora ingreso 10 se guarda 0.16666666666,
                        //        en la inversa, 0.1666666666 da 9.99996, asi que en la desconversion
                        //        deberia intentar hacer redondeo para mostrar.
                        //        Preguntar en la lista de OpenEHR si alguien supo resolver esto de una forma que no sea
                        //        cambiando el tipo del Quantity por 2 campos individuales en un cluster.

                        println "Old: " + oldValue + ", New: " + newValue

                        params.(transform.path) = newValue
                    }
                    catch (Exception e)
                    {
                        println "WARNING: Se intenta transformar un valor con mal formato: '" + params.(transform.owner.owner.id+transform.path) + "'"
                    }
                }
                else
                {
                    println "Transform: Params no tiene la path: " + transform.owner.owner.id + transform.path
                }
            }
            println "+++++++++++++++++++++++++++++++++++++++++++++++++++"

            //render(view:'showParams')

            // params es GrailsParameterMap y bind espera LinkedHashMap
            def pathValue = [:]

            params.each{
                //println "   (=) " + params[it.key].getClass().getSimpleName()
                //println "   (+) " + it.value.getClass().getSimpleName()
                //println "    it: " + it
                // no deja pasar los parametros que se separan con puntos, los "railsified params".
                if (it.value instanceof String || it.value.getClass().isArray())
                    pathValue[it.key] = it.value
            }
            //println "   -=-=- PATH VALUE: " + pathValue


            // Pongo archivos (puede no venir ninguno)
            pathValue += files


            /*
                println "*****************"
                println "Path value: " + pathValue
                println "*****************"
            */
            //Ejemplo del valor de pathValue
            /******************
             Path value: [templateId:MOTIVO-motivo_de_consulta, openEHR-EHR-OBSERVATION.motiv
             o_de_consulta.v1/data[at0001]/events[at0002]/data[at0003]/items[at0004]/items[at
             0005]/value:Hola, doit:Guardar, action:save, controller:guiGen]
             ******************/

            // Bind
            //BindingAOMRM bindingAOMRM = new BindingAOMRM()
            BindingAOMRM bindingAOMRM = new BindingAOMRM(session)
            def rmobj = bindingAOMRM.bind(pathValue, params.templateId)

            //println "Params: " + params.toString() + "\n"
            //println "Path value: " + pathValue + "\n"

            XStream xstream = new XStream()
            //println xstream.toXML(rmobj)

            if (rmobj)
            {
                //if (!rmobj.save() || bindingAOMRM.getErrors().hasErrors() || bindingAOMRM.hasErrors() )
                // flush para que el save se haga de inmediato
                // FIXME: bindingAOMRM.getErrors() no se esta usando
                if (!rmobj.save(flush: true) || bindingAOMRM.getErrors().hasErrors() || bindingAOMRM.hasErrors() )
                {
                    if (bindingAOMRM.getErrors().hasErrors())
                        println "Hay errors en los errors del binder: " + bindingAOMRM.getErrors()
                    if (bindingAOMRM.hasErrors())
                        println "Hay errors en el binder (bandera errors en true)"

                    println "ERROR AL SALVAR: ---> " + rmobj.errors
                    println "TheErrors: " + bindingAOMRM.getErrors() + "\n\n"
                    // TIENE QUE VOLVER Al CREATE con los errores y valores ya ingresados.
                    // No puedo hacer redirect porque pierdo los valores y los errores.

                    /*
                    // Subsections de la section seleccionada
                    def subsections = []
                    def subSectionPrefix = params.templateId.split("-")[0]
                    grailsApplication.config.hce.emergencia.sections.trauma."$subSectionPrefix".each { subsection ->
                        subsections << subSectionPrefix + "-" + subsection
                    }
                    */
                    def sections = this.getSections()
                    def subsections = this.getSubsections(params.templateId.split("-")[0]) // this.getSubsections('EVALUACION_PRIMARIA')



                    EventManager.getInstance().handle("post_save_error", [composition:comp])

                    flash.message = 'guiGen.save.error'

                    render(view: 'generarShow',
                            model: [ rmNode: rmobj, // si no pudo guardar no puedo hacer get a la base...
                                    index: bindingAOMRM.getRMRootsIndex(),
                                    patient:patient,
                                    template: template,
                                    mode: 'edit',
                                    errors: bindingAOMRM.getErrors(), // FIXME: esto creo que ya no se usa...
                                    episodeId: session.traumaContext?.episodioId, // necesario para el layout
                                    userId: session.traumaContext.userId,
                                    sections: sections,
                                    subsections: subsections,
                                    allSubsections: this.getDomainTemplates()
                                    //grailsApplication.config.hce.emergencia.sections.trauma // Mapa nombre seccion -> lista de subsecciones
                            ]
                    )
                    return
                }
                else
                {
                    println "SALVADO ENTRY O SECTION OK"

                    // Se linkea las Entry y Section bindeadas a la Composition Correspondiente
                    comp.addToContent(rmobj)
                    if (!comp.save())
                    {
                        println "ERROR AL SALVAR COMPOSITION"
                        // TODO
                        // Todas las salvadas que se hacen ahí deberían ser
                        // parte de una misma transaccion y si algo falla,
                        // volver todo para atrás, ir a la página y decirle
                        // que intente submitear de nuevo, mostrándole la
                        // pantalla con los valores que acaba de ingresar,
                    }
                    else
                    {
                        println "SALVADA COMPOSITION OK"

                        // Hay un handler que se encarga de verificar si se dan
                        // las condiciones de cierre del registro.
                        EventManager.getInstance().handle("post_save_ok", [composition:comp])


                        //Pregunto cual vista renderizar

                        if(params.autoSave){

                            redirect(controller: 'records', action: 'registroClinico2', params: [section: params.autoSave]

                            )


                            return

                        }else if(params.autoSaveHref){

                            redirect(url:params.autoSaveHref)

                        }else{


                            // Redirige a show para mostrar el registro ingresado.

                            redirect(action: 'generarShow',
                                    params: [id:rmobj.id]
                            )

                            return
                        }
                    }
                }
            }
            else
            {
                // volver a la pagina y pedirle que ingrese algun dato
                println "EL RESULTADO DEL BINDEO ES NULL"
            }

            // FIXME: aqui no deberia llegar
            render( text: xstream.toXML(rmobj), contentType:'text/xml' )

        }else {
            if (item==null)
            {
                flash.message = 'trauma.list.error.noEpisodeSelected'
                redirect(controller:'guiGen', action:'showRecord')
            } else {
                redirect( controller:'guiGen', action:'generarShow', id: item.id,
                        params: ['flash.message': 'trauma.list.error.registryAlreadyDone'] )
            }

            return
        }
    }
    
   /**
    * generar show o edit, depende del 'mode'
    * in: id identificador del locatable
    */
    def generarShow = {
        
println "----------------------------------------------------"
println "GENERAR SHOW"
println ""

        // DEBE haber un episodio seleccionado para poder asociar el registro clinico.
        // Se necesita para mostrar el layout con el resumen del episodio arriba.
        if (!session.traumaContext?.episodioId)
        {
            flash.message = 'trauma.list.error.noEpisodeSelected'
            redirect(controller:'records', action:'list')
            return
        }
        
        // ContentItem
        def rmNode = Locatable.get(params.id)
        println("rmNode: "+rmNode)
//println "TemplateID : "+ rmNode.archetypeDetails.templateId


        def composition = Composition.get( session.traumaContext?.episodioId )

        // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
        def patient = hceService.getPatientFromComposition( composition )


//println "Template: " + template
//XStream xstream = new XStream()
//println xstream.toXML(template)
//println xstream.toXML(template.getArchetypesByZone('content'))
//println xstream.toXML( hceService.getRMRootsIndex(template, rmNode) )
//println "============================================================"

        /*
        // Secciones predefinidas para seleccionar registro clinico
        // Es necesario para mostrar el menu
        def sections = []
        grailsApplication.config.hce.emergencia.sections.trauma.keySet().each { sectionPrefix ->
            sections << sectionPrefix
        }

        // Subsections de la section seleccionada
        def subsections = []
        def subSectionPrefix = rmNode.archetypeDetails.templateId.split("-")[0]
        grailsApplication.config.hce.emergencia.sections.trauma."$subSectionPrefix".each { subsection ->
            subsections << subSectionPrefix + "-" + subsection
        }
        */
        def sections = this.getSections()
        
        def subsections = this.getSubsections(rmNode.archetypeDetails.templateId.split("-")[0]) // this.getSubsections('EVALUACION_PRIMARIA')
        
        /* Prueba de generar el HTML de una entrada y guardarlo en una variable.
        // IDEA PARA METER los datos en formato HTML en el CDA...
        def a = g.render(template:'generarShow', model:
            [
            rmNode:    rmNode,
            template:  template,
            index:     hceService.getRMRootsIndex(template, rmNode),
            episodeId: session.traumaContext?.episodioId, // necesario para el layout
            mode: ((params.mode)?params.mode:'show'),
            userId: session.traumaContext.userId,
            sections: sections,
            subsections: subsections,
            allSubsections: grailsApplication.config.hce.emergencia.sections.trauma // Mapa nombre seccion -> lista de subsecciones
            ]
        )
        println a
        */
        
        // Template del ContentItem del que se quiere hacer show
        def template = TemplateManager.getInstance().getTemplate( rmNode.archetypeDetails.templateId )

        // Ver si el template es de una pagina estatica, el edit no es autogenerado,
        // pero el show si es autogenerado.
        String pathToView = '../hce/'+template.id
        def fullUri = grailsAttributes.getViewUri(pathToView, request)
        def resource = grailsAttributes.pagesTemplateEngine.getResourceForUri(fullUri)
        if ( resource && resource.file && resource.exists() && params.mode == 'edit')
        {
            println 'vista estatica'
            
            EventManager.getInstance().handle("post_gui_gen", params)
            
            
            
            
            render( view: pathToView, // create y edit son la misma pagina, pero podrian ser distintas
                    model: [
                    rmNode: rmNode,
                    patient: patient,
                    template: template,
                    sections: sections,
                    subsections: subsections,
                    episodeId: session.traumaContext?.episodioId,
                    mode: params.mode,
                    userId: session.traumaContext.userId,
                    allSubsections: this.getDomainTemplates()
                    ] )
            return
        }
        else
        {
            println 'vista dinamica'
        
            // El template lo puedo sacar del objeto Locatable del RM.
            // TODO: lo que no tengo es el indice de arquetipo y estructura del RM
            //       que me dice para un estructura bindeada, donde estan las raices
            //       de las estructuras que se bindean por cada slot.
            //       Es lo que obtengo de bindingAOMRM.getRMRootsIndex() pero aca
            //       no tengo la instancia de bindindAOMRM que usé para bindear.
            //       Lo que se podría hacer es guardarla en la base, referenciando 
            //       como raíz al locatable que obtengo acá, así se cómo obtenerlo.
           
            
            println("template.getArchetypesByZone: ->"+template.getArchetypesByZone('content'))
            
            return [
              rmNode:    rmNode,
              template:  template,
              index:     hceService.getRMRootsIndex(template, rmNode),
              episodeId: session.traumaContext?.episodioId, // necesario para el layout
              mode: ((params.mode)?params.mode:'show'),
              patient:patient,
              userId: session.traumaContext.userId,
              sections: sections,
              subsections: subsections,
              allSubsections: this.getDomainTemplates()
            ]
        }
    }
    
    def showRecord = {
        
        // DEBE haber un episodio seleccionado para poder asociar el registro clinico.
        // Se necesita para mostrar el layout con el resumen del episodio arriba.
        if (!session.traumaContext?.episodioId)
        {
            flash.message = 'trauma.list.error.noEpisodeSelected'
            redirect(controller:'records', action:'list')
            return
        }
        
        def composition = Composition.get(session.traumaContext?.episodioId)
        if (!composition)
        {
            flash.message = 'trauma.list.error.noEpisodeSelected' // FIXME: el error es otro...
            redirect(controller:'records', action:'list')
            return
        }
        
        // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
        def patient = hceService.getPatientFromComposition( composition )
         
        def content = hceService.getCompositionInOrder(composition,this.getDomainTemplates())



        // NECESARIO PARA EL MENU
      
        def sections = this.getSections()
       

        return [composition: composition,
                content: content,
                userId: session.traumaContext.userId,
                patient: patient,
                episodeId: session.traumaContext?.episodioId,
                sections: sections, // necesario para el menu
                allSubsections: this.getDomainTemplates()
               ]
    }
}
