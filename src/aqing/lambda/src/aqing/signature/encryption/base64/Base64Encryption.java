package aqing.lambda.src.aqing.signature.encryption.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**  
 * @Description: base64编码译码
 * @author fuqi@meizu.com 
 * @date 2018年3月29日 下午8:01:51 
 * @version V1.0   
 */
public class Base64Encryption {

//	Base64
	public static String byte2Base64(byte[] keyEncode){
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(keyEncode);
	}
	
	public static byte[] base642Byte(String keyDecode) throws IOException{
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(keyDecode);
	}
	
	public static void main(String[] args) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		String token = "eyJ2IjozLCJnIjpmYWxzZSwidSI6IjEzMDE1MTg4NTEiLCJ0IjoxNTE2Njg4NzIwODU0LCJzIjoiaGsuaW4iLCJjIjoiMSIsInIiOiJSdWh3Y3ZHUjMzUmszeGlhbGhmNCIsImEiOiI4MjRCRkUxQzMxQjY0NTBBNURGMjFDQUU1NTIzRkNFNSIsImwiOiJCQ0MzMEIyNTJGNTkzODE0RUJDQkU3MDU2M0M5RkY3NSJ9";
		System.out.println(new String(new BASE64Decoder().decodeBuffer(token)));
	}
}
