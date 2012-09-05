/*
 * Esta clase especifica el formato que deben de tener las
 * entradas de log.
 */
 
package util
import auth.AuthorizationService
import util.HCESession
import demographic.role.*
import authorization.LoginAuth
import java.util.*
import hce.core.common.directory.Folder

 /**
 *
 * @author Angel
 *
 */
 
 class FormatLog {

		

		private String createFormat(String message, String format, long userId){
			if(userId==	-1)
				return message +" "
			else
				message = message + "{"
				
				
				def mssg = message + " userId: " +userId
				
				mssg = mssg + ", " + LoginAuth.get(userId).user
					
				if(format.equals("short")){
					//println format+"==short"
					return mssg + " }"
				}
				mssg = mssg + ", " + LoginAuth.get(userId).person
				
				if(format.equals("mide")){
					//println format+"==mide"
					return mssg + " }"
				}
				mssg = mssg + ", " + LoginAuth.get(userId).person.roles.type
					
				if(format.equals("long")){
					//println format+"==long"				
					return mssg + " }"
				}



		}
		

 }