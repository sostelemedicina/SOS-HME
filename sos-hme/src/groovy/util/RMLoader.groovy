package util

import hce.core.datastructure.itemstructure.representation.*
import hce.core.data_types.basic.*
import hce.core.data_types.encapsulated.*
import hce.core.data_types.quantity.*
import hce.core.data_types.quantity.date_time.*
import hce.core.data_types.text.*
import hce.core.data_types.uri.*
import hce.core.support.basic.*
import hce.core.support.identification.*
import hce.core.datastructure.itemstructure.*
import hce.core.datastructure.history.*
import hce.core.datastructure.*
import hce.core.composition.*
import hce.core.composition.content.*
import hce.core.composition.content.entry.*
import hce.core.composition.content.navigation.*
import hce.core.common.archetyped.*
import hce.core.common.generic.*
import authorization.*
import demographic.contact.*
import demographic.identity.*
import demographic.party.*
import demographic.role.*
import demographic.*
import com.thoughtworks.xstream.XStream

/**
 *
 * @author leacar21
 */
class RMLoader {

    static void imprimirObjetoXML(Object o){
        println "-----------------"
        XStream xstream = new XStream();
        String xml = xstream.toXML(o);
        println xml
        println "-----------------"
    }

    //--------------------------------------------------------------------------

    static Composition loadComposition(int id){
        def composition = Composition.get(id)
        imprimirObjetoXML(composition) // FIXME
        Composition new_composition = new Composition()
        recorrerComposition(composition, new_composition) // Al recorrer el composition cargo toda su estructura
        if (composition != null){
            return new_composition
        }
        else{
            return null
        }
    }

    //--------------------------------------------------------------------------

    // COMPOSITION
    static def recorrerComposition(c, Composition new_c){
        if (c != null){
            if (c.composer != null){
                def metodoRecorrerPartyProxy = 'recorrer' + c.composer.getClass().getSimpleName()
                def metodoGetInstancePartyProxy = 'getInstance' + c.composer.getClass().getSimpleName()
                this."$metodoRecorrerPartyProxy"(c.composer, new_c.composer = this."$metodoGetInstancePartyProxy"())
            }
            recorrerEventContext(c.context, new_c.context = new EventContext())
            recorrerDvCodedText(c.category, new_c.category = new DvCodedText())
            recorrerCodePhrase(c.territory, new_c.territory = new CodePhrase())
            recorrerCodePhrase(c.language, new_c.language = new CodePhrase())
            if (c.content != null){
                c.content.each{elem ->
                    def metodoRecorrerContentItem = 'recorrer' + elem.getClassName()
                    def metodoGetInstanceContentItem = 'getInstance' + elem.getClassName()
                    def new_elem = null
                    this."$metodoRecorrerContentItem"(elem, new_elem = this."$metodoGetInstanceContentItem"())
                    new_c.addToContent(new_elem)
                    println "SALIENDO RECORRER COMPOSITION"
                }
            }
            recorrerLocatable(c, new_c)
        }
    }

    //--------------------------------------------------------------------------
    // hce.core.common.archetyped
    //--------------------------------------------------------------------------

    // ARCHETYPED
    static def recorrerArchetyped(a, Archetyped new_a){
        if (a != null){
            new_a.archetypeId = (a.archetypeId)
            new_a.templateId = (a.templateId)
            new_a.rmVersion = (a.rmVersion)
        }
    }

    //--------------------------------------------------------------

    // LINK
    static def recorrerLink(l, Link new_l){
        if (l != null){
            recorrerDvText(l.meaning, new_l.meaning = new DvText())
            recorrerDvText(l.type, new_l.type = new DvText())
            recorrerDvEHRURI(l.target, new_l.target = new DvEHRURI())
        }
    }

    //--------------------------------------------------------------

    // LOCATABLE
    static def recorrerLocatable(l, Locatable new_l){
        if (l != null){
            new_l.archetypeNodeId = (l.archetypeNodeId)
            recorrerDvText(l.name, new_l.name = new DvText())
            recorrerArchetyped(l.archetypeDetails, new_l.archetypeDetails = new Archetyped())
            new_l.path = (l.path)
        }
    }

    //--------------------------------------------------------------

    // PATHABLE
    static def recorrerPathable(p, Pathable new_p){
        if (p != null){
            recorrerPathable(p.parent, new_p.parent = new Pathable())
        }
    }

    //--------------------------------------------------------------------------
    // hce.core.common.generic
    //--------------------------------------------------------------------------

