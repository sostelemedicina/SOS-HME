
package webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para enviarCasoTriaje complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="enviarCasoTriaje">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caso" type="{http://webService/}pojoCaso" minOccurs="0"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enviarCasoTriaje", propOrder = {
    "caso",
    "uuid"
})
public class EnviarCasoTriaje {

    protected PojoCaso caso;
    protected String uuid;

    /**
     * Obtiene el valor de la propiedad caso.
     * 
     * @return
     *     possible object is
     *     {@link PojoCaso }
     *     
     */
    public PojoCaso getCaso() {
        return caso;
    }

    /**
     * Define el valor de la propiedad caso.
     * 
     * @param value
     *     allowed object is
     *     {@link PojoCaso }
     *     
     */
    public void setCaso(PojoCaso value) {
        this.caso = value;
    }

    /**
     * Obtiene el valor de la propiedad uuid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Define el valor de la propiedad uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

}
