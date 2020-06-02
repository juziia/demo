package com.git.mybatis.mapper;

import com.git.mybatis.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {

    List<Student> selectAll();


    List<Student>  selectByClazzId(int clazzId);


    List<Student> findByIdOrClazzId(@Param("id") long id,@Param("clazzId") int clazzId);


    List<Student> findByIds(@Param("ids") List<Long> ids);


    int addStudents(@Param("students") List<Student> students);

}
