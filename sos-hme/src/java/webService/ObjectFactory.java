
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceHolaMundo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService/", name = "serviceHolaMundo")
    public JAXBElement<ServiceHolaMundo> createServiceHolaMundo(ServiceHolaMundo value) {
        return new JAXBElement<ServiceHolaMundo>(_ServiceHolaMundo_QNAME, ServiceHolaMundo.class, null, value);
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
