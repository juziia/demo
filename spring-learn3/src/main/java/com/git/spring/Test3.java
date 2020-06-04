package com.git.spring;

import com.git.spring.config.AppConfig;
import com.git.spring.dao.TestDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test3 {

    // 用于测试切面的实例化模型: 切面的实例化模型有两种 1. perthis  2. pertarget
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);

        TestDao tset = (TestDao) context.getBean("test");
        TestDao tset2 = (TestDao) context.getBean("test");
        TestDao tset3 = (TestDao) context.getBean("test");
        tset.test();
        System.out.println(tset);
        System.out.println("===========");
        tset2.test();
        System.out.println(tset2);
        System.out.println("===========");
        tset3.test();
        System.out.println(tset3);
    }

}
