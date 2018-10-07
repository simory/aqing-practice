package aqing.reflection.jdk.proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/10/6 下午12:32
 */
public class HelloServiceProxy implements InvocationHandler {

    private Object target;

    //绑定委托对象并返回一个代理类
    public Object bind(Object target){
        this.target = target;
        //jdk代理需要提供的接口,参数分别是类加载器，代理接口挂哪个接口下，this代表当前helloService是使用HelloService的代理方法作为代理执行者
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    /**
     *
     * @param proxy--代理对象
     * @param method--被调用对象
     * @param args---方法参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        System.err.println("######我是jdk动态代理########");
        Object result = null;
        //反射方法钱调用
        System.err.println("我准备说hello.");
        result = method.invoke(target, args);
        System.err.println("我说过hello了");
        return result;
    }
}
