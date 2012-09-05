package binding

import org.springframework.validation.FieldError
import org.springframework.validation.BeanPropertyBindingResult

import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.support.RequestContextUtils as RCU

import hce.core.datastructure.itemstructure.representation.*
import hce.core.data_types.basic.*
import hce.core.data_types.encapsulated.*
import hce.core.data_types.quantity.*
import hce.core.data_types.quantity.date_time.*
import hce.core.data_types.text.*
import hce.core.data_types.uri.*
import hce.core.support.identification.*
import hce.core.datastructure.itemstructure.*
import hce.core.datastructure.history.*
import hce.core.composition.*
import hce.core.composition.content.*
import hce.core.composition.content.entry.*
import hce.core.composition.content.navigation.*
import hce.core.common.archetyped.*

import org.openehr.am.archetype.Archetype
import org.openehr.am.openehrprofile.datatypes.quantity.*
import org.openehr.am.openehrprofile.datatypes.text.CCodePhrase
import org.openehr.am.openehrprofile.datatypes.quantity.Ordinal
import org.openehr.am.archetype.constraintmodel.*

import org.codehaus.groovy.grails.commons.ApplicationHolder

import java.util.Set
import java.net.URI
import com.thoughtworks.xstream.XStream

import converters.DateConverter // Para formatos de fechas

/**
 * @author Leandro Carrasco, Pablo Pazos Gutierrez<pablo.swp@gmail.com>
 * 
 */
class FactoryObjectRM {

    //private static final INSTANCE = new FactoryObjectRM()
    //public static getInstance(){ return INSTANCE }
    //private FactoryObjectRM() {}
    def session
    def FactoryObjectRM(Object session)
    {
        this.session = session
    }

    //-----------------------------------------------------------------------------

    /**
    *
    * Operación que encapsula el seteo de los atrinutos archetypeNodeId, name y
    * archetypeDetails del los Objetos del RM creados.
    *
    */
    def completarLocatable(Locatable locatable, String archNodeId, Archetype archetype, String tempId)
    {
        //println "ENTRANDO A COMPLETAR LOCATABLE"

        String rmV = ApplicationHolder.application.config.openEHR.RMVersion
        String archetypeId = archetype.archetypeId.value

        // FIXME: si es null o vacio es lo mismo.
        // Creo que no se puede crear un nodo si no tiene id de arquetipo, ver especificacion.
        if(archetypeId == null)
        {
            archetypeId = ""
        }
        // FIXME: sacar version de config
        if (rmV == null)
        {
            rmV = "1.0.2"
        }

        Archetyped archDetails = new Archetyped(archetypeId: archetypeId, templateId: tempId, rmVersion: rmV)

        // ===============================
        //XStream xstream = new XStream()
       // String xml = xstream.toXML(archetype.ontology)
        //println xml
        //println "archNodeId: " + archNodeId
        // ===============================
        
        // FIXME: para esto se puede usar CtrlTerminologia
        String lang = 'es' // Lenguaje por defecto, FIXME: sacar de config.
        if (this.session)
        {
            def locale = session.locale
            if (locale)
            {
                lang = locale.getLanguage()
            }
        }
        
        String nameN
        if (archNodeId) // FIXME: todos los nodos tienen nodeID, es al pedo chequear.
        {
            // FIXME: sacar el idioma del locale seleccionado, si no, no va a encontrar
            // la definicion del termino.
            // FIXME: ver que se pone si lo que retorna termDefinition es null y 
            // no se puede hacer getItems. Esto pasa cuando hay un nodo CodedText 
            // que referencia a una terminologia externa mediante ConstraintRef.
            //nameN = archetype.ontology.termDefinition("es", archNodeId)?.getItems()?.text
            
            def term = archetype.ontology.termDefinition(lang, archNodeId)
            if (!term)
            {
                nameN = 'Termino no encontrado en el arquetipo ['+archetypeId+'], '+
                        'para el nodo ['+archNodeId+'], y el lang ['+lang+']'
            }
            else
            {
                nameN = term.getItems().text // TODO: verificar si getItems da un solo texto!
            }
        }
        else
        {
            // FIXME
            nameN = 'Termino no encontrado en el arquetipo ['+archetypeId+'], '+
                    'para el nodo ['+archNodeId+']'
        }
        DvText nameNode = new DvText(value: nameN)

        locatable.archetypeNodeId = archNodeId
        locatable.name = nameNode
        locatable.archetypeDetails = archDetails
    }

    //--------------------------------------------------------------------------------------

    /**
    *
    * Operación que encapsula el seteo de los atrinutos encoding, language
    * del los Objetos del Entry creados.
    *
    */
    def completarEntry (Entry e)
    {
        TerminologyID tidE = TerminologyID.create("TODO_E", null) // TODO, Obtenerlo de algún lado
        TerminologyID tidL = TerminologyID.create("TODO_L", null) // TODO, Obtenerlo de algún lado

        e.encoding = new CodePhrase(codeString: "TODO", terminologyId: tidE)
        e.language = new CodePhrase(codeString: "TODO", terminologyId: tidL)
    }

    //--------------------------------------------------------------------------------------

