package hce;

import hce.core.composition.Composition
import hce.HceService
import hce.core.datastructure.itemstructure.ItemTree
import hce.core.datastructure.itemstructure.representation.*
import hce.core.data_types.text.*
import hce.core.data_types.basic.*
import util.*
import com.thoughtworks.xstream.XStream


/**
 * Este servicio contiene operaciones que dependen de un arquetipo particular y
 * una version particular del arquetipo, tambien pueden depender de un template
 * particular de trauma, por lo cual no son para uso de cualquier aplicacion que
 * se desarrolle sobre el framework, son particulares para los arquetipos de trauma.
 * 
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */

class CustomQueriesService {
    
    def hceService
    
    /**
     * Devuelve el codigo de la evaluacion de triage para el
     * episodio dado. Devuelve null si todavia no se ha hecho
     * el triage.
     * Los codigos de triage estan en el arquetipo:
     *   - openEHR-EHR-EVALUATION.triage_trauma.v1.adl
     * 
     * Se utiliza desde TraumaTagLib para mostrar el color del
     * triage en el resumen del episodio que se muestra arriba
     * en las pantallas de registro.
     * 
     * @param composition episodio de donde sacar el triage
     * @return codigo de clasificacion del triage
     */
    def getTriageClasification( Composition composition )
    {
        def triageEvaluation = hceService.getCompositionContentItemForTemplate( composition, "INGRESO-triage" )

        // Si no se ha registrado el triage
        if (!triageEvaluation) return null
        
        //XStream xstream = new XStream()
        //println xstream.toXML(triageEvaluation)
        
        def triageElement = triageEvaluation.data.items.find{ it.path.endsWith('[at0002]')}
        
        //println xstream.toXML(triageElement)
        
        // element.value es DvOrdinal
        // .symbol es DvCodedText
        // .definingCode es CodePhrase
        if (triageElement)
            return triageElement.value.symbol.definingCode.codeString
        
        return null
    }

    //--------------------------------------------------------------------------

    //(Composition).[(ContentItem)content](Instruction).[(Activity)activities].(ItemTree)description.[(Item)items](Element).(DvCodedText)value.(CodePhrase)definingCode.(String)codeString == at0008

    /**
     * Retorna la lista de todos los codeString correspondientes a los movimientos de un episodio
     * @autor Leandro Carrasco
     **/
    List<String> getMovimientosEpisodio(Composition comp){

        List<String> listaMovimientos = new LinkedList<String>()
        def movimientoPaciente = hceService.getCompositionContentItemForTemplate( comp, "COMUNES-movimiento_paciente" )

        // Si no se ha registrado movimiento de Paciente
        if (!movimientoPaciente) return listaMovimientos

        //(Composition).[(ContentItem)content](Instruction).[(Activity)activities].(ItemTree)description.[(Item)items](Element).(DvCodedText)value.(CodePhrase)definingCode.(String)codeString == at0008

        // movimientoPaciente es una Instruction
        movimientoPaciente.activities.each{act ->
            getMovimientosEpisodioItemTree(act.description, listaMovimientos)
        }
        return listaMovimientos
    }
    
    // FIXME: it es una palabra clave y le falta el tipo.
    def getMovimientosEpisodioItemTree(it, List<String> listaMovimientos){
        it.items.each{i ->
            String metodoGetMovimientosEpisodio = "getMovimientosEpisodio" + i.getClassName()
            this."$metodoGetMovimientosEpisodio"(i, listaMovimientos)
        }
    }
    
    // FIXME: a c le falta el tipo.
    def getMovimientosEpisodioCluster(c, List<String> listaMovimientos){
        c.items.each{i ->
            String metodoGetMovimientosEpisodio = "getMovimientosEpisodio" + i.getClassName()
            this."$metodoGetMovimientosEpisodio"(i, listaMovimientos)
        }
    }
    
    // FIXME: a e le falta el tipo.
    //def getMovimientosEpisodioElement(e, List<String> listaMovimientos){
    //    // (DvCodedText)value.(CodePhrase)definingCode.(String)codeString
    //   if ((e.value != null) && (e.value.definingCode != null) && (e.value.definingCode.codeString != null)){
    //        listaMovimientos.add(e.value.definingCode.codeString)
    //    }
    //}

