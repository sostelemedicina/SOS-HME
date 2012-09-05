package hce.core.data_types.encapsulated

import hce.core.data_types.text.CodePhrase;
import hce.core.data_types.uri.DvURI;
//import org.openehr.rm.support.terminology.OpenEHRCodeSetIdentifiers;
//import org.openehr.rm.support.terminology.TerminologyAccess;
//import org.openehr.rm.support.terminology.TerminologyService;


/**
 * A specialisation of Encapsulated for audiovisual and biosignal types.
 * Includes further metadata relating to multimedia types which are not
 * applicable to other subtypes of Encapsulated.
 * Instances of this class are immutable.
 *
 * Similar a ObservationMedia de HL7 CDA.
 *
 */
class DvMultimedia extends DvEncapsulated {

     String alternateText
     CodePhrase mediaType // http://www.iana.org/assignments/mediatypes
     CodePhrase compressionAlgorithm
     byte[] integrityCheck // binary cryptographic integrity checksum
     CodePhrase integrityCheckAlgorithm
     DvMultimedia thumbnail // The thumbnail for this item, if one exists; mainly for graphics formats.
     DvURI uri // URI reference to electronic information stored outside the record
     byte[] data

     static mapping = {
        mediaType cascade: "save-update"
        compressionAlgorithm cascade: "save-update"
        integrityCheckAlgorithm cascade: "save-update"
        thumbnail cascade: "save-update"
        uri cascade: "save-update"
    }

     static constraints = {
         mediaType(nullable:false)
         compressionAlgorithm(nullable:false)
         data(maxSize :1024*1024*10) // 10MB
     }

     /* TODO:
        if (integrityCheck != null && integrityCheckAlgorithm == null) {
            throw new IllegalArgumentException(
                    "null integrity check algorithm");
        }
     */
     /* TODO:
        if (uri == null && data == null) {
            throw new IllegalArgumentException("both uri and ata are null");
        }
     */

    static transients = ['external', 'inline', 'compressed']

    boolean isExternal() {
        return uri != null
    }

    boolean isInline() {
        return data != null
    }

    boolean isCompressed() {
        return compressionAlgorithm != null
    }

    boolean hasIntegrityCheck() {
        return integrityCheckAlgorithm != null
    }

    String toString() {
        return 'DvMultimedia -> ' + mediaType.toString()
    }

}
