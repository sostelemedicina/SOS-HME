
package webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para getCasoResuelto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="getCasoResuelto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caso" type="{http://webService/}pojoCaso" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCasoResuelto", propOrder = {
    "caso"
})
public class GetCasoResuelto {

    protected PojoCaso caso;

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

}
