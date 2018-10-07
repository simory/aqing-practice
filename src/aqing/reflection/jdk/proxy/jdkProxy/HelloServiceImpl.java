package aqing.reflection.jdk.proxy.jdkProxy;

/**
 * @Description: 被代理实现类
 * @Author: fuqi
 * @Date: 18/10/6 下午12:29
 */
public class HelloServiceImpl implements HelloService {

    public void sayHello(String name) {
        System.err.println("hello"+name);
    }
}
