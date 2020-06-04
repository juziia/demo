package com.git.spring.service;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class UserDaoImpl implements UserDao {

    public void test() {
        System.out.println("调用了dao层方法....1111111111111");
    }

}