    /**
    * Operación que copia los valores de todos los atributos de rmObjectRootSec
    * a rmObject. Esta operación se utiliza en la operacion completarRMOAS de la clase
    * BindingAOMRM para armar el arbol del RM completo para un template.
    */
    def clonarRMO(Locatable rmObject, Locatable rmObjectRootSec)
    {
        // PAB: con groovy podes pedir los atributos como una lista, iterarlos e irlos seteando por su nombre (te evita hacer el switch).
        //      creo que haciendo rmObject.class.fields te da todos los campos, hay que probar.
        rmObject.archetypeNodeId = rmObjectRootSec.archetypeNodeId
        rmObject.name = rmObjectRootSec.name
        rmObject.archetypeDetails = rmObjectRootSec.archetypeDetails
        Class tipoRM = rmObjectRootSec.getClass()
        switch(tipoRM) {
            case Cluster:
                rmObject.items = rmObjectRootSec.items
                break;
            case Element:
                rmObject.null_flavor = rmObjectRootSec.null_flavor
                rmObject.value = rmObjectRootSec.value
                break;
            case ItemList:
                rmObject.items = rmObjectRootSec.items
                break;
            case ItemSingle:
                rmObject.item = rmObjectRootSec.item
                break;
            case ItemTable:
                rmObject.rows = rmObjectRootSec.rows
                break;
            case ItemTree:
                rmObject.items = rmObjectRootSec.items
                break;
            case CareEntry:
                break;
            case Action:
                rmObject.time = rmObjectRootSec.time
                rmObject.description = rmObjectRootSec.description
                break;
            case Observation:
                rmObject.data = rmObjectRootSec.data
                break;
            case Instruction:
                rmObject.narrative = rmObjectRootSec.narrative
                rmObject.expiryTime = rmObjectRootSec.expiryTime
                rmObject.wfDefinition = rmObjectRootSec.wfDefinition
                rmObject.activities = rmObjectRootSec.activities
                break;
            case Evaluation:
                rmObject.data = rmObjectRootSec.data
                break;
            case Section:
                rmObject.items = rmObjectRootSec.items
                break;
            case Composition:
                rmObject.context = rmObjectRootSec.context
                rmObject.category = rmObjectRootSec.category
                rmObject.territory = rmObjectRootSec.territory
                rmObject.language = rmObjectRootSec.language
                rmObject.content = rmObjectRootSec.content
                break;
            default:
               println "tipoRM no considerado: " + tipoRM.ToString()
               break;
        }
    }

    //--------------------------------------------------------------------------

    /**
    *
    * Operación que crea un objeto del RM "vacio". Utilizada para
    * bindear ArchetypeSlots.
    *
    * FIXME: Locatable es un super-tipo, nunca es utilizado directamente.
    *        ¿desde donde se llama a este metodo? desde BindingAOMRM.bindArchetypeSlot()
    *        pero no se si es correcto llamar solo para crear una instancia vacia...
    *        
    * TODO: necesito el arquetipo que se usa para crear este locatable... para setearle su
    *       archetypeDetails aqui, asi luego puedo saber con que arquetipo se arquetipo y
    *       que nodo se usa para hacerlo.
    */
    //def createLOCATABLE(String tipoRM)
    def createLOCATABLE(String tipoRM, String archNodeId, Archetype arquetipo, String tempId)
    {
        //println "|||||||||||||||||||||||||||||||||||||||||||||||||"
        //println "|||| CREATE LOCATABLE type: " + tipoRM + " |||||"
        //println "|||||||||||||||||||||||||||||||||||||||||||||||||"
        Locatable rmObject
        switch(tipoRM)
        {
            case "CLUSTER":
                rmObject = new Cluster()
            break
            case "ELEMENT":
                rmObject = new Element()
            break
            case "ITEM_LIST":
                rmObject = new ItemList()
                break;
            case "ITEM_SINGLE":
                rmObject = new ItemSingle()
                break;
            case "ITEM_TABLE":
                rmObject = new ItemTable()
            break
            case "ITEM_TREE":
                rmObject = new ItemTree()
            break
            case "CARE_ENTRY":
                rmObject = new CareEntry()
            break
            case "ACTION":
                rmObject = new Action()
            break
            case "OBSERVATION":
                rmObject = new Observation()
            break
            case "INSTRUCTION":
                rmObject = new Instruction()
            break
            case "EVALUATION":
                rmObject = new Evaluation()
            break
            case "ADMIN_ENTRY":
                rmObject = new AdminEntry()
            break
            case "SECTION":
                rmObject = new Section()
            break
            case "COMPOSITION":
                rmObject = new Composition()
            break
            default:
               println "tipoRM no considerado: " + tipoRM
            break
        }

        completarLocatable(rmObject, archNodeId, arquetipo, tempId)

        return rmObject
    }

    // -----------------------------
    // data_structure.representation
    // -----------------------------

    def createSECTION(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        Section sec = new Section()
        List<Object> listaItems = listaListRMO[0]
        
        /*
        println "--"
        println "--"
        println "-- createSECTION listListRMO: " + listaListRMO
        println "--"
        println "--"
        */

        if (listaItems.size() == 0) return null

        listaItems.each{ item ->
        
            if (item)
                sec.addToItems(item)
        }

        completarLocatable(sec, archNodeId, arquetipo, tempId)
        return sec
    }

    def createITEM_TREE(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        // FIXME: si no vienen items, retornar null
        ItemTree itemT = new ItemTree()
        List<Object> listaItems = listaListRMO[0]
    
        println "--------------------------------------------------------"
        println "---------- ITEM TREE items: " + listaListRMO
        println "--------------------------------------------------------"
        
        // Dejo bindear para mostrar errores del GORM
        // Si no tengo items, no tengo arbol
        //if (listaItems.size()==0) return null
    
        listaItems.each{eachItem ->
            if (eachItem != null){
                itemT.addToItems(eachItem)
            }
        }

        completarLocatable(itemT, archNodeId, arquetipo, tempId)
        return itemT
    }

