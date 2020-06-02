package com.git.spring.test;

import com.git.spring.config.SpringConfig;
import com.git.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigApplicationContextTest {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(SpringConfig.class);

        UserService userService = (UserService) context.getBean("userService");

        userService.service();
    }
}
