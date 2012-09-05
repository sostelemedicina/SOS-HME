/**
 * 
 */
package events


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class EventManager
{
   // FIXME: configuracion!
   String configPath = "src/groovy/events/event_handlers.xml"
    
   // Manejadores de eventos registrados
   // nombre evento => manejadores del evento
   Map<String, List<EventHandler>> handlers = [:]
   
   static EventManager instance = null
   
   public static EventManager getInstance()
   {
       if (instance == null) instance = new EventManager()
       return instance
   }
   
   private EventManager()
   {
       this.loadConfig()
   }
   
   public void add(String event, EventHandler eh)
   {
      // FIXME: que pasa si se registra 2 veces el mismo handlers... no deberia dejarlo.
      if ( !handlers[event] ) handlers[event] = [] 
      handlers[event] << eh
   }
   
   public void handle(String event, Map context)
   {
      //println "   MANAGER HANDLE: " + event
      if ( handlers[event] )
      {
         //println "   - found handlers: " + handlers[event]
         try
         {
             handlers[event].each { handler ->
                handler.handle( context )
             }
         }
         catch (Exception e)
         {
            println "Ocurrio un error al ejecutar event handler ["+ event +"] " + e.getMessage() 
         }
      }
      else
         println "   - no se encontraron handlers registrados: " + event
      // Si no hay handlers para el evento no pasa nada.
   }
   
   public void loadConfig()
   {
       // Carga todos los handlers de disco y los instancia
       def loader = new EventLoader()
       loader.loadAll()
       
       // Archivo de configuracion con handlers asignados a eventos
       def configFile = new File(configPath)
       def _event_handlers = new XmlSlurper().parseText( configFile.getText() )
       
       // Registra handlers a eventos
       _event_handlers.event.each { event ->
       
          def eventName = event.'@name'.text()
          
          //println "  event: " + eventName
          
          event.handler.each { handler ->
          
             //println "  - handler: " + handler.'@name'.text()
          
             // EventHandler por nombre de clase
             EventHandler ehInstance = loader.get( handler.'@name'.text() )
             
             this.add(eventName, ehInstance)
          }
       }
   }
    
}
