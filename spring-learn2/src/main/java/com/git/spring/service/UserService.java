package com.git.spring.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


//@Scope("prototype")
@Service
public class UserService/* implements InitializingBean, DisposableBean */{

    @Autowired
    private UserDao dao1;

    @Autowired
    private UserService userService;


    public UserService() {
        System.out.println("构造方法.....");
    }

    public void service() {

        dao1 = (UserDao)dao1;
        System.out.println("dao:  " + this.dao1);
        System.out.println("service" + this);

        ((UserDao) dao1).test();
    }


/*
    @PostConstruct
    public void init(){
        System.out.println("bean初始化后回调的方法...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("bean销毁后所回调的方法");
    }
*/


    public void init(){
        System.out.println("bean初始化后回调的方法...");
    }

    public void destroy(){
        System.out.println("bean销毁后所回调的方法");
    }




   /* public void afterPropertiesSet() throws Exception {
        System.out.println("初始化之后回调....");
    }

    public void destroy() throws Exception {
        System.out.println("被销毁时调用此方法");
    }
    */
}
