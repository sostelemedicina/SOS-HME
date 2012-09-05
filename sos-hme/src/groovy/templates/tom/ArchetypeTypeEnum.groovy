/**
 * 
 */
package templates.tom

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
public enum ArchetypeTypeEnum {
    
    COMPOSITION('composition'),
    SECTION('section'),
    OBSERVATION('observation'),
    EVALUATION('evaluation'),
    INSTRUCTION('instruction'),
    ACTION('action'),
    CLUSTER('cluster'),
    ELEMENT('element')

    String name
    
    public ArchetypeTypeEnum(String name)
    {
       this.name = name
    }
    
    static ArchetypeTypeEnum fromValue( String name )
    {
        switch(name)
        {
            case "composition": return COMPOSITION
            case "section": return SECTION
            case "observation": return OBSERVATION
            case "evaluation": return EVALUATION
            case "instruction": return INSTRUCTION
            case "action": return ACTION
            case "cluster": return CLUSTER
            case "element": return ELEMENT
        }
    }
   
    static list() {
     [
      COMPOSITION,
      SECTION,
      OBSERVATION,
      EVALUATION,
      INSTRUCTION,
      ACTION,
      CLUSTER,
      ELEMENT
     ]
   }
}

