package com.git;

import com.git.dao.TestDao;
import com.git.dao.TestDaoImpl;
import com.git.simulate.InvocationHandler;
import com.git.simulate.Proxy;
import com.git.simulate.ProxyGenerator;
import com.juzi.User;

import java.lang.reflect.Method;

public class ProxyDemo {


    public static void main(String[] args) throws Exception {

        final TestDao testDao = new TestDaoImpl();


        TestDao proxy = (TestDao) Proxy.newProxyInstance(testDao.getClass().getClassLoader(), testDao.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println("===========对方法进行增强=============");
                System.out.println(this.getClass().getName());
                return method.invoke(testDao, args);
            }
        });

        TestDao dao = (TestDao) Proxy.newProxyInstance(testDao.getClass().getClassLoader(), testDao.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(this.getClass().getName());

                System.out.println("=====================================");

                return method.invoke(testDao, args);
            }
        });
//
        User byUser = proxy.findByUser();
//        System.out.println(byUser);

        User byUser1 = dao.findByUser();
//        System.out.println(byUser1);

    }

}
