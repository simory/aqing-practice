package aqing.reflection.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:通知和通知器/切面和配置
 * @Author: fuqi
 * @Date: 18/10/3 上午11:12
 */
@Aspect
@Component
public class DefinedAdvisor {

    @Resource
    private DefinedAdvisorHandler definedAdvisorHandler;

    //通知/切面--AOP环绕方法的切面行为
    @Around("@annotation(definedPointCut)")
    private void doAround(ProceedingJoinPoint point, DefinedPointCut definedPointCut) throws Throwable{
        //可以从point中解析出参数
        definedAdvisorHandler.test();
    }
}
