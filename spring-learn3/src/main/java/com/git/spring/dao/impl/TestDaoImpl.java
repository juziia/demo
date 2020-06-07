package com.git.spring.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.git.spring.dao.TestDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Repository("test")
//@Scope("prototype")
public class TestDaoImpl implements TestDao {

    public void test() {
        System.out.println("dao层测试方法");
    }
}
