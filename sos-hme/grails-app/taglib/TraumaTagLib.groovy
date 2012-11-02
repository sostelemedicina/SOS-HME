
import hce.core.composition.Composition
import hce.HceService 
import hce.CustomQueriesService
import authorization.LoginAuth
import demographic.DemographicService
import demographic.identity.PersonName
import demographic.party.Person
import hce.core.common.change_control.Version
import demographic.role.*
import java.text.DecimalFormat;

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class TraumaTagLib {
    
    def hceService
    def customQueriesService
    def demographicService
    
    //
    def menuSession = {  attrs, body ->
        
        if(attrs?.session?.traumaContext?.userId > -1){
           out << "<span class='menuButton menuButtonDerecha'>"
            out << "<a class='list' href='${createLink(controller:"authorization", action: "login")}'>"
            out << message(code:'authorization.action.logout')
            out << "</a>"
            out << "</span>"
        }else{
            out << "<span class='menuButton menuButtonDerecha'>"
            out << "<a class='list' href='${createLink(controller:"authorization", action: "logout")}'>"
            out << message(code:'auth.login.action.signin2')
            out << "</a>"
            out << "</span>"
        }
    }
    def person = { attrs ->

        def persona = hceService.getPatientFromComposition(attrs['param1'])
        if(persona){
            def name= persona.identities.find{ it.purpose == 'PersonNamePatient'}

            if(!name || !name.foto || !name.tipofoto){

                if(persona.sexo=='Masculino'){

                    out << "<img src='${createLinkTo(dir:"images", file:"man.png")}' style='width: 30px; height: auto;' /> "
                }else{


                    out << "<img src='${createLinkTo(dir:"images", file:"woman.png")}' style='width: 30px; height: auto;'  /> "
                }
            }else{
    
                out << "<img src='${createLink(controller:"demographic", action: "fotopaciente", params:[persona:persona.id])}' style='width: 30px; height: auto;'/> "
            }
        

            out << "<a href='demographic/show/"+ persona.id +"'>"
            out << (name?.primerNombre?:"") + " "+ (name?.segundoNombre?:"") +" "+ (name?.primerApellido?:"") + " "+(name?.segundoApellido?:"")
            out << "</a>"
        }else{
            out << "<img src='${createLinkTo(dir:"images", file:"siluetaPersona.png")}' style='width: 30px; height: auto;' /> "
            out << message(code:'records.list.paciente.false')


        }
    }

    // Viene archetypeId o (rmtype y idMarchingKey)
    // in: episodeId
    def resumenEpisodio = { attrs, body ->
        
        // TODO: esto deberia hacerse en un template y de aqui renderearlo nomas.
       
        def composition = Composition.get( attrs.episodeId )
        if (!composition)
        throw new Exception("No se encuentra el episodio con id " + attrs.episodeId + " @TraumaTagLib 1")

        //-----------------------------------------------------------------------
        // Datos para mostrar Triage
        //-----------------------------------------------------------------------
        def color = triageColor( composition ) 

        /*
        //-----------------------------------------------------------------------
        // Datos para mostrar RTS y RTSp
        //-----------------------------------------------------------------------
        def FR = customQueriesService.getFrecuenciaRespiratoriaEpisodio(composition)
        def PAS = customQueriesService.getPresionArterialSistolicaEpisodio(composition)
        def GCS = customQueriesService.getGlasgowComaScaleEpisodio(composition)
        def RTS = customQueriesService.getRTSEpisodio(composition)
        def RTSp = customQueriesService.getRTSpEpisodio(composition) 
  
        def resultRTS
        def resultRTSp
        if (RTS != null){ 
        DecimalFormat formateador = new DecimalFormat("####.##")
        resultRTS = message(code:'trauma.list.label.RTS') + ': ' + RTS.intValue() + ' (FR = ' + FR.doubleValue() + ', PAS = ' + PAS.doubleValue() + ', GCS = ' + GCS.doubleValue() + ')'
        resultRTSp = message(code:'trauma.list.label.RTSp') + ': ' + formateador.format(RTSp.doubleValue())
        }
        else{
        resultRTS = message(code:'trauma.list.label.FaltanDatosParaCalcularRTS')
        resultRTSp = message(code:'trauma.list.label.FaltanDatosParaCalcularRTSp')
        }
         */

        //-----------------------------------------------------------------------
        //-----------------------------------------------------------------------

        out << "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"
        out << "<tr>"
        out << "<td width='15%' valign='top'><h2>"+message(code:'trauma.label.resumenEpisodio')+"</h2></td>"
        out << "<td width='30%' valign='top'> <span class='negritas'>"+message(code:'trauma.list.label.startTime')+": </span>"+g.formatDate( date: composition.context.startTime?.toDate(), formatName: 'default.date.format.text' )+" <br />"
        out << "<span class='negritas'>"+message(code:'trauma.list.label.endTime') +": </span>"+g.formatDate( date: composition.context.endTime?.toDate(),formatName: 'default.date.format.text' )+"</td>"
        out << "<td valign='top'><p>"+message(code:'trauma.list.label.observations')+":</p>"
        out << "<p class='info'>"+composition.context.otherContext.item.value.value+"</p></td>"
       
        out  << "</tr>"
        out  << "<tr>"

        
        // RESPONSABLE
        // Si han firmado, mostrar el responsable de la atencion.
        if (composition.composer)
        {
            def persons = demographicService.findPersonUserById( composition.composer.externalRef.objectId )
            
			
            def responsable
            if (persons.size() == 0)
            {
                println "nadaa!!!"
                // no hago nada, no se encuentra el responsable, aca hay un error de consistencia de datos en el sistema!
            }
            else if (persons.size() == 1)
            {
                responsable = persons[0]
            }
            else
            {
                // TODO: en teoria no deberia pasar pero en ningun lugar hay una restriccion explicita de no tener 2 pacientes con un id comun, hay que probar.
                println persons
                throw new Exception('Se encuentran: ' + persons.size() + ' personas a partir del ID: '+ composition.composer.externalRef.objectId )
            }
            
            println "responsable2: "+responsable
            // TODO: mostrar un identificador
            def nombres = responsable.identities.find{ it.purpose == 'PersonNameUser' }
            
            out << "<td width='15%' valign='top'>"
            out << "<h2>"+ message(code:'trauma.list.label.composer')+"</h2></td><td>Dr. " +
            ((nombres.primerNombre) ? (nombres.primerNombre + ' ') : '') +
            ((nombres.segundoNombre) ? (nombres.segundoNombre + ' ') : '') +
            ((nombres.primerApellido) ? (nombres.primerApellido + ' ') : '') +
            ((nombres.segundoApellido) ? (nombres.segundoApellido) : '')
                
            out << '</td>'
        }
        // /RESPONSABLE
        out  << "</tr>"
        out <<   '</div>' // /comienzo-fin-observaciones-responsable
        
       

        
        // Para mostrar el color del triage si es que ya fue registrado.
        if (color)
        {
            // Separacion entre Comienxo-Final y Triage
            //out << '<div style="float: left;">&nbsp;&nbsp;&nbsp;</div>'
            // Triage
            out << '<div style="float: left; border-style: solid; border-color: #000; border-width: 1px; text-align: center; padding: 16px; padding-left: 20px; padding-right: 20px; background-color:'+color+';">T</div>'
            
            //out << '<div style="float: left;">&nbsp;&nbsp;&nbsp;</div>'
        }
        
        out << '</div>' // resumen_episodio

       
        out << "</table>"
    }
    
    
    /**
     * userId es el identificador del LoginAuth en session.
     */
    def datosUsuario = { attrs, body ->    
        
        if (!attrs.userId)
        throw new Exception("TraumaTagLib.datosUsuario: userId es obligatorio y no vino")
        
        def login = LoginAuth.get( attrs.userId ) // TODO: puedo sacar el userId de session.traumaContext.userId

        // FIXME:
        // Falla porque me dice que la identity que tiene adentro tiene clase PartyIdentity_$$_javassist_123
        //def personName = login.person.identities.find{ it instanceof PersonName }

        // FIX rapido, se que tiene una sola identidad y es PersonName, quiero esa...
        def personName = login.person.identities.find{ true }
        // Si no seria: def nombres = person.identities.find{ it.purpose == 'PersonName' }
        
        //println "xxxxxxxxxxxxxxxxxxxxx"
        //login.person.identities.each { println it.getClass() }
        //println "xxxxxxxxxxxxxxxxxxxxx"
        
        // FIXME: i18n
        // FIXME: usar un template para mostrar el Person Name
        if (login.person.sexo == Person.SEXO_FEMENINO)
        {
            out << "${message(code:'default.bienvenida.femenino')}"
        }
        else
        {
            out << "${message(code:'default.bienvenida.masculino')}"
        }
        out << g.link(controller:"loginAuth",action:"show",id:attrs.userId, personName.primerNombre + " " + personName.primerApellido)
    }
    
    /**
     * TagLib condicional, si el component tiene el item para el template, ertorna el body.
     * in: templateId
     * in: episodeId (composition.id)
     */
    def hasContentItemForTemplate = { attrs, body ->
        
        def composition = Composition.get( attrs.episodeId )
        if (!composition)
        throw new Exception("No se encuentra el episodio con id " + attrs.episodeId + " @TraumaTagLib 2")
        
        if (!attrs.templateId)
        throw new Exception("El templateId es obligatorio @TraumaTagLib 2")
        
        def item = hceService.getCompositionContentItemForTemplate(composition, attrs.templateId)

        // Mando un boolean como var para que en la vista pueda discutir si hay o no un item en la composition.
        //out << body(item!=null)
        
        out << body( hasItem:(item!=null), itemId:item?.id)
    }
    
    /**
     * Imprime el body si la composition actual tiene paciente asignado
     */
    def compositionHasPatient = { attrs, body ->
        
        def composition = Composition.get( attrs.episodeId )
        if (!composition) return
        
        // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
        if ( hceService.getPatientFromComposition( composition ) )
        {
            out << body()
        }
    }
    def compositionHasNotPatient = { attrs, body ->
        
        def composition = Composition.get( attrs.episodeId )
        if (!composition) return
        
        // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
        if ( !hceService.getPatientFromComposition( composition ) )
        {
            out << body()
        }
    }
    
    def isIncompleteRecord = { attrs, body ->
        
        def composition = Composition.get( attrs.episodeId )
        def version = Version.findByData( composition )
        out << body( answer: (version.lifecycleState == Version.STATE_INCOMPLETE) )
    }
    
    /**
     * Verifica la condicion para poder firmar el registro.
     * La condicion es que el registro este completo, esta condicion 
     * se da cuando se mueve al paciente y hay un paciente seleccionado
     * para la composition. Dicha condicion es verificada desde el
     * event handler VerificarCondicionCierre.
     */
    /* Desde que el cerrado y firmado del registro lo hace explicitamente el medico,
     * esta tag no tiene sentido. Ver: http://code.google.com/p/open-ehr-gen-framework/issues/detail?id=9
    def canSignRecord = { attrs, body ->
        
    def composition = Composition.get( attrs.episodeId )
    def version = Version.findByData( composition )
    //def item = hceService.getCompositionContentItemForTemplate( composition, "COMUNES-movimiento_paciente" )
    //if (item)
    if (version.lifecycleState == Version.STATE_COMPLETE)
    out << body()
    }
     */
    def isNotSignedRecord = { attrs, body ->
       
        def composition = Composition.get( attrs.episodeId )
        if (!composition.composer)
        out << body()
    }
    
    def isSignedRecord = { attrs, body ->
        
        def composition = Composition.get( attrs.episodeId )
        if (composition.composer)
        out << body()
    }
    
    def reabrirEpisodio = { attrs, body ->
        def composition = Composition.get( attrs.episodeId )
        def version = Version.findByData( composition )
        if (version.lifecycleState == Version.STATE_SIGNED){
            out << body()
        }
    }

    def stateForComposition = { attrs, body ->
        
        def composition = Composition.get( attrs.episodeId )
        def version = Version.findByData( composition )
        
        //println "=========================================================="
        //println "Lifecycle state: " + version.lifecycleState.getClass()
        // BUG de Grails 1.2: aunque retorno string, a la vista llega org.codehaus.groovy.grails.web.util.StreamCharBuffer
        out << version.lifecycleState
    }
    
    
    /**
     * Para mostrar un color en la web respecto al codigo del triage.
     */
    private String triageColor( Composition composition )
    {
        def triageCode = customQueriesService.getTriageClasification( composition )

        if (triageCode)
        {
            switch(triageCode)
            {
                // Los codigos estan en el arquetipo de triage de trauma.
                case 'at0003':
                return '#55cc55'
                break;
                case 'at0004':
                return '#dddd55'
                break;
                case 'at0005':
                return '#ff5555'
                break;
                case 'at0006':
                return'#cccccc'
                break;
                case 'at0007':
                return '#333333'
                break;
            }
        }
    }
    
    /**
     * Muestra el body si puede mostrar el registro clinico.
     * En si solo los medicos y enfermeras pueden acceder al registro clinico,
     * otros roles como los administrativos no podran accederlo.
     */
    def canFillClinicalRecord = { attrs, body ->

        def login = LoginAuth.get( session.traumaContext.userId )

        // Roles de la persona
        def roles = Role.withCriteria {
            eq('performer', login.person)
        }
        
        def roleKeys = roles.type
        
        if ( roleKeys.intersect([Role.MEDICO,Role.ENFERMERIA]).size() > 0 )
        out << body()
    }

    def canFillClinicalImp = { attrs, body ->

        def login = LoginAuth.get( session.traumaContext.userId )

        // Roles de la persona
        def roles = Role.withCriteria {
            eq('performer', login.person)
        }
        
        def roleKeys = roles.type
        
        if ( roleKeys.intersect([Role.MEDICO]).size() > 0 )
        out << body()
    }	
	
    def canFillAdmin = { attrs, body ->

        def login = LoginAuth.get( session.traumaContext.userId )

        // Roles de la persona
        def roles = Role.withCriteria {
            eq('performer', login.person)
        }

        def roleKeys = roles.type

        if ( roleKeys.intersect([Role.ADMIN]).size() > 0 )
        out << body()
    }
    def canNotFillAdmin = { attrs, body ->

        def login = LoginAuth.get( session.traumaContext.userId )

        // Roles de la persona
        def roles = Role.withCriteria {
            eq('performer', login.person)
        }

        def roleKeys = roles.type

        if ( roleKeys.intersect([Role.ADMIN]).size() <= 0 )
        out << body()
    }
    


    def langSelector = { attrs, body ->
        
        grailsApplication.config.langs.each {
            out << body(it) 
        }
    }
    
    def canEditPatient = { attrs, body ->
        
        // Puedo editar si:
        //   1. le faltan datos
        //   2. la base es local
        if ( !demographicService.personHasAllData( attrs.patient ) && grailsApplication.config.hce.patient_administration.serviceType.local )
        out << body()
    }
}