    //------------
    def getMovimientosEpisodioElement(e, List<String> listaMovimientos){
        String metodoGetMovimientosEpisodio = "getMovimientosEpisodio" + e.value.getClass().getSimpleName()
        this."$metodoGetMovimientosEpisodio"(e.value, listaMovimientos)
    }

    def getMovimientosEpisodioDvCodedText(dvct, List<String> listaMovimientos){
        // Obtengo path y codeString del examen imagenológico
        if ((dvct != null) && (dvct.definingCode != null) && (dvct.definingCode.codeString != null)){
            listaMovimientos.add(dvct.definingCode.codeString)
        }
    }

    def getMovimientosEpisodioDvDateTime(dvb, List<String> listaMovimientos){
        println "ENTRO getMovimientosEpisodioDvDateTime: " + dvb // FIXME no deberia llegar aqui, pero llega
    }
    //------------

    //------------------------------------------------------------------------------------------------------

     /**
     * Retorna la lista de todos los codeString correspondientes a los codigos CIE-10 de diagnosticos de un episodio
     * @autor Leandro Carrasco
     **/
    List<String> getDiagnosticosEpisodio(Composition comp){

        def diagnostico = hceService.getCompositionContentItemForTemplate( comp, "DIAGNOSTICO-diagnosticos" )
         
        List<String> listaCIE10 = new LinkedList<String>()
        if (diagnostico != null){

            diagnostico.data.events.each{event ->
                getDiagnosticosEpisodioItemList(event.data, listaCIE10)
            }
        }

        return listaCIE10
    }

    def getDiagnosticosEpisodioItemList(il, List<String> listaCIE10){
        il.items.each{i ->
            String metodoGetDiagnosticosEpisodio = "getDiagnosticosEpisodio" + i.getClassName()
            this."$metodoGetDiagnosticosEpisodio"(i, listaCIE10)
        }
    }

    def getDiagnosticosEpisodioCluster(c, List<String> listaCIE10){
        c.items.each{i ->
            String metodoGetMovimientosEpisodio = "getMovimientosEpisodio" + i.getClassName()
            this."$metodoGetMovimientosEpisodio"(i, listaCIE10)
        }
    }

    def getDiagnosticosEpisodioElement(e, List<String> listaCIE10){
        // at0004 es el archetypeNodeId de diagnostico
        if ((e.archetypeNodeId == "at0004") && (e.value != null) && (e.value.definingCode != null) && (e.value.definingCode.codeString != null)){
            listaCIE10.add(e.value.definingCode.codeString)
        }
    }

    //--------------------------------------------------------------------------

     /**
     * Retorna la lista de todos los codeString correspondientes
     * a los "cantDiagnosticos" diagnosticos mas frecuentes (con su respectiva frecuencia)
     * @autor Leandro Carrasco 
     **/
    // FIXME: falta el tipo a listaComp.
    List<StringEntero> getDiagnosticosMasFrecuetes(listaComp, int cantDiagnosticos){

        // Cuento la cantidad de veces que aparece cada diagnostico (utilizo un HashMap para mantener los valores)
        HashMap<String, Integer> mapDiagCant = new HashMap<String, Integer>()
        listaComp.each{comp ->
            List<String> listaDiagsEpisodio = getDiagnosticosEpisodio(comp)
            listaDiagsEpisodio.each{codeStringDiag ->
                if (mapDiagCant.containsKey(codeStringDiag)){
                    mapDiagCant.put(codeStringDiag,mapDiagCant.get(codeStringDiag)+1)
                }
                else{
                    mapDiagCant.put(codeStringDiag,1)
                }
            }
        }

        // Creo una Lista a partir del Map para poder ordenar los por cantidad
        // listDiagnosticos.entrySet() retorna un Set de Map.Entry<K,V>
        // StringEntero implementa la interfaz Comparable, y toma en cuanta el entero (cantidad) para ordenar
        List<StringEntero> listDiagCant = new LinkedList<StringEntero>()
        mapDiagCant.entrySet().each{mapEntry ->
            // FIXME: para que inventar un nuevo tipo de dato si se puede hacer con un Map<String, Integer>,
            //        donde la clave es mapEntry.getKey() y el valor mapEntry.getValue(), igual que en mapDiagCant.
            StringEntero se = new StringEntero(str: mapEntry.getKey(), entero: mapEntry.getValue())
            listDiagCant.add(se)
        }

        // Ordeno segun la cantidad de veces que aparece cada diagnostico
        // FIXME: sort es una clausura, hay que pasarle el criterio de ordenamiento, p.e. listDiagCant.sort{ it.id } ordena por id de menor a mayor.
        // http://groovy.codehaus.org/groovy-jdk/java/util/Collection.html#sort(groovy.lang.Closure)
        Collections.sort(listDiagCant);
        Collections.reverse(listDiagCant); // Hago el reverse porque el sort ordena de menor a mayor (y queremos al revez)

        if (listDiagCant.size() < cantDiagnosticos){
            return listDiagCant
        }
        else{
            // FIXME: sublist es tambien una lista (interfaz List), si no usar getAt() descrito aca> http://groovy.codehaus.org/groovy-jdk/java/util/List.html 
            int cantItems = listDiagCant.size()
            List<StringEntero> listReturn = new LinkedList<StringEntero>() // Hago una copia limpia de los elementos porque sino retorna un objeto de la clase SubList (y no me gusto)
            listDiagCant.subList(0, cantDiagnosticos).each{se ->
                listReturn.add(se)
            }
            return listReturn
        }

    }

