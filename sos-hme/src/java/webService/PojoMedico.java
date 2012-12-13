
package webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para pojoMedico complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="pojoMedico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="colegioDeMedico" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ministerioDeSalud" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pojoMedico", namespace = "http://triaje/", propOrder = {
    "nombre",
    "apellido",
    "colegioDeMedico",
    "ministerioDeSalud"
})
public class PojoMedico {

    protected String nombre;
    protected String apellido;
    protected int colegioDeMedico;
    protected int ministerioDeSalud;

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Define el valor de la propiedad apellido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido(String value) {
        this.apellido = value;
    }

    /**
     * Obtiene el valor de la propiedad colegioDeMedico.
     * 
     */
    public int getColegioDeMedico() {
        return colegioDeMedico;
    }

    /**
     * Define el valor de la propiedad colegioDeMedico.
     * 
     */
    public void setColegioDeMedico(int value) {
        this.colegioDeMedico = value;
    }

    /**
     * Obtiene el valor de la propiedad ministerioDeSalud.
     * 
     */
    public int getMinisterioDeSalud() {
        return ministerioDeSalud;
    }

    /**
     * Define el valor de la propiedad ministerioDeSalud.
     * 
     */
    public void setMinisterioDeSalud(int value) {
        this.ministerioDeSalud = value;
    }

}