    def createITEM_SINGLE(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "== ITEM SINGLE"
        //println "==== listaListRMO: " + listaListRMO
        
        ItemSingle itemSingle = new ItemSingle()
        List<Object> listaItems = listaListRMO[0]
        
        // Dejo bindear para mostrar errores del GORM
        // Si no tengo item, el item single es null
        //if (!listaItems[0]) return null
    
        itemSingle.item = listaItems[0]

        completarLocatable(itemSingle, archNodeId, arquetipo, tempId)
        return itemSingle
    }

    def createITEM_LIST(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "---------- ITEM LIST items: " + listaListRMO
        
        List<Object> listaItems = listaListRMO[0]
        
        // Dejo bindear para mostrar errores del GORM
        // Si no tengo items, no tengo lista
        //if (listaItems.size()==0) return null
        
        ItemList itemL = new ItemList()
        listaItems.each{ item ->
            if (item)
            {
                if (item instanceof List)
                {
                    item.each{ subItem ->
                        if (subItem)
                            itemL.addToItems(subItem)
                    }
                }
                else
                {
                    itemL.addToItems(item)
                }
            }
        }

        completarLocatable(itemL, archNodeId, arquetipo, tempId)
        return itemL
    }

    def createITEM_TABLE(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        ItemTable itemT = new ItemTable()
        List<Object> listaItems = listaListRMO[0]
        
        // Dejo bindear para mostrar errores del GORM
        // Si no tengo rows no tengo tabla
        //if (listaItems.size()==0) return null
    
        listaItems.each{eachItem ->
            if (eachItem != null){
                itemT.addToRows(eachItem)
            }
        }

        completarLocatable(itemT, archNodeId, arquetipo, tempId)
        return itemT
    }

    def createEVENT(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "== createEVENT"
        //println "==== listaList: " + listaListRMO
        
        Event e
        List<Object> listaItems = listaListRMO[0]
        if (listaItems.size() == 1)
        {
            e = new Event()
            e.data = listaItems[0]
            e.time = new DvDateTime(value: DateConverter.toHL7DateFormat(new Date()) )
            completarLocatable(e, archNodeId, arquetipo, tempId)
        }
        
        //println "==== return event: " + e
        //println "=================================================="
        
        return e
    }

    def createHISTORY(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        History h = new History()
        List<Object> listaEvents = listaListRMO[0]
        
        // Si no tiene eventos, no tengo history
        if (listaEvents.size() == 0) return null
        
        //imprimirObjetoXML(listaEvents)
        
        if (listaEvents.size() == 1)
        {
            listaEvents.each { event ->
                
                if (event)
                    h.addToEvents(event)
            }
    
            h.origin = new DvDateTime(value: DateConverter.toHL7DateFormat(new Date()) ) //new DvDateTime(value: "20091121")
            completarLocatable(h, archNodeId, arquetipo, tempId)
        }
        
        // TODO: verificar que no haya otro caso.
        
        return h
    }

    def createACTIVITY(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        Activity a = new Activity()
        List<Object> listaItems = listaListRMO[0]
        if (listaItems.size() == 1)
        {
            a.description = listaItems[0]
            a.timing = new DvParsable(value: "value", formalism: "formalism") // FIXME: de donde sacar los valores?
            a.action_archetype_id = arquetipo.archetypeId.value
            completarLocatable(a, archNodeId, arquetipo, tempId)
        }

        return a
    }

    
    //----------------------------------------------------------------------
    // ENTRY
    //----------------------------------------------------------------------

    def createOBSERVATION(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        Observation o
        List<Object> listaItems = listaListRMO[0]
        
        /*
        println "--"
        println "--"
        println "-- createOBSERVATION listListRMO: " + listaListRMO
        println "--"
        println "--"
        */
        
        //if (listaItems.size() == 0) return null
        
        if (listaItems.size() == 1)
        {
            o = new Observation()
            o.data = listaItems[0]
            completarLocatable(o, archNodeId, arquetipo, tempId)
            completarEntry(o)
        }

        // TODO: no puede haber un caso donde vengan 2 elementos,
        // y si lo hay cae aca y deberia tirar except o algo...
        
        return o
    }
    
    def createEVALUATION(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "== createEVALUATION"
        //println "==== listaList: " + listaListRMO
        //println "============================================="
        
        Evaluation e
        List<Object> listaItems = listaListRMO[0]
        
        //if (listaItems.size() == 0) return null
        
        // Si viene algo es un item_structure
        if (listaItems.size() == 1)
        {
            e = new Evaluation()
            e.data = listaItems[0]
            completarLocatable(e, archNodeId, arquetipo, tempId)
            completarEntry(e)
        }
        
        // TODO: No deberia haber otro caso, no esta de mas chequear
        
        return e
    }

