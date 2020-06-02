package com.git.spring.test;

import com.git.spring.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) {
        // 创建ClassPathXmlApplicationContext读取配置文件,初始化spring的运行环境
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        // 根据
        UserService service = (UserService) context.getBean("service");

        System.out.println(service);
        service.service();



    }
}
