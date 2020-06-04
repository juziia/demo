package com.git.spring.dao.impl;

import com.git.spring.dao.TestDao;
import org.springframework.stereotype.Service;

@Service("test")
public class TestDaoImpl implements TestDao {

    public void test() {
        System.out.println("dao层测试方法");
    }
}
