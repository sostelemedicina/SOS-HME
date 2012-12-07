
package imp;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para conjuntoPaciente complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="conjuntoPaciente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="listPacienteArr" type="{http://imp/}pacienteArr" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conjuntoPaciente", propOrder = {
    "total",
    "listPacienteArr"
})
public class ConjuntoPaciente {

    protected int total;
    @XmlElement(nillable = true)
    protected List<PacienteArr> listPacienteArr;

    /**
     * Obtiene el valor de la propiedad total.
     * 
     */
    public int getTotal() {
        return total;
    }

    /**
     * Define el valor de la propiedad total.
     * 
     */
    public void setTotal(int value) {
        this.total = value;
    }

    /**
     * Gets the value of the listPacienteArr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listPacienteArr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListPacienteArr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PacienteArr }
     * 
     * 
     */
    public List<PacienteArr> getListPacienteArr() {
        if (listPacienteArr == null) {
            listPacienteArr = new ArrayList<PacienteArr>();
        }
        return this.listPacienteArr;
    }

}
