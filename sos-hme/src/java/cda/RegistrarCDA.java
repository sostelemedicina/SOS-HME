
package cda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registrarCDA complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registrarCDA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cda" type="{http://cda/}cdaArr" minOccurs="0"/>
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
@XmlType(name = "registrarCDA", propOrder = {
    "cda",
    "idPacienteOrg",
    "idOrganizacion"
})
public class RegistrarCDA {

    protected CdaArr cda;
    protected String idPacienteOrg;
    protected String idOrganizacion;

    /**
     * Obtiene el valor de la propiedad cda.
     * 
     * @return
     *     possible object is
     *     {@link CdaArr }
     *     
     */
    public CdaArr getCda() {
        return cda;
    }

    /**
     * Define el valor de la propiedad cda.
     * 
     * @param value
     *     allowed object is
     *     {@link CdaArr }
     *     
     */
    public void setCda(CdaArr value) {
        this.cda = value;
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
