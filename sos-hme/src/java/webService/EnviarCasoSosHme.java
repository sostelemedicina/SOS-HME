
package webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para enviarCasoSosHme complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="enviarCasoSosHme">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caso" type="{http://triaje/}pojoCasoResuelto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enviarCasoSosHme", namespace = "http://triaje/", propOrder = {
    "caso"
})
public class EnviarCasoSosHme {

    protected PojoCasoResuelto caso;

    /**
     * Obtiene el valor de la propiedad caso.
     * 
     * @return
     *     possible object is
     *     {@link PojoCasoResuelto }
     *     
     */
    public PojoCasoResuelto getCaso() {
        return caso;
    }

    /**
     * Define el valor de la propiedad caso.
     * 
     * @param value
     *     allowed object is
     *     {@link PojoCasoResuelto }
     *     
     */
    public void setCaso(PojoCasoResuelto value) {
        this.caso = value;
    }

}
