/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imp
import cda.*
import hce.core.composition.Composition
import hce.core.common.change_control.Version
/**
 *
 * @author Armando
 */
class CdaRecords {




    public static String registrarCda(def id, def domainTemplates){

        def idEpisodio = id.toInteger()
        
        // Creo el archivo CDA
        def cdaMan = new ManagerCDA()
        cdaMan.createFileCDA(idEpisodio, domainTemplates)

        def composition = Composition.get(idEpisodio)

        def version = Version.findByData(composition)
        
       // println version.nombreArchCDA

        return version.nombreArchCDA

       // redirect(controller: "service", action: "registrarCda", id: version.nombreArchCDA)
       



    }

   /* def eliminarCda = {


        
    }*/



	
}

