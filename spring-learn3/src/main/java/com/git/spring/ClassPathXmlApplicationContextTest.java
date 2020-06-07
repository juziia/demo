package com.git.spring;

import com.git.spring.dao.TestDao;
import com.git.spring.dao.impl.TestDaoImpl;
import com.git.spring.service.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClassPathXmlApplicationContextTest {

    // 测试使用xml配置aop的切面
    public static void main(String[] args) throws IOException {

//        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy", new Class[]{TestDao.class});
//        FileOutputStream out = new FileOutputStream("D://TestDao$Proxy.class");
//
//        out.write(bytes);
//        out.flush();
//        out.close();
        final TestDao testDao1 = new TestDaoImpl();

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Proxy.newProxyInstance(TestDaoImpl.class.getClassLoader(), new Class[]{TestDao.class}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return method.invoke(testDao1,args);
            }
        });



        /*ClassPathXmlApplicationContext context
                = new ClassPathXmlApplicationContext("spring-context.xml");

        // 获取TestService
        TestService testService = (TestService) context.getBean("testService");

        testService.test();

        System.out.println("=============================");

        TestDao testDao = (TestDao) context.getBean("testDaoImpl");
        testDao.test();*/

    }

}
