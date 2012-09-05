/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imp;

/**
 *
 * @author Armando
 */
public class Personal {
    protected String nombre; //un codigo que poseen todos los médicos
    protected String apellido;
    protected String rol;
    protected String cedula;
    protected String id_registro_medicina;

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the id_registro_medicina
     */
    public String getId_registro_medicina() {
        return id_registro_medicina;
    }

    /**
     * @param id_registro_medicina the id_registro_medicina to set
     */
    public void setId_registro_medicina(String id_registro_medicina) {
        this.id_registro_medicina = id_registro_medicina;
    }


}
