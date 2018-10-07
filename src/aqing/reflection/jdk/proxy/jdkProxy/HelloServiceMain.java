package aqing.reflection.jdk.proxy.jdkProxy;

/**
 * @Description:helloService执行
 * @Author: fuqi
 * @Date: 18/10/6 下午1:14
 */
public class HelloServiceMain {

    public static void main(String[] args){
        HelloServiceProxy helloHandler = new HelloServiceProxy();
        HelloService proxy = (HelloService) helloHandler.bind(new HelloServiceImpl());
        proxy.sayHello("lengtu");
    }

}