    //--------------------------------------------------------------------------

    /**
    * Retorna el valor de la Frecuencia Respiratoria para un Episodio.
    * Retorna null si no fue registrada.
    * @autor Leandro Carrasco
    **/
    Double getFrecuenciaRespiratoriaEpisodio(Composition comp){
 
        // El dato a encontrar esta en sectionVentilacion.items[i].data.events[j].data.item.value.magnitude
        def sectionVentilacion = hceService.getCompositionContentItemForTemplate( comp, "EVALUACION_PRIMARIA-ventilacion" )
        def result = null
        if ((sectionVentilacion != null) && (sectionVentilacion.items != null)){
            sectionVentilacion.items.each{item ->
                if ((item.archetypeNodeId != null) && (item.archetypeNodeId == "openEHR-EHR-OBSERVATION.frecuencia_respiratoria.v1")){
                    if ((item.data != null) && (item.data.events != null)){
                        item.data.events.each{event ->
                            if (event.archetypeNodeId == "at0002"){
                                if ((event.data != null) && (event.data.item != null) && (event.data.item.value != null) && (event.data.item.value.magnitude != null)){
                                    result = event.data.item.value.magnitude
                                }
                            }
                        }
                    }
                }
            }
        }

        return result
    }

    //--------------------------------------------------------------------------

