package aqing.concurrent.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description
 * @author 付琪
 * @date上午3:04:23
 * 
 */
public class TestAsyncAppender {

	public static void main(String[] args) {
		final AsyncAppender appender = new AsyncAppender(1024);
		appender.append("lalalalallalallalalalalala");

		ExecutorService excutor = Executors.newFixedThreadPool(5);

		for(int i = 0; i<1024;i++){
			final int j = i;
			//生产
            Future<?> submit = excutor.submit(new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					appender.append(Thread.currentThread().getName()+"-good night me-"+j);
				}
			}));
        }
        final TissotAppender tissotAppender = new TissotAppender();
        for(int i = 0; i<1024;i++){
            //消费
			excutor.submit(new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					appender.start(tissotAppender,Thread.currentThread().getName(),false);
				}
			}));
        }
	}




}
