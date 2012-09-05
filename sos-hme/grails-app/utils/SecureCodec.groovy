/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import sun.misc.BASE64Encoder
import sun.misc.BASE64Decoder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.commons.codec.binary.Hex
/**
 *
 * @author angel
 */
class SecureCodec {
    String password = "clave_de_cifrado_que_no_deberiamos_guardar_aqui"

    static encode = { str ->
        if(['null', 'Null', 'NULL', '', null].contains(str)) str = ''
        try {
            Cipher cipher = setupCipher(Cipher.ENCRYPT_MODE, password)

            // encriptamos
            byte[] encodedBytes = cipher.doFinal(str.getBytes())

            // pasamos a hexadecimal
            String hex = new String(new Hex().encode(encodedBytes))?.toUpperCase()
            return hex;
        } catch(Exception e) {
            return str
        }
    }

    static decode = { hex ->
        try {
            // pasamos de hexadecimal a bytes
            byte[] bytes = new Hex().decodeHex((char[])hex)

            // desencriptamos
            Cipher cipher = setupCipher(Cipher.DECRYPT_MODE, password)
            def decripted = new String(cipher.doFinal(bytes))

            return decripted
        } catch(Exception e) {
            return hex
        }
    }

    private static setupCipher(mode, password) {
        Cipher cipher = Cipher.getInstance("AES");

        // recortamos el pass a 16 caracteres - 128 bits
        byte[] keyBytes = new byte[16];
        byte[] b = password.getBytes();
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);

        // creating SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        cipher.init(mode, keySpec);
        return cipher
    }

}