    def createINSTRUCTION(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "== createINSTRUCTION"
        
        // En listaListRMO viene:
        // - Siempre: un DvText que es el narrative
        // - Opcional: lista de Activities
        
        Instruction instruction = new Instruction()
        
        //println "~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~~+~+~+~+~"
        //println "~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~~+~+~+~+~"
        //imprimirObjetoXML(listaListRMO)
        //println "~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~~+~+~+~+~"
        //println "~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~~+~+~+~+~"
        
        DvText narrative
        List activities = []
        if (listaListRMO.size()==1) // viene solo el narrative
        {
            if (listaListRMO[0][0] instanceof DvText)
                narrative = listaListRMO[0][0] // el narrative es el primer elemento de la primer lista
            else
                throw new Exception("Se esperaba un DvText y se obtuvo un " + listaListRMO[0][0].getClass() + " revisar el arquetipo de la Instruction porque seguramente no se definio el nodo narrative que es obligatorio, ver: " + arquetipo.archetypeId.value)
            
        }
        else // viene narrative y activities
        {
            if (listaListRMO[0] instanceof List) // el primer elemento son las activities
            {
                activities = listaListRMO[0]
                narrative  = listaListRMO[1][0] // el narrative es el primer elemento de la segunda lista
            }
            else // el primer elemento el el narrative
            {
                activities = listaListRMO[1]
                narrative  = listaListRMO[0][0] // el narrative es el primer elemento de la primer lista
            }
        }
        // No tengo otro caso posible
        
        
        instruction.narrative = narrative
        
        activities.each{ activity ->
        
            if (activity)
                instruction.addToActivities(activity)
        }

        completarLocatable(instruction, archNodeId, arquetipo, tempId)
        completarEntry(instruction)
        
        return instruction
    }

    def createACTION(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "== createACTION"
        //println "==== listaListRMO: "+ listaListRMO
        
        Action a = new Action()
        List<Object> listaItems = listaListRMO[0]
        
        //if (listaItems.size()==0) return null
        
        //println listaItems + " sz:"+listaItems.size() + " class:"+listaItems.getClass().getSimpleName()
        //println "==============================================="
        
        if (listaItems.size() == 1)
        {
            a.description = listaItems[0] // Se sabe que description es oblig.
            
            // FIXME: mal fecha
            //a.time = new DvDateTime(value: "20091121")
            a.time = new DvDateTime(value: DateConverter.toHL7DateFormat(new Date()) )
        }

        completarLocatable(a, archNodeId, arquetipo, tempId)
        completarEntry(a)
        return a
    }

    def createADMIN_ENTRY(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "=== ADMIN_ENTRY ==="
        //println "=== listaListRMO: " + listaListRMO
        AdminEntry ae = new AdminEntry()
        List<Object> listaItems = listaListRMO[0]
        if (listaItems.size() == 1)
        {
            ae.data = listaItems[0]
        }

        completarLocatable(ae, archNodeId, arquetipo, tempId)
        completarEntry(ae)
        return ae
    }

    //----------------------------------------------------------------------
    // CLUSTER
    def createCLUSTER(List<Object> listaItems, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "??????????????? createCLUSTER"
        //println "??????????????? listaItems: " + listaItems
        //println "???????????????????????????????????????????????????"
        
        // Dejo bindear para mostrar errores del GORM
        //if (listaItems.size()==0) return null
        
        Cluster cl = new Cluster()
        listaItems.each{ item ->
        
            if ( item )
            {
                cl.addToItems(item)
            }
        }

        completarLocatable(cl, archNodeId, arquetipo, tempId)
        return cl
    }

    //--------------------------------------------------------------------------
    //ELEMENT
    def createELEMENT(DataValue bindedValue, Archetype arquetipo, String archNodeId, String tempId)
    {
        // PAB:
        // Saque un TRY que era al pedo porque queria tirar una except y tiraba para cartchear
        // y tirar de nuevo, la tiro directamente.
        
        Element e = new Element(value: bindedValue)
        completarLocatable(e, archNodeId, arquetipo, tempId)
        return e
    }

    // -----------------------------
    // data_types.basic
    // -----------------------------
    
    // DV_IDENTIFIER
    def createDV_IDENTIFIER(pathValor, Archetype arquetipo, String archNodeId, String tempId)
    {
        // TODO
        return new DvIdentifier(assigner: "assigner", code: "code", issuer: "issuer", type: " type")
    }

    // DV_STATE
    def createDV_STATE(pathValor, Archetype arquetipo, String archNodeId, String tempId)
    {
        // TODO
        return new DvState(terminal: false, new DvCodedText(definingCode: new CodePhrase(codeString: "TODO"), value: "TODO" ))
    }

    // -----------------------------
    // data_types.encapsulated
    // -----------------------------

    // DV_ENCAPSULATED
    // TODO: ?
    
    /**
     * org.springframework.web.multipart.MultipartFile es el tipo de los archivos que se suben desde la web.
     * 
     */
    def createDV_MULTIMEDIA (
            org.springframework.web.multipart.MultipartFile file,
            Archetype arquetipo,
            String archNodeId,
            String tempId)
    {
        //println "createDV_MULTIMEDIA"
        //println "--- file: " + file
        //println "======================================================="
        
        
        def mm = new DvMultimedia()
        
        mm.data = new byte[file.size]
        file.inputStream.read( mm.data ) // file -> mm.data
        
        // FIXME: ver los tipos en la terminologia openehr...
        mm.mediaType = new CodePhrase(
                         codeString: file.contentType,
                         terminologyId: TerminologyID.create('openehr', null)
                       )
        
        // FIXME: ver si el alternate text esta bien usado
        mm.alternateText = file.originalFilename
        mm.size = file.size // tamanio en bytes

        return mm
    }

