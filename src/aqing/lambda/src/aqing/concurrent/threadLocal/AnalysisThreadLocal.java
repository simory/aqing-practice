package aqing.lambda.src.aqing.concurrent.threadLocal;

import java.util.concurrent.atomic.AtomicInteger;

/**  
 * @Description: 描述
 * @author fuqi@meizu.com 
 * @date 2018年4月25日 下午12:38:06 
 * @version V1.0   
 */
public class AnalysisThreadLocal {

	public static void main(String[] args) {
		AtomicInteger nextHashCode = new AtomicInteger();
		int HASH_INCREMENT = 0x61c88647;
		System.out.println(nextHashCode.get());
		System.out.println(HASH_INCREMENT);
		nextHashCode.getAndAdd(HASH_INCREMENT);
		System.out.println(nextHashCode.get());
	}
}
