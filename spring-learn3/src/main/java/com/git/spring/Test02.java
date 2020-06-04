package com.git.spring;

import com.git.spring.config.AppConfig;
import com.git.spring.dao.TestDao;
import com.git.spring.dao.impl.UserDao;
import org.joda.time.DateTime;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test02 {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext
                context = new AnnotationConfigApplicationContext(AppConfig.class);

        TestDao testDao = (TestDao) context.getBean("userDao");

        testDao.test();
    }
}
