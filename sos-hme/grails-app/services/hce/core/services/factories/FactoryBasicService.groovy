package hce.core.services.factories

import hce.core.data_types.basic.*;
import hce.core.data_types.text.DvCodedText;

class FactoryBasicService {

    boolean transactional = true

    //-----------------------------------

    static getDvBoolean(){
        return new DvBoolean()
    }

    static getDvBoolean(boolean v){
        return new DvBoolean(value: v)
    }

    //-----------------------------------

    static getDvIdentifier(){
        return new DvIdentifier()
    }

    static getDvIdentifier(String i, String a, String c, String t){
        return new DvIdentifier(issuer: i, assigner: a, code: c, type: t)
    }

    //-----------------------------------

    static getDvState(){
        return new DvState()
    }

    static getDvState(boolean t, DvCodedText v){
        return new DvState(terminal: t, value: v)
    }

    //-----------------------------------
}
