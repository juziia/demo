package com.git.spring.service;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


public class UserDaoImpl2 implements UserDao {

    public void test() {
        System.out.println("调用了dao层方法....2222222222");
    }

}
