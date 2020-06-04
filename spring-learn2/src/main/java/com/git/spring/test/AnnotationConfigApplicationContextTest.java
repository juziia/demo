package com.git.spring.test;

import com.git.spring.config.AppConfig;
import com.git.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigApplicationContextTest {


    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(AppConfig.class);


        UserService userService4 = (UserService) context.getBean("userService");

        System.out.println(userService4);
//        userService4.service();


        context.destroy();


    }
}
