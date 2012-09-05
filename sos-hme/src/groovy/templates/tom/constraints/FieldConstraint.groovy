/**
 * 
 */
package templates.tom.constraints

import templates.tom.ArchetypeField

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
abstract class FieldConstraint {

    String path
    ArchetypeField owner
    
    abstract Object process( Object value );
}
