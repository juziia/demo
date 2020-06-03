package com.git.spring.test;

import com.git.spring.config.AppConfig;
import com.git.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigApplicationContextTest {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = (UserService) context.getBean("userService");
        UserService userService2 = (UserService) context.getBean("userService");
        UserService userService3 = (UserService) context.getBean("userService");
        UserService userService4 = (UserService) context.getBean("userService");
        System.out.println("=============================================");

        System.out.println(userService);
        System.out.println(userService2);
        System.out.println(userService3);
        System.out.println(userService4);

        System.out.println("=============================================");
        userService.service();
        userService.service();
        userService.service();
        userService.service();

        Object userDaoImpl = context.getBean("userDaoImpl2");
        Object userDaoImpl2 = context.getBean("userDaoImpl2");
        Object userDaoImpl3 = context.getBean("userDaoImpl2");
        Object userDaoImpl4 = context.getBean("userDaoImpl2");


        System.out.println("=============================================");
        System.out.println(userDaoImpl);
        System.out.println(userDaoImpl2);
        System.out.println(userDaoImpl3);
        System.out.println(userDaoImpl4);
    }
}
