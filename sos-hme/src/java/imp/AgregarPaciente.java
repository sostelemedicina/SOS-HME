
package imp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para agregarPaciente complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="agregarPaciente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paciente" type="{http://imp/}pacienteArr" minOccurs="0"/>
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
@XmlType(name = "agregarPaciente", propOrder = {
    "paciente",
    "idOrganizacion"
})
public class AgregarPaciente {

    protected PacienteArr paciente;
    protected String idOrganizacion;

    /**
     * Obtiene el valor de la propiedad paciente.
     * 
     * @return
     *     possible object is
     *     {@link PacienteArr }
     *     
     */
    public PacienteArr getPaciente() {
        return paciente;
    }

    /**
     * Define el valor de la propiedad paciente.
     * 
     * @param value
     *     allowed object is
     *     {@link PacienteArr }
     *     
     */
    public void setPaciente(PacienteArr value) {
        this.paciente = value;
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
