/**
 * 
 */
package hce

import tablasMaestras.Cie10Trauma

import grails.converters.* // as JSON
import com.thoughtworks.xstream.XStream

// TEST BINDER
import binding.BindingAOMRM

import hce.core.composition.* // Composition y EventContext

import hce.HceService

import templates.TemplateManager

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class AjaxApiController {

    def hceService
    
    // FIXME: tambien esta implementadas en GuiGenController
    
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
        //println domainTemplates
        
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
    
    
    // Prueba
    def diagnosticos = {
       println "DIAGNOSTICOS ==============="
       println ""
       println ""
       render(view:'../hce/DIAGNOSTICO-diagnosticos')
    }
    
    /**
     * Devuelve una lista de codigos CIE10 basandose en la entrada de texto.
     * La web se encarga de por lo menos enviar algunos caracteres minimos
     * para realizar una busqueda de valor, no estaria de mas que aqui
     * tambien se verifique que vienen por lo minimo X caracteres con X configurable.
     * 
     * @param String text
     */
    def findCIE10 = {
        
        
        def partes = params.text.split(" ") // saco palabras por espacios
        
        println partes
        
        def _codigos = Cie10Trauma.withCriteria {
            //like('nombre', '%'+ params.text +'%') // Ok si uso el texto completo
            and {
                partes.each { parte ->
                    like('nombre', '%'+ parte +'%')
                }
            }
        }
        
        // TODO: hacerlo JSON creo que hay un JSON builder
        
        // Lo hago derecho HTML para probar nomas.
        /*
        def html = '<table>'
        _codigos.each { codigo ->
            
            / *
            html += '<tr><td>' +
                    it.nombre.replaceAll(params.text, '<b>'+params.text+'</b>') + // bien para marcar con negrita el texto completo
                    '</td></tr>' // TODO: highlight de params.text!
            * /
            
            html += '<tr><td>'
            
            //println "Codigo: " + codigo.nombre
            
            def nombre = codigo.nombre
            partes.each { parte ->
                //println "Parte: " + parte
                nombre = nombre.replaceAll(parte, '<b>'+parte+'</b>')
                println nombre
            }
            
            
            html += nombre
            html += '</td></tr>'
        }
        html += '</table>'
        
        render( html )
        */
        

        //Armando: Se codifica el return en JSON para ser atajado con JQUERY

        render(contentType: "text/json") {
         codigos = array {
            _codigos.each { _codigo ->

              // a negrita los textos de entrada en el texto de salida
              def _nombre = _codigo.nombre
              partes.each { parte ->

                // El texto en la base esta en upper
                _nombre = _nombre.replaceAll(parte.toUpperCase(), '<b class="highlight">'+parte.toUpperCase()+'</b>')
              }
              //println "nombre: "+_nombre

             codigo (
                id: _codigo.id,
                grupo: _codigo.grupo,
                subgrupo: _codigo.subgrupo,
                codigo: _codigo.codigo,


                //nombre: _codigo.nombre
                nombre: _nombre
              )

            }
          }
        }


    } // findCIE10
    
    
    // No se si va aca, capaz en un controller que se encargue de recibir las salvadas.
    def saveDiagnostico = {
        
        def cie10ids = request.getParameterValues("codes") as List
        //List everyDays = java.util.Arrays.asList( everyDaysArray );
        
        def pathValor = [:]
        
        def codePath = 'openEHR-EHR-OBSERVATION.diagnosticos.v1/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/defining_code'
        def descPath = 'openEHR-EHR-OBSERVATION.diagnosticos.v1/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value'
        def templateId = "DIAGNOSTICO-diagnosticos"
        
        //XStream xstream = new XStream()
        
        cie10ids.each { id ->
        
            def code = Cie10Trauma.get( id )
            
            //println xstream.toXML(code)
            
            if (!pathValor[ codePath ])
                pathValor[ codePath ] = []

            // le meto cie10 al principio para saber de cual terminologica tiene que sacar el texto y para que pueda crear la terminology Id del definingcode del CodedText.
            // FIXME: si el seleccionado es un subgrupo, no tiene codigo!
            //pathValor['openEHR-EHR-OBSERVATION.diagnosticos.v1/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/defining_code'] << 'cie10::'+code.codigo
            pathValor[ codePath ] << ((code.codigo)?code.codigo:code.subgrupo) // si es un subgrupo, el codigo es el del subgrupo!
        }
        
        //println "PathValor: " + pathValor
        
        // Necesito que sea array porque es el tipo que usa java para poner los valores multiples. //
        if ( pathValor[ codePath ] )
            pathValor[ codePath ] = pathValor[ codePath ].toArray()
        
        pathValor[ descPath ] = params.descripcion
        
        // Armo path -> valor para binder! (a mano)
        // openEHR-EHR-ACTION.columna_vertebral.v1/description[at0001]/items[at0002]/value/defining_code
        // openEHR-EHR-OBSERVATION.diagnosticos.v1/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/defining_code
        
        //BindingAOMRM bindingAOMRM = new BindingAOMRM()
        BindingAOMRM bindingAOMRM = new BindingAOMRM( session )
        def rmobj = bindingAOMRM.bind(pathValor, templateId)

        // Se necesita para generar la vista edit
        def template = TemplateManager.getInstance().getTemplate( templateId )
        
        // FIXME: hacer flujo de guardar y volver al registro clinico.
        //println pathValor.toString()
        //println xstream.toXML(rmobj)
        
        Composition comp = Composition.get(session.traumaContext.episodioId)

        // Idem al chequeo de GuiGenController.save, si ya existe el ContentItem y
        // es mode=edit, ser borra el viejo y se guarda el nuevo CI.
        // Si mode!=edit, el registro ya esta hecho, no se puede volver a hacer.
        def item = hceService.getCompositionContentItemForTemplate( comp, templateId )
        if (item)
        {
            // Si es el save de un edit, borra el registro anterior y sustituye por el nuevo.
            if (params.mode == 'edit')
            {
                comp.removeFromContent(item)
                item.delete(flush:true) // FIXME: delete no es en cascada si no se pone belongsTo en las clases hijas.


            //Agregado por Armando
            //FIXME: Puede ocurrir que se intente borrar un diagnóstico anterior mediante un edit
            //pero no se puede hacer save() porque va a tener errores
            


            }
            else // Si no es save de edit, esta tratando de salvar de nuevo algo que ya habia salvado.
            {
                println "Registro ya realizado, se va a show para y no se vuelve a guardar"
                redirect( controller:'guiGen', action:'generarShow', id: item.id,
                          params: ['flash.message': 'trauma.list.error.registryAlreadyDone'] )
                return
            }
        }
        
        if (rmobj)
        {
            if (!rmobj.save(flush:true) || bindingAOMRM.getErrors().hasErrors() || bindingAOMRM.hasErrors() )
            {
                println "ERROR AL SALVAR: ---> " + rmobj.errors
                println "TheErrors: " + bindingAOMRM.getErrors() + "\n\n"
                // TIENE QUE VOLVER Al CREATE con los errores y valores ya ingresados.
                // No puedo hacer redirect porque pierdo los valores y los errores.
                
                // ==============================================================================
                // Model: Paciente del episodio seleccionado
                def composition = Composition.get( session.traumaContext.episodioId )

                // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
                def patient = hceService.getPatientFromComposition( composition )

                // ==============================================================================

                // Secciones predefinidas para seleccionar registro clinico
                // Es necesario para mostrar el menu
                /*
                def sections = []
                grailsApplication.config.hce.emergencia.sections.trauma.keySet().each { sectionPrefix ->
                    sections << sectionPrefix
                }
                
                // Subsections de la section seleccionada
                def subsections = []
                def subSectionPrefix = rmobj.archetypeDetails.templateId.split("-")[0]
                grailsApplication.config.hce.emergencia.sections.trauma."$subSectionPrefix".each { subsection ->
                    subsections << subSectionPrefix + "-" + subsection
                }
                */

                def sections = this.getSections()
                def subsections = this.getSubsections(rmobj.archetypeDetails.templateId.split("-")[0]) // this.getSubsections('EVALUACION_PRIMARIA')
                

                render( view: '../hce/DIAGNOSTICO-diagnosticos',
                       model: [
                           patient: patient,
                           template: template,
                           sections: sections,
                           subsections: subsections,
                           episodeId: session.traumaContext?.episodioId,
                               userId: session.traumaContext.userId,
                               // Params para edit
                               rmNode: rmobj, // si no pudo guardar no puedo hacer get a la base...
                               index: bindingAOMRM.getRMRootsIndex(),
                               errors: bindingAOMRM.getErrors(),
                               allSubsections: this.getDomainTemplates()
                               //grailsApplication.config.hce.emergencia.sections.trauma // Mapa nombre seccion -> lista de subsecciones
                       ] )
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
                    //  parte de una misma transaccion y si algo falla,
                    //  volver todo para atrás, ir a la página y decirle
                    //  que intente submitear de nuevo, mostrándole la
                    //  pantalla con los valores que acaba de ingresar,
                }
                else
                {
                    println "SALVADA COMPOSITION OK"

                    //Pregunto cual vista renderizar

                    if(params.autoSave){

                    redirect(controller: 'records', action: 'registroClinico2', params: [section: params.autoSave]

                           )


                    return

                   }else if(params.autoSaveHref){

                        redirect(url:params.autoSaveHref)

                    }else{

                   // Redirige a show para mostrar el registro ingresado.

                    redirect(controller:'guiGen',action: 'generarShow',
                            params: [id:rmobj.id]
                           )

                    return
                    }
                    
                    /*  redirect(controller: 'guiGen',
                              action: 'generarShow',
                              params: [id: rmobj.id])
                    return*/


                }
            }
        }
        else
        {
            // volver a la pagina y pedirle que ingrese algun dato
            println "EL RESULTADO DEL BINDEO ES NULL"
        }
        
        render( text: xstream.toXML(rmobj), contentType:'text/xml' )
        
    } // save diagsnostico
}