package com.git.spring.service;


import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    public void test() {
        System.out.println("调用了dao层方法....1111111111111");
    }

}
