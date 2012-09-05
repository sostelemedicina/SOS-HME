/**
 * 
 */
package identification

/**
 * FIXME: esta clase no se usa, se usa la que esta en el paquete hce.core.support.identification
 */


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
public class ArchetypeId {

    /* Nombre del concepto que representa el arquetipo, incluyendo
     * especializacion, p.e. "biochemistry result-cholesterol" */
    String domainConcept
    
    /* Entidad del modelo de referencia calificada globalmente,
     * p.e. "openehr-ehr_rm-entry" */
    String qualifiedRmEntity
    
    /* Nombre de la entidad del modelo de referencia al que
     * apunta el arquetipo, en OpenEHR las opciones pueden ser:
     * folder, composition, section, entry, etc. */
    String rmEntity
    
    /* Nombre del modelo de referencia, p.e. "rim", "ehr_rm", "en13606" */
    String rmName
    
    /* Organizacion que origina el modelo de referencia sobre el
     * que esta basado el arquetipo, p.e. "openehr", "cen", "hl7". */
    String originator
    
    /* Nombre de un concepto de especializacion, en el caso que
     * sea un arquetipo que especialice otro arquetipo. */
    String specialisation
    
    /* Version del arquetipo */
    String version
    
    
    /* Nombres de entidades OpenEHR */
    static String RM_ENTITY_FOLDER      = "folder" 
    static String RM_ENTITY_COMPOSITION = "composition"
    static String RM_ENTITY_SECTION     = "section"
    static String RM_ENTITY_ENTRY       = "entry"
    static String RM_ENTITY_OBSERVATION = "observation"
    static String RM_ENTITY_EVALUATION  = "evaluation"
    static String RM_ENTITY_INDICATION  = "indication"
    static String RM_ENTITY_ACTION      = "action"
    static String RM_ENTITY_ADMIN_ENTRY = "admin_entry"
    
    /* Valores posibles para rmName */
    static String RM_NAME_OPENEHR       = "ehr_rm"
    static String RM_NAME_RIM           = "rim"
    static String RM_NAME_EN13606       = "en13606"
    
    /* Valores posibles para originator */
    static String ORIGINATOR_OPENEHR    = "openehr"
    static String ORIGINATOR_HL7        = "hl7"
    static String ORIGINATOR_CEN        = "cen"
}
