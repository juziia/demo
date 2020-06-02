package com.git.mybatis.mapper;

import com.git.mybatis.entity.Clazz;
import com.git.mybatis.entity.Student;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StudentMapperTest {

    @Test
    public void testSelectAll() throws IOException {

        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 创建会话工厂
        SqlSessionFactory sessionFactory = sessionFactoryBuilder.
                build(Resources.getResourceAsStream("mybatis-config.xml"));
        // 创建会话对象
        SqlSession session = sessionFactory.openSession();
        // 获取Mapper对象
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.selectAll();

        students.forEach(System.out::println);
    }


    @Test
    public void testClazzMapper() throws IOException {
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = sessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));

        SqlSession sqlSession = sessionFactory.openSession();

        ClazzMapper clazzMapper = sqlSession.getMapper(ClazzMapper.class);

        List<Clazz> clazzList = clazzMapper.findAll();
        clazzList.forEach(obj -> System.out.println(obj));
    }


    @Test
    public void testStudentMapper() throws IOException {
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 创建会话工厂
        SqlSessionFactory sessionFactory = sessionFactoryBuilder.
                build(Resources.getResourceAsStream("mybatis-config.xml"));
        // 创建会话对象
        SqlSession session = sessionFactory.openSession();
        // 获取Mapper对象
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);

//        List<Student> students = studentMapper.findByIdOrClazzId(1001, 1);
        List<Student> students = studentMapper.findByIds(Arrays.asList(1001l, 1002l, 1003l));
        System.out.println(students);
    }


    /**
     *  批量插入
     * @throws IOException
     */
    @Test
    public void testStudentMapper2() throws IOException {
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 创建会话工厂
        SqlSessionFactory sessionFactory = sessionFactoryBuilder.
                build(Resources.getResourceAsStream("mybatis-config.xml"));
        // 创建会话对象
        SqlSession session = sessionFactory.openSession(true);
        // 获取Mapper对象
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);

        List<Student> list = new ArrayList<>();
        list.add(new Student(1008l,"大马哈",20,"男",1));
        list.add(new Student(1009l,"马儿扎哈",22,"男",2));
        list.add(new Student(1010l,"玛雅哈哈",20,"女",1));
        list.add(new Student(1011l,"小王",17,"男",2));
        list.add(new Student(1012l,"小乔",20,"男",1));

        studentMapper.addStudents(list);
        session.commit();
        StringUtils.join();

        ;
    }

}
