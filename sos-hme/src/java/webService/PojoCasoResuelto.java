
package webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para pojoCasoResuelto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="pojoCasoResuelto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idCasoSOS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="responsable" type="{http://triaje/}pojoMedico" minOccurs="0"/>
 *         &lt;element name="opinion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaSolucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pojoCasoResuelto", namespace = "http://triaje/", propOrder = {
    "idCasoSOS",
    "responsable",
    "opinion",
    "fechaSolucion"
})
public class PojoCasoResuelto {

    protected String idCasoSOS;
    protected PojoMedico responsable;
    protected String opinion;
    protected String fechaSolucion;

    /**
     * Obtiene el valor de la propiedad idCasoSOS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCasoSOS() {
        return idCasoSOS;
    }

    /**
     * Define el valor de la propiedad idCasoSOS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCasoSOS(String value) {
        this.idCasoSOS = value;
    }

    /**
     * Obtiene el valor de la propiedad responsable.
     * 
     * @return
     *     possible object is
     *     {@link PojoMedico }
     *     
     */
    public PojoMedico getResponsable() {
        return responsable;
    }

    /**
     * Define el valor de la propiedad responsable.
     * 
     * @param value
     *     allowed object is
     *     {@link PojoMedico }
     *     
     */
    public void setResponsable(PojoMedico value) {
        this.responsable = value;
    }

    /**
     * Obtiene el valor de la propiedad opinion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * Define el valor de la propiedad opinion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpinion(String value) {
        this.opinion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaSolucion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaSolucion() {
        return fechaSolucion;
    }

    /**
     * Define el valor de la propiedad fechaSolucion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaSolucion(String value) {
        this.fechaSolucion = value;
    }

}