    // PARTICIPATION
    static def recorrerParticipation(p, Participation new_p){
        if (p != null){
            def metodoRecorrerPartyProxy = 'recorrer' + p.performer.getClass().getSimpleName()
            def metodoGetInstancePartyProxy = 'getInstance' + p.performer.getClass().getSimpleName()
            this."$metodoRecorrerPartyProxy"(p.performer, new_p.performer = this."$metodoGetInstancePartyProxy"())
            recorrerDvText(p.function, new_p.function = new DvText())
            recorrerDvCodedText(p.mode, new_p.mode = new DvCodedText())
            recorrerDvInterval(p.time, new_p.time = new DvInterval())
        }
    }

    //--------------------------------------------------------------

    // PARTY_PROXY
    static def recorrerPartyProxy(pp, PartyProxy new_pp){
        if (pp != null){
            recorrerPartyRef(pp.externalRef, new_pp.externalRef = new PartyRef())
        }
    }

    //--------------------------------------------------------------

    // PARTY_IDENTIFIED
    static def recorrerPartyIdentified(pi, PartyIdentified new_pi){
        if (pi != null){
            new_pi.name = (pi.name)
            if (pi.identifiers != null){
                pi.identifiers.each{elem ->
                    def new_elem = null
                    recorrerDvIdentifier(elem, new_elem = new DvIdentifier())
                    new_pi.addToIdentifiers(new_elem)
                }
            }
            recorrerPartyProxy(pi, new_pi) // Por ser subclase de PartyProxy
        }
    }

    static def getInstancePartyIdentified(){
        return new PartyIdentified()
    }

    //--------------------------------------------------------------

    // PARTY_SELF
    static def recorrerPartySelf(ps, PartySelf new_ps){
        if (ps != null){
            recorrerPartyProxy(ps, new_ps) // Por ser subclase de PartyProxy
        }
    }

    static def getInstancePartySelf(){
        return new PartySelf()
    }

    //--------------------------------------------------------------------------
    // hce.core.composition.content.entry
    //--------------------------------------------------------------------------

    // ACTION
    static def recorrerAction(a, Action new_a){
        if (a != null){
            recorrerDvDateTime(a.time, new_a.time = new DvDateTime())
            def metodoRecorrerItemStructure = 'recorrer' + a.description.getClassName()
            def metodoGetInstanceItemStructure = 'getInstance' + a.description.getClassName()
            this."$metodoRecorrerItemStructure"(a.description, new_a.description = this."$metodoGetInstanceItemStructure"())
            recorrerCareEntry(a, new_a) // Por ser subclase de CareEntry
        }
    }

    static def getInstanceAction(){
        return new Action()
    }

    //--------------------------------------------------------------------------

    // ACTIVITY
    static def recorrerActivity(a, Activity new_a){
        if (a != null){
            def metodoRecorrerItemStructure = 'recorrer' + a.description.getClassName()
            def metodoGetInstanceItemStructure = 'getInstance' + a.description.getClassName()
            this."$metodoRecorrerItemStructure"(a.description, new_a.description = this."$metodoGetInstanceItemStructure"())
            recorrerDvParsable(a.timing, new_a.timing = new DvParsable())
            new_a.action_archetype_id = (a.action_archetype_id)
            recorrerLocatable(a, new_a) // Por ser subclase de Locatable
        }
    }

    //--------------------------------------------------------------------------

    // ADMIN_ENTRY
    static def recorrerAdminEntry(ae, AdminEntry new_ae){
        if (ae != null){
            def metodoRecorrerItemStructure = 'recorrer' + ae.data.getClassName()
            def metodoGetInstanceItemStructure = 'getInstance' + ae.data.getClassName()
            this."$metodoRecorrerItemStructure"(ae.data, new_ae.data = this."$metodoGetInstanceItemStructure"())
            recorrerEntry(ae, new_ae) // Por ser subclase de Entry
        }
    }

    static def getInstanceAdminEntry(){
        return new AdminEntry()
    }

    //--------------------------------------------------------------------------

     // CARE_ENTRY
    static def recorrerCareEntry(ce, CareEntry new_ce){
        if (ce != null){
            if (ce.protocol != null){
                def metodoRecorrerItemStructure = 'recorrer' + ce.protocol.getClassName()
                def metodoGetInstanceItemStructure = 'getInstance' + ce.protocol.getClassName()
                this."$metodoRecorrerItemStructure"(ce.protocol, new_ce.protocol = this."$metodoGetInstanceItemStructure"())
            }
            recorrerEntry(ce, new_ce) // Por ser subclase de Entry
        }
    }

