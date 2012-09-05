/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Armando
 */
//import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
class ConexionImpJob {
	
   
   def customSecureServiceClientImp
   def timeout = 260000l // execute job once in x miliseconds
   def startDelay = 00000l // empieza al minuto

     def execute()
   {
   //PREGUNTAR SI EXISTE CONEXION AL IMP
   //println SCH.servletContext.conexionImp
   
   def agreImp
    try{
    // FIXME: DEBERIA EXISTIR UN SERVICIOS QUE ME INDIQUE CUANDO HAY O NO CONEXION
    agreImp = customSecureServiceClientImp.existePaciente("test","test")
    SCH.servletContext.conexionImp = true
       
        }catch(Exception e){
     SCH.servletContext.conexionImp = false
     //OCURRIO UN ERROR NO SE PUDO CONECTAR CON IMP
       
    }

    println "CONEXION IMP "+SCH.servletContext.conexionImp

    }

}

