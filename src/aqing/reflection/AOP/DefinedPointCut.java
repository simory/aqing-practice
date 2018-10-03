package aqing.reflection.AOP;

import java.lang.annotation.*;

/**
 * @Description:定义切点或者说目标对象：是一个作用连接点
 * @Author: fuqi
 * @Date: 18/10/3 上午11:01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefinedPointCut {

    //使用的参数位置
    int[] keyArgs() default{0, 1, 2};

    //是否使用附加的操作
    boolean addition() default false;
}
