package aqing.reflection.jdk.proxy.cglibProxy;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/10/6 下午1:21
 */
public class HelloSeriveCglib implements MethodInterceptor {
    private Object target;

    public Object getInstance(Object target){
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
//        enhancer.setCallback(this);//回调方法
        //创建代理对象
        return enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable{
        System.err.println("#########我是CGLIB的动态代理##########");
        System.err.println("我准备说hello");
        Object returnObj= proxy.invokeSuper(obj, args);
        System.err.println("我说过hello了");
        return returnObj;

    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }
}
