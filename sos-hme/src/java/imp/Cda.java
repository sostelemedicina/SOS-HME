/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imp;

/**
 *
 * @author Armando
 */
import imp.Organizacion;
import imp.Paciente;
import imp.Personal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Cda {

   protected String id;
   protected String documento;
   protected String titulo;
   protected String fechaCreacion;

   protected Organizacion organizacionAutora;
   protected Organizacion organizacionCustodia;
   protected Organizacion organizacionAutentificadora;

   protected Personal personalAutor;
   protected Personal personalAutentificador;

   protected Paciente paciente;

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return the fechaCreacion
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @return the organizacionAutora
     */
    public Organizacion getOrganizacionAutora() {
        return organizacionAutora;
    }

    /**
     * @return the organizacionCustodia
     */
    public Organizacion getOrganizacionCustodia() {
        return organizacionCustodia;
    }

    /**
     * @return the personalAutor
     */
    public Personal getPersonalAutor() {
        return personalAutor;
    }

    /**
     * @return the personalAutentificador
     */
    public Personal getPersonalAutentificador() {
        return personalAutentificador;
    }

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @param organizacionAutora the organizacionAutora to set
     */
    public void setOrganizacionAutora(Organizacion organizacionAutora) {
        this.organizacionAutora = organizacionAutora;
    }

    /**
     * @param organizacionCustodia the organizacionCustodia to set
     */
    public void setOrganizacionCustodia(Organizacion organizacionCustodia) {
        this.organizacionCustodia = organizacionCustodia;
    }

    /**
     * @param personalAutor the personalAutor to set
     */
    public void setPersonalAutor(Personal personalAutor) {
        this.personalAutor = personalAutor;
    }

    /**
     * @param personalAutentificador the personalAutentificador to set
     */
    public void setPersonalAutentificador(Personal personalAutentificador) {
        this.personalAutentificador = personalAutentificador;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the organizacionAutentificadora
     */
    public Organizacion getOrganizacionAutentificadora() {
        return organizacionAutentificadora;
    }

    /**
     * @param organizacionAutentificadora the organizacionAutentificadora to set
     */
    public void setOrganizacionAutentificadora(Organizacion organizacionAutentificadora) {
        this.organizacionAutentificadora = organizacionAutentificadora;
    }

   
}
