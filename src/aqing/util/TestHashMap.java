package aqing.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 测试HashMap
 * @author fuqi@meizu.com
 * @date 2018年5月2日 下午12:09:30
 * @version V1.0
 */
public class TestHashMap {

	public static void main(String[] args) {
		System.out.print("CPU个数:");//Runtime.getRuntime()获取当前运行时的实例
		System.out.println(Runtime.getRuntime().availableProcessors());//availableProcessors()获取当前电脑CPU数量
		System.out.print("虚拟机内存总量:");
		System.out.println(Runtime.getRuntime().totalMemory());//totalMemory()获取java虚拟机中的内存总量
		System.out.print("虚拟机空闲内存量:");
		System.out.println(Runtime.getRuntime().freeMemory());//freeMemory()获取java虚拟机中的空闲内存量
		System.out.print("虚拟机使用最大内存量:");
		System.out.println(Runtime.getRuntime().maxMemory());//maxMemory()获取java虚拟机试图使用的最大内存量

		final Map<String, Object> map = new HashMap<String, Object>(4);
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for(int i = 0; i<1000;i++){
			executor.execute(new Thread(){
				public void run(){
					map.put(Math.random()*10+"", "hello world");
				}
			});
		}
		executor.shutdown();


		for(int i =0;i<30;i++){
			System.out.println(i+"==="+indexFor(hash(i+""),4));
		}
		System.out.println(indexFor(hash("5"),4));
		System.out.println(indexFor(hash("9"),4));
		map.put("0", "");
		map.put("1", "");
		map.put("2", "");
		map.put("3", "");
		Set<Entry<String, Object>> smap = map.entrySet();
		Iterator<Entry<String, Object>> imap = smap.iterator();
		while(imap.hasNext()){
			Entry<String, Object> entry = imap.next();
			System.out.println(entry.getKey()+"==="+entry.getValue()+"===");
		}
	}



	public static int hash(Object k){
		int h = 0;
        if (k instanceof String) {
                return sun.misc.Hashing.stringHash32((String) k);
        }
        h = 0;//hashSeed;

        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
	}

	 static int indexFor(int h, int length) {
	        return h & (length-1);
	}
}
