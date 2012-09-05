/**
 * 
 */
package tablasMaestras



/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class OpenEHRConcept {

    String group
    String conceptId
    String rubric
    String lang
    
    static mapping = {
        group column: "concept_group" // group no le gusta a mysql     
    }
    
    static List getConcepts()
    {
        def res = []
        
        res << new OpenEHRConcept(group:'attestation reason',conceptId:'240',rubric:'firmado',lang:'es')
        res << new OpenEHRConcept(group:'attestation reason',conceptId:'648',rubric:'testigo',lang:'es')
        
        res << new OpenEHRConcept(group:'audit change type',conceptId:'249',rubric:'creacion',lang:'es')
        res << new OpenEHRConcept(group:'audit change type',conceptId:'250',rubric:'correccion',lang:'es')
        res << new OpenEHRConcept(group:'audit change type',conceptId:'251',rubric:'modificacion',lang:'es')
        res << new OpenEHRConcept(group:'audit change type',conceptId:'252',rubric:'sintesis',lang:'es') // podria ser resumen
        res << new OpenEHRConcept(group:'audit change type',conceptId:'523',rubric:'eliminacion',lang:'es')
        res << new OpenEHRConcept(group:'audit change type',conceptId:'666',rubric:'certificacion',lang:'es')
        res << new OpenEHRConcept(group:'audit change type',conceptId:'253',rubric:'desconocido',lang:'es')
        
        res << new OpenEHRConcept(group:'composition category',conceptId:'431',rubric:'persistente',lang:'es')
        res << new OpenEHRConcept(group:'composition category',conceptId:'433',rubric:'evento',lang:'es')
        
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'387',rubric:'audio/DVI4',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'388',rubric:'audio/G722',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'389',rubric:'audio/G723',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'390',rubric:'audio/G726-16',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'391',rubric:'audio/G726-24',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'392',rubric:'audio/G726-32',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'393',rubric:'audio/G726-40',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'394',rubric:'audio/G728',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'395',rubric:'audio/L8',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'396',rubric:'audio/L16',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'397',rubric:'audio/LPC',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'398',rubric:'audio/G729',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'399',rubric:'audio/G729D',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'400',rubric:'audio/G729E',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'401',rubric:'video/BT656',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'402',rubric:'video/CelB',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'403',rubric:'video/JPEG',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'404',rubric:'video/H261',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'405',rubric:'video/H263',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'406',rubric:'video/H263-1998',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'407',rubric:'video/H263-2000',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'408',rubric:'video/MPV',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'409',rubric:'audio/mpeg',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'410',rubric:'audio/mpeg4-generic',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'411',rubric:'audio/L20',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'412',rubric:'audio/L24',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'413',rubric:'audio/telephone-event',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'414',rubric:'video/quicktime',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'415',rubric:'text/calendar',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'416',rubric:'text/directory',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'417',rubric:'text/html',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'418',rubric:'text/plain',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'419',rubric:'text/rtf',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'420',rubric:'text/sgml',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'421',rubric:'text/tab-separated-values',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'422',rubric:'text/uri-list',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'423',rubric:'text/xml',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'424',rubric:'text/xml-external-parsed-entity',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'425',rubric:'image/cgm',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'426',rubric:'image/gif',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'427',rubric:'image/png',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'428',rubric:'image/tiff',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'429',rubric:'image/jpeg',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'517',rubric:'application/msword',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'518',rubric:'application/pdf',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'519',rubric:'application/rtf',lang:'es')
        res << new OpenEHRConcept(group:'MultiMedia',conceptId:'637',rubric:'application/dicom',lang:'es')

        
        res << new OpenEHRConcept(group:'property',conceptId:'339',rubric:'Aceleracion',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'342',rubric:'Aceleracion angular',lang:'es')
        /*
         * <concept id="381" rubric="Amount (Eq)"/>
           <concept id="384" rubric="Amount (mole)"/>
           <concept id="497" rubric="Angle, plane"/>
           <concept id="500" rubric="Angle, solid"/>
         */
        res << new OpenEHRConcept(group:'property',conceptId:'335',rubric:'Area',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'119',rubric:'Concentracion',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'350',rubric:'Densidad',lang:'es')
         /*
          * <concept id="362" rubric="Diffusion coefficient"/>
            <concept id="501" rubric="Electrical capacitance"/>
            <concept id="498" rubric="Electrical charge"/>
            <concept id="502" rubric="Electrical conductance"/>
            <concept id="334" rubric="Electrical current"/>
            <concept id="377" rubric="Electrical field strength"/>
            <concept id="655" rubric="Electrical potential time"/>
          */
        res << new OpenEHRConcept(group:'property',conceptId:'121',rubric:'Energia',lang:'es')
          /*
           * <concept id="366" rubric="Energy density"/>
            <concept id="508" rubric="Energy dose"/>
            <concept id="365" rubric="Energy per area"/>
            <concept id="364" rubric="Energy, linear"/>
            <concept id="347" rubric="Flow rate, mass"/>
            <concept id="352" rubric="Flow rate, mass/force"/>
            <concept id="351" rubric="Flow rate, mass/volume"/>
            <concept id="126" rubric="Flow rate, volume"/>
            <concept id="348" rubric="Flux, mass"/>
           */
        res << new OpenEHRConcept(group:'property',conceptId:'355',rubric:'Fuerza',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'358',rubric:'Fuerza por masa',lang:'es')
        //<concept id="357" rubric="Force, body"/>
        
        res << new OpenEHRConcept(group:'property',conceptId:'',rubric:'Frecuencia',lang:'es')
        /*
         * <concept id="586" rubric="Glomerular filtration rate"/>
            <concept id="373" rubric="Heat transfer coefficient"/>
            <concept id="505" rubric="Illuminance"/>
         */
        res << new OpenEHRConcept(group:'property',conceptId:'379',rubric:'Inductancia',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'122',rubric:'Largo',lang:'es')
         /*
          * <concept id="499" rubric="Light intensity"/>
            <concept id="123" rubric="Loudness"/>
            <concept id="504" rubric="Luminous flux"/>
            <concept id="378" rubric="Magnetic flux"/>
            <concept id="503" rubric="Magnetic flux density"/>
          */
        res << new OpenEHRConcept(group:'property',conceptId:'124',rubric:'Masa',lang:'es')
          /*
           * concept id="385" rubric="Mass (IU)"/>
            <concept id="445" rubric="Mass (Units)"/>
            <concept id="349" rubric="Mass per area"/>
            <concept id="344" rubric="Moment inertia, area"/>
            <concept id="345" rubric="Moment inertia, mass"/>
            <concept id="340" rubric="Momentum"/>
            <concept id="346" rubric="Momentum, flow rate"/>
            <concept id="343" rubric="Momentum, angular"/>
            <concept id="363" rubric="Power"/>
            <concept id="369" rubric="Power density"/>
            <concept id="368" rubric="Power flux"/>
            <concept id="367" rubric="Power, linear"/>
           */
        res << new OpenEHRConcept(group:'property',conceptId:'125',rubric:'Presion',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'507',rubric:'Proporcion',lang:'es')
           //<concept id="380" rubric="Qualified real"/>
        res << new OpenEHRConcept(group:'property',conceptId:'506',rubric:'Radioactividad',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'375',rubric:'Resistencia',lang:'es')
        /*
         * <concept id="370" rubric="Specific energy"/>
            <concept id="371" rubric="Specific heat, gas constant"/>
            <concept id="337" rubric="Specific surface"/>
            <concept id="336" rubric="Specific volume"/>
            <concept id="354" rubric="Specific weight"/>
            <concept id="356" rubric="Surface tension"/>
         */
        res << new OpenEHRConcept(group:'property',conceptId:'127',rubric:'Temperatura',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'372',rubric:'Conductividad termica',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'128',rubric:'Tiempo',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'359',rubric:'Torque',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'338',rubric:'Velocidad',lang:'es')
         /*
          * <concept id="341" rubric="Velocity, angular"/>
        <concept id="360" rubric="Velocity, dynamic"/>
        <concept id="361" rubric="Velocity, kinematic"/>
        <concept id="374" rubric="Voltage, electrical"/>
          */
        res << new OpenEHRConcept(group:'property',conceptId:'129',rubric:'Volumen',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'130',rubric:'Trabajo',lang:'es')
        /*
        res << new OpenEHRConcept(group:'property',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'property',conceptId:'',rubric:'',lang:'es')
        */
        
        res << new OpenEHRConcept(group:'version lifecycle state',conceptId:'532',rubric:'Completo',lang:'es')
        res << new OpenEHRConcept(group:'version lifecycle state',conceptId:'553',rubric:'Incompleto',lang:'es')
        res << new OpenEHRConcept(group:'version lifecycle state',conceptId:'523.1',rubric:'Eliminado',lang:'es') // hay un 523 en 'audit change type',conceptId:'523',rubric:'eliminacion'
        
        res << new OpenEHRConcept(group:'participation function',conceptId:'253.1',rubric:'desconocido',lang:'es') // se repite 'audit change type',conceptId:'253',rubric:'desconocido'
        // TODO: poner las funciones de participacon de HL7 y las que sean necesarias para la HCE
        
        res << new OpenEHRConcept(group:'null flavours',conceptId:'271',rubric:'No hay informacion',lang:'es')
        res << new OpenEHRConcept(group:'null flavours',conceptId:'253.2',rubric:'Desconocido',lang:'es') // se repite 'audit change type',conceptId:'253',rubric:'desconocido'
        res << new OpenEHRConcept(group:'null flavours',conceptId:'272',rubric:'Oculto',lang:'es')
        res << new OpenEHRConcept(group:'null flavours',conceptId:'273',rubric:'No aplicable',lang:'es')
        
        /*
        <group name="participation mode">
        <concept id="193" rubric="not specified"/>
        <concept id="216" rubric="face-to-face com-"/>
        <concept id="223" rubric="interpreted face-to-"/>
        <concept id="217" rubric="signing (face-to-"/>
        <concept id="195" rubric="live audiovisual; "/>
        <concept id="198" rubric="videoconferencing"/>
        <concept id="197" rubric="videophone"/>
        <concept id="218" rubric="signing over video"/>
        <concept id="224" rubric="interpreted video "/>
        <concept id="194" rubric="asynchronous audi-"/>
        <concept id="196" rubric="recorded video"/>
        <concept id="202" rubric="live audio-only; tel-"/>
        <concept id="204" rubric="telephone"/>
        <concept id="203" rubric="teleconference"/>
        <concept id="205" rubric="internet telephone"/>
        <concept id="222" rubric="interpreted audio-"/>
        <concept id="199" rubric="asynchronous "/>
        <concept id="200" rubric="dictated"/>
        <concept id="201" rubric="voice-mail"/>
        <concept id="212" rubric="live text-only; inter-"/>
        <concept id="213" rubric="internet chat"/>
        <concept id="214" rubric="SMS chat"/>
        <concept id="215" rubric="interactive written "/>
        <concept id="206" rubric="asynchronous text; "/>
        <concept id="211" rubric="handwritten note"/>
        <concept id="210" rubric="printed/typed letter"/>
        <concept id="207" rubric="email"/>
        <concept id="208" rubric="facsimile/telefax"/>
        <concept id="221" rubric="translated text"/>
        <concept id="209" rubric="SMS message"/>
        <concept id="219" rubric="physically present"/>
        <concept id="220" rubric="physically remote"/>
    </group>
    <group name="instruction states">
        <concept id="524" rubric="initial"/>
        <concept id="526" rubric="planned"/>
        <concept id="527" rubric="postponed"/>
        <concept id="528" rubric="cancelled"/>
        <concept id="529" rubric="scheduled"/>
        <concept id="245" rubric="active"/>
        <concept id="530" rubric="suspended"/>
        <concept id="531" rubric="aborted"/>
        <concept id="532" rubric="completed"/>
        <concept id="533" rubric="expired"/>
    </group>
    <group name="instruction transitions">
        <concept id="535" rubric="initiate"/>
        <concept id="536" rubric="plan step"/>
        <concept id="537" rubric="postpone"/>
        <concept id="538" rubric="restore"/>
        <concept id="166" rubric="cancel"/>
        <concept id="542" rubric="postponed step"/>
        <concept id="539" rubric="schedule"/>
        <concept id="540" rubric="start"/>
        <concept id="541" rubric="do"/>
        <concept id="543" rubric="active step"/>
        <concept id="544" rubric="suspend"/>
        <concept id="545" rubric="suspended step"/>
        <concept id="546" rubric="resume"/>
        <concept id="547" rubric="abort"/>
        <concept id="548" rubric="finish"/>
        <concept id="549" rubric="time out"/>
        <concept id="550" rubric="notify aborted"/>
        <concept id="551" rubric="notify completed"/>
        <concept id="552" rubric="notify cancelled"/>
    </group>
    <group name="subject relationship">
        <concept id="0" rubric="self"/>
        <concept id="3" rubric="foetus"/>
        <concept id="10" rubric="mother"/>
        <concept id="9" rubric="father"/>
        <concept id="6" rubric="donor"/>
        <concept id="253" rubric="unknown"/>
        <concept id="261" rubric="adopted daughter"/>
        <concept id="260" rubric="adopted son"/>
        <concept id="259" rubric="adoptive father"/>
        <concept id="258" rubric="adoptive mother"/>
        <concept id="256" rubric="biological father"/>
        <concept id="255" rubric="biological mother"/>
        <concept id="23" rubric="brother"/>
        <concept id="28" rubric="child"/>
        <concept id="265" rubric="cohabitee"/>
        <concept id="257" rubric="cousin"/>
        <concept id="29" rubric="daughter"/>
        <concept id="264" rubric="guardian"/>
        <concept id="39" rubric="maternal aunt"/>
        <concept id="8" rubric="maternal"/>
        <concept id="7" rubric="maternal "/>
        <concept id="38" rubric="maternal uncle"/>
        <concept id="189" rubric="neonate"/>
        <concept id="254" rubric="parent"/>
        <concept id="22" rubric="partner/spouse"/>
        <concept id="41" rubric="paternal aunt"/>
        <concept id="36" rubric="paternal grandfa-"/>
        <concept id="37" rubric="paternal grand-"/>
        <concept id="40" rubric="paternal uncle"/>
        <concept id="27" rubric="sibling"/>
        <concept id="24" rubric="sister"/>
        <concept id="31" rubric="son"/>
        <concept id="263" rubric="step father"/>
        <concept id="262" rubric="step mother"/>
        <concept id="25" rubric="step or half brother"/>
        <concept id="26" rubric="step or half sister"/>
    </group>
    <group name="term mapping purpose">
        <concept id="669" rubric="public health"/>
        <concept id="670" rubric="reimbursement"/>
        <concept id="671" rubric="research study"/>
    </group>
    <group name="event math function">
        <concept id="145" rubric="minimum"/>
        <concept id="144" rubric="maximum"/>
        <concept id="267" rubric="mode"/>
        <concept id="268" rubric="median"/>
        <concept id="146" rubric="mean"/>
        <concept id="147" rubric="change"/>
        <concept id="148" rubric="total"/>
        <concept id="149" rubric="variation"/>
        <concept id="521" rubric="decrease"/>
        <concept id="522" rubric="increase"/>
        <concept id="640" rubric="actual"/>
    </group>
    <group name="setting">
        <concept id="225" rubric="home"/>
        <concept id="227" rubric="emergency care"/>
        <concept id="228" rubric="primary medical "/>
        <concept id="229" rubric="primary nursing "/>
        <concept id="230" rubric="primary allied "/>
        <concept id="231" rubric="midwifery care"/>
        <concept id="232" rubric="secondary medical "/>
        <concept id="233" rubric="secondary nursing "/>
        <concept id="234" rubric="secondary allied "/>
        <concept id="235" rubric="complementary "/>
        <concept id="236" rubric="dental care"/>
        <concept id="237" rubric="nursing home care"/>
        <concept id="238" rubric="other care"/>
    </group>
        
          
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        res << new OpenEHRConcept(group:'',conceptId:'',rubric:'',lang:'es')
        */
    }
    
}
