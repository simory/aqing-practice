package aqing.lambda.src.aqing.signature.encryption.symmetric;

import aqing.lambda.src.aqing.signature.encryption.base64.Base64Encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**  
 * @Description: DES加密
 * @author fuqi@meizu.com 
 * @date 2018年3月29日 下午8:47:33 
 * @version V1.0   
 */
public class DESEncryptionGenerator {

	public static final String SYMMETRIC_DES = "DES";
	
	public static String genKeyDES(){
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(SYMMETRIC_DES);
			keyGen.init(56);
			SecretKey secretKey = keyGen.generateKey();
			return secretKey2String(secretKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String secretKey2String(SecretKey secretKey){
		return Base64Encryption.byte2Base64(secretKey.getEncoded());
	}
	
	public static SecretKey string2SecretKey(String key) throws IOException{
		byte[] bytes = Base64Encryption.base642Byte(key);
		return new SecretKeySpec(bytes,SYMMETRIC_DES);
	}
	
}