    // DV_PARSABLE
    def createDV_PARSABLE(pathValor, Archetype arquetipo, String archNodeId, String tempId)
    {
        // TODO
        return new DvParsable(value: "TODO", formalism: "TODO")
    }

    // -----------------------------
    // data_types.quantyty
    // -----------------------------

    //DV_ABSOLUTE_QUANTITY
    // TODO: ?
    
    //DV_AMOUNT
    // TODO: ?

    // DV_COUNT
    //def createDV_COUNT(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    // El valor es string y lo tengo que pasar a INT, ahi puedo tirar except por si no tiene formato correcto.
    // El value viene bindeado de createInteger
    //def createDV_COUNT(Integer value, Archetype arquetipo, String archNodeId, String tempId)
    // pruebo pasarle string para que valide en GORM, el deberia decir si es null o si esta mal formado al pasar a int.
    def createDV_COUNT(String value, Archetype arquetipo, String archNodeId, String tempId)
    {
        //println "----- createDV_COUNT value: " + value
        
        // prueba: para que si no hay valor ni siquiera siga y si el ELEMENT era
        //         obligatorio, deberia saltar el error al verificar ocurrencias.
        /* ABC*
         * Si retorno null pasa esto>
         * Caused by: java.lang.NullPointerException: Cannot get property 'errors' on null object
         *     at binding.BindingAOMRM.bindDV_COUNT(BindingAOMRM.groovy:2367)
         */
        if (!value) return null
        
        // Reescribo string vacio a null, para que de el error correcto en el GORM
//        if (!value) value = null
        def dvcount = new DvCount(magnitude: value) // Por ahora me sirve ponerle null asi puedo ponerle rejectValue cuando viene un string no parseable a int
        
        // Es necesario validar aca por el ticket #23: http://code.google.com/p/open-ehr-sa/issues/detail?id=23
        if (!dvcount.validate()) // Si valido asi me mete como rejectedValue null y es un string tipo "werwet"...
        {
            // FIXME: BUG de GRAILS: si le meto un string mal formado a un Double y trato de validar
            //        me dice que el rejectedValue es null... y quiero el string mal formado!
            try
            {
                Integer.parseInt(value)	
            }
            catch (Exception e)
            {
                //println "No valida numero entero: " + e.getMessage()
                
                // Si ya hay un error en magnitude
                if ( dvcount.errors.getFieldError('magnitude') )
                {
                    // http://static.springsource.org/spring/docs/2.5.x/api/index.html?org/springframework/validation/FieldError.html
                    dvcount.errors = new BeanPropertyBindingResult(dvcount, 'DvCount') // Borro el error actual y meto uno con el rejectedValue correcto.
                    
                    // FieldError(String objectName, String field, Object rejectedValue, boolean bindingFailure, String[] codes, Object[] arguments, String defaultMessage)
                    dvcount.errors.addError(
                        new FieldError('DvCount', 'magnitude',
                            value, false,
                            ["typeMismatch.java.lang.Integer"] as String[],
                            null, null
                        )
                    )
                }
            }
        }
        
        return dvcount
    }
    
    
    // Quiero que valide el GORM por eso le paso un string en el value.
    // Value sera true, false, '' o null.
    def createDV_BOOLEAN(String value, Archetype arquetipo, String archNodeId, String tempId)
    {
        // Reescribo string vacio a null, para que de el error correcto en el GORM
        if (!value) value = null
        def ret = new DvBoolean(value: value)
        
        ret.validate() // quiero que valide por nulls
        
        //println "== createDV_BOOLEAN"
        //println "==== ERRORS: " + ret.errors
        
        return ret
    }
    
    /**
     * FIXME:
     * Esta mal el nombre del tipo en el archetype parser, en lugar de Boolean pone
     * DvBoolean. Ver que para Integer pone Integer no DvInteger. Ver que de esto
     * hubo una discusion en la mail list hace un tiempo. 
     */
    //def createDvBoolean(String value, Archetype arquetipo, String archNodeId, String tempId)
    //{
    //    return Boolean.parseBoolean(value)
    //}

    // DV_INTERVAL
    def createDV_INTERVAL(pathValor, Archetype arquetipo, String archNodeId, String tempId){
        // TODO
        return new DvInterval()
    }

    //DV_ORDERED
    // TODO: ?

    //DV_ORDINAL
    // Se usa createDvOrdinal

    // DV_PROPORTION
    def createDV_PROPORTION(pathValor, Archetype arquetipo, String archNodeId, String tempId)
    {
        // TODO
        return new DvProportion() // Da error en versiones > Grails 1.1.1
    }

    //DV_QUANTIFIED
    // TODO: ?

    // PROPORTION_KIND
    def createPROPORTION_KIND(pathValor, Archetype arquetipo, String archNodeId, String tempId)
    {
        return ProportionKind.RATIO
    }

    // REFERENCE_RANGE
    def createREFERENCE_RANGE(pathValor, Archetype arquetipo, String archNodeId, String tempId)
    {
        // TODO
        ReferenceRange rr = new ReferenceRange(range: new DvInterval())
        return rr
    }

    // -----------------------------
    // data_types.quantity.date_tyme
    // -----------------------------

