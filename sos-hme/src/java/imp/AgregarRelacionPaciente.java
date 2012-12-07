
package imp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para agregarRelacionPaciente complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="agregarRelacionPaciente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idCentroImp" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idPacienteImp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "agregarRelacionPaciente", propOrder = {
    "idCentroImp",
    "idPacienteImp",
    "idPacienteOrg",
    "idOrganizacion"
})
public class AgregarRelacionPaciente {

    protected Long idCentroImp;
    protected String idPacienteImp;
    protected String idPacienteOrg;
    protected String idOrganizacion;

    /**
     * Obtiene el valor de la propiedad idCentroImp.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdCentroImp() {
        return idCentroImp;
    }

    /**
     * Define el valor de la propiedad idCentroImp.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdCentroImp(Long value) {
        this.idCentroImp = value;
    }

    /**
     * Obtiene el valor de la propiedad idPacienteImp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPacienteImp() {
        return idPacienteImp;
    }

    /**
     * Define el valor de la propiedad idPacienteImp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPacienteImp(String value) {
        this.idPacienteImp = value;
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
