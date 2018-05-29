package aqing.signature.encryption.symmetric;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import aqing.signature.encryption.asymmetric.RSAEncryption;

/**  
 * @Description:des加解密
 * @author fuqi@meizu.com 
 * @date 2018年3月29日 下午8:53:11 
 * @version V1.0   
 */
public class DESEncryption {

	public static byte[] encryptDES(byte[] source, SecretKey key){
		try {
			Cipher cipher = Cipher.getInstance(DESEncryptionGenerator.SYMMETRIC_DES);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] bytes = cipher.doFinal(source);
			return bytes;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] decryptDES(byte[] source, SecretKey key){
		try {
			Cipher cipher = Cipher.getInstance(DESEncryptionGenerator.SYMMETRIC_DES);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] bytes = cipher.doFinal(source);
			return bytes;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static final ThreadLocal<Long> PARAMETER_THREADLOCAL = new ThreadLocal<Long>(){
		protected Long initValue(){
			return System.currentTimeMillis();
		}
	};
	
	private static final void begin(){
		PARAMETER_THREADLOCAL.set(System.currentTimeMillis());
	}
	
	private static final Long end(){
		return System.currentTimeMillis()-PARAMETER_THREADLOCAL.get();
	}
	public static void main(String[] args) throws IOException {
		String passwordStr = "I say I am the king of Galatic";
		DESEncryption.begin();
		String stringKey = DESEncryptionGenerator.genKeyDES();
		System.out.println(DESEncryption.end());
		DESEncryption.begin();
		SecretKey key = DESEncryptionGenerator.string2SecretKey(stringKey);
		System.out.println(DESEncryption.end());
		DESEncryption.begin();
		byte[] encrytStr = encryptDES(passwordStr.getBytes(),key);
		System.out.println(DESEncryption.end());
		DESEncryption.begin();
		System.out.println(new String(encrytStr));
		System.out.println(new String(decryptDES(encrytStr,key)));
		//
		System.out.println("2222");
		byte[] debytes = decryptDES(passwordStr.getBytes(),key);
		System.out.println(new String(encryptDES(debytes,key)));
	}
	
	
}
