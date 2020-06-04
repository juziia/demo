package com.git.spring.aop;

import com.git.spring.dao.TestDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class XmlAopAspect {


    public void beforeAdvice(JoinPoint joinPoint){
        System.out.println("前置通知");
    }


    public void afterAdvice(JoinPoint joinPoint){
        System.out.println("后置通知");
    }

    public void afterReturning(JoinPoint joinPoint){
        System.out.println("最终通知");
    }

    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("异常通知");
    }


    /**
     *  ProceedingJoinPoint: 它是JoinPoint的子接口,它扩展了proceed()方法,用于执行连接点
     *  JoinPoint: JoinPoint它能够获取连接点的相关参数,包括原生目标对象,代理对象和方法的相关参数
     * @param proceedingJoinPoint:
     */
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("=============环绕通知==========");

        beforeAdvice(proceedingJoinPoint);
        try {
            // 执行连接点
            proceedingJoinPoint.proceed();

            afterAdvice(proceedingJoinPoint);
        } catch (Throwable throwable) {
            afterThrowing(proceedingJoinPoint);
            throwable.printStackTrace();
        }
        afterReturning(proceedingJoinPoint);

    }

}
