package com.git.mybatis.entity;

import java.util.List;

public class Clazz {

    private int id;
    private String clazzName;

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", clazzName='" + clazzName + '\'' +
                ", students=" + students +
                '}';
    }
}
