package hce.core.services.factories

import hce.core.data_types.text.*;
import hce.core.data_types.uri.DvURI;
import hce.core.support.identification.TerminologyID;

class FactoryTextService {

    boolean transactional = true

    //-------------------------------

    static getCodePhrase(){
        return new CodePhrase()
    }

    static getCodePhrase(String cs, TerminologyID ti){
        return new CodePhrase(codeString: cs, terminologyId: ti)
    }

    //-------------------------------

    static getDvCodedText(){
        return new DvCodedText()
    }

    static getDvCodedText(CodePhrase dc){
        return new DvCodedText(definingCode: dc)
    }

    //-------------------------------

    static getDvParagraph(){
        return new DvParagraph()
    }

    //-------------------------------

    static getDvText(){
        return new DvText()
    }

    static getDvText(String f, DvURI h, String v, CodePhrase l, CodePhrase e){
        return new DvText(formatting: f, hyperlink: h, value: v, language: l, encoding: e)
    }

    //-------------------------------

    static getTermMapping(){
        return new TermMapping()
    }

    static getTermMapping(char m, DvCodedText p, CodePhrase t){
        return new TermMapping(match: m, purpose: p, target: t)
    }

    //-------------------------------
}
