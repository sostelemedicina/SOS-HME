/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package binding

import hce.core.support.identification.*
import org.openehr.am.archetype.Archetype

import tablasMaestras.* 

/**
 * @author leacar21
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 */
class CtrlTerminologia {
    
    private static final INSTANCE = new CtrlTerminologia()
    public static getInstance(){ return INSTANCE }
    private CtrlTerminologia() {}
    
    // FIXME: la logica asociada con cada terminologyId deberia configurarse por 
    //        fuera, por ejemplo en un XML como se hizo lo de los eventos, asi no
    //        es necesario tocar esta clase cada vez que se agrega una nueva tabla
    //        maestra o acceso a terminologia externa mediante WS.
    String getTermino(TerminologyID terminologyId, String codigo, Archetype arquetipo, Locale locale)
    {
        String lang = 'es' // Lenguaje por defecto, FIXME: sacar de config.
        if (locale)
        {
            lang = locale.getLanguage()
        }
        
        switch ( terminologyId.name )
        {
            case "local":
                /*
                def archetypeTerm = arquetipo.ontology.termDefinition('es', codigo) // TODO: lenguaje no hardcoded
                if (!archetypeTerm)
                    return "" //El termino con codigo [${code}] no esta definido en el arquetipo, posiblemente el termino no esta definido para el lenguaje seleccionado.<br/>
                else
                    return archetypeTerm.text //archetypeTerm.items.text
                */
                String text
                def term = arquetipo.ontology.termDefinition(lang, codigo)
                if (!term)
                {
                    text = 'Termino no encontrado en el arquetipo ['+codigo+'], '+
                           'para el nodo ['+codigo+'], y el lang ['+lang+']'
                }
                else
                {
                    //text = term.getItems().text // TODO: esto que tira en comparacion con term.text?
                    text = term.text
                }
                
                return text
            break
            case "cie10":
                // FIXME: optimizacion: usar criteria y un OR.
                def cie10 = Cie10Trauma.findByCodigo( codigo )
                if (!cie10)
                    cie10 = Cie10Trauma.findBySubgrupo( codigo ) // Caso de que se selecciona un subgrupo, no tiene codigo.

                return cie10.nombre
            break
            case "openehr":
                def oehconcept = OpenEHRConcept.findByConceptId( codigo )
                return oehconcept.rubric
            break
            case "motivos_consulta":
                def mc = MotivoConsulta.findByCodigo( codigo )
                return mc.nombre
            break
            case "departamentos_uy":
                def du = DepartamentoUY.findByIso3166_2UY( codigo )
                return du.nombre
            break
            case "emergencias_moviles":
                def em = EmergenciaMovil.findByNombre( codigo ) // no tienen codigos, el codigo es el propio nombre
                return em.nombre
            break
        }
        
        // TODO
        // TerminologyID puede no tener version y en name puede venir la version con la siguiente sintaxis: name(version)
        return "Termino Provisorio | Codigo: " + codigo
    }
    
    public List getNombreTerminos(String terminologyId)
    {
        def list
        switch ( terminologyId )
        {
            case "openehr":
                list = OpenEHRConcept.list()
                return list.rubric // lista
            break
            case "motivos_consulta":
                list = MotivoConsulta.list()
                return list.nombre // lista de nombres
            break
            case "departamentos_uy":
                list = DepartamentoUY.list()
                return list.nombre // lista de nombres
            break
            case "emergencias_moviles":
                list = EmergenciaMovil.list()
                return list.nombre // lista de nombres
            break
        }
        
        return []
    }
    
    public List getCodigoTerminos(String terminologyId)
    {
        def list
        switch ( terminologyId )
        {
            case "openehr":
                list = OpenEHRConcept.list()
                return list.conceptId // lista
            break
            case "motivos_consulta":
                list = MotivoConsulta.list()
                return list.codigo // lista
            break
            case "departamentos_uy":
                list = DepartamentoUY.list()
                return list.iso3166_2UY // lista
            break
            case "emergencias_moviles":
                list = EmergenciaMovil.list()
                return list.nombre // no tienen codigos, el codigo es el propio nombre
            break
        }
        
        return []
    }
}

