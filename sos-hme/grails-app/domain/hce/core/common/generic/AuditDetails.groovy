package hce.core.common.generic

import hce.core.data_types.quantity.date_time.*
import hce.core.data_types.text.*

class AuditDetails {

    // FIXME: Por ahora, para simplificar, los change_type los definimos aquí, deberían estar en la terminologia openehr.
    // Estos corresponden con los cambios por Correccion de Datos de Pacinete, Cambiod de Pacinete y Reapertura Episodio respectivamente.
    // del registro clinico.
    static String CHANGE_TYPE_CORRECCION_PACIENTE = 'ehr.version.change_type.correccionPacinete'
    static String CHANGE_TYPE_CAMBIO_PACIENTE = 'ehr.version.change_type.cambioPacinete'
    static String CHANGE_TYPE_REAPERTURA_EPISODIO = 'ehr.version.change_type.reaperturaEpisodio'

    String system_id            // Identity of the system where the change was committed. Ideally this is a machine- and human-processable identifier, but it may not be.
    PartyProxy committer        // Identity and optional reference into identity management service, of user who committed the item.
    DvDateTime time_committed   // Time of committal of the item.
    DvText description          // Reason for committal.
    //DvCodedText change_type   // Type of change. Coded using the openEHR Terminology “audit change type” group.
    String change_type          // FIXME: Representaremos al tipo de cambio (change_Type) con un String.
                                // Por Ahora manejamos los tipos de cambio definido como constantes:
                                // CHANGE_TYPE_CORRECCION_PACIENTE
                                // CHANGE_TYPE_CAMBIO_PACIENTE
                                // CHANGE_TYPE_REAPERTURA_EPISODIO
    static mapping = {
        committer : "save-update"
        time_committed: "save-update"
        description : "save-update"
    }

    static constraints = {
        system_id (nullable: false, blank:false)
        committer  (nullable: false)
        time_committed (nullable: false)
    }

}

