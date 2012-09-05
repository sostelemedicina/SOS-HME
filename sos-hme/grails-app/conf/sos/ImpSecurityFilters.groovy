package sos
import util.HCESession

class ImpSecurityFilters {

    def filters = {
        all(controller:'service', action:'agregarPaciente|eliminarPaciente|agregarRelacionPaciente|eliminarRelacionPaciente') {
            before = {	
			
				if(session.traumaContext.authTemp.equals("stop")){
					//flash.message = "acceso invalido!!"
					println "acceso invalido a servicios"
					redirect(controller:'records', action:'list')
				}else{
					println "acceso valido a servicios"
				}
                
            }
            after = {


                
            }
            afterView = {
                
            }
        }
    }
    
}
