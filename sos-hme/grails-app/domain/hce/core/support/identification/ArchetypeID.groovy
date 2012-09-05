package hce.core.support.identification

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Identificador para arquetipos, instancias de esta clase son inmutables.
 *
 * @author Leandro Carrasco
 * @version 1.0
 */

class ArchetypeID extends ObjectID{

    /* static fields */
    static final String AXIS_SEPARATOR = ".";
    static final String SECTION_SEPARATOR = "-";
    static Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9()_/%\$#&]*");
    static Pattern VERSION_PATTERN = Pattern.compile("[a-zA-Z0-9]+");

    /* fields */
    String qualifiedRmEntity;   // calculated
    String rmOriginator;
    String rmName;
    String rmEntity;
    String domainConcept;       // calculated
    String conceptName;
    List<String> specialisation;
    String versionID;

    static constraints = {
        /*rmOriginator(validator: {NAME_PATTERN.matcher(it).matches()})
        rmName(validator: {NAME_PATTERN.matcher(it).matches()})
        rmEntity(validator: {NAME_PATTERN.matcher(it).matches()})
        conceptName(validator: {NAME_PATTERN.matcher(it).matches()})
        specialisation(validator: {
            if (it != null) {
        	for(String name : specialisation) {
                    if (!NAME_PATTERN.matcher(name).matches())
                        return false;
        	}
            }
            return true;
        })
        versionID(VERSION_PATTERN.matcher(it).matches())*/
    }

}
