// Place your Spring DSL code here
import com.cxf.demo.logging.CustomLoggingInInterceptor
import com.cxf.demo.logging.VerboseCustomLoggingInInterceptor
import com.cxf.demo.security.CustomSecurityInterceptor
import com.cxf.demo.logging.CustomLoggingOutInterceptor
//import org.apache.cxf.transports.http.configuration.HTTPClientPolicy

beans = {
    myCustomInterceptor(CustomSecurityInterceptor){
    user = "myAlias"
    pass = "keystore"

    }
customLoggingInInterceptor(CustomLoggingInInterceptor) {
    name = "customLoggingInInterceptor"
    
}

verboseLoggingInInterceptor(VerboseCustomLoggingInInterceptor) {
    name = "verboseLoggingInInterceptor"
}

 customLoggingOutInterceptor(CustomLoggingOutInterceptor) {
    name = "customLoggingOutInterceptor"
}
  

}