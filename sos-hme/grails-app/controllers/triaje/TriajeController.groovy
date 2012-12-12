package triaje
import webService.PojoPrueba
import webService.PojoEspecialidad
import webService.PojoPaciente
import webService.PojoArchivo
import webService.PojoCaso

class TriajeController {

    def customSecureServiceClientTriaje
    def index = {
        
       PojoPrueba arg = customSecureServiceClientTriaje.serviceHolaMundo() 
        
        render arg.nombre + " "+ arg.edad
        
        
       PojoEspecialidad especialidad1 = new PojoEspecialidad()
            especialidad1.setNombre("Dermatologia")
            especialidad1.setDescripcion("Descripcion dermatologia")
            
       PojoPaciente paciente = new PojoPaciente()
            paciente.setNombre("Carmen")
            paciente.setApellido("Guzman")
            paciente.setCedula("19867443")
            paciente.setSexo("Femenino")
            paciente.setNacionalidad("Venezolana")
            paciente.setFechaNacimiento("1987-06-01")
           
//       PojoArchivo archivo = new PojoArchivo()
//            archivo.setNombre()
//            archivo.setDescripcion()
//            archivo.setAdjunto()
//        

        PojoCaso caso = new PojoCaso()

        
            
    }
}
