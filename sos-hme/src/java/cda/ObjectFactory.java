
package cda;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cda package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BuscarCDAById_QNAME = new QName("http://cda/", "buscarCDAById");
    private final static QName _EliminarCDAResponse_QNAME = new QName("http://cda/", "eliminarCDAResponse");
    private final static QName _BuscarCDAByRango_QNAME = new QName("http://cda/", "buscarCDAByRango");
    private final static QName _BuscarCDAByPaciente_QNAME = new QName("http://cda/", "buscarCDAByPaciente");
    private final static QName _RegistrarCDAResponse_QNAME = new QName("http://cda/", "registrarCDAResponse");
    private final static QName _BuscarCDAByPacienteAndRangoResponse_QNAME = new QName("http://cda/", "buscarCDAByPacienteAndRangoResponse");
    private final static QName _ListarOrganizacionesByPacienteResponse_QNAME = new QName("http://cda/", "listarOrganizacionesByPacienteResponse");
    private final static QName _BuscarCDAByIdResponse_QNAME = new QName("http://cda/", "buscarCDAByIdResponse");
    private final static QName _BuscarCDAByRangoResponse_QNAME = new QName("http://cda/", "buscarCDAByRangoResponse");
    private final static QName _BuscarCDAByPacienteAndRango_QNAME = new QName("http://cda/", "buscarCDAByPacienteAndRango");
    private final static QName _BuscarCDAByPacienteAndOrganizacion_QNAME = new QName("http://cda/", "buscarCDAByPacienteAndOrganizacion");
    private final static QName _ListarOrganizacionesByPaciente_QNAME = new QName("http://cda/", "listarOrganizacionesByPaciente");
    private final static QName _RegistrarCDA_QNAME = new QName("http://cda/", "registrarCDA");
    private final static QName _BuscarCDAByPacienteResponse_QNAME = new QName("http://cda/", "buscarCDAByPacienteResponse");
    private final static QName _BuscarCDAByPacienteAndOrganizacionResponse_QNAME = new QName("http://cda/", "buscarCDAByPacienteAndOrganizacionResponse");
    private final static QName _EliminarCDA_QNAME = new QName("http://cda/", "eliminarCDA");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cda
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BuscarCDAByPacienteResponse }
     * 
     */
    public BuscarCDAByPacienteResponse createBuscarCDAByPacienteResponse() {
        return new BuscarCDAByPacienteResponse();
    }

    /**
     * Create an instance of {@link BuscarCDAByPacienteAndOrganizacionResponse }
     * 
     */
    public BuscarCDAByPacienteAndOrganizacionResponse createBuscarCDAByPacienteAndOrganizacionResponse() {
        return new BuscarCDAByPacienteAndOrganizacionResponse();
    }

    /**
     * Create an instance of {@link EliminarCDA }
     * 
     */
    public EliminarCDA createEliminarCDA() {
        return new EliminarCDA();
    }

    /**
     * Create an instance of {@link BuscarCDAByPacienteAndRango }
     * 
     */
    public BuscarCDAByPacienteAndRango createBuscarCDAByPacienteAndRango() {
        return new BuscarCDAByPacienteAndRango();
    }

    /**
     * Create an instance of {@link BuscarCDAByPacienteAndOrganizacion }
     * 
     */
    public BuscarCDAByPacienteAndOrganizacion createBuscarCDAByPacienteAndOrganizacion() {
        return new BuscarCDAByPacienteAndOrganizacion();
    }

    /**
     * Create an instance of {@link ListarOrganizacionesByPaciente }
     * 
     */
    public ListarOrganizacionesByPaciente createListarOrganizacionesByPaciente() {
        return new ListarOrganizacionesByPaciente();
    }

    /**
     * Create an instance of {@link RegistrarCDA }
     * 
     */
    public RegistrarCDA createRegistrarCDA() {
        return new RegistrarCDA();
    }

    /**
     * Create an instance of {@link BuscarCDAByRangoResponse }
     * 
     */
    public BuscarCDAByRangoResponse createBuscarCDAByRangoResponse() {
        return new BuscarCDAByRangoResponse();
    }

    /**
     * Create an instance of {@link BuscarCDAById }
     * 
     */
    public BuscarCDAById createBuscarCDAById() {
        return new BuscarCDAById();
    }

    /**
     * Create an instance of {@link EliminarCDAResponse }
     * 
     */
    public EliminarCDAResponse createEliminarCDAResponse() {
        return new EliminarCDAResponse();
    }

    /**
     * Create an instance of {@link BuscarCDAByRango }
     * 
     */
    public BuscarCDAByRango createBuscarCDAByRango() {
        return new BuscarCDAByRango();
    }

    /**
     * Create an instance of {@link RegistrarCDAResponse }
     * 
     */
    public RegistrarCDAResponse createRegistrarCDAResponse() {
        return new RegistrarCDAResponse();
    }

    /**
     * Create an instance of {@link BuscarCDAByPaciente }
     * 
     */
    public BuscarCDAByPaciente createBuscarCDAByPaciente() {
        return new BuscarCDAByPaciente();
    }

    /**
     * Create an instance of {@link BuscarCDAByIdResponse }
     * 
     */
    public BuscarCDAByIdResponse createBuscarCDAByIdResponse() {
        return new BuscarCDAByIdResponse();
    }

    /**
     * Create an instance of {@link ListarOrganizacionesByPacienteResponse }
     * 
     */
    public ListarOrganizacionesByPacienteResponse createListarOrganizacionesByPacienteResponse() {
        return new ListarOrganizacionesByPacienteResponse();
    }

    /**
     * Create an instance of {@link BuscarCDAByPacienteAndRangoResponse }
     * 
     */
    public BuscarCDAByPacienteAndRangoResponse createBuscarCDAByPacienteAndRangoResponse() {
        return new BuscarCDAByPacienteAndRangoResponse();
    }

    /**
     * Create an instance of {@link CdaArr }
     * 
     */
    public CdaArr createCdaArr() {
        return new CdaArr();
    }

    /**
     * Create an instance of {@link ConjuntoCda }
     * 
     */
    public ConjuntoCda createConjuntoCda() {
        return new ConjuntoCda();
    }

    /**
     * Create an instance of {@link OrgArr }
     * 
     */
    public OrgArr createOrgArr() {
        return new OrgArr();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAById")
    public JAXBElement<BuscarCDAById> createBuscarCDAById(BuscarCDAById value) {
        return new JAXBElement<BuscarCDAById>(_BuscarCDAById_QNAME, BuscarCDAById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EliminarCDAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "eliminarCDAResponse")
    public JAXBElement<EliminarCDAResponse> createEliminarCDAResponse(EliminarCDAResponse value) {
        return new JAXBElement<EliminarCDAResponse>(_EliminarCDAResponse_QNAME, EliminarCDAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByRango }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByRango")
    public JAXBElement<BuscarCDAByRango> createBuscarCDAByRango(BuscarCDAByRango value) {
        return new JAXBElement<BuscarCDAByRango>(_BuscarCDAByRango_QNAME, BuscarCDAByRango.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPaciente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPaciente")
    public JAXBElement<BuscarCDAByPaciente> createBuscarCDAByPaciente(BuscarCDAByPaciente value) {
        return new JAXBElement<BuscarCDAByPaciente>(_BuscarCDAByPaciente_QNAME, BuscarCDAByPaciente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarCDAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "registrarCDAResponse")
    public JAXBElement<RegistrarCDAResponse> createRegistrarCDAResponse(RegistrarCDAResponse value) {
        return new JAXBElement<RegistrarCDAResponse>(_RegistrarCDAResponse_QNAME, RegistrarCDAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPacienteAndRangoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPacienteAndRangoResponse")
    public JAXBElement<BuscarCDAByPacienteAndRangoResponse> createBuscarCDAByPacienteAndRangoResponse(BuscarCDAByPacienteAndRangoResponse value) {
        return new JAXBElement<BuscarCDAByPacienteAndRangoResponse>(_BuscarCDAByPacienteAndRangoResponse_QNAME, BuscarCDAByPacienteAndRangoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarOrganizacionesByPacienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "listarOrganizacionesByPacienteResponse")
    public JAXBElement<ListarOrganizacionesByPacienteResponse> createListarOrganizacionesByPacienteResponse(ListarOrganizacionesByPacienteResponse value) {
        return new JAXBElement<ListarOrganizacionesByPacienteResponse>(_ListarOrganizacionesByPacienteResponse_QNAME, ListarOrganizacionesByPacienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByIdResponse")
    public JAXBElement<BuscarCDAByIdResponse> createBuscarCDAByIdResponse(BuscarCDAByIdResponse value) {
        return new JAXBElement<BuscarCDAByIdResponse>(_BuscarCDAByIdResponse_QNAME, BuscarCDAByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByRangoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByRangoResponse")
    public JAXBElement<BuscarCDAByRangoResponse> createBuscarCDAByRangoResponse(BuscarCDAByRangoResponse value) {
        return new JAXBElement<BuscarCDAByRangoResponse>(_BuscarCDAByRangoResponse_QNAME, BuscarCDAByRangoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPacienteAndRango }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPacienteAndRango")
    public JAXBElement<BuscarCDAByPacienteAndRango> createBuscarCDAByPacienteAndRango(BuscarCDAByPacienteAndRango value) {
        return new JAXBElement<BuscarCDAByPacienteAndRango>(_BuscarCDAByPacienteAndRango_QNAME, BuscarCDAByPacienteAndRango.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPacienteAndOrganizacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPacienteAndOrganizacion")
    public JAXBElement<BuscarCDAByPacienteAndOrganizacion> createBuscarCDAByPacienteAndOrganizacion(BuscarCDAByPacienteAndOrganizacion value) {
        return new JAXBElement<BuscarCDAByPacienteAndOrganizacion>(_BuscarCDAByPacienteAndOrganizacion_QNAME, BuscarCDAByPacienteAndOrganizacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarOrganizacionesByPaciente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "listarOrganizacionesByPaciente")
    public JAXBElement<ListarOrganizacionesByPaciente> createListarOrganizacionesByPaciente(ListarOrganizacionesByPaciente value) {
        return new JAXBElement<ListarOrganizacionesByPaciente>(_ListarOrganizacionesByPaciente_QNAME, ListarOrganizacionesByPaciente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarCDA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "registrarCDA")
    public JAXBElement<RegistrarCDA> createRegistrarCDA(RegistrarCDA value) {
        return new JAXBElement<RegistrarCDA>(_RegistrarCDA_QNAME, RegistrarCDA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPacienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPacienteResponse")
    public JAXBElement<BuscarCDAByPacienteResponse> createBuscarCDAByPacienteResponse(BuscarCDAByPacienteResponse value) {
        return new JAXBElement<BuscarCDAByPacienteResponse>(_BuscarCDAByPacienteResponse_QNAME, BuscarCDAByPacienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPacienteAndOrganizacionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPacienteAndOrganizacionResponse")
    public JAXBElement<BuscarCDAByPacienteAndOrganizacionResponse> createBuscarCDAByPacienteAndOrganizacionResponse(BuscarCDAByPacienteAndOrganizacionResponse value) {
        return new JAXBElement<BuscarCDAByPacienteAndOrganizacionResponse>(_BuscarCDAByPacienteAndOrganizacionResponse_QNAME, BuscarCDAByPacienteAndOrganizacionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EliminarCDA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "eliminarCDA")
    public JAXBElement<EliminarCDA> createEliminarCDA(EliminarCDA value) {
        return new JAXBElement<EliminarCDA>(_EliminarCDA_QNAME, EliminarCDA.class, null, value);
    }

}
