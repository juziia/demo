package com.git.spring.config;

import com.git.spring.service.UserService;
import org.springframework.context.annotation.*;

import javax.annotation.Resources;

@Configuration  // 标识配置类
@ComponentScan("com.git.spring")
@ImportResource("spring-context.xml")
public class AppConfig {

    /*@Bean(destroyMethod = "destroy" , initMethod = "init")
    public UserService userService(){
      return new UserService();
    }*/
}
