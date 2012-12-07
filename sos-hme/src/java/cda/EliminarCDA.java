
package cda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para eliminarCDA complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="eliminarCDA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idCda" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idPacienteOrg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idOrganizacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eliminarCDA", propOrder = {
    "idCda",
    "idPacienteOrg",
    "idOrganizacion"
})
public class EliminarCDA {

    protected Long idCda;
    protected String idPacienteOrg;
    protected String idOrganizacion;

    /**
     * Obtiene el valor de la propiedad idCda.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdCda() {
        return idCda;
    }

    /**
     * Define el valor de la propiedad idCda.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdCda(Long value) {
        this.idCda = value;
    }

    /**
     * Obtiene el valor de la propiedad idPacienteOrg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPacienteOrg() {
        return idPacienteOrg;
    }

    /**
     * Define el valor de la propiedad idPacienteOrg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPacienteOrg(String value) {
        this.idPacienteOrg = value;
    }

    /**
     * Obtiene el valor de la propiedad idOrganizacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOrganizacion() {
        return idOrganizacion;
    }

    /**
     * Define el valor de la propiedad idOrganizacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOrganizacion(String value) {
        this.idOrganizacion = value;
    }

}
