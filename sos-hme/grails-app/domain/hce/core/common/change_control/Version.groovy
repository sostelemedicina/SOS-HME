package hce.core.common.change_control;

import hce.core.support.identification.ObjectRef;
import hce.core.common.archetyped.Locatable;
import hce.core.support.identification.ObjectVersionID; // Todavia no se usa para nada, se usaria cuando se implemente el 'change control'
import hce.core.data_types.quantity.date_time.DvDateTime;
import hce.core.common.generic.AuditDetails;
import hce.core.common.generic.*;

class Version {
    
    // FIXME: Por ahora y para simplificar, los lifecycleStates los defino aca, deberian estar en la terminologia openehr.
    // Estos corresponden con los estados Nuevo registro, Registro completo y Registro firmado de la maquina de estados
    // del registro clinico.
    static String STATE_INCOMPLETE = 'ehr.lifecycle.incomplete'
    static String STATE_COMPLETE = 'ehr.lifecycle.complete'
    static String STATE_SIGNED = 'ehr.lifecycle.signed'

    // FIXME: Este atributo es en realidad un atributo de AuditDetails.
    // Esta clase Version tiene una relacion a un AuditDetails que no esta 
    // implementada pero es necesario el atributo de la fecha para saber
    // cuando fue creada la nueva version, y asi saber cual es la ultima.
    DvDateTime timeCommited
    PartyProxy committer //responsable del registro, se guarda cuando la versión no es la actual y 'data' se setea en NULL
    PartyProxy partySelf //paciente del registro, se guarda cuando la versión no es la actual y 'data' se setea en NULL

    boolean inIMP = false//Si esta registrado en el IMP, 'true' en caso afirmativo, 'false' en caso negativo

    // FIXME: Por ahora se seguira actualizando el atributo timeCommited, pero luego
    // seria bueno dejarlo de utilizar y quitarlo ya que AuditDetails tiene un atributo
    // con el mismo proposito: time_committed.
    AuditDetails commit_audit
    
    String signature
    ObjectRef contribution
    
    // En la definicion es DvCodedText, la simplifico.
    // Los valores posibles son los de la terminologica openehr de lifecycleState
    /*
     * <group name="version lifecycle state">
		<concept id="532" rubric="complete"/>
		<concept id="553" rubric="incomplete"/>
		<concept id="523" rubric="deleted"/>
	   </group>
     */
    String lifecycleState = STATE_INCOMPLETE
    
    String canonicalForm // serializacion a string de data
    
    // Puede ser uno de FOLDER, COMPOSITION o PARTY
    // Por ahora solo se usa para composition
    Locatable data
    
    // No se va a usar todavia, se usara cuando se implemente el control de cambios.
    ObjectVersionID uid
    
    int numeroVers //Numero de la version
    String nombreArchCDA //nombre del archivo CDA asociado a la version

    static constraints = {
        uid(nullable:true)
        canonicalForm(nullable:true)
        signature(nullable:true)
        contribution(nullable:true)
        commit_audit(nullable:true)
        nombreArchCDA(nullable:true)
        data(nullable:true)
        committer(nullable:true)
        partySelf(nullable:true)

    }
    static mapping = {
        timeCommited cascade: "save-update"
        commit_audit cascade: "save-update"
        data cascade: "save-update"
        committer cascade: "save-update"
        data column: "version_data"
    }

    def getNumVersion(){
        return numeroVers
    }
}
