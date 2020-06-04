package com.git.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


/**
 *  切面的作用: 用于定义将通知应用到哪些连接点上,连接点就是满足切入点定义规则的一个方法
 */
@Aspect     // 声明当前类是一个切面类          切面中包含 切入点以及通知
@Component
public class AopAspect {

    /**
     * 切入点: 切入点就是对连接点的一组定义规则
     * 连接点: 在Spring AOP中连接点就是满足切入点定义规则的方法
     */


    // 定义切入点

    /**
     *  execution: 可以根据方法的访问权限修饰符,返回类型,参数个数以及所在包名,类名匹配连接点
     *  within: 根据包名或者类名匹配连接点
     *  args: 根据方法参数类型匹配连接点,与连接点的包名,类名无关
     *  this: 根据代理后的类型匹配连接点,与连接点的包名,类名无关
     *  target: 指定目标类也就是代理类的类型
     *  @annotation: 根据的方法上的指定的一个或多个注解匹配连接点,与连接点的包名,类名无关
     *  @args: 根据指定方法参数类型的类上的一个或多个注解来匹配连接点,与连接点的包名,类名无关
     *  @within:
     *  @annotation:
     *
     *
     */
//    @Pointcut("execution (public String com.git.spring.service.TestService.*(..))")
//    @Pointcut("within(com.git.spring.service.*)")
//    @Pointcut("args(java.lang.String,int,..)")
//    @Pointcut("@annotation(com.git.spring.aop.annotation.Juzi)")
//    @Pointcut("@args(com.git.spring.aop.annotation.Juzi,..)")
    //@Pointcut("this(com.git.spring.dao.TestDao)")   // 在jdk动态代理时,this必须为目标类(被代理类)的接口类型,cglib则是目标类类型

//    @Pointcut("target(com.git.spring.dao.TestDao)")
//    @Pointcut("@within(com.git.spring.aop.annotation.Juzi)")
//    @Pointcut("@annotation(com.*.spring.aop.annotation.Juzi)")
    @Pointcut("execution(* a*(..))")
    public void pointcut(){}


    /** 通知: 就是定义了在连接前后所需要执行的代码,
     *     对通知作用在连接点的位置分为4种类型,分别为前置通知,后置通知,最终通知和异常通知
            前置通知: 顾名思义,在连接点执行之前执行,前置通知无法阻止连接点的正常执行,除非前置通知抛出异常
            后置通知: 在连接点执行之后执行,当连接点抛出异常,将不会执行
            异常通知: 当前置通知,连接点或者后置通知抛出异常时才会执行,否则不会执行
            最终通知: 无论前置通知,连接点和异常通知是否正常执行,都会执行最终通知

            当然还有一种通知类型为环绕通知,在我看来它只是将前置通知,连接点,后置通知,异常通知,最终通知组合在了一起,
         不再需要额外的定义其他的通知,当然,如果定义了其他通知,那么环绕通知中的代码逻辑会优先于其他通知执行

            环绕通知: 围绕连接点执行,这也是最有用的切面方式
     */
    @Before("pointcut()")
    public void beforeAdvice(JoinPoint joinPoint){
        System.out.println("前置通知");
    }

    @AfterThrowing("pointcut()")
    public void throwingAdvice(){
        System.out.println("抛出了一个异常1111");
    }

    @AfterReturning("pointcut()")
    public void afterReturningAdvice(){
        System.out.println("最终通知111");
    }

    @After("pointcut()")
    public void afterAdvice(){
//        int i = 1 /0;
        System.out.println("后置通知111");
    }


    @Pointcut("execution(public * com.git.spring.*.*.*(..))")
    public void pointcutExecution(){

    }

    @Around("pointcutExecution()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        // 代理对象  proceedingJoinPoint.getThis()
        System.out.println(proceedingJoinPoint.getThis().getClass().getName());
        // 目标对象 proceedingJoinPoint.getTarget()
        System.out.println(proceedingJoinPoint.getTarget().getClass().getName());

        System.out.println("前置通知2222");

        try {
            System.out.println(proceedingJoinPoint.getThis());
            proceedingJoinPoint.proceed();
            System.out.println("后置通知2222");
        } catch (Throwable throwable) {
            System.out.println("抛出了无数个异常222");
            throwable.printStackTrace();
        }
        System.out.println("最终通知2222");
    }

}
