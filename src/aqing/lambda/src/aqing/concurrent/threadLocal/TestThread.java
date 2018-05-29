package aqing.lambda.src.aqing.concurrent.threadLocal;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**  
 * @Description: 
 * @author fuqi@meizu.com 
 * @date 2018年5月28日 下午11:42:54 
 * @version V1.0   
 */
public class TestThread {

	private ReentrantLock lock = new ReentrantLock(false);
	private Condition condition = lock.newCondition();
	
	private AtomicBoolean test = new AtomicBoolean(false);
	public static void main(String[] args){
		int length = (2 << 16)-1;
		System.out.println(2 << 16);
		System.out.println(2 < 3);
		for(int i =131071 ; i>131051;i--){
			System.out.println(i+"&"+length+"="+(i&length));
		}///(++i == items.length) ? 0 : i
		System.out.println(9&8);
		new Thread(new TestThread().new TestYield()).start();
	}
	
	class TestYield implements Runnable{

		@Override
		public void run() {
			for(;;){
				System.out.println("1");
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("2");
			}
			
		}
		
	}
}
