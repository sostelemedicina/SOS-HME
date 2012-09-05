/**
 * 
 */
package util

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 */
class HCESession {

    // No lo uso, saco al paciente del episodio... serviria para acelerar 
    // el sacado del paciente del episodio y se setearia al seleccionar un
    // paciente o un episodio que tiene un paciente seleccionado.
    
    // Path al dominio actual
    String domainPath
    
	String authTemp
	
    // Id del paciente seleccionado
    Long patientId
    
    //Long pacienteId // Identificador del paciente (uno de sus Ids), no es el id en la base.
    Long episodioId // Identificador en la base de la composition que modela el registro del episodio.
    Long userId     // Identificador en la base del usuario logueado en este momento.
}
