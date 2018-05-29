package aqing.lambda.src.aqing.concurrent.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**  
 * @Description: 读源码时测一测ArrayBlockingQueue
 * @author fuqi@meizu.com 
 * @date 2018年5月17日 下午8:08:18 
 * @version V1.0   
 */
public class TestArrayBlockingQueue {

	private static void testConstratorIllegal(){
		List<String> aList = new ArrayList<String>();
		aList.add("dad");
		aList.add("mom");
		aList.add("bro");
		aList.add("me");
		aList.add("little bro.");
		//当capacity小于aList的length时抛出异常:因为初始化队列中的数组items长度是3,而会循环把5个aList赋值到items中,这样到超过3时数组越界异常
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3, true, aList);
	}
	
	public static void testAdd(){
		List<String> aList = new ArrayList<String>();
		aList.add("dad");
		aList.add("mom");
		aList.add("bro");
		aList.add("me");
		aList.add("little bro.");
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(5, true, aList);
		queue.add("baby sister");
	}
	public static void main(String[] args) {
//		testConstratorIllegal();
		testAdd();
	}
	
}