    // DV_DATE
    /*def createDV_DATE(LinkedHashMap<String, Object> pathValor, Archetype arquetipo, String archNodeId, String tempId){

        // Encuentro los valores en la coleccion de path valor
        String year = pathValor.find{it.key.endsWith("year")}?.value
        String month = pathValor.find{it.key.endsWith("month")}?.value
        String day = pathValor.find{it.key.endsWith("day")}?.value

        if ((year != null) && (month != null) && (day != null)){
            // Creo un string con formato ISO 8601
            String fechaISO8601 = crearFechaISO8601(year, month, day, "", "", "")
            return new DvDate(value: fechaISO8601)
        }
        else{
            return null
            //throw new Exception("createDV_DATE: Colección de pathValor no tiene path a 'year' o 'month' o 'day'.")
        }
    }*/

    def createDV_DATE(String year, String month, String day, Archetype arquetipo, String archNodeId, String tempId){

        if(year == "" && month == "" && day ==""){
           //Retorna un DvDate null, esperando que GORM valide
            def date = new DvDate(value: null)
            date.validate()
            return date
        }

        //println "ENTRO DV_DATE"
        String fechaISO8601 = crearFechaISO8601(year, month, day, "", "", "")
        //println "---->" + fechaISO8601
        return new DvDate(value: fechaISO8601)
    }

    /*def createDV_DATE(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId){

        if (listaListRMO.flatten() == []){
            return null
        }
        try{
            if (listaListRMO[0].size() == 1){
                // Creo un string con formato ISO 8601
                String fechaISO8601 = (String)(listaListRMO[0][0])
                return new DvDate(value: fechaISO8601)
            }
            throw new Exception()
        }
        catch (Exception exc){
            throw new Exception("createDV_DATE con listaListRMO: Colección de listaListRMO no contiene un solo elemento (que debería ser string).")
        }
    }*/

    // DV_DATE_TIME
    def createDV_DATE_TIME(String year, String month, String day, String hour, String minute, String seg, Archetype arquetipo, String archNodeId, String tempId){

        if(year == "" && month == "" && day =="" && hour =="" && minute == "" && seg == ""){
           //Retorna un DvDate null, esperando que GORM valide
            def date = new DvDateTime(value: null)
            date.validate()
            return date
        }


        //println "ENTRO DV_DATE_TIME"
        String fechaISO8601 = crearFechaISO8601(year, month, day, hour, minute, seg)
        //println "---->" + fechaISO8601
        return new DvDateTime(value: fechaISO8601)
        /*
        // Encuentro los valores en la coleccion de path valor
        String year = pathValor.find{it.key.endsWith("year")}?.value
        String month = pathValor.find{it.key.endsWith("month")}?.value
        String day = pathValor.find{it.key.endsWith("day")}?.value
        String hour = pathValor.find{it.key.endsWith("hour")}?.value
        String minute = pathValor.find{it.key.endsWith("minute")}?.value
        String seg = pathValor.find{it.key.endsWith("seg")}?.value

        if ((year != null) && (month != null) && (day != null) && (hour != null)){
            // Creo un string con formato ISO 8601
            String fechaISO8601 = crearFechaISO8601(year, month, day, hour, minute, seg)
            return new DvDateTime(value: fechaISO8601)
        }
        else{
            return null
            //throw new Exception("createDV_DATE_TIME: Colección de pathValor no tiene path a 'year' o 'month' o 'day' u 'hour'.")
        }
        */
    }

    /*def createDV_DATE_TIME(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId){

        if (listaListRMO.flatten() == []){
            return null
        }
        try{
            if (listaListRMO[0].size() == 1){
                // Creo un string con formato ISO 8601
                String fechaISO8601 = (String)(listaListRMO[0][0])
                return new DvDateTime(value: fechaISO8601)
            }
            throw new Exception()
        }
        catch (Exception exc){
            throw new Exception("createDV_DATE_TIME con listaListRMO: Colección de listaListRMO no contiene un solo elemento (que debería ser string).")
        }
    }*/

    // DV_DURATION
    def createDV_DURATION(pathValor, Archetype arquetipo, String archNodeId, String tempId){
        // TODO
        DvDuration d = new DvDuration()
        return d
    }

    //DV_INTERVAL

    //DV_TEMPORAL

    // DV_TIME
    def createDV_TIME(String hour, String minute, String seg, Archetype arquetipo, String archNodeId, String tempId){

        //println "ENTRO DV_TIME"
        String fechaISO8601 = crearFechaISO8601("", "", "", hour, minute, seg)
        //println "---->" + fechaISO8601
        return new DvTime(value: fechaISO8601)

        /*
        // Encuentro los valores en la coleccion de path valor
        String hour = pathValor.find{it.key.endsWith("hour")}?.value
        String minute = pathValor.find{it.key.endsWith("minute")}?.value
        String seg = pathValor.find{it.key.endsWith("seg")}?.value

        if ((month != null) && (day != null) && (hour != null)){
            // Creo un string con formato ISO 8601
            String fechaISO8601 = crearFechaISO8601("", "", "", hour, minute, seg)
            //return new DvDateTime(value: fechaISO8601)
            return new DvTime(value: fechaISO8601)
        }
        else{
            return null
            //throw new Exception("createDV_DATE_TIME: Colección de pathValor no tiene path a 'year' o 'month' o 'day' u 'hour'.")
        }
        */
    }

    /*def createDV_TIME(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId){
        // TODO

        if (listaListRMO.flatten() == []){
            return null
        }
        try{
            if (listaListRMO[0].size() == 1){
                // Creo un string con formato ISO 8601
                String fechaISO8601 = (String)(listaListRMO[0][0])
                return new DvTime(value: fechaISO8601)
            }
            throw new Exception()
        }
        catch (Exception exc){
            throw new Exception("createDV_DATE_TIME con listaListRMO: Colección de listaListRMO no contiene un solo elemento (que debería ser string).")
        }
    }*/