    /**
    * Retorna el valor de la Presion Arterial Sistolica para un Episodio.
    * Retorna null si no fue registrada.
    * @autor Leandro Carrasco
    **/
    Double getPresionArterialSistolicaEpisodio(Composition comp){
        // El dato a encontrar esta en sectionEstadoCirculatorio.items[i].data.events[j].data.item.value.magnitude
        def sectionEstadoCirculatorio = hceService.getCompositionContentItemForTemplate( comp, "EVALUACION_PRIMARIA-estado_circulatorio" )
        def result = null
        if ((sectionEstadoCirculatorio != null) && (sectionEstadoCirculatorio.items != null)){
            sectionEstadoCirculatorio.items.each{item ->
                if ((item.archetypeNodeId != null) && (item.archetypeNodeId == "openEHR-EHR-OBSERVATION.presion_arterial.v1")){
                    if ((item.data != null) && (item.data.events != null)){
                        item.data.events.each{event ->
                            if (event.archetypeNodeId == "at0002"){
                                if (event.data != null){
                                    event.data.items.each{itemData ->
                                        if (itemData.archetypeNodeId == "at0005"){
                                            itemData.items.each{itemItemData->
                                                if (itemItemData.archetypeNodeId == "at0006"){
                                                    if ((itemItemData.value != null) && (itemItemData.value.magnitude != null)){
                                                        result = itemItemData.value.magnitude
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                                //if ((event.data != null) && (event.data.item != null) && (event.data.item.value != null) && (event.data.item.value.magnitude != null)){
                                //    result = event.data.item.value.magnitude
                                //}
                            }
                        }
                    }
                }
            }
        }
        
        return result
    }

    //--------------------------------------------------------------------------

    /**
    * Retorna el valor para la Escala de Coma de Glasgow para un Episodio.
    * Retorna null si no fue registrada.
    * @autor Leandro Carrasco
    **/
    Double getGlasgowComaScaleEpisodio(Composition comp){

        // El dato a encontrar esta en sectionDisfuncionNeurologica.items[i].data.events[j].data.items[k].value.magnitude
        def sectionDisfuncionNeurologica = hceService.getCompositionContentItemForTemplate( comp, "EVALUACION_PRIMARIA-disfuncion_neurologica" )
        def result = null
        
        if ((sectionDisfuncionNeurologica != null) && (sectionDisfuncionNeurologica.items != null)){
            sectionDisfuncionNeurologica.items.each{item ->
                if ((item.archetypeNodeId != null) && (item.archetypeNodeId == "openEHR-EHR-OBSERVATION.glasgow_coma.v1draft")){
                    if ((item.data != null) && (item.data.events != null)){
                        item.data.events.each{event ->
                            if (event.archetypeNodeId == "at0002"){
                                if (event.data != null){
                                    event.data.items.each{itemData ->
                                        if (itemData.archetypeNodeId == "at0026"){
                                            if ((itemData.value != null) && (itemData.value.magnitude != null)){
                                                result = itemData.value.magnitude
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return result
    }

    //--------------------------------------------------------------------------

    /**
    * Retorna el valor del RTS para un Episodio.
    * Retorna null si falta alguno de los datos para carcular la RTS (FR, PAS o GCS)
    * @autor Leandro Carrasco
    **/
    Integer getRTSEpisodio(Composition comp){
        /* 
            FR: 10..29 => 4 || > 29 => 3 || 6..9 => 2 || 1..5 => 1 || 0 => 0
            PAS: > 89 => 4 || 76..89 => 3 || 50..75 => 2 || 1..49 => 1 || 0 => 0
            GCS: 13..15 => 4 || 9..12 => 3 || 6..8 => 2 || 4..5 => 1 || 3 => 0
        */
        def FR = getFrecuenciaRespiratoriaEpisodio(comp)
        def PAS = getPresionArterialSistolicaEpisodio(comp)
        def GCS = getGlasgowComaScaleEpisodio(comp)

        def RTS = null

        if ((FR != null) && (PAS != null) && (GCS != null)){
            int numAsociadoFR = 0
            int numAsociadoPAS = 0
            int numAsociadoGCS = 0

            if (FR == 0) numAsociadoFR = 0
            else if (FR >= 1 && FR <= 5) numAsociadoFR = 1
            else if (FR >= 6 && FR <= 9) numAsociadoFR = 2
            else if (FR >= 10 && FR <= 29) numAsociadoFR = 4
            else if (FR > 29) numAsociadoFR = 3
            else println "Valor Erroneo FR al calcular RTS (no puede ser un numero negativo)" // No deberia llegar aqui

            if (PAS == 0) numAsociadoPAS = 0
            else if (PAS >= 1 && PAS <= 49) numAsociadoPAS = 1
            else if (PAS >= 50 && PAS <= 75) numAsociadoPAS = 2
            else if (PAS >= 76 && PAS <= 89) numAsociadoPAS = 3
            else if (PAS > 89) numAsociadoPAS = 4
            else println "Valor Erroneo PAS al calcular RTS (no puede ser un numero negativo)" // No deberia llegar aqui

            if (GCS == 3) numAsociadoGCS = 0
            else if (GCS >= 4 && GCS <= 5) numAsociadoGCS = 1
            else if (GCS >= 6 && GCS <= 8) numAsociadoGCS = 2
            else if (GCS >= 9 && GCS <= 12) numAsociadoGCS = 3
            else if (GCS >= 13 && GCS <= 15) numAsociadoGCS = 4
            else println "Valor Erroneo GCS al calcular RTS (debe estar en el rango 3..15)" // No deberia llegar aqui

            RTS =  new Integer(numAsociadoFR + numAsociadoPAS + numAsociadoGCS)
        }

        return RTS
    }

    //--------------------------------------------------------------------------

    /**
    * Retorna el valor del RTSp para un Episodio.
    * Retorna null si falta alguno de los datos para carcular la RTSp (FR, PAS o GCS)
    * @autor Leandro Carrasco
    **/
    Double getRTSpEpisodio(Composition comp){
        def FR = getFrecuenciaRespiratoriaEpisodio(comp)
        def PAS = getPresionArterialSistolicaEpisodio(comp)
        def GCS = getGlasgowComaScaleEpisodio(comp)

        def RTSp = null

        if ((FR != null) && (PAS != null) && (GCS != null)){
            RTSp = new Double ((0.2908 * FR.doubleValue()) + (0.7326 * PAS.doubleValue()) + (0.9368 * GCS.doubleValue()))
        }

        return RTSp
    }

    //--------------------------------------------------------------------------

    /**
    * Retorna el valor del tiempo de permanencia (en minutos) del paciente en la etapa de Admisión.
    * El tiempo de ingreso en la etapa esta dado por el tiempo registrado en la Admisión y la salida por el
    * el tiempo que se registra en el triague. Si solo esta este ultimo tiempo o el de salida es mayor
    * o igual al de ingreso, se considera 0 el tiempo de permanencia en la etapa (no paso por administración)
    * Si solo esta el tiempo que se registro en la Admisión, se retorna null.
    * @autor Leandro Carrasco
    **/
    Integer getTiempoPermanenciaEtapaAdmisionEpisodio(Composition comp){
        Integer tiempoPermanencia = null // Valor a retornar
        Date fechaIni = null // Se le asignara fecha registrada en admision (creación el episodio)
        Date fechaFin = null // Se le asignara fecha registrada en el triege

        // Busco fecha registrada en la admisión (creación del episodio)
        if ((comp.context != null) && (comp.context.startTime != null)){
            fechaIni = comp.context.startTime.toDate()
        }
        
        // Busco fecha registrada en el triege
        def triageEvaluation = hceService.getCompositionContentItemForTemplate( comp, "INGRESO-triage" )
        if (triageEvaluation){
            def fechaHoraTriageElement = triageEvaluation.data.items.find{ it.path.endsWith('[at0009]')}
            if (fechaHoraTriageElement){
                fechaFin = fechaHoraTriageElement.value.toDate()
            }
        }

        if ((fechaIni != null) && (fechaFin != null)){
            // Convierto fechas a Date y calculo la fiferencia
            tiempoPermanencia = new Integer(DateDifference.numberOfMinutes(fechaIni, fechaFin))
        }


        return tiempoPermanencia
    }

    //--------------------------------------------------------------------------

    /**
    * Retorna el valor del tiempo de permanencia (en minutos) del paciente en la etapa de Evaluación.
    * El tiempo de ingreso en la etapa esta dado por el tiempo que se registra en el triague y la salida por el
    * el tiempo que se registra en la Evaluación Secundaria. Si no se registro este ultimo se retorna null.
    * @autor Leandro Carrasco
    **/
    Integer getTiempoPermanenciaEtapaEvaluacionEpisodio(Composition comp){
        Integer tiempoPermanencia = null // Valor a retornar
        Date fechaIni = null // Se le asignara fecha registrada en el triege
        Date fechaFin = null // Se le asignara fecha registrada en la evaluación secundaria

        // Busco fecha registrada en el triege
        def triageEvaluation = hceService.getCompositionContentItemForTemplate( comp, "INGRESO-triage" )
        if (triageEvaluation){
            def fechaHoraTriageElement = triageEvaluation.data.items.find{ it.path.endsWith('[at0009]')}
            if (fechaHoraTriageElement){
                fechaIni = fechaHoraTriageElement.value.toDate()
            }
        }

        // Busco fecha registrada en la Evaluación Secundaria (creación del episodio)
        def evalSecSection = hceService.getCompositionContentItemForTemplate( comp, "EVALUACION_SECUNDARIA-exposicion_corporal_total" )
        if (evalSecSection){
            evalSecSection.items.each{entry ->
                if (entry.archetypeNodeId == "openEHR-EHR-ACTION.resumen_actuacion.v1"){
                    def fechaHoraEvalSecElement = entry.description.items.find{ it.archetypeNodeId == "at0005" }
                    if (fechaHoraEvalSecElement){
                        fechaFin = fechaHoraEvalSecElement.value.toDate()
                    }
                }
            }
        }

        if ((fechaIni != null) && (fechaFin != null)){
            // Convierto fechas a Date y calculo la fiferencia
            tiempoPermanencia = new Integer(DateDifference.numberOfMinutes(fechaIni ,fechaFin))
        }

        return tiempoPermanencia
    }

    //--------------------------------------------------------------------------

    /**
    * Retorna el valor del tiempo de permanencia (en minutos) del paciente en la etapa de espera luego de la evaluación.
    * El tiempo de ingreso en la etapa esta dado por el tiempo que se registra en la Evaluación Secundaria
    * y la salida por el el tiempo mas temprano de todos los movimientos registrados para el paciente.
    * Si solo esta presente el tiempo de ingreso a la etapa, se retorna null.
    * @autor Leandro Carrasco
    **/
    Integer getTiempoPermanenciaEtapaEsperaLuegoDeEvaluacionEpisodio(Composition comp){
        Integer tiempoPermanencia = null // Valor a retornar
        Date fechaIni = null // Se le asignara fecha registrada en el triege
        Date fechaFin = null // Se le asignara fecha registrada en la evaluación secundaria

        // Busco fecha registrada en la Evaluación Secundaria (creación del episodio)
        def evalSecSection = hceService.getCompositionContentItemForTemplate( comp, "EVALUACION_SECUNDARIA-exposicion_corporal_total" )
        if (evalSecSection){
            evalSecSection.items.each{entry ->
                if (entry.archetypeNodeId == "openEHR-EHR-ACTION.resumen_actuacion.v1"){
                    def fechaHoraEvalSecElement = entry.description.items.find{ it.archetypeNodeId == "at0005" }
                    if (fechaHoraEvalSecElement){
                        fechaIni = fechaHoraEvalSecElement.value.toDate()
                    }
                }
            }
        }

        // Busco movimiento con fecha mas temprana
        def movInstruction = hceService.getCompositionContentItemForTemplate( comp, "COMUNES-movimiento_paciente" )
        if (movInstruction){
            movInstruction.activities.each{act ->
                def fechaHoraMovInstructionElement = act.description.items.find{ it.archetypeNodeId == "at0012" }
                if (fechaHoraMovInstructionElement){
                    if (!fechaFin){ // Si no se seteo ninguna fecha de movimiento
                        fechaFin = fechaHoraMovInstructionElement.value.toDate()
                    }
                    else if (fechaFin.after(fechaHoraEvalSecElement.value.toDate())){ // Si la fecha es menor a la que tenia, la actualizo
                        fechaFin = fechaHoraMovInstructionElement.value.toDate()
                    }
                }
            }
        }

        if ((fechaIni != null) && (fechaFin != null)){
            // Convierto fechas a Date y calculo la fiferencia
            tiempoPermanencia = new Integer(DateDifference.numberOfMinutes(fechaIni ,fechaFin))
        }

        return tiempoPermanencia
    }

    //--------------------------------------------------------------------------

    Double getTiempoPermanenciaPromedioEtapaAdmisionEpisodio(listaComp){
        def totalMin = 0
        def cantidadEpisodios = 0
        listaComp.each{comp ->
            def cantMinutos = getTiempoPermanenciaEtapaAdmisionEpisodio(comp)
            if (cantMinutos){
                totalMin += cantMinutos
                cantidadEpisodios++
            }
        }

        if (cantidadEpisodios > 0)
            return new Double (totalMin / cantidadEpisodios)
        else
            return null
    }

    //--------------------------------------------------------------------------

    Double getTiempoPermanenciaPromedioEtapaEvaluacionEpisodio(listaComp){
        def totalMin = 0
        def cantidadEpisodios = 0
        listaComp.each{comp ->
            def cantMinutos = getTiempoPermanenciaEtapaEvaluacionEpisodio(comp)
            if (cantMinutos){
                totalMin += cantMinutos
                cantidadEpisodios++
            }
        }

        if (cantidadEpisodios > 0)
            return new Double (totalMin / cantidadEpisodios)
        else
            return null
    }

    //--------------------------------------------------------------------------
    
    Double getTiempoPermanenciaPromedioEtapaEsperaLuegoDeEvaluacionEpisodio(listaComp){
        def totalMin = 0
        def cantidadEpisodios = 0
        listaComp.each{comp ->
            def cantMinutos = getTiempoPermanenciaEtapaEsperaLuegoDeEvaluacionEpisodio(comp)
            if (cantMinutos){
                totalMin += cantMinutos
                cantidadEpisodios++
            }
        }

        if (cantidadEpisodios > 0)
            return new Double (totalMin / cantidadEpisodios)
        else
            return null
    }
    

    //--------------------------------------------------------------------------

    /**
     * Retorna la lista de todos los archetypeId-path-codeString
     * correspondientes a los examenes Imagenologicos de un Episodio
     * @autor Leandro Carrasco
     **/
    List<String> getExamenesImagenologicosEpisodio(Composition comp){
        return getExamenesEpisodio(comp, "PARACLINICA-pedido_imagenes")
    }

    //--------------------------------------------------------------------------

     /**
     * Retorna la lista de todos los archetypeId-path-codeString
     * correspondientes a los examenes de Laboratorio de un Episodio
     * @autor Leandro Carrasco
     **/
    List<String> getExamenesLaboratorioEpisodio(Composition comp){
        return getExamenesEpisodio(comp, "PARACLINICA-pedido_laboratorio")
    }

    //--------------------------------------------------------------------------

    /**
     * Retorna la lista de todos los archetypeId-path-codeString correspondientes a los examenes de un episodio
     * El parametro templateId define si los tipos de examenes de laboratorio o imagenologico
     * Para los elements que tienen un DvBoolean se retorna 'archetypeId + " " + path'
     * Para los elements que tienen un DvCodedText se retorna 'archetypeId + " " + path + " " + codeString'
     * @autor Leandro Carrasco
     **/
    List<String> getExamenesEpisodio(Composition comp, String templateId){

        def examenes = hceService.getCompositionContentItemForTemplate( comp, templateId )

        List<String> listaExamImagEpisodio = new LinkedList<String>()
        if (examenes != null){
            examenes.activities.each{act ->
                //String metodoGetExamenesEpisodio = "getExamenes" + act.description.getClassName()
                //this."$metodoGetExamenesEpisodio"(act.description, listaExamImagEpisodio)
                getExamenesEpisodioItemTree(act.description, listaExamImagEpisodio)
            }
        }

        return listaExamImagEpisodio
    }

    def getExamenesEpisodioItemTree(itree, List<String> listaExamImagEpisodio){
        itree.items.each{i ->
            String metodoGetExamenesEpisodio = "getExamenesEpisodio" + i.getClassName()
            this."$metodoGetExamenesEpisodio"(i, listaExamImagEpisodio)
        }
    }

    def getExamenesEpisodioCluster(c, List<String> listaExamImagEpisodio){
        c.items.each{i ->
            String metodoGetExamenesEpisodio = "getExamenesEpisodio" + i.getClassName()
            this."$metodoGetExamenesEpisodio"(i, listaExamImagEpisodio)
        }
    }

    def getExamenesEpisodioElement(e, List<String> listaExamImagEpisodio){
        if ((e.archetypeDetails != null) && (e.archetypeDetails.archetypeId != null)){
            String metodoGetExamenesEpisodio = "getExamenesEpisodio" + e.value.getClass().getSimpleName()
            this."$metodoGetExamenesEpisodio"(e.value, e.archetypeDetails.archetypeId + " " + e.path, listaExamImagEpisodio)
        }
    }

    def getExamenesEpisodioDvCodedText(dvct, String arq_path, List<String> listaExamImagEpisodio){
        // Obtengo path y codeString del examen imagenológico
        if ((dvct != null) && (dvct.definingCode != null) && (dvct.definingCode.codeString != null)){
            listaExamImagEpisodio.add(arq_path + " " + dvct.definingCode.codeString)
        }
    }

    def getExamenesEpisodioDvBoolean(dvb, String arq_path, List<String> listaExamImagEpisodio){
        // Obtengo path del examen imagenológico
        if ((dvb != null) && (dvb.value)){
            listaExamImagEpisodio.add(arq_path)
        }
    }

    //--------------------------------------------------------------------------

    void imprimirObjetoXML(Object o){
        println "-----------------"
        XStream xstream = new XStream();
        String xml = xstream.toXML(o);
        println xml
        println "-----------------"
    }
}
