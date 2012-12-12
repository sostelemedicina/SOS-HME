package triaje
import webService.PojoPrueba

class TriajeController {

    def customSecureServiceClientTriaje
    def index = {
        
        PojoPrueba arg = customSecureServiceClientTriaje.serviceHolaMundo() 
        
    render arg.nombre + " "+ arg.edad
    
    }
}