    // -----------------------------
    // data_types.text
    // -----------------------------

    //CODE_PHRASE
    def createCODE_PHRASE(pathValor, Archetype arquetipo, String archNodeId, String tempId){

        if (pathValor.size() == 0)
        {
            return null
        }
        else if (pathValor.size() == 1)
        {
            String defCode = pathValor.find{it.key.endsWith("defining_code")}?.value
            String[] defCodes = defCode.split(",")

            // Si tiene mas de un valor para la path, retorno una lista de CodePhrase
            if (defCodes.size() > 1)
            {
                LinkedList<CodePhrase> listaCodePhrase = new LinkedList<CodePhrase>()
                defCodes.each { dc ->
                    listaCodePhrase.add(createCodePhrase(ccp, dc.replace(' ',''), arquetipo, archNodeId, tempId))
                }

                return listaCodePhrase
            }
            else
            {
                String dc = pathValor.find{it.key.endsWith("defining_code")}?.value
                if (dc)
                {
                    return createCodePhrase(ccp, dc, arquetipo, archNodeId, tempId)
                }
                else
                {
                    return null
                }
            }
        }
        else
        {
            throw new Exception("bindCCodePhrase: Colección de pathValor no tiene 1 elemento.")
        }
    }


    def createDV_CODED_TEXT(List<Object> listaListRMO, Archetype arquetipo, String archNodeId, String tempId)
    {
        if (listaListRMO.flatten() == [])
        {
            return null
        }

        LinkedList<DvCodedText> listaDvCodedText = new LinkedList<DvCodedText>()

        listaListRMO[0].each { cph ->
            
            Locale locale = this.session.locale
            String val = CtrlTerminologia.getInstance().getTermino(cph.terminologyId, cph.codeString, arquetipo, locale)

            // FIXME: encodear value en UTF-8 para que concuerde con text.encoding
            def ctext = new DvCodedText(definingCode: cph, value: val)

            // TODO: sacar language de config
            ctext.language = new CodePhrase(
                    codeString: 'es-UY',
                    terminologyId: TerminologyID.create('ISO_639-1', null)
            )
            ctext.encoding = new CodePhrase(
                    codeString: 'UTF-8',
                    terminologyId: TerminologyID.create('IANA_character-sets', null)
            )
            
            listaDvCodedText << ctext
        }

        return listaDvCodedText
    }


    def createDV_PARAGRAPH(pathValor, Archetype arquetipo, String archNodeId, String tempId)
    {
        // TODO
        return new DvParagraph()
    }

    //DV_TEXT
    def createDV_TEXT(String value, Archetype arquetipo, String archNodeId, String tempId)
    {
        // Reescribo string vacio a null, para que de el error correcto en el GORM
        if (!value) value = null
        
        // FIXME: encodear value en UTF-8 para que concuerde con text.encoding
        def text = new DvText(value: value)
        
        // TODO: sacar language de config
        text.language = new CodePhrase(
            codeString: 'es-UY',
            terminologyId: TerminologyID.create('ISO_639-1', null)
        )
        text.encoding = new CodePhrase(
            codeString: 'UTF-8',
            terminologyId: TerminologyID.create('IANA_character-sets', null)
        )
        
        text.validate()
        return text
    }


    //TERM_MAPPING
    def createTERM_MAPPING(pathValor, Archetype arquetipo, String archNodeId, String tempId){
        // TODO
        TerminologyID tid = TerminologyID.create("TODO", null) // Obtenerlo de algún lado
        CodePhrase cph =  new CodePhrase(codeString: "TODO", terminologyId: tid)

        return new TermMapping(match: TermMapping.UNKNOWN, target: cph)
    }

    // -----------------------------
    // data_types.time_spesification
    // -----------------------------

    //DV_GENERAL_TIME_SPECIFICATION

    //DV_PERIODIC_TIME_SPECIFICATION
    def createDV_PERIODIC_TIME_SPECIFICATION(){
        // TODO
    }

    //DV_TIME_SPECIFICATION
    def createDV_TIME_SPECIFICATION(){
        // TODO
    }

    // -----------------------------
    // data_types.uri
    // -----------------------------

    // DV_EHR_URI
    def createDV_EHR_URI(pathValor, Archetype arquetipo, String archNodeId, String tempId){
        // TODO
        return new DvEHRURI(value: new URI("ehr.TODO"))
    }

    def createDV_URI(pathValor, Archetype arquetipo, String archNodeId, String tempId){
        // TODO
        return new DvURI(value: new URI("TODO"))
    }


    //-----------------------------------
    // C_DOMAIN_TYPE
    //-----------------------------------

    def createCodePhrase(CCodePhrase ccp, String cs, Archetype arquetipo, String archNodeId, String tempId)
    {
        Locale locale = this.session.locale
        
        // Obtengo terminologiId del objeto CCodePhrase que viene por parametro
        TerminologyID tid = TerminologyID.create(ccp.getTerminologyId().name(), ccp.getTerminologyId().versionID()) //new TerminologyID(name: "local", versionId: "1.0")
        return new CodePhrase(codeString: cs, terminologyId: tid)
    }

