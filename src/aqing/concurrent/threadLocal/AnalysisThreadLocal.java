package aqing.concurrent.threadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**  
 * @Description: 描述
 * @author fuqi@meizu.com 
 * @date 2018年4月25日 下午12:38:06 
 * @version V1.0   
 */
public class AnalysisThreadLocal {

	private static ThreadLocal<Map<String, Object>> THREAD_LOCAL_MAP = new ThreadLocal<Map<String, Object>>(){
		public Map<String, Object> initialValue1(){
			return new HashMap<String, Object>();
		}
		public void testIf(){}
	};
	public static void main(String[] args) {
		AtomicInteger nextHashCode = new AtomicInteger();
		int HASH_INCREMENT = 0x61c88647;
		System.out.println(nextHashCode.get());
		System.out.println(HASH_INCREMENT);
		nextHashCode.getAndAdd(HASH_INCREMENT);
		System.out.println(nextHashCode.get());
	}
}