    static def getInstanceCareEntry(){
        return new CareEntry()
    }

    //--------------------------------------------------------------------------

    // ENTRY
    static def recorrerEntry(e, Entry new_e){
        if (e != null){
            recorrerCodePhrase(e.language, new_e.language = new CodePhrase())
            recorrerCodePhrase(e.encoding, new_e.encoding = new CodePhrase())
            recorrerContentItem(e, new_e) // Por ser subclase de ContentItem
        }
    }

    //--------------------------------------------------------------------------

    // EVALUATION
    static def recorrerEvaluation(e, Evaluation new_e){
        if (e != null){
            def metodoRecorrerItemStructure = 'recorrer' + e.data.getClassName()
            def metodoGetInstanceItemStructure = 'getInstance' + e.data.getClassName()
            this."$metodoRecorrerItemStructure"(e.data, new_e.data = this."$metodoGetInstanceItemStructure"())
            recorrerEntry(e, new_e) // Por ser subclase de CareEntry
        }
    }

    static def getInstanceEvaluation(){
        return new Evaluation()
    }

    //--------------------------------------------------------------------------

    // INSTRUCTION
    static def recorrerInstruction(i, Instruction new_i){
        if (i != null){
            recorrerDvText(i.narrative, new_i.narrative = new DvText())
            recorrerDvDateTime(i.expiryTime, new_i.expiryTime = new DvDateTime())
            recorrerDvParsable(i.wfDefinition, new_i.wfDefinition = new DvParsable())
            if (i.activities != null){
                i.activities.each{elem ->
                    Activity new_elem = null
                    recorrerActivity(elem, new_elem = new Activity())
                    new_i.addToActivities(new_elem)
                }
            }
            recorrerCareEntry(i, new_i) // Por ser subclase de CareEntry
        }
    }

    static def getInstanceInstruction(){
        return new Instruction()
    }

    //--------------------------------------------------------------------------

    // OBSERVATION
    static def recorrerObservation(o, Observation new_o){
        if (o != null){
            recorrerHistory(o.data, new_o.data = new History())
            recorrerCareEntry(o, new_o) // Por ser subclase de CareEntry
        }
    }

    static def getInstanceObservation(){
        return new Observation()
    }

    //--------------------------------------------------------------------------
    // hce.core.composition.content.navigation
    //--------------------------------------------------------------------------

    // SECTION
    static def recorrerSection(s, Section new_s){
        if (s != null){
            if (s.items != null){
                s.items.each{elem ->
                    def metodoRecorrerContentItem = 'recorrer' + elem.getClassName()
                    def metodoGetInstanceContentItem = 'getInstance' + elem.getClassName()
                    def new_elem = null
                    this."$metodoRecorrerContentItem"(elem, new_elem = this."$metodoGetInstanceContentItem"())
                    new_s.addToItems(new_elem)
                }
            }
            recorrerContentItem(s, new_s) // Por ser subclase de ContentItem
        }
    }

    static def getInstanceSection(){
        return new Section()
    }

    //--------------------------------------------------------------------------
    // hce.core.composition.content
    //--------------------------------------------------------------------------

    // CONTENT_ITEM
    static def recorrerContentItem(ci, ContentItem new_ci){
        if (ci != null){
            recorrerLocatable(ci, new_ci) // Por ser subclase de Locatable
        }
    }

    //--------------------------------------------------------------------------
    // hce.core.composition
    //--------------------------------------------------------------------------

