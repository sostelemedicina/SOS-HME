package hce.core.datastructure.itemstructure

import hce.core.datastructure.DataStructure

// prueba
import hce.core.composition.content.entry.Activity
import hce.core.composition.content.entry.Action
import hce.core.composition.content.entry.Evaluation
import hce.core.composition.content.entry.AdminEntry
import hce.core.datastructure.history.Event
import hce.core.composition.EventContext

class ItemStructure extends DataStructure  {
   
    static constraints = {
    }
    
    // Pablo: con esto soluciono una excepcion que me tira al salvar un ItemStructure que esta adentro
    // de una Action o Evaluation que a su vez esta adentro de una Section, simplemente agrego aqui las
    // clases que tienen una relacion hacia ItemStructure y tienen un nivel igual o superior en la
    // jerarquia en el RM.
    static belongsTo = [Activity, Action, Evaluation, Event, AdminEntry, EventContext]
}
