package hce.core.support.identification

class TerminologyID extends ObjectID {

    // Identifier for terminologies such accessed via a terminology query service. In this class,
    // the value attribute identifies the Terminology in the terminology service, e.g. “SNOMED-CT”.
    // A terminology is assumed to be in a particular language, which must be explicitly specified.
    // The value if the id attribute is the precise terminology id identifier, including actual
    // release (i.e. actual “version”), local modifications etc; e.g. “ICPC2”

    // FIXED: name y versionId son metodos en el RM, no campos.
    // Van codificados en 'value' de la superclase ObjectID
    //String name
    //String versionId // version

    //static constraints = {
    //    name (nullable: false, blank: false)
    //    versionId (nullable: false)
    //}
   
   static transients = ['name','versionId']
   
   def getName()
   {
       return this.value?.split(' ')[0]
   }

   def getVersionId()
   {
       // TODO: sacar parentesis
       return this.value?.split(' ')[1]
   }
   
   static TerminologyID create(String name, String versionId)
   {
       // FIXME: name != null
       return new TerminologyID(
                    value: ((name)?name:'')+ ' '+((versionId)?'('+versionId+')':'')
                  )
   }
    
    /*
    String name(){ // Return the terminology id (which includes the “version” in some cases). Distinct names correspond to distinct (i.e. non-compatible) terminologies. Thus the names “ICD10AM” and “ICD10” refer to distinct terminologies.
        // TO DO
        return ""
    }

    String versionId(){ //Version of this terminology, if versioning supported, else the empty string.
        // TO DO
        return ""
    }
    */
}
