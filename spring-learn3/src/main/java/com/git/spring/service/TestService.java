package com.git.spring.service;

import com.git.spring.aop.annotation.Juzi;
import com.git.spring.service.test.User;
import org.springframework.stereotype.Service;

@Service
@Juzi
public class TestService {

    @Juzi
    public void test(){
        System.out.println("执行了一个测试方法111....");
//        int i = 1 /0;
    }



    public String testString(User user){
        System.out.println("执行了一个测试方法222....");

        return "";
    }



    public String testString(String a,int b){
        System.out.println("执行了一个测试方法222....");

        return "";
    }


}
