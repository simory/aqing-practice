package aqing.classLoader;

/**  
 * @Description: 
 * @author fuqi@meizu.com 
 * @date 2018年5月11日 下午1:00:12 
 * @version V1.0   
 */
public class ClassLoaderTest {

	
	public static void main(String[] args) throws ClassNotFoundException{
//		ClassLoader
		Class.forName("ClassLoaderTest");//默认进行初始化
//		Class<?> loadClass = ClassLoader.loadClass("ClassLoaderTest");//默认不会进行link
		//类生命周期：加载-->连接->验证->准备->解析-->初始化-->使用-->卸载
		System.out.println(2 & (10-1));
		System.out.println(2 | (10-1));
		System.out.println(2 ^ (10-1));
	}
}
