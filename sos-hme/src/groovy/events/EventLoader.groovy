/**
 * 
 */
package events

//import groovy.lang.GroovyClassLoader


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class EventLoader
{
   // Path donde estan ubicados los handlers de los eventos.
   // FIXME: Podria ir en configuracion pero es interno al framework.
   static String handlerPath = "src/groovy/events/handlers"
   
   // Map className=>handler
   private Map<String,EventHandler> loadedHandlers = [:]
   
   /**
    GroovyClassLoader gcl = new GroovyClassLoader();
    Class domainClass = gcl.parseClass("class Test { Long id; Long version; }" );
    Class controller = gcl.parseClass("class TestController { def list = {} }");
    GrailsApplication app = new DefaultGrailsApplication(
      new Class[] {
        domainClass, controller
      }
    );
    */
   /**
    GroovyClassLoader gcl = new GroovyClassLoader();
    Class domainClass = gcl.parseClass("class Test { Long id; Long version; public Test(Long id){this.id=id;} }" );
    println domainClass.getName()
    
    def ins = domainClass.newInstance( [1234L].toArray() ) // Busca el constructor que matchee con los atributos en cantidad y tipo, este matchea con el que setea el id
    ins.version = 3367
    println ins.id // 1234
    println ins.version // 3367
    */
   public List<EventHandler> loadAll()
   {
      def ret = []
      GroovyClassLoader gcl = new GroovyClassLoader();
      
      //println "loadAll: carga de " + handlerPath 
      
      def p = ~/.*\.groovy/ // Recorrer solo los archivos groovy
      new File(handlerPath).eachFileMatch(p)
      { f ->
      
         //println "   " + f.name
      
         // Contenido del source de la clase EvenHandler
         String content = f.getText()
         Class clazz = gcl.parseClass( content )
         EventHandler handler = clazz.newInstance()
         handler.id = f.name-".groovy"
         ret << handler
         
         //println "   carga id: " + handler.id
         
         loadedHandlers[handler.id] = handler
      }
      
      //println "   loadAll CARGADOS: " + this.loadedHandlers
      return ret
   }
    
    public EventHandler get( String className )
    {
        //println "   LOADER GET: " + className
        return this.loadedHandlers[className]
    }
    
    
}
