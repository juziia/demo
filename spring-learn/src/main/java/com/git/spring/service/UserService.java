package com.git.spring.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Scope("singleton")
public class UserService /*implements ApplicationContextAware*/ implements BeanNameGenerator {

    private ApplicationContext applicationContext;

//   @Autowired
   // @Resource(name = "dao")
//    private Object dao1;


//    @Autowired
//    @Qualifier("dao")
//    private UserDao userDaoImpl2;
    @Autowired
    private UserDao dao1;


    @Lookup
    public UserDao a(){return null;}


    // TODO
   /* public UserService(Object dao2) {
        this.dao1 = (UserDao) dao2;
    }*/
//
    public void service(){
//        dao1.test();
//        ((UserDao)dao).test();
//        UserDao bean = applicationContext.getBean(UserDao.class);
        dao1 = (UserDao) a();
        System.out.println("dao:  "+ this.dao1);
        System.out.println("service"+ this);

        ((UserDao)dao1).test();
    }

    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry beanDefinitionRegistry) {
        beanDefinition.setFactoryBeanName("service001");
        beanDefinitionRegistry.registerBeanDefinition("service001",beanDefinition);
        return "service001";
    }

   /* public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }*/

   /* @Resource
    public void dao(Object obj){
        this.dao1 = (UserDao) obj;
    }*/

/*
    public void setDao(UserDao dao) {
//        this.dao1 = dao;
        this.dao =  dao;
    }*/


/*    public void setdao(UserDao dao) {
//        this.dao1 = dao;
        this.dao1 =  dao;
    }*/
}
