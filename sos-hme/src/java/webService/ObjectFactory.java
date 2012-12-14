
package webService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webService package. 
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

    private final static QName _GetStatusCasoResponse_QNAME = new QName("http://webService/", "getStatusCasoResponse");
    private final static QName _ServicioPruebaSosHMEResponse_QNAME = new QName("http://triaje/", "servicioPruebaSosHMEResponse");
    private final static QName _ServicioPruebaSosHME_QNAME = new QName("http://triaje/", "servicioPruebaSosHME");
    private final static QName _GetStatusCaso_QNAME = new QName("http://webService/", "getStatusCaso");
    private final static QName _EnviarCasoTriaje_QNAME = new QName("http://webService/", "enviarCasoTriaje");
    private final static QName _EnviarCasoTriajeResponse_QNAME = new QName("http://webService/", "enviarCasoTriajeResponse");
    private final static QName _GetCasoResuelto_QNAME = new QName("http://webService/", "getCasoResuelto");
    private final static QName _EnviarCasoSosHme_QNAME = new QName("http://triaje/", "enviarCasoSosHme");
    private final static QName _EnviarCasoSosHmeResponse_QNAME = new QName("http://triaje/", "enviarCasoSosHmeResponse");
    private final static QName _GetCasoResueltoResponse_QNAME = new QName("http://webService/", "getCasoResueltoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStatusCasoResponse }
     * 
     */
    public GetStatusCasoResponse createGetStatusCasoResponse() {
        return new GetStatusCasoResponse();
    }

    /**
     * Create an instance of {@link GetCasoResuelto }
     * 
     */
    public GetCasoResuelto createGetCasoResuelto() {
        return new GetCasoResuelto();
    }

    /**
     * Create an instance of {@link EnviarCasoTriajeResponse }
     * 
     */
    public EnviarCasoTriajeResponse createEnviarCasoTriajeResponse() {
        return new EnviarCasoTriajeResponse();
    }

    /**
     * Create an instance of {@link EnviarCasoTriaje }
     * 
     */
    public EnviarCasoTriaje createEnviarCasoTriaje() {
        return new EnviarCasoTriaje();
    }

    /**
     * Create an instance of {@link GetStatusCaso }
     * 
     */
    public GetStatusCaso createGetStatusCaso() {
        return new GetStatusCaso();
    }

    /**
     * Create an instance of {@link GetCasoResueltoResponse }
     * 
     */
    public GetCasoResueltoResponse createGetCasoResueltoResponse() {
        return new GetCasoResueltoResponse();
    }

    /**
     * Create an instance of {@link PojoArchivo }
     * 
     */
    public PojoArchivo createPojoArchivo() {
        return new PojoArchivo();
    }

    /**
     * Create an instance of {@link PojoEspecialidad }
     * 
     */
    public PojoEspecialidad createPojoEspecialidad() {
        return new PojoEspecialidad();
    }

    /**
     * Create an instance of {@link PojoPaciente }
     * 
     */
    public PojoPaciente createPojoPaciente() {
        return new PojoPaciente();
    }

    /**
     * Create an instance of {@link PojoCaso }
     * 
     */
    public PojoCaso createPojoCaso() {
        return new PojoCaso();
    }

    /**
     * Create an instance of {@link ServicioPruebaSosHMEResponse }
     * 
     */
    public ServicioPruebaSosHMEResponse createServicioPruebaSosHMEResponse() {
        return new ServicioPruebaSosHMEResponse();
    }

    /**
     * Create an instance of {@link ServicioPruebaSosHME }
     * 
     */
    public ServicioPruebaSosHME createServicioPruebaSosHME() {
        return new ServicioPruebaSosHME();
    }

    /**
     * Create an instance of {@link EnviarCasoSosHmeResponse }
     * 
     */
    public EnviarCasoSosHmeResponse createEnviarCasoSosHmeResponse() {
        return new EnviarCasoSosHmeResponse();
    }

    /**
     * Create an instance of {@link EnviarCasoSosHme }
     * 
     */
    public EnviarCasoSosHme createEnviarCasoSosHme() {
        return new EnviarCasoSosHme();
    }

    /**
     * Create an instance of {@link PojoCasoResuelto }
     * 
     */
    public PojoCasoResuelto createPojoCasoResuelto() {
        return new PojoCasoResuelto();
    }

    /**
     * Create an instance of {@link PojoMedico }
     * 
     */
    public PojoMedico createPojoMedico() {
        return new PojoMedico();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStatusCasoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "getStatusCasoResponse")
    public JAXBElement<GetStatusCasoResponse> createGetStatusCasoResponse(GetStatusCasoResponse value) {
        return new JAXBElement<GetStatusCasoResponse>(_GetStatusCasoResponse_QNAME, GetStatusCasoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioPruebaSosHMEResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://triaje/", name = "servicioPruebaSosHMEResponse")
    public JAXBElement<ServicioPruebaSosHMEResponse> createServicioPruebaSosHMEResponse(ServicioPruebaSosHMEResponse value) {
        return new JAXBElement<ServicioPruebaSosHMEResponse>(_ServicioPruebaSosHMEResponse_QNAME, ServicioPruebaSosHMEResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioPruebaSosHME }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://triaje/", name = "servicioPruebaSosHME")
    public JAXBElement<ServicioPruebaSosHME> createServicioPruebaSosHME(ServicioPruebaSosHME value) {
        return new JAXBElement<ServicioPruebaSosHME>(_ServicioPruebaSosHME_QNAME, ServicioPruebaSosHME.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStatusCaso }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "getStatusCaso")
    public JAXBElement<GetStatusCaso> createGetStatusCaso(GetStatusCaso value) {
        return new JAXBElement<GetStatusCaso>(_GetStatusCaso_QNAME, GetStatusCaso.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarCasoTriaje }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "enviarCasoTriaje")
    public JAXBElement<EnviarCasoTriaje> createEnviarCasoTriaje(EnviarCasoTriaje value) {
        return new JAXBElement<EnviarCasoTriaje>(_EnviarCasoTriaje_QNAME, EnviarCasoTriaje.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarCasoTriajeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "enviarCasoTriajeResponse")
    public JAXBElement<EnviarCasoTriajeResponse> createEnviarCasoTriajeResponse(EnviarCasoTriajeResponse value) {
        return new JAXBElement<EnviarCasoTriajeResponse>(_EnviarCasoTriajeResponse_QNAME, EnviarCasoTriajeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCasoResuelto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "getCasoResuelto")
    public JAXBElement<GetCasoResuelto> createGetCasoResuelto(GetCasoResuelto value) {
        return new JAXBElement<GetCasoResuelto>(_GetCasoResuelto_QNAME, GetCasoResuelto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarCasoSosHme }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://triaje/", name = "enviarCasoSosHme")
    public JAXBElement<EnviarCasoSosHme> createEnviarCasoSosHme(EnviarCasoSosHme value) {
        return new JAXBElement<EnviarCasoSosHme>(_EnviarCasoSosHme_QNAME, EnviarCasoSosHme.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarCasoSosHmeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://triaje/", name = "enviarCasoSosHmeResponse")
    public JAXBElement<EnviarCasoSosHmeResponse> createEnviarCasoSosHmeResponse(EnviarCasoSosHmeResponse value) {
        return new JAXBElement<EnviarCasoSosHmeResponse>(_EnviarCasoSosHmeResponse_QNAME, EnviarCasoSosHmeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCasoResueltoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "getCasoResueltoResponse")
    public JAXBElement<GetCasoResueltoResponse> createGetCasoResueltoResponse(GetCasoResueltoResponse value) {
        return new JAXBElement<GetCasoResueltoResponse>(_GetCasoResueltoResponse_QNAME, GetCasoResueltoResponse.class, null, value);
    }

}
