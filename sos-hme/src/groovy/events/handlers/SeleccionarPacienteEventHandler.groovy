/**
 * Este evento se lanza al seleccionar un paciente en el controller demografico.
 * Crea una planificacion para busqueda posterior de examenes imagenologicos en lso pacs que se tengan conectados.
 */
package events.handlers

import events.EventHandler

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class SeleccionarPacienteEventHandler extends EventHandler
{
    public void handle( Map context )
    {
        println "========================="
        println "EventHandler: "+ this.id
        println "Context: " + context
        println "========================="
        
        if (!context.patient) throw new Exception("En el contexto debe venir la persona seleccionada")

        // TODO
    }
}
