/**
 * Este evento se lanza al seleccionar un paciente en el controller demografico o
 * al guardar un registro.
 * 
 * Verifica la condicion de cierre del registro.
 */
package events.handlers

import events.EventHandler
import hce.core.composition.Composition;
import hce.core.common.change_control.Version;
import hce.HceService;
import org.codehaus.groovy.grails.commons.ApplicationHolder;
import converters.DateConverter;

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class VerificarCondicionCierreEventHandler extends EventHandler
{
    //ApplicationContext ctx = (ApplicationContext)ApplicationHolder.getApplication().getMainContext();
    //CountryServiceInt service = (CountryServiceInt) ctx.getBean("countryService");
    //String str = service.sayHello(request.getParameter.("name"));
    
    def ctx = ApplicationHolder.getApplication().getMainContext();
    def hceService = ctx.getBean("hceService");
    
    //def hceService = new HceService()
    
    public void handle( Map context )
    {
        println "========================="
        println "EventHandler: "+ this.id
        println "Context: " + context
        println "========================="
        
        if (!context.composition) throw new Exception("En el contexto debe venir la composition")
        
        // Si tiene paciente y se hizo el movimiento del paciente, puedo cerrar el registro.
        if (hceService.getPatientFromComposition( context.composition ) &&
            hceService.getCompositionContentItemForTemplate( context.composition, 'COMUNES-movimiento_paciente' ))
        {
            // Setea la fecha de fin sobre la composition y cambia el estado de version
            def item = hceService.getCompositionContentItemForTemplate( context.composition, 'COMUNES-movimiento_paciente' )
            def fechaFin
            item.activities[0].description.items.each{e ->
                if (e.archetypeNodeId == "at0012"){
                    fechaFin = e.value
                }
            }
            hceService.closeComposition( context.composition, DateConverter.toIso8601ExtendedDateTimeFormat( fechaFin.toDate() ) )
        }
    }
}
