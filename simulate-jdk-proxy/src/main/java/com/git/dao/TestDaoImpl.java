package com.git.dao;

import com.juzi.User;

public class TestDaoImpl implements TestDao {
    @Override
    public void test(TestDao testDao) throws Exception {
        System.out.println("...................");
    }

    @Override
    public Integer test() {

        System.out.println("测试test方法");
        return 1;
    }

    @Override
    public User findByUser() {
        User user = new User("test",1,"dddd");

        return user;
    }

    @Override
    public int testInt(int i) {

        System.out.println("当前对象: "+ this+ "调用了testInt方法, 传入的值是: "+i);
        return i;
    }

  /*  public Integer test() {

        System.out.println("测试====================");

        return 1000;
    }*/

    public Integer testInteger(Integer i){
        System.out.println("当前对象: "+ this+ "调用了testInteger方法, 传入的值是: "+i);

        return i;
    }

}
