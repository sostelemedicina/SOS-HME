/**
 * 
 */
package events


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
abstract class EventHandler
{
    String id // nombre edl handler
    
    // TODO: podria pasarle tambien el nombre del evento
    public abstract void handle( Map context );
}
