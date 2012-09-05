package util;

/**
 * Clase utilitaria que se encarca de crear identificadores unicos para ser usados donde sea necesario.
 * Un uso es para los names de los anchors que se generan en la vista.
 * 
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class UniqueIdIssuer {
    
    static List letters = ['a','b','c','d','e','f','g','h','i',
                           'j','k','l','m','n','o','p','q','r',
                           's','t','u','v','w','x','y','z']
    static Integer counter = 0
    
    static String getId()
    {
        String id = letters[counter % letters.size()] + counter.toString()
        counter++
        return id
    }
}
