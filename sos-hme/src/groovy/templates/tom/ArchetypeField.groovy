package templates.tom

import templates.tom.constraints.*
import templates.tom.controls.*

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 */
class ArchetypeField {

    String path
    ArchetypeReference owner
    
    // TODO: ver como defino valores por defecto para los
    // atomos (campos simples), sobre todo para los que no
    // dejo ingresar al medico.
    
    List constraints = [] // Que restriccion se aplica a cada campo generado (un archField puede generar varios campos en la web)
    List controls = [] // Que tipo de control se usara para cada campo generado (un archField puede generar varios campos en la web)

    /**
     * Obtiene una restriccion particular por la subpath interna al field
     * que esta en cada constraint del field.
     */
    Object getConstraintByPath( String path )
    {
        return this.constraints.find{ it.path == path }
    }

    Control getControlByPath( String path )
    {
        return this.controls.find{ it.path == path }
    }
}