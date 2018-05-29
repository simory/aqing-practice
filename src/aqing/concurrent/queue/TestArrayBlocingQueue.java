package aqing.concurrent.queue;

/**  
 * @Description: 
 * @author fuqi@meizu.com 
 * @date 2018年5月29日 下午4:33:19 
 * @version V1.0   
 */
public class TestArrayBlocingQueue {

	private static ThreadLocal<Long> PARAMETER = new ThreadLocal<Long>(){
	};

	public static void begin(){
		PARAMETER.set(System.currentTimeMillis());
	}

	public static long end(){
		return System.currentTimeMillis()-PARAMETER.get();
	}
	static int length = (2 << 2)-1;
	public static void main(String[] args){
		int i =0;
		while(i <100){
			test();
			i++;
			System.out.println("------");
		}

	}

	private static void test(){
		int j = 0;
		begin();
		for (int i = 0;i<1000000000;i++){
			j= i & length;
		}
		System.out.println(end());
		begin();
		for (int i = 0;i<1000000000;i++){
			j=(i++ == length) ? 0 : i;
		}
		System.out.println(end());
	}
}
