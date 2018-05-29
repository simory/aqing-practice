package aqing.concurrent.queue;

/**
 * @Description: 描述
 * @author fuqi@meizu.com
 * @date 2018年5月17日 下午1:46:16
 * @version V1.0
 */
public class TestAsyncAppender {
	
	public static void main(String[] args) {
		AsyncAppender appender = new AsyncAppender(512);
		appender.append("lalalalallalallalalalalala");
	}
}
