package com.git.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserService {

//   @Autowired
   // @Resource(name = "dao")
//    private Object dao1;


//    @Autowired
//    @Qualifier("dao")
//    private UserDao userDaoImpl2;
    private UserDao dao1;

    // TODO
    public UserService(Object dao2) {
        this.dao1 = (UserDao) dao2;
    }
//
    public void service(){
//        dao1.test();
//        ((UserDao)dao).test();
        ((UserDao)dao1).test();
    }

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