    // Pruebo pasarle en magnitude el valor que recibo de la web para que el GORM valide y reporte errores.
    def createDvQuantity(String mag, String units, Archetype arquetipo, String archNodeId, String tempId)
    {
		// ABC*
		// prueba para retornar null de createDvQuantity
		if (!mag && !units) return null
		
        //println "---------------------------------------> Entrando a createDvQuantity(double mag, String un)"
        // Reescribo string vacio a null, para que de el error correcto en el GORM
        if (!mag) mag = null
        if (!units) units = null
        def q = new DvQuantity(magnitude: mag, units: units)
        
        if (!q.validate() && mag != null) // Si es null, quiero dejar el error del GORM asi como esta, solo quiero ejecutar el codigo de abajo si viene algo distinto de vacio que no valide.
        {
            // Puede entrar si le pongo algo a magnitude pero nada a units entonces validate da false.
            
            //println q.errors.getClass() // BeanPropertyBindingResult
            
            // FIXME: BUG de GRAILS: si le meto un string mal formado a un Double y trato de validar
            //        me dice que el rejectedValue es null... y quiero el string mal formado!
            try
            {
                Double.parseDouble(mag)
                
                q.errors = new BeanPropertyBindingResult(q, 'DvQuantity') // Borro el error actual
                
                // TODO: sacar mensaje de Errors
                if (!units) q.errors.rejectValue('units',"DvQuantity.error.incomplete")
            }
            catch (Exception)
            {
                // Si ya hay un error en magnitude
                if ( q.errors.getFieldError('magnitude') )
                {
                    // http://static.springsource.org/spring/docs/2.5.x/api/index.html?org/springframework/validation/FieldError.html
                    q.errors = new BeanPropertyBindingResult(q, 'DvQuantity') // Borro el error actual y meto uno con el rejectedValue correcto.
                    
	                // FieldError(String objectName, String field, Object rejectedValue, boolean bindingFailure, String[] codes, Object[] arguments, String defaultMessage)
	                q.errors.addError(
	                    new FieldError('DvQuantity', 'magnitude', mag, false,
	                                   ["typeMismatch.java.lang.Double"] as String[], null, null)
                    )
                    
                    // TODO: sacar mensaje de Errors
                    if (!units) q.errors.rejectValue('units',"DvQuantity.error.incomplete")
                }
            }
        }
        
        return q
    }

    // PAB: que es 's' ?
    // 's' es el codeString que se selecciona en la web como valor.
    def createDvOrdinal(CDvOrdinal cdvo, String s, Archetype arquetipo, String archNodeId, String tempId)
    {
        def ord
        if (!s)
        {
            ord = new DvOrdinal(symbol: null, value: null)
            ord.validate()
        }
        else
        {
            Set<Ordinal> setOrdinal = cdvo.getList()
            //Ordinal ordin = setOrdinal.find{it.getSymbol().codeString.endsWith(s)}
            Ordinal ordin = setOrdinal.find{it.getSymbol().codeString == s} // la condicion es que sea el mismo codigo.
    
            TerminologyID ti = TerminologyID.create(ordin.getSymbol().terminologyId.name, null)
            CodePhrase cp = new CodePhrase(codeString: s, terminologyId: ti)
            
            Locale locale = this.session.locale
            String value = CtrlTerminologia.getInstance().getTermino(cp.terminologyId, cp.codeString, arquetipo, locale)
            DvCodedText ct = new DvCodedText(value: value, definingCode: cp)
            
            int v = ordin.value
            
            ord = new DvOrdinal(symbol: ct, value: v)
        }
        
        return ord
    }

    //-----------------------------------

    void imprimirObjetoXML(Object o)
    {
        println "-----------------................"
        XStream xstream = new XStream();
        String xml = xstream.toXML(o);
        println xml
        println "-----------------................."
    }

    //-----------------------------------
    // Funciones auxiliares
    //-----------------------------------

    /*
     * @author Leandro Carrasco
     *
     * FIXME: o usamos o metodos estaticos o el singleton para no tener 2 criterios distintos para hacer la misma cosa.
     */
    static String crearFechaISO8601(String anio, String mes, String dia, String hora, String min, String seg)
    {
        // FIXME: if (!mes) en lugar de if ((mes != null) && (mes != ""))
        if ((mes != null) && (mes != "") && (mes.length() == 1) && (Integer.parseInt(mes) < 10)) mes = "0" + mes
        if ((dia != null) && (dia != "") && (dia.length() == 1) && (Integer.parseInt(dia) < 10)) dia = "0" + dia
        if ((hora != null) && (hora != "") && (hora.length() == 1) && (Integer.parseInt(hora) < 10)) hora = "0" + hora
        if ((min != null) && (min != "") && (min.length() == 1) && (Integer.parseInt(min) < 10)) min = "0" + min
        if ((seg != null) && (seg != "") && (seg.length() == 1) && (Integer.parseInt(seg) < 10)) seg = "0" + seg

        if ((dia == null) || (dia == ""))
            dia = "00"
        if ((mes == null) || (mes  == ""))
            mes = "00"
        if ((min == null) || (min == ""))
            min = "00"
        if ((seg == null) || (seg == ""))
            seg = "00"

        if ((hora == null) || (hora == "")) // Date
            return anio +"-"+ mes +"-"+ dia
        else if ((anio == null) || (anio == "")) // Time
            return hora + ":" + min + ":" + seg
        else // DateTime
            return anio + "-" + mes + "-" + dia + " " + hora + ":" + min + ":" + seg // FIXME Este no es el formato ISO8601, corregirlo aqui y en el resto de la aplicación
    }
}

