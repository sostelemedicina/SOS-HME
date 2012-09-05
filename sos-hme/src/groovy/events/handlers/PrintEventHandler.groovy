/**
 * 
 */
package events.handlers

import events.EventHandler

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class PrintEventHandler extends EventHandler
{
    public void handle( Map context )
    {
        println "========================="
        println "EventHandler: "+ this.id
        println "Context: " + context
        println "========================="
    }
}
