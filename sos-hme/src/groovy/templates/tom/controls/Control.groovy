/**
 * 
 */
package templates.tom.controls


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Control {

    String type // FIXME: deberia ser un enumerado
    String path // Campo particular al que se aplica
    
    String toString()
    {
       return "Control: " + type + " ["+path+"]"
    }
}