    // EVENT_CONTEXT
    static def recorrerEventContext(ec, EventContext new_ec){
        if (ec != null){
            recorrerDvDateTime(ec.startTime, new_ec.startTime = new DvDateTime())
            recorrerDvDateTime(ec.endTime, new_ec.endTime = new DvDateTime())
            new_ec.location = (ec.location)
            recorrerDvCodedText(ec.setting, new_ec.setting = new DvCodedText())
            if (ec.participations != null){
                ec.participations.each{elem ->
                    Participation new_elem = null
                    recorrerParticipation(elem, new_elem = new Participation())
                    new_ec.addToParticipations(new_elem)
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    // hce.core.data_types.basic
    //--------------------------------------------------------------------------

    // DV_BOOLEAN
    static def recorrerDvBoolean(dvb, DvBoolean new_dvb){
        if (dvb != null){
            new_dvb.value = (dvb.value)
        }
    }

    static def getInstanceDvBoolean(){
        return new DvBoolean()
    }

    //--------------------------------------------------------------------------

    // DV_IDENTIFIER
    static def recorrerDvIdentifier(dvi, DvIdentifier new_dvi){
        if (dvi != null){
            new_dvi.assigner = (dvi.assigner)
            new_dvi.code = (dvi.code)
            new_dvi.issuer (dvi.issuer)
        }
    }

    static def getInstanceDvIdentifier(){
        return new DvIdentifier()
    }

    //--------------------------------------------------------------------------

    // DV_IDENTIFIER
    static def recorrerDvState(dvs, DvState new_dvs){
        if (dvs != null){
            new_dvs.terminal = (dvs.terminal)
            recorrerDvCodedText(dvs.value, dvs.value = new DvCodedText())
        }
    }

    static def getInstanceDvState(){
        return new DvState()
    }

    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hce.core.data_types.encapsulated
    //--------------------------------------------------------------------------

    //DV_ENCAPSULATED
    static def recorrerDvEncapsulated(e, DvEncapsulated new_e){
        if (e != null){
            recorrerCodePhrase(e.charset, new_e.charset = new CodePhrase())
            recorrerCodePhrase(e.language, new_e.language = new CodePhrase())
            new_e.size = (e.size)
        }
    }

    //DV_MULTIMEDIA
    static def recorrerDvMultimedia(dvmm, DvMultimedia new_dvmm){
        if (dvmm != null){
            new_dvmm.alternateText = (dvmm.alternateText)
            recorrerCodePhrase(dvmm.mediaType, new_dvmm.mediaType = new CodePhrase())
            recorrerCodePhrase(dvmm.compressionAlgorithm, new_dvmm.compressionAlgorithm = new CodePhrase())
            new_dvmm.integrityCheck = (dvmm.integrityCheck) // byte[]
            recorrerCodePhrase(dvmm.integrityCheckAlgorithm, new_dvmm.integrityCheckAlgorithm = new CodePhrase())
            recorrerDvMultimedia(dvmm.thumbnail, new_dvmm.thumbnail = new DvMultimedia())
            recorrerDvURI(dvmm.uri, new_dvmm.uri = new DvURI())
            new_dvmm.data = (dvmm.data) // byte[]
            recorrerDvEncapsulated(dvmm, new_dvmm) // Por ser subclase de DvEncapsulated
        }
    }

    static def getInstanceDvMultimedia(){
        return new DvMultimedia()
    }

    //--------------------------------------------------------------------------

    // DV_PARSEABLE
    static def recorrerDvParsable(dvp, DvParsable new_dvp){
        if (dvp != null){
            new_dvp.value = (dvp.value)
            new_dvp.formalism = (dvp.formalism)
            recorrerDvEncapsulated(dvp, new_dvp) // Por ser subclase de DvEncapsulated
        }
    }

    static def getInstanceDvParsable(){
        return new DvParsable()
    }

    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hce.core.data_types.quantity.date_time
    //--------------------------------------------------------------------------

    // DV_DATE
    static def recorrerDvDate(dvd, DvDate new_dvd){
        if (dvd != null){
            recorrerDvTemporal(dvd, new_dvd) // Por ser subclase de DvTemporal
        }
    }

    static def getInstanceDvDate(){
        return new DvDate()
    }

    //--------------------------------------------------------------------------

    // DV_DATE_TIME
    static def recorrerDvDateTime(dvdt, DvDateTime new_dvdt){
        if (dvdt != null){
            recorrerDvTemporal(dvdt, new_dvdt) // Por ser subclase de DvTemporal
        }
    }

    static def getInstanceDvDateTime(){
        return new DvDateTime()
    }

    //--------------------------------------------------------------------------

    // DV_TIME
    static def recorrerDvTime(dvt, DvTime new_dvt){
        if (dvt != null){
            recorrerDvTemporal(dvt, new_dvt) // Por ser subclase de DvTemporal
        }
    }

    static def getInstanceDvTime(){
        return new DvTime()
    }

    //--------------------------------------------------------------------------

    // DV_DURATION
    static def recorrerDvDuration(dvd, DvDuration new_dvd){
        if (dvd != null){
            new_dvd.value = (dvd.value)
            recorrerDvAmount(dvd, new_dvd) // Por ser subclase de DvAmount
        }
    }

    static def getInstanceDvDuration(){
        return new DvDuration()
    }

    //--------------------------------------------------------------------------

    // DV_TEMPORAL
    static def recorrerDvTemporal(dvt, DvTemporal new_dvt){
        if (dvt != null){
            new_dvt.value = (dvt.value)
        }
    }

    static def getInstanceDvTemporal(){
        return new DvTemporal()
    }

    //--------------------------------------------------------------------------
    // hce.core.data_types.quantity
    //--------------------------------------------------------------------------

    // DV_ABSOLUTE_QUANTITY
    static def recorrerDvAbsoluteQuantity(dvaq, DvAbsoluteQuantity new_dvaq){
        if (dvaq != null){
            recorrerDvAmount(dvaq.accuracy, new_dvaq.accuracy = new DvAmount())
            recorrerDvQuantified(dvaq, new_dvaq) // Por ser subclase de DvQuantified
        }
    }

    static def getInstanceDvAbsoluteQuantity(){
        return new DvAbsoluteQuantity()
    }

    //--------------------------------------------------------------------------

    // DV_AMOUNT
    static def recorrerDvAmount(dva, DvAmount new_dva){
        if (dva != null){
            new_dva.accuracy = (dva.accuracy)
            new_dva.accuracyIsPercent = (dva.accuracyIsPercent)
            recorrerDvQuantified(dva, new_dva) // Por ser subclase de DvQuantified
        }
    }

    static def getInstanceDvAmount(){
        return new DvAmount()
    }

    //--------------------------------------------------------------------------

    // DV_COUNT
    static def recorrerDvCount(dc, DvCount new_dc){
        if (dc != null){
            new_dc.magnitude = (dc.magnitude)
            recorrerDvAmount(dc, new_dc) // Por ser subclase de DvAmount
        }
    }

    static def getInstanceDvCount(){
        return new DvCount()
    }

    //--------------------------------------------------------------------------

    // DV_INTERVAL
    static def recorrerDvInterval(dvi, DvInterval new_dvi){
        if (dvi != null){
            recorrerInterv(dvi.interv, new_dvi.interv = new Interv())
        }
    }

    static def getInstanceDvInterval(){
        return new DvInterval()
    }

    //--------------------------------------------------------------------------

    // DV_ORDERED
    static def recorrerDvOrdered(dvo, DvOrdered new_dvo){
        if (dvo != null){
            recorrerDvInterval(dvo.normalRange, new_dvo.normalRange = new DvInterval())
            if (dvo.otherReferenceRanges != null){
                dvo.otherReferenceRanges.each{elem ->
                    ReferenceRange new_elem = null
                    recorrerReferenceRange(elem, new_elem = new ReferenceRange())
                    new_dvo.addToOtherReferenceRanges(new_elem)
                }
            }
        }
    }

    static def getInstanceDvOrdered(){
        return new DvOrdered()
    }

    //--------------------------------------------------------------------------

    // DV_ORDINAL
    static def recorrerDvOrdinal(dvo, DvOrdinal new_dvo){
        if (dvo != null){
            new_dvo.value = (dvo.value)
            recorrerDvCodedText(dvo.symbol, new_dvo.symbol = new DvCodedText())
            new_dvo.limitsIndex = (dvo.limitsIndex)
            recorrerDvOrdered(dvo, new_dvo) // Por ser subclase de DvOrdered
        }
    }

    static def getInstanceDvOrdinal(){
        return new DvOrdinal()
    }

    //--------------------------------------------------------------------------

    // DV_PROPORTION
    static def recorrerDvProportion(dvp, DvProportion new_dvp){
        if (dvp != null){
            new_dvp.numerator = (dvp.numerator)
            new_dvp.denominator = (dvp.denominator)
            new_dvp.precision = (dvp.precision)
            recorrerDvAmount(dvp, new_dvp) // Por ser subclase de DvAmount
        }
    }

    static def getInstanceDvProportion(){
        return new DvProportion()
    }

    //--------------------------------------------------------------------------

    static def recorrerDvQuantified(dvq, DvQuantified new_dvq){
        if (dvq != null){
            recorrerDvOrdered(dvq, new_dvq) // Por ser subclase de DvOrdered
        }
    }

    static def getInstanceDvQuantified(){
        return new DvQuantified()
    }

    //--------------------------------------------------------------------------

    // DV_QUANTITY
    static def recorrerDvQuantity(dvq, DvQuantity new_dvq){
        if (dvq != null){
            new_dvq.magnitude = (dvq.magnitude)
            new_dvq.precision = (dvq.precision)
            new_dvq.units = (dvq.units)
            recorrerDvAmount(dvq, new_dvq) // Por ser subclase de DvAmount
        }
    }

    static def getInstanceDvQuantity(){
        return new DvQuantity()
    }

    //--------------------------------------------------------------------------

    // REFERENCE_RANGE
    static def recorrerReferenceRange(rr, ReferenceRange new_rr){
        if (rr != null){
            recorrerDvInterval(rr.range, new_rr.range = new DvInterval())
        }
    }

    static def getInstanceReferenceRange(){
        return new ReferenceRange()
    }

    //--------------------------------------------------------------------------
    // hce.core.data_types.text
    //--------------------------------------------------------------------------

    // DV_CODED_TEXT
    static def recorrerDvCodedText(ct, DvCodedText new_ct){
        if (ct != null){
            recorrerCodePhrase(ct.definingCode, new_ct.definingCode = new CodePhrase())
            recorrerDvText(ct, new_ct) // Por ser subclase de DvText
        }
    }

    static def getInstanceDvCodedText(){
        return new DvCodedText()
    }

    //--------------------------------------------------------------------------

    // DV_TEXT
    static def recorrerDvText(t, DvText new_t){
        if (t != null){
            new_t.formatting = (t.formatting)
            recorrerDvURI(t.hyperlink, new_t.hyperlink = new DvURI())
            new_t.value = (t.value)
            if (t.mappings != null){
                t.mappings.each{elem ->
                    TermMapping new_elem = null
                    recorrerTermMapping(elem, new_elem = new TermMapping())
                    new_t.addToMappings(new_elem)
                }
            }
            recorrerCodePhrase(t.language, new_t.language = new CodePhrase())
            recorrerCodePhrase(t.encoding, new_t.encoding = new CodePhrase())
        }
    }

    static def getInstanceDvText(){
        return new DvText()
    }

    //--------------------------------------------------------------------------

    // TERM_MAPPING
    static def recorrerTermMapping(tm, TermMapping new_tm){
        if (tm != null){
            new_tm.match = (tm.match)
            recorrerDvCodedText(tm.purpose, new_tm.purpose = new DvCodedText())
            recorrerCodePhrase(tm.target, new_tm.target = new CodePhrase())
        }
    }

    static def getInstanceTermMapping(){
        return new TermMapping()
    }

    //--------------------------------------------------------------------------

    // CODE_PHRASE
    static def recorrerCodePhrase(cp, CodePhrase new_cp){
        if (cp != null){
            new_cp.codeString = (cp.codeString)
            recorrerTerminologyID(cp.terminologyId, new_cp.terminologyId = new TerminologyID())
        }
    }

    static def getInstanceCodePhrase(){
        return new CodePhrase()
    }

    //--------------------------------------------------------------------------

    // DV_PARAGRAPH
    static def recorrerDvParagraph(dvp, DvParagraph new_dvp){
        if (dvp != null){
            if (dvp.items != null){
                dvp.items.each{elem ->
                    DvText new_elem = null
                    recorrerDvText(elem, new_elem = new DvText())
                    new_dvp.addToItems(newElem)
                }
            }
        }
    }

    static def getInstanceDvParagraph(){
        return new DvParagraph()
    }

    //--------------------------------------------------------------------------
    // hce.core.data_types.uri
    //--------------------------------------------------------------------------

    // DV_EHRURI
    static def recorrerDvEHRURI(eu, DvEHRURI new_eu){
        if (eu != null){
            recorrerDvURI(eu, new_eu)
        }
    }

    static def getInstanceDvEHRURI(){
        return new DvEHRURI()
    }

    //--------------------------------------------------------------------------

    // DV_URI
    static def recorrerDvURI(u, DvURI new_u){
        if (u != null){
            new_u.value = (u.value)
        }
    }

    static def getInstanceDvURI(){
        return new DvURI()
    }

    //--------------------------------------------------------------------------
    // hce.core.datastructure.history
    //--------------------------------------------------------------------------

    // EVENT
    static def recorrerEvent(e, Event new_e){
        if (e != null){
            recorrerDvDateTime(e.time, new_e.time = new DvDateTime())
            def metodoRecorrerItemStructure = 'recorrer' + e.data.getClassName()
            def metodoGetInstanceItemStructure = 'getInstance' + e.data.getClassName()
            this."$metodoRecorrerItemStructure"(e.data, new_e.data = this."$metodoGetInstanceItemStructure"())
            recorrerDataStructure(e, new_e) // Por ser subclase de DataStructure
        }
    }

    static def getInstanceEvent(){
        return new Event()
    }

    //--------------------------------------------------------------------------

    // HISTORY
    static def recorrerHistory(h, History new_h){
        if (h != null){
            recorrerDvDateTime(h.origin, new_h.origin = new DvDateTime())
            recorrerDvDateTime(h.period, new_h.period = new DvDateTime())
            recorrerDvDateTime(h.duration, new_h.duration = new DvDateTime())
            if (h.events != null){
                h.events.each{elem ->
                    Event new_elem = null
                    recorrerEvent(elem, new_elem = new Event())
                    new_h.addToEvents(new_elem)
                }
            }
            recorrerDataStructure(h, new_h) // Por ser subclase de DataStructure
        }
    }

    static def getInstanceHistory(){
        return new History()
    }

    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hce.core.datastructure.itemstructure.representation
    //--------------------------------------------------------------------------

    // CLUSTER
    static def recorrerCluster(c, Cluster new_c){
        if (c != null){
            if (c.items != null){
                c.items.each{elem ->
                    def metodoRecorrerItem = 'recorrer' + elem.getClassName()
                    def metodoGetInstanceItem = 'getInstance' + elem.getClassName()
                    def new_elem = null
                    this."$metodoRecorrerItem"(elem, new_elem = this."$metodoGetInstanceItem"())
                    new_c.addToItems(new_elem)
                }
            }
            recorrerItem(c, new_c) // Por ser subclase de Item
        }
    }

    static def getInstanceCluster(){
        return new Cluster()
    }

    //--------------------------------------------------------------------------

    // ELEMET
    static def recorrerElement(e, Element new_e){
        if (e != null){
            recorrerDvCodedText(e.null_flavor, new_e.null_flavor = new DvCodedText())
            def metodoRecorrerDataValue = 'recorrer' + e.value.getClass().getSimpleName() //.getClassName()
            def metodoGetInstanceDataValue = 'getInstance' + e.value.getClass().getSimpleName() //.getClassName()
            this."$metodoRecorrerDataValue"(e.value, new_e.value = this."$metodoGetInstanceDataValue"())
            recorrerItem(e, new_e) // Por ser subclase de Item
        }
    }

    static def getInstanceElement(){
        return new Element()
    }

    //--------------------------------------------------------------------------

    // ITEM
    static def recorrerItem(i, Item new_i){
        if (i != null){
            recorrerLocatable(i, new_i) // Por ser subclase de Locatable
        }
    }

    static def getInstanceItem(){
        return new Item()
    }

    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hce.core.datastructure.itemstructure.representation
    //--------------------------------------------------------------------------

    // ITEM_LIST
    static def recorrerItemList(il, ItemList new_il){
        if (il != null){
            if (il.items != null){
                il.items.each{elem ->
                    Element new_elem = null
                    recorrerElement(elem, new_elem = new Element())
                    new_il.addToItems(new_elem)
                }
            }
            recorrerItemStructure(il, new_il) // Por ser subclase de ItemStructure
        }
    }

    static def getInstanceItemList(){
        return new ItemList()
    }

    //--------------------------------------------------------------------------

    // ITEM_SINGLE
    static def recorrerItemSingle(is, ItemSingle new_is){
        if (is != null){
            recorrerElement(is.item, new_is.item = new Element())
            recorrerItemStructure(is, new_is) // Por ser subclase de ItemStructure
        }
    }

    static def getInstanceItemSingle(){
        return new ItemSingle()
    }

    //--------------------------------------------------------------------------

    // ITEM_STRUCTURE
    static def recorrerItemStructure(is, ItemStructure new_is){
        if (is != null){
            recorrerDataStructure(is, new_is) // Por ser subclase de DataStructure
        }
    }

    //--------------------------------------------------------------------------

    // ITEM_TABLE
    static def recorrerItemTable(it, ItemTable new_it){
        if (it != null){
            if (it.items != null){
                it.items.each{elem ->
                    Cluster new_elem = null
                    recorrerCluster(elem, new_elem = new Cluster())
                    new_it.addToItems(new_elem)
                }
            }
            recorrerItemStructure(it, new_it) // Por ser subclase de ItemStructure
        }
    }

    static def getInstanceItemTable(){
        return new ItemTable()
    }

    //--------------------------------------------------------------------------

    // ITEM_TREE
    static def recorrerItemTree(it, ItemTree new_it){
        if (it != null){
            if (it.items != null){
                it.items.each{elem ->
                    def metodoRecorrerItem = 'recorrer' + elem.getClassName()
                    def metodoGetInstanceItem = 'getInstance' + elem.getClassName()
                    def new_elem = null
                    this."$metodoRecorrerItem"(elem, new_elem = this."$metodoGetInstanceItem"())
                    new_it.addToItems(new_elem)
                }
            }
            recorrerItemStructure(it, new_it) // Por ser subclase de ItemStructure
        }
    }

    static def getInstanceItemTree(){
        return new ItemTree()
    }

    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hce.core.datastructure.itemstructure
    //--------------------------------------------------------------------------

    // DATA_STRUCTURE
    static def recorrerDataStructure(ds, DataStructure new_ds){
        if (ds != null){
            recorrerLocatable(ds, new_ds)
        }
    }

    static def getInstanceDataStructure(){
        return new DataStructure()
    }

    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hce.core.support.basic
    //--------------------------------------------------------------------------

    // INTERV
    static def recorrerInterv(i, Interv new_i){
        if (i != null){
            recorrerDvOrdered(i.lower, new_i.lower = new DvOrdered())
            recorrerDvOrdered(i.upper, new_i.upper = new DvOrdered())
            new_i.lowerIncluded = (i.lowerIncluded)
            new_i.upperIncluded = (i.upperIncluded)
        }
    }

    static def getInstanceInterv(){
        return new Interv()
    }

    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hce.core.support.definition
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hce.core.support.identification
    //--------------------------------------------------------------------------

    // ARCHETYPE_ID
    static def recorrerArchetypeID(ai, ArchetypeID new_ai){
        if (ai != null){
            new_ai.qualifiedRmEntity = (ai.qualifiedRmEntity)   // calculated
            new_ai.rmOriginator = (ai.rmOriginator)
            new_ai.rmName = (ai.rmName)
            new_ai.rmEntity = (ai.rmEntity)
            new_ai.domainConcept = (ai.domainConcept)       // calculated
            new_ai.conceptName = (ai.conceptName)
            new_ai.specialisation = (ai.specialisation) // List<String>
            new_ai.versionID = (ai.versionID)
            recorrerObjectID(ai, new_ai)
        }
    }

    static def getInstanceArchetypeID(){
        return new ArchetypeID()
    }

    //--------------------------------------------------------------------------

    // OBJECT_ID
    static def recorrerObjectID(oi, ObjectID new_oi){
        if (oi != null){
            new_oi.value = (oi.value)
            //recorrerRMObject(oi, new_oi)
        }
    }

    static def getInstanceObjectID(){
        return new ObjectID()
    }

    //--------------------------------------------------------------------------

    // OBJECT_REF
    static def recorrerObjectRef(or, ObjectRef new_or){
        if (or != null){
            new_or.type = (or.type)
            recorrerObjectID(or.objectId, new_or.objectId = new ObjectID())
        }
    }

    static def getInstanceObjectRef(){
        return new ObjectRef()
    }

    //--------------------------------------------------------------------------

    // PARTY_REF
    static def recorrerPartyRef(pr, PartyRef new_pr){
        if (pr != null){
            recorrerObjectRef(pr, new_pr) // Por ser subclase de ObjectRef
        }
    }

    static def getInstancePartyRef(){
        return new PartyRef()
    }

    //--------------------------------------------------------------------------

    // TEMPLATE_ID
    static def recorrerTemplateID(ti, TemplateID new_ti){
        if (ti != null){
            recorrerObjectID(ti, new_ti) // Por ser subclase de ObjectID
        }
    }

    static def getInstanceTemplateID(){
        return new TemplateID()
    }

    //--------------------------------------------------------------------------

    // TERMINOLOGY_ID
    static def recorrerTerminologyID(ti, TerminologyID new_ti){
        if (ti != null){
            new_ti.name = (ti.name)
            new_ti.versionId = (ti.versionId)
            recorrerObjectID(ti, new_ti) // Por ser subclase de ObjectID
        }
    }

    static def getInstanceTerminologyID(){
        return new TerminologyID()
    }

    //--------------------------------------------------------------------------

    // UID_BASE_ID
    static def recorrerUIDBasedID(uidbi, UIDBasedID new_uidbi){
        if (uidbi != null){
            recorrerObjectID(uidbi, new_uidbi) // Por ser subclase de ObjectID
        }
    }

    static def getInstanceUIDBasedID(){
        return new UIDBasedID()
    }

    //--------------------------------------------------------------------------

}

