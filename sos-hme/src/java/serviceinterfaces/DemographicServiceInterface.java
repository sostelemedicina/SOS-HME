package serviceinterfaces;

import demographic.party.*;
import demographic.identity.*;
import demographic.role.*;
import hce.core.support.identification.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @author leacar21
 */
public interface DemographicServiceInterface {


    /**
     * Encuentra otros identificadores de la persona, a partir de un identificador conocido.
     * Se corresponde a PIX ids query con un solo identificador como criterio de busqueda.
     */
    public List<UIDBasedID> findIdsById( UIDBasedID id );

    /**
     * Encuentra otros identificadores de la persona, a partir de los identificadores conocidos.
     * Se corresponde a PIX ids query con muchos identificadores como criterio de busqueda.
     * El resultado debe ser el mismo que si se invoca findIdsById para cada id de ids y luego se hace merge de los resultados.
     */
    public List<UIDBasedID> findIdsByIds( List<UIDBasedID> ids );

    /**
     * Busca por extension, sin considerar root.
     */
    public List<Person> findPersonById( UIDBasedID id );

    public List<Person> findByPersonData( PersonName n, Date bithdate, String sex );

    public List<Person> findByPersonDataAndId( PersonName n, Date bithdate, String sex, UIDBasedID id );

    public List<Person> findByPersonDataAndIds( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids );

    public List<Person> findByPersonDataAndRole( PersonName n, Date bithdate, String sex, Role role );

    public List<Person> findByPersonDataAndIdAndRole( PersonName n, Date bithdate, String sex, UIDBasedID id, String roleType );

    public List<Person> findByPersonDataAndIdsAndRole( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids, Role role );
}
