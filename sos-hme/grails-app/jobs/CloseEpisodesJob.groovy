
import hce.core.common.change_control.*
import hce.HceService
import hce.core.composition.* // Composition y EventContext
import hce.core.common.change_control.Version
import converters.DateConverter
import util.DateDifference

class CloseEpisodesJob {
   
   def hceService
   def grailsApplication
   
   def timeout = 260000l // execute job once in x miliseconds
   def startDelay = 60000l // empieza al minuto

   def execute()
   {
//      // Verifica si puedo o no ejecutar el job, dependiendo de la config
//      if ( grailsApplication.config.hce.close_record_job_on )
//      {
//         def versions = Version.findAllByLifecycleState( Version.STATE_INCOMPLETE )
//
//         versions.each { version ->
//
//            def composition = version.data
//            def startDate = composition.context.startTime.toDate()
//
//            // FIXME: hacerlo con DateDiference porque esto cuenta que cambie la fecha no que hayan pasadon 24hs...
//            def difDias = DateDifference.numberOfDays(startDate, new Date())
//            //println "Pasaron: " + (new Date() - startDate) + " dias"
//
//            println "... CloseEpisodes ..."
//            println "Pasaron: $difDias dias"
//
//            //if ( new Date() - startDate > 0 ) // da el resultado en dias
//            if ( difDias > 0 )
//            {
//               // Cerrar
//               hceService.closeComposition( composition, DateConverter.toIso8601ExtendedDateTimeFormat( new Date() ) )
//            }
//         }
//      }
   }
}
