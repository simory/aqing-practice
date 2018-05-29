package aqing.lambda.src.aqing.signature.encryption.asymmetric;

import aqing.lambda.src.aqing.signature.encryption.base64.Base64Encryption;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**  
 * @Description: 字符串转公钥,秘钥
 * @author fuqi@meizu.com 
 * @date 2018年3月29日 下午7:59:56 
 * @version V1.0   
 */
public class RSAEncryptionToKey {

	/**
	 * @Description: 字符串转公钥
	 * @param pubStr
	 * @return
	 * @throws Exception 
	 * @author fuqi@meizu.com
	 * @date 2018年3月29日 下午8:10:55
	 */
	public static PublicKey string2PublicKey(String pubStr) throws Exception{
		byte[] keyBytes = Base64Encryption.base642Byte(pubStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSAEncryptionGenerator.ASYMMETRIC_RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	
	/**
	 * @Description: 字符串转私钥
	 * @param privateStr
	 * @return 
	 * @author fuqi@meizu.com
	 * @date 2018年3月29日 下午8:11:11
	 */
	public static PrivateKey string2PrivateKey(String privateStr){
		try {
			byte[] keyBytes = Base64Encryption.base642Byte(privateStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(RSAEncryptionGenerator.ASYMMETRIC_RSA);
			PrivateKey privateKey  = keyFactory.generatePrivate(keySpec);
			return privateKey;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
}
