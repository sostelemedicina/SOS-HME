package hce.core.data_types.uri

import java.net.URI;
import hce.core.data_types.basic.*;

class DvURI extends DataValue{

    URI value

    static mapping = {
        value column: "dvuri_value"
        value cascade: "save-update"
    }

    static constraints={
        value (nullable:false, blank:false)
    }

     /**
     * Returns the decoded fragment component of this URI.
     *
     * @return fragment
     */
    String fragmentID() {
        return value.getFragment()
    }

     /**
     * Returns the decoded path component of this URI.
     *
     * @return path
     */
    String path() {
        return value.getPath()
    }

     /**
     * Returns the decoded query component of this URI.
     *
     * @return query
     */
    String query() {
        return value.getQuery()
    }

    /**
     * Returns the scheme of this URI.
     *
     * @return scheme
     */
    String scheme() {
        return value.getScheme()
    }


    // Operaciones que no estan en el estandar

    /**
    * Return value of URI as a String.
    *
    * @return string value
    */
    String toString() {
        return value.toString()
    }

    /**
     * Return value of URI as a String.
     *
     * @return string value
     */
    //String getValue() {
    //    return value.toString()
    //}

    /**
     * Equals if values are the same
     *
     * @param o
     * @return true if equals
     */
    boolean equals(Object o) {
        if (this == o) return true
        if (!( o instanceof DvURI )) return false

        DvURI dvURI = (DvURI) o
        if (!value.equals(dvURI.value)) return false

        return true
    }
}
