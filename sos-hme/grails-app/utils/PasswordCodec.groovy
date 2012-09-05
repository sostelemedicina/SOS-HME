/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.security.MessageDigest
import sun.misc.BASE64Encoder
import sun.misc.CharacterEncoder
/**
 *
 * @author angel
 */
class PasswordCodec {
	static encode = { str ->
		MessageDigest md = MessageDigest.getInstance('SHA-512')
		md.update(str.getBytes('UTF-8'))
		return (new BASE64Encoder()).encode(md.digest())
	}
}

