package aqing.concurrent.threadLocal;

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
	
//	private AtomicBoolean test = new AtomicBoolean(false);
	public static void main(String[] args){
//		int length = (2 << 2)-1;
//		System.out.println(2 << 2);
//		System.out.println(2 < 3);
//		for(int i =0 ; i<36;i++){
//			System.out.println(i+"&"+length+"="+(i&length));
//		}///(++i == items.length) ? 0 : i
//		System.out.println(9&8);
		TestYield test = new TestThread().new TestYield();
		Thread tt = new Thread(test);
		tt.start();
		for(int k=0;k<5;k++){
			test.addA() ;
			System.out.println(" Main Thread Add a: " +test.a);
		}
	}

	class TestYield implements Runnable{
		public int a =0;
		@Override
		public void run() {
//			for(;;){
			synchronized(this) {
                while (a < 4) {
                    System.out.println("a<4" + Thread.currentThread().getName());
                    //Thread.currentThread().yield();
                    try {
					    Thread.sleep(1000);//不会释放锁
//                      this.wait(1000);
//					    Thread.currentThread().wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("a>4" + Thread.currentThread().getName());
            }
		}
		public synchronized void addA(){
			a++ ;
		}
	}
}
