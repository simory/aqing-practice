package aqing.lambda.src.aqing.signature.encryption.asymmetric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

/**  
 * @Description: RSA加密解密
 * @author fuqi@meizu.com 
 * @date 2018年3月29日 下午8:12:01 
 * @version V1.0   
 */
public class RSAEncryption {

	/**
	 * @Description: 公钥加密
	 * @param content
	 * @param publicKey
	 * @return 
	 * @author fuqi@meizu.com
	 * @date 2018年4月25日 下午12:46:55
	 */
	public static byte[] publicEncryption(byte[] content, PublicKey publicKey){
		try{
			Cipher cipher = Cipher.getInstance(RSAEncryptionGenerator.ASYMMETRIC_RSA);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] bytes = cipher.doFinal(content);
			return bytes;
		}catch(NoSuchPaddingException e){

		}catch(InvalidKeyException e){

		}catch(BadPaddingException e){

		} catch (NoSuchAlgorithmException e) {
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description: 私钥解密
	 * @param content
	 * @param privateKey
	 * @return
	 * @author fuqi@meizu.com
	 * @date 2018年4月25日 下午12:46:38
	 */
	public static byte[] privateDecryption(byte[] content, PrivateKey privateKey){
		try {
			Cipher cipher = Cipher.getInstance(RSAEncryptionGenerator.ASYMMETRIC_RSA);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] bytes = cipher.doFinal(content);
			return bytes;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description: 私钥加密
	 * @param content
	 * @param privateKey
	 * @return
	 * @author fuqi@meizu.com
	 * @date 2018年4月25日 下午12:47:07
	 */
	public static byte[] privateEncryption(byte[] content, PrivateKey privateKey){
		try{
			Cipher cipher = Cipher.getInstance(RSAEncryptionGenerator.ASYMMETRIC_RSA);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] bytes = cipher.doFinal(content);
			return bytes;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] publicDecryption(byte[] content, PublicKey publicKey){
		try{
			Cipher cipher = Cipher.getInstance(RSAEncryptionGenerator.ASYMMETRIC_RSA);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] bytes = cipher.doFinal(content);
			return bytes;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
		protected Long initialValue(){
			return System.currentTimeMillis();
		}
	};

	private static final void begin(){
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}

	private static final Long end(){
		return System.currentTimeMillis()-TIME_THREADLOCAL.get();
	}

	public static void main(String[] args) throws Exception {
		String passwordString = "I say I am the king of Galactic";
		RSAEncryption.begin();
		KeyPair keyPair = RSAEncryptionGenerator.getKeyPair();
		System.out.println("生成keyPair:"+ RSAEncryption.end());
		//公钥加密,私钥解密
		RSAEncryption.begin();
		PublicKey publicKey = RSAEncryptionToKey.string2PublicKey(RSAEncryptionGenerator.getPublicKey(keyPair));
		System.out.println("获取公钥:"+ RSAEncryption.end());
		RSAEncryption.begin();
		byte[] publicString = publicEncryption(passwordString.getBytes(), publicKey);
		System.out.println("公钥加密密码:"+ RSAEncryption.end());
		RSAEncryption.begin();
		System.out.println(new String(publicString));
		System.out.println("公钥密数据转字符串:"+ RSAEncryption.end());
		RSAEncryption.begin();
		PrivateKey privateKey = RSAEncryptionToKey.string2PrivateKey(RSAEncryptionGenerator.getPrivateKey(keyPair));
		System.out.println("获取私钥:"+ RSAEncryption.end());
		RSAEncryption.begin();
		System.out.println(new String(privateDecryption(publicString, privateKey)));
		System.out.println("私钥解密:"+ RSAEncryption.end());
		
		//私钥加密,公钥解密
		PrivateKey privateKey1 = RSAEncryptionToKey.string2PrivateKey(RSAEncryptionGenerator.getPrivateKey(keyPair));
		byte[] privateEncryptionBytes = privateEncryption(passwordString.getBytes(),privateKey1);
		System.out.println(new String(privateEncryptionBytes));
		System.out.println(new String(publicDecryption(privateEncryptionBytes,publicKey)));
	}
}
