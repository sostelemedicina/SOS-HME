package cda;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.2
 * 2012-12-14T10:13:09.048-04:30
 * Generated source version: 2.6.2
 * 
 */
@WebService(targetNamespace = "http://cda/", name = "CdaServicePortType")
@XmlSeeAlso({ObjectFactory.class})
public interface CdaServicePortType {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buscarCDAByPaciente", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPaciente")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByPacienteResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPacienteResponse")
    public cda.ConjuntoCda buscarCDAByPaciente(
        @WebParam(name = "paciente", targetNamespace = "")
        java.lang.String paciente,
        @WebParam(name = "offset", targetNamespace = "")
        int offset,
        @WebParam(name = "idOrganizacion", targetNamespace = "")
        java.lang.String idOrganizacion
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buscarCDAByRango", targetNamespace = "http://cda/", className = "cda.BuscarCDAByRango")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByRangoResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByRangoResponse")
    public cda.ConjuntoCda buscarCDAByRango(
        @WebParam(name = "desde", targetNamespace = "")
        java.lang.String desde,
        @WebParam(name = "hasta", targetNamespace = "")
        java.lang.String hasta,
        @WebParam(name = "offset", targetNamespace = "")
        int offset,
        @WebParam(name = "idOrganizacion", targetNamespace = "")
        java.lang.String idOrganizacion
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buscarCDAByPacienteAndRango", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPacienteAndRango")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByPacienteAndRangoResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPacienteAndRangoResponse")
    public cda.ConjuntoCda buscarCDAByPacienteAndRango(
        @WebParam(name = "paciente", targetNamespace = "")
        java.lang.String paciente,
        @WebParam(name = "desde", targetNamespace = "")
        java.lang.String desde,
        @WebParam(name = "hasta", targetNamespace = "")
        java.lang.String hasta,
        @WebParam(name = "offset", targetNamespace = "")
        int offset,
        @WebParam(name = "idOrganizacion", targetNamespace = "")
        java.lang.String idOrganizacion
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "listarOrganizacionesByPaciente", targetNamespace = "http://cda/", className = "cda.ListarOrganizacionesByPaciente")
    @WebMethod
    @ResponseWrapper(localName = "listarOrganizacionesByPacienteResponse", targetNamespace = "http://cda/", className = "cda.ListarOrganizacionesByPacienteResponse")
    public java.util.List<cda.OrgArr> listarOrganizacionesByPaciente(
        @WebParam(name = "paciente", targetNamespace = "")
        java.lang.String paciente,
        @WebParam(name = "idOrganizacion", targetNamespace = "")
        java.lang.String idOrganizacion
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buscarCDAByPacienteAndOrganizacion", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPacienteAndOrganizacion")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByPacienteAndOrganizacionResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPacienteAndOrganizacionResponse")
    public cda.ConjuntoCda buscarCDAByPacienteAndOrganizacion(
        @WebParam(name = "paciente", targetNamespace = "")
        java.lang.String paciente,
        @WebParam(name = "numeroOrg", targetNamespace = "")
        java.lang.Long numeroOrg,
        @WebParam(name = "offset", targetNamespace = "")
        int offset,
        @WebParam(name = "idOrganizacion", targetNamespace = "")
        java.lang.String idOrganizacion
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "eliminarCDA", targetNamespace = "http://cda/", className = "cda.EliminarCDA")
    @WebMethod
    @ResponseWrapper(localName = "eliminarCDAResponse", targetNamespace = "http://cda/", className = "cda.EliminarCDAResponse")
    public boolean eliminarCDA(
        @WebParam(name = "idCda", targetNamespace = "")
        java.lang.Long idCda,
        @WebParam(name = "idPacienteOrg", targetNamespace = "")
        java.lang.String idPacienteOrg,
        @WebParam(name = "idOrganizacion", targetNamespace = "")
        java.lang.String idOrganizacion
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "registrarCDA", targetNamespace = "http://cda/", className = "cda.RegistrarCDA")
    @WebMethod
    @ResponseWrapper(localName = "registrarCDAResponse", targetNamespace = "http://cda/", className = "cda.RegistrarCDAResponse")
    public boolean registrarCDA(
        @WebParam(name = "cda", targetNamespace = "")
        cda.CdaArr cda,
        @WebParam(name = "idPacienteOrg", targetNamespace = "")
        java.lang.String idPacienteOrg,
        @WebParam(name = "idOrganizacion", targetNamespace = "")
        java.lang.String idOrganizacion
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buscarCDAById", targetNamespace = "http://cda/", className = "cda.BuscarCDAById")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByIdResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByIdResponse")
    public cda.CdaArr buscarCDAById(
        @WebParam(name = "id", targetNamespace = "")
        int id,
        @WebParam(name = "idOrganizacion", targetNamespace = "")
        java.lang.String idOrganizacion
    );
}
