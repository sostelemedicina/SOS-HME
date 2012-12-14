package triaje
import webService.PojoPrueba

import webService.PojoEspecialidad
import webService.PojoPaciente
import webService.PojoArchivo
import webService.PojoCaso


class TriajeController {

    def customSecureServiceClientTriaje
    def index = {
        

//       PojoPrueba arg = customSecureServiceClientTriaje.serviceHolaMundo() 
//        
//        render arg.nombre + " "+ arg.edad

        
       PojoEspecialidad especialidad1 = new PojoEspecialidad()
            especialidad1.setNombre("Dermatologia")
                        
       List<PojoEspecialidad> especialidades = new ArrayList<PojoEspecialidad>();
       especialidades.add(especialidad1)        
            
       PojoPaciente paciente = new PojoPaciente()
            paciente.setNombre("Carmen")
            paciente.setApellido("Guzman")
            paciente.setCedula("19867443")
            paciente.setSexo("Femenino")
            paciente.setNacionalidad("Venezolana")
            paciente.setFechaNacimiento("1987-06-01")

        //Se abre el archivo
        File txt = new File("C:/hola.txt")             
//        render txt.getBytes().toString()

       PojoArchivo archivo = new PojoArchivo()
            archivo.setNombre("hola.txt")
            archivo.setDescripcion("prueba de archivo")
            archivo.setAdjunto(txt.getBytes())        
            
       List<PojoArchivo> archivos = new ArrayList<PojoArchivo>();
       archivos.add(archivo)            
        
//        byte[] bytes = archivo.getBytes() 
        
        PojoCaso caso = new PojoCaso()
            caso.setIdCasoSOS("10a")
            caso.archivos = archivos
            caso.especialidad = especialidades
            caso.setPaciente(paciente)
            caso.setDescripcion("Desc. Caso de prueba enviado desde SOS-HME")
//            caso.setFechaInicio() //se coloca cuando llega a triaje
//            caso.setFechaSolucion() //se coloca cuando sale de triaje

        String uuid = "c99338f6-1221-4bc1-a4ea-243eff0b4167"
        
        boolean answer = customSecureServiceClientTriaje.enviarCasoTriaje(caso, uuid)
        
        if (answer==true){
            render "ACCESO PERMITIDO"
        }else{
            render "ACCESO DENEGADO"
        }

        
            
    }
}
