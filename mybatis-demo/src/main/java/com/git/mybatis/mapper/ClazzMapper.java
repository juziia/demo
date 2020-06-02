package com.git.mybatis.mapper;

import com.git.mybatis.entity.Clazz;

import java.util.List;

public interface ClazzMapper {

    Clazz findById(int id);

    List<Clazz> findAll();
}
