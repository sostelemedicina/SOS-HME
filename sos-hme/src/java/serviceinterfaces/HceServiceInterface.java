package serviceinterfaces;

/**
 * Interfaz auziliar necesaria para acceder al HceService desde clases 
 * en src/groovy, segun lo que dice aca: http://www.grails.org/Services
 */

import hce.core.common.archetyped.Locatable;
import hce.core.common.generic.PartyProxy;
import hce.core.composition.Composition;
import templates.tom.Template;
import demographic.party.Person;
import hce.core.common.generic.Participation;
import hce.core.common.generic.PartySelf;
import java.util.Map;
import hce.core.composition.content.ContentItem;

public interface HceServiceInterface {

	public Composition createComposition( String _startTime, String _otherContext );

    public boolean closeComposition( Composition composition, String endTime );    
    
    public PartySelf createPatientPartysSelf( String root, String extension );
    
    public boolean setCompositionComposer( Composition composition, String root, String extension );
    
    public boolean isIncompleteComposition( Composition composition );
    
    public boolean isCompleteComposition( Composition composition );
    
    public Participation createParticipationToPerformer( PartyProxy _performer );
    
    public PartySelf getPartySelfFromComposition( Composition composition );
    
    public Person getPatientFromComposition( Composition composition );
    
    public Composition getActiveCompositionForPatient( Person person );
    
    public Map getRMRootsIndex( Template template, Locatable rmNode);
    
    public Locatable findRootNodeForArchId ( Locatable rmNode, String archId );
    
    public ContentItem getCompositionContentItemForTemplate( Composition composition, String templateId );
}
