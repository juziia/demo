package com.git.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;



@Service
public class UserService {


    private UserDao dao1;


    @Lookup
    public UserDao a() {
        return null;
    }


    public void service() {

        dao1 = (UserDao) a();
        System.out.println("dao:  " + this.dao1);
        System.out.println("service" + this);

        ((UserDao) dao1).test();
    }

}
