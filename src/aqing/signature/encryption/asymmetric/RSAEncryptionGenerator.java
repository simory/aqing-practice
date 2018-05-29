package aqing.signature.encryption.asymmetric;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import aqing.signature.encryption.base64.Base64Encryption;

/**  
 * @Description: RSA非对称加密
 * @author fuqi@meizu.com 
 * @date 2018年3月29日 下午6:39:52 
 * @version V1.0   
 */
public class RSAEncryptionGenerator {

	public static final String ASYMMETRIC_RSA = "RSA";
	
	//
	public static KeyPair getKeyPair() throws NoSuchAlgorithmException{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ASYMMETRIC_RSA);
		keyPairGenerator.initialize(512);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}
	
	public static String getPublicKey(KeyPair keyPair){
		PublicKey publicKey = keyPair.getPublic();
		return Base64Encryption.byte2Base64(publicKey.getEncoded());
	}
	
	public static String getPrivateKey(KeyPair keyPair){
		PrivateKey privateKey = keyPair.getPrivate();
		return Base64Encryption.byte2Base64(privateKey.getEncoded());
	}
	
	public static void main(String[] args) throws Exception {
		
		String testBase64 = "i say the king of Galactic";
		byte[] bytes = testBase64.getBytes(); 
		System.out.println(bytes);
		String decodes = Base64Encryption.byte2Base64(bytes);
		System.out.println(decodes);
		byte[] decodeBytes = Base64Encryption.base642Byte(decodes);
		System.out.println(new String(decodeBytes));
//		for()
//		KeyPair keyPair = getKeyPair();
//		System.out.println(getPublicKey(keyPair));
//		System.out.println("99999");
//		System.out.println(getPrivateKey(keyPair));
//		System.out.println("99999");
	}
}
