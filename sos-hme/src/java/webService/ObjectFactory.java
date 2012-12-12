
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

    private final static QName _ServiceHolaMundo_QNAME = new QName("http://webService/", "serviceHolaMundo");
    private final static QName _EnviarCasoTriaje_QNAME = new QName("http://webService/", "enviarCasoTriaje");
    private final static QName _EnviarCasoTriajeResponse_QNAME = new QName("http://webService/", "enviarCasoTriajeResponse");
    private final static QName _ServiceHolaMundoResponse_QNAME = new QName("http://webService/", "serviceHolaMundoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServiceHolaMundo }
     * 
     */
    public ServiceHolaMundo createServiceHolaMundo() {
        return new ServiceHolaMundo();
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
     * Create an instance of {@link ServiceHolaMundoResponse }
     * 
     */
    public ServiceHolaMundoResponse createServiceHolaMundoResponse() {
        return new ServiceHolaMundoResponse();
    }

    /**
     * Create an instance of {@link PojoPrueba }
     * 
     */
    public PojoPrueba createPojoPrueba() {
        return new PojoPrueba();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceHolaMundo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "serviceHolaMundo")
    public JAXBElement<ServiceHolaMundo> createServiceHolaMundo(ServiceHolaMundo value) {
        return new JAXBElement<ServiceHolaMundo>(_ServiceHolaMundo_QNAME, ServiceHolaMundo.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceHolaMundoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "serviceHolaMundoResponse")
    public JAXBElement<ServiceHolaMundoResponse> createServiceHolaMundoResponse(ServiceHolaMundoResponse value) {
        return new JAXBElement<ServiceHolaMundoResponse>(_ServiceHolaMundoResponse_QNAME, ServiceHolaMundoResponse.class, null, value);
    }

}
