package aqing.lambda.src.aqing.concurrent.queue;

import java.util.concurrent.atomic.AtomicInteger;

/**  
 * @Description: 描述
 * @author fuqi@meizu.com 
 * @date 2018年5月17日 下午1:49:26 
 * @version V1.0   
 */
public class TestBuffer {
	public static AtomicInteger index = new AtomicInteger(0);  
    
    public static void main(String[] args){  
          
        int tCount = 10; // thread count  
        int length = 0;  // buffer length -> 2^16  
          
        final RingBuffer<Integer> buffer = new RingBuffer<Integer>(Integer.class, length);
        // provider  
        Runnable pr = new Runnable(){  
            @Override  
            public void run() {  
                while(true){  
                    try {  
                        Thread.sleep(100);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                    int tindex = index.getAndIncrement();  
                    buffer.enQueue(tindex);  
                    System.out.println("buffer enQueue: " + tindex);  
                    try {  
                        Thread.sleep(100);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        };  
        // consumer  
        Runnable cr = new Runnable(){  
            @Override  
            public void run() {  
                while(true){  
                    try {  
                        Thread.sleep(100);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                    Integer cindex = buffer.deQueue();  
                    System.out.println("buffer deQueue: " + cindex);  
                    try {  
                        Thread.sleep(100);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        };  
          
//        for(int i=0; i<tCount; i++){  
//            new Thread(cr).start();  
//        }  
          
        for(int i=0; i<tCount; i++){  
            new Thread(pr).start();  
        }  
    }  
}
