package com.git.spring;

import com.git.spring.aop.AopAspect;
import com.git.spring.dao.TestDao;
import com.git.spring.dao.impl.TestDaoImpl;
import com.git.spring.config.AppConfig;
import com.git.spring.service.TestService;
import com.git.spring.service.test.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        // 创建ioc运行上下文对象,用于初始化容器,将作用域为singleton的bean进行实例化,添加到容器中
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(AppConfig.class);

        // 获取对象
        TestService testService = (TestService) context.getBean("testService");
        System.out.println(testService);
        testService.test();
        System.out.println("===================");
        testService.testString("",1);
        System.out.println("===================");
        testService.testString(new User());

        System.out.println("====================");
        //java.lang.ClassCastException: com.sun.proxy.$Proxy22 cannot be cast to com.git.spring.dao.impl.TestDaoImpl
        // ioc容器会在bean的初始化就会实现动态代理,类型转换错误的原因是因为Spring AOP采用的是JDK Proxy动态代理
        // 使用JDK Proxy动态代理的前提,是被代理类必须最少需要实现了一个接口,这是由于JDK Proxy动态代理的底层原理所决定的
        // 使用JDK Proxy动态代理,它会使用反射以及字节码重组技术为目标类动态生成一个新的代理类,该代理类会继承了
        // java.lang.reflect.Proxy,并且会实现了我们传入的目标类的接口数组,所以代理类的类型会是Proxy类型和
        // 传入接口数组中的所有接口的类型,唯独不会是目标类类型,代理类与目标类是一个兄弟的关系
        TestDao testDaoImpl = (TestDao) context.getBean("test");



        testDaoImpl.test();
        System.out.println("==================================");

        AopAspect aopAspect = (AopAspect) context.getBean("aopAspect");
        AopAspect aopAspect1 = (AopAspect) context.getBean("aopAspect");
        AopAspect aopAspect2 = (AopAspect) context.getBean("aopAspect");

        System.out.println(aopAspect);
        System.out.println(aopAspect1);
        System.out.println(aopAspect2);

    }

}
