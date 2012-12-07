
package imp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para eliminarRelacionPaciente complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="eliminarRelacionPaciente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "eliminarRelacionPaciente", propOrder = {
    "idPacienteOrg",
    "idOrganizacion"
})
public class EliminarRelacionPaciente {

    protected String idPacienteOrg;
    protected String idOrganizacion;

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
