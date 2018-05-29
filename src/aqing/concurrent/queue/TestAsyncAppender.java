package aqing.concurrent.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description: 描述
 * @author fuqi@meizu.com
 * @date 2018年5月17日 下午1:46:16
 * @version V1.0
 */
public class TestAsyncAppender {

	public static void main(String[] args) {
		final AsyncAppender appender = new AsyncAppender(1024);
		appender.append("lalalalallalallalalalalala");

		ExecutorService excutor = Executors.newFixedThreadPool(5);

		for(int i = 0; i<1024;i++){
			//生产
            Future<?> submit = excutor.submit(new Thread() {
                appender.append(Thread.currentThread().getName()+"-good night me-"+i);
            });
        }
        final TissotAppender tissotAppender = new TissotAppender();
        for(int i = 0; i<1024;i++){
            //消费
			excutor.submit(excutor.submit(new Thread(){
                appender.start(tissotAppender,Thread.currentThread().getName(),false);
            }));
        }
	}


}
