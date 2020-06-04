package com.git.spring;

import com.git.spring.dao.TestDao;
import com.git.spring.service.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplicationContextTest {

    // 测试使用xml配置aop的切面
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context
                = new ClassPathXmlApplicationContext("spring-context.xml");

        // 获取TestService
        TestService testService = (TestService) context.getBean("testService");

        testService.test();

        System.out.println("=============================");

        TestDao testDao = (TestDao) context.getBean("testDaoImpl");
        testDao.test();

    }

}
