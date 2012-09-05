package hce.core.services.factories

import java.net.URI;
import hce.core.data_types.uri.*;

class FactoryUriService {

    boolean transactional = true

    //-----------------------------------

    static getDvURI(){
        return new DvURI()
    }

    static getDvURI(URI v){
        return new DvURI(value: v)
    }

    //-----------------------------------

    static getDvEHRURI(){
        return new DvEHRURI()
    }

    static getDvEHRURI(URI v){
        return new DvEHRURI(value: v)
    }

    //-----------------------------------
}
