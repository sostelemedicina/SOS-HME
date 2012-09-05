package com.cxf.demo.logging

import org.apache.cxf.common.injection.NoJSR250Annotations
import org.apache.cxf.interceptor.AbstractLoggingInterceptor
import org.apache.cxf.interceptor.Fault
import org.apache.cxf.interceptor.LoggingInInterceptor
import org.apache.cxf.message.Message
import org.apache.cxf.phase.Phase
/*
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.UnsupportedCallbackException
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
import org.apache.ws.security.WSPasswordCallback
import org.apache.ws.security.handler.WSHandlerConstants
import com.grails.cxf.client.CxfClientInterceptor
import org.apache.cxf.ws.security.wss4j.AbstractWSS4JInterceptor
*/

@NoJSR250Annotations
public class CustomLoggingInInterceptor extends AbstractLoggingInterceptor {

    def name

    public CustomLoggingInInterceptor() {
        super(Phase.RECEIVE)
        println "Creando interceptor de entrada"

/*
        Map<String, Object> inProps = [:]
        inProps.put(WSHandlerConstants.ACTION, org.apache.ws.security.handler.WSHandlerConstants.USERNAME_TOKEN+" "+org.apache.ws.security.handler.WSHandlerConstants.TIMESTAMP +" "+org.apache.ws.security.handler.WSHandlerConstants.SIGNATURE+ " "+org.apache.ws.security.handler.WSHandlerConstants.ENCRYPT )
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, org.apache.ws.security.WSConstants.PW_DIGEST);
        inProps.put(WSHandlerConstants.PW_CALLBACK_REF, new CallbackHandler() {

              //SE CREA UNA INSTANCIA DE LA CLASE CallbackHandler
              //
              //Se sobreescribe el método handle()

            void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

                    WSPasswordCallback pc = (WSPasswordCallback) callbacks[0]

                    pc.setPassword("keystore")


            }
        })




        inProps.put(WSHandlerConstants.SIG_PROP_FILE, "server.properties")
        inProps.put(WSHandlerConstants.DEC_PROP_FILE, "server_key.properties")


        this.setProperties(inProps)
  */
    }

    public void handleMessage(Message message) throws Fault {
        println "$name :: I AM IN CUSTOM IN LOGGER!!!!!!!"
    }
}