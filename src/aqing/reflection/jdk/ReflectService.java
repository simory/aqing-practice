package aqing.reflection.jdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description:测试使用jdk的反射
 * @Author: fuqi
 * @Date: 18/10/6 上午11:56
 */
public class ReflectService {

    public void sayHello(String name){
        System.err.println("hello"+name);
    }

    public static void main(String[] args) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,
            IllegalAccessException,IllegalArgumentException,InvocationTargetException{
        Object service = Class.forName(ReflectService.class.getName()).newInstance();
        Method method = service.getClass().getMethod("sayHello",String.class);
        method.invoke(service,"lengtu");
    }
}
