/**
 * 
 */
package tablasMaestras

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class Cie10Trauma {

    String grupo
    String subgrupo
    String codigo
    String nombre // TODO: se deberia sacar del servicio terminologico por el codigo o de i18n
    
    static constraints = {
        codigo(nullable:true)
        //nombre(maxSize :1024*1024*10)
    }
    static mapping = {
        //nombre type: 'text'
    }
    
    //static transients = ['codigos', 'grupos', 'subgrupos']

    static def getGrupos()
    {
        def grupos = [:]
        
        grupos['S00-S09'] = "Traumatismos de la cabeza"
        grupos['S10-S19'] = "Traumatismos de cuello"
        grupos['S20-S29'] = "Traumatismos de tórax"
        grupos['S30-S39'] = "Traumatismo del abdomen, área lumbosacra y pelvis"
        grupos['S40-S49'] = "Traumatismos de los hombros y brazos"
        grupos['S50-S59'] = "Traumatismos del codo y del antebrazo"
        grupos['S60-S79'] = "Traumatismos en muñecas y manos"
        grupos['S80-S89'] = "Traumatismos en rodillas y piernas"
        grupos['S90-S99'] = "Traumatismos en tobillos y pies"
        grupos['T00-T07'] = "Lesiones múltiples"
        grupos['T08-T14'] = "Lesiones no clasificadas en cabeza, cuello y otras partes"
        grupos['T15-T19'] = "Cuerpos extraños en alguna parte del cuerpo"
        grupos['T20-T32'] = "Quemaduras y corrosiones"
        grupos['T33-T35'] = "Congelaciones"
        grupos['T36-T50'] = "Intoxicaciones por fármacos"
        grupos['T51-T65'] = "Intoxicaciones pos sustancias no medicinales"
        grupos['T66-T78'] = "Lesiones por otras causas externas"
        grupos['T79']     = "Complicaciones traumáticas"
        grupos['T80-T88'] = "Complicaciones quirúrgicas"
        grupos['T90-T99'] = "Complicaciones post-traumáticas no clasificadas en otra parte"
        
        return grupos
    }

    static def getSubgrupos()
    {
        def subgrupos = [:]
        
        // TODO> estos tambien hay que meterlos como codigos, sol osubgrupo, sin codigo mas especifico.
        /*
         * S00) Traumatismo superficial de la cabeza
            S01) Herida de la cabeza
             S02) Fractura de huesos del cráneo y de la cara
             S03) Luxación, esguince y torcedura de articulaciones y de ligamentos de la cabeza
             S04) Traumatismo de nervios craneales
             S05) Traumatismo del ojo y de la órbita
             S06) Traumatismo intracraneal
             S07) Traumatismo por aplastamiento de la cabeza
             S08) Amputación traumática de parte de la cabeza
             S09) Otros traumatismos y traumatismos no especificados de la cabeza
             S10) Traumatismo superficial del cuello 
             S11) Herida del cuello
             S12) Fractura del cuello
             S13) Luxación, esguince y torcedura de articulaciones y ligamentos del cuello
           S14) Traumatismo de la médula espinal y de nervios a nivel del cuello
            S15) Traumatismo de vasos sanguíneos a nivel del cuello
             S16) Traumatismo de tendón y músculos a nivel del cuello
             S17) Traumatismo por aplastamiento del cuello
              
         */
        
        return subgrupos
    }

    static def getCodigos()
    {
        def ret = []
/*
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.0", nombre:"traumatismo superficial del cuero cabelludo")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.1", nombre:"contusión de los párpados y de la región periocular")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.2", nombre:"otros traumatismos superficiales del párpado y de la región periocular")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.3", nombre:"traumatismo superficial de la nariz")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.4", nombre:"traumatismo superficial del oído")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.5", nombre:"traumatismo superficial del labio y de la cavidad bucal")
        // no hay S00.6
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.7", nombre:"traumatismos superficiales múltiples de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.8", nombre:"traumatismo superficial de otras partes de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S00", codigo:"S00.9", nombre:"traumatismo superficial de la cabeza, parte no especificada")
        
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.0", nombre:"herida del cuero cabelludo")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.1", nombre:"herida del párpado y de la región periocular")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.2", nombre:"herida de la nariz")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.3", nombre:"herida del oído")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.4", nombre:"herida de la mejilla y de la región temporomandibular")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.5", nombre:"herida del labio y de la cavidad bucal")
        // no hay 6
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.7", nombre:"heridas múltiples de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.8", nombre:"herida de otras partes de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S01", codigo:"S01.9", nombre:"herida de la cabeza, parte no especificada")
        
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.0", nombre:"fractura de la bóveda del cráneo")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.1", nombre:"fractura de la base del cráneo")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.2", nombre:"fractura de los huesos de la nariz")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.3", nombre:"fractura del suelo de la órbita")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.4", nombre:"fractura del malar y del hueso maxilar superior")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.5", nombre:"fractura de los dientes")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.6", nombre:"fractura del maxilar inferior")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.7", nombre:"fracturas múltiples que comprometen el cráneo y los huesos de la cara")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.8", nombre:"fractura de otros huesos del cráneo y de la cara")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S02", codigo:"S02.9", nombre:"fractura del cráneo y de los huesos de la cara, parte no especificada")
    
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S03", codigo:"S03.0", nombre:"luxación del maxilar")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S03", codigo:"S03.1", nombre:"luxación del cartílago septal de la nariz")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S03", codigo:"S03.2", nombre:"luxación de diente")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S03", codigo:"S03.3", nombre:"luxación de otras partes y de las no especificadas de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S03", codigo:"S03.4", nombre:"esguinces y torceduras del maxilar")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S03", codigo:"S03.5", nombre:"esguinces y torceduras de articulaciones y ligamentos de otras partes y las no especificadas de la cabeza")

        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.0", nombre:"traumatismo del nervio óptico [II par] y de las vías ópticas")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.1", nombre:"traumatismo del nervio motor ocular común [III par]")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.2", nombre:"traumatismo del nervio patético [IV par]")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.3", nombre:"traumatismo del nervio trigémino [V par]")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.4", nombre:"traumatismo del nervio motor ocular externo [VI par]")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.5", nombre:"traumatismo del nervio facial [VII par]")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.6", nombre:"traumatismo del nervio acústico [VIII par]")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.7", nombre:"traumatismo del nervio espinal [XI par]")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.8", nombre:"traumatismo de otros nervios craneales")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S04", codigo:"S04.9", nombre:"traumatismo de nervios craneales, no especificado")
        
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.0", nombre:"traumatismo de la conjuntiva y abrasión corneal sin mención de cuerpo extraño")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.1", nombre:"contusión del globo ocular y del tejido orbitario")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.2", nombre:"laceración y ruptura ocular con prolapso o pérdida del tejido intraocular")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.3", nombre:"laceración ocular sin prolapso o pérdida del tejido intraocular")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.4", nombre:"herida penetrante de la órbita con o sin cuerpo extraño")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.5", nombre:"herida penetrante del globo ocular con cuerpo extraño")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.6", nombre:"herida penetrante del globo ocular sin cuerpo extraño")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.7", nombre:"avulsión de ojo")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.8", nombre:"otros traumatismos del ojo y de la órbita")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S05", codigo:"S05.9", nombre:"traumatismo del ojo y de la órbita, no especificado")
        
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.0", nombre:"concusión")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.1", nombre:"edema cerebral traumático")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.2", nombre:"traumatismo cerebral difuso")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.3", nombre:"traumatismo cerebral focal")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.4", nombre:"hemorragia epidural")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.5", nombre:"hemorragia subdural traumática")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.6", nombre:"hemorragia subaracnoidea traumática")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.7", nombre:"traumatismo intracraneal con coma prolongado")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.8", nombre:"otros traumatismos intracraneales")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S06", codigo:"S06.9", nombre:"traumatismo intracraneal, no especificado")

        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S07", codigo:"S07.0", nombre:"traumatismo por aplastamiento de la cara")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S07", codigo:"S07.1", nombre:"traumatismo por aplastamiento del cráneo")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S07", codigo:"S07.8", nombre:"traumatismo por aplastamiento de otras partes de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S07", codigo:"S07.9", nombre:"traumatismo por aplastamiento de la cabeza, parte no especificada")
        
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S08", codigo:"S08.0", nombre:"avulsión del cuero cabelludo")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S08", codigo:"S08.1", nombre:"amputación traumática de la oreja")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S08", codigo:"S08.8", nombre:"amputación traumática de otras partes de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S08", codigo:"S08.9", nombre:"amputación traumática de parte no especificada de la cabeza")

        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S09", codigo:"S09.0", nombre:"traumatismo de los vasos sanguíneos de la cabeza, no clasificadas")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S09", codigo:"S09.1", nombre:"traumatismo de músculo y tendón de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S09", codigo:"S09.2", nombre:"ruptura traumática del tímpano")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S09", codigo:"S09.7", nombre:"traumatismo múltiple de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S09", codigo:"S09.8", nombre:"otra traumatismo especificada de la cabeza")
        ret << new Cie10Trauma(grupo:"S00-S09", subgrupo:"S09", codigo:"S09.9", nombre:"traumatismo de la cabeza no especificas")
        
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S10", codigo:"S10.0", nombre:"contusión de la garganta")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S10", codigo:"S10.1", nombre:"otros traumatismos superficiales y los no especificados de la garganta")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S10", codigo:"S10.7", nombre:"traumatismo superficial múltiple del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S10", codigo:"S10.8", nombre:"traumatismo superficial de otras partes del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S10", codigo:"S10.9", nombre:"traumatismo superficial del cuello, parte no especificada")
        
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S11", codigo:"S11.0", nombre:"herida que compromete la laringe y la tráquea")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S11", codigo:"S11.1", nombre:"herida que compromete la glándula tiroides")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S11", codigo:"S11.2", nombre:"herida que compromete la faringe y el esófago cervical")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S11", codigo:"S11.7", nombre:"heridas múltiples del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S11", codigo:"S11.8", nombre:"heridas de otras partes del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S11", codigo:"S11.9", nombre:"herida de cuello, parte no especificada")
        
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S12", codigo:"S12.0", nombre:"fractura de la primera vértebra cervical")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S12", codigo:"S12.1", nombre:"fractura de la segunda vértebra cervical")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S12", codigo:"S12.2", nombre:"fractura de otras vértebras cervicales especificadas")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S12", codigo:"S12.7", nombre:"fracturas múltiples de columna cervical")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S12", codigo:"S12.8", nombre:"fractura de otras partes del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S12", codigo:"S12.8", nombre:"fractura del cuello, parte no especificada")

        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S13", codigo:"S13.0", nombre:"ruptura traumática de disco cervical intervertebral")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S13", codigo:"S13.1", nombre:"luxación de vértebra cervical")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S13", codigo:"S13.2", nombre:"luxaciones de otras partes y de las no especificadas del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S13", codigo:"S13.3", nombre:"luxaciones múltiples del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S13", codigo:"S13.4", nombre:"esguinces y torceduras de la columna cervical")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S13", codigo:"S13.5", nombre:"esguinces y torceduras de la región tiroidea")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S13", codigo:"S13.6", nombre:"esguinces y torceduras de articulaciones y ligamentos de otros sitios especificados y de los no especificados del cuello")
        
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S14", codigo:"S14.0", nombre:"concusión y edema de la médula espinal cervical")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S14", codigo:"S14.1", nombre:"otros traumatismos de la médula espinal cervical y los no especificados")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S14", codigo:"S14.2", nombre:"traumatismo de raíz nerviosa de columna cervical")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S14", codigo:"S14.3", nombre:"traumatismo del plexo braquial")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S14", codigo:"S14.4", nombre:"traumatismo de nervios periféricos del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S14", codigo:"S14.5", nombre:"traumatismo de nervios cervicales simpáticos")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S14", codigo:"S14.6", nombre:"traumatismo de otros nervios y de los no especificados del cuello")
        
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S15", codigo:"S15.0", nombre:"traumatismo de la arteria carótida")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S15", codigo:"S15.1", nombre:"traumatismo de la arteria vertebral")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S15", codigo:"S15.2", nombre:"traumatismo de la vena yugular externa")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S15", codigo:"S15.3", nombre:"traumatismo de la vena yugular interna")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S15", codigo:"S15.7", nombre:"traumatismo de múltiples vasos sanguíneos a nivel del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S15", codigo:"S15.8", nombre:"traumatismo de otros vasos sanguíneos a nivel del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S15", codigo:"S15.9", nombre:"traumatismo de vasos sanguíneos no especificados a nivel del cuello")
        
        // Solo subgrupo, no tiene codigos mas especificos
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S16", nombre:"traumatismo de tendón y músculos a nivel del cuello")
        
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S17", codigo:"S17.0", nombre:"traumatismo por aplastamiento de la laringe y de la tráquea")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S17", codigo:"S17.8", nombre:"traumatismo por aplastamiento de otras partes del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S17", codigo:"S17.9", nombre:"traumatismo por aplastamiento del cuello, parte no especificada")
        
        // Solo subgrupo, no tiene codigos mas especificos
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S18", nombre:"amputación traumática a nivel del cuello")
        
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S19", nombre:"otros traumatismos y los no especificados del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S19", codigo:"S19.7", nombre:"traumatismos múltiples del cuello")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S19", codigo:"S19.8", nombre:"otros traumatismos del cuello, especificados")
        ret << new Cie10Trauma(grupo:"S10-S19", subgrupo:"S19", codigo:"S19.9", nombre:"traumatismo del cuello, no especificado")
        
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S20", nombre:"traumatismo superficial del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S20", codigo:"S20.0", nombre:"contusión de la mama")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S20", codigo:"S20.1", nombre:"otros traumatismos superficiales y los no especificados de la mama")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S20", codigo:"S20.2", nombre:"contusión del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S20", codigo:"S20.3", nombre:"otros traumatismos superficiales de la pared anterior del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S20", codigo:"S20.4", nombre:"otros traumatismos superficiales de la pared posterior del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S20", codigo:"S20.7", nombre:"traumatismos superficiales múltiples del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S20", codigo:"S20.8", nombre:"traumatismo superficial de otras partes y de las no especificadas del tórax")
   
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S21", nombre:"herida del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S21", codigo:"S21.0", nombre:"herida de la mama")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S21", codigo:"S21.1", nombre:"herida de la pared anterior del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S21", codigo:"S21.2", nombre:"herida de la pared posterior del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S21", codigo:"S21.7", nombre:"herida múltiple de la pared torácica")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S21", codigo:"S21.8", nombre:"herida de otras partes del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S21", codigo:"S21.9", nombre:"herida del tórax, parte no especificada")
        
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", nombre:"fractura de las costillas, del esternón y de la columna torácica [dorsal]")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", codigo:"S22.0", nombre:"fractura de vértebra torácica")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", codigo:"S22.1", nombre:"fracturas múltiples de columna torácica")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", codigo:"S22.2", nombre:"fractura del esternón")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", codigo:"S22.3", nombre:"fractura de costilla")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", codigo:"S22.4", nombre:"fracturas múltiples de costillas")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", codigo:"S22.5", nombre:"tórax azotado")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", codigo:"S22.8", nombre:"fractura de otras partes del tórax óseo")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S22", codigo:"S22.9", nombre:"fractura del tórax óseo, parte no especificada")
        
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S23", nombre:"luxación, esguince y torcedura de articulaciones y ligamentos del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S23", codigo:"S23.0", nombre:"ruptura traumática de disco intervertebral torácico")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S23", codigo:"S23.1", nombre:"luxación de vértebra torácica")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S23", codigo:"S23.2", nombre:"luxación de otras partes y de las no especificadas del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S23", codigo:"S23.3", nombre:"esguinces y torceduras de columna torácica")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S23", codigo:"S23.4", nombre:"esguinces y torceduras de costillas y esternón")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S23", codigo:"S23.5", nombre:"esguinces y torceduras de otras partes y de las no especificadas del tórax")
        
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S24", nombre:"traumatismo de nervios y de la médula espinal a nivel del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S24", codigo:"S24.0", nombre:"concusión y edema de la médula espinal torácica")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S24", codigo:"S24.1", nombre:"otros traumatismos y los no especificados de la médula espinal torácica")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S24", codigo:"S24.2", nombre:"traumatismo de raíces nerviosas de la columna torácica")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S24", codigo:"S24.3", nombre:"traumatismo de nervios periféricos del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S24", codigo:"S24.4", nombre:"traumatismo de nervios simpáticos torácicos")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S24", codigo:"S24.5", nombre:"traumatismo de otros nervios del tórax")
        ret << new Cie10Trauma(grupo:"S20-S29", subgrupo:"S24", codigo:"S24.6", nombre:"traumatismo de nervio no especificado del tórax")
*/
        
        def cie10_t_codes = new File("data/CIE10_T00_T98.psv")
        cie10_t_codes.eachLine { line ->
        
           def partes = line.split("\\\$")
           
           //println "partes: " + partes
           
           ret << new Cie10Trauma(
                        grupo: partes[0],
                        subgrupo: ((partes[1] == "null") ? null : partes[1]),
                        codigo: ((partes[2] == "null") ? null : partes[2]),
                        nombre: partes[3])
        }
        /*
        def cie10_s_codes = new File("data/CIE10_S00_S99.psv")
        cie10_s_codes.eachLine { line ->
        
           def partes = line.split("\\\$")
           
           //println "partes: " + partes
           
           ret << new Cie10Trauma(
                        grupo: partes[0],
                        subgrupo: ((partes[1] == "null") ? null : partes[1]),
                        codigo: ((partes[2] == "null") ? null : partes[2]),
                        nombre: partes[3])
        }
        */
        
        
        return ret
    }

    
}
